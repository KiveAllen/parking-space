package com.allen.part.controller;

import com.allen.part.common.BaseResponse;
import com.allen.part.common.DeleteRequest;
import com.allen.part.common.ErrorCode;
import com.allen.part.common.ResultUtils;
import com.allen.part.exception.BusinessException;
import com.allen.part.exception.ThrowUtils;
import com.allen.part.model.dto.reservation.ReservationListDTO;
import com.allen.part.model.dto.reservation.ReservationQueryRequest;
import com.allen.part.model.entity.ParkingSpace;
import com.allen.part.model.entity.Reservation;
import com.allen.part.model.entity.User;
import com.allen.part.service.ParkingSpaceService;
import com.allen.part.service.ReservationService;
import com.allen.part.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    @Resource
    private UserService userService;

    @Resource
    private ParkingSpaceService parkingSpaceService;

    /**
     * 创建订单
     */
    @PostMapping("/add")
    public BaseResponse<Long> addReservation(@RequestBody Reservation reservation, HttpServletRequest request) {
        //  填充默认值
        User loginUser = userService.getLoginUser(request);
        reservation.setUserId(loginUser.getId());

        // 写入数据库
        boolean result = reservationService.save(reservation);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 更新车位状态
        ParkingSpace parkingSpace = ParkingSpace.builder()
                .id(reservation.getSpaceId())
                .isAvailable(0)
                .build();
        parkingSpaceService.updateById(parkingSpace);

        // 返回新写入的数据 id
        long newReservationId = reservation.getId();
        return ResultUtils.success(newReservationId);
    }

    /**
     * 删除订单
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteReservation(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();

        // 判断是否存在
        Reservation oldReservation = reservationService.getById(id);
        ThrowUtils.throwIf(oldReservation == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = reservationService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新订单
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateReservation(@RequestBody Reservation reservation) {
        // 操作数据库
        boolean result = reservationService.updateById(reservation);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 更新车位状态
        ParkingSpace parkingSpace = ParkingSpace.builder()
                .id(reservation.getSpaceId())
                .isAvailable(1)
                .build();
        parkingSpaceService.updateById(parkingSpace);

        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取订单
     */
    @GetMapping("/get")
    public BaseResponse<Reservation> getReservationById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Reservation reservation = reservationService.getById(id);
        ThrowUtils.throwIf(reservation == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(reservation);
    }

    /**
     * 分页获取订单列表
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<ReservationListDTO>> listReservationByPage(@RequestBody ReservationQueryRequest reservationQueryRequest) {
        long current = reservationQueryRequest.getPageNum();
        long size = reservationQueryRequest.getPageSize();
        // 查询数据库
        Page<Reservation> reservationPage = reservationService.page(new Page<>(current, size),
                new LambdaQueryWrapper<Reservation>()
                        .eq(reservationQueryRequest.getUserId() != null,
                                Reservation::getUserId, reservationQueryRequest.getUserId())
                        .eq(reservationQueryRequest.getOwnerId() != null,
                                Reservation::getOwnerId, reservationQueryRequest.getOwnerId())
                        .eq(reservationQueryRequest.getReservationStatus() != null,
                                Reservation::getReservationStatus, reservationQueryRequest.getReservationStatus())
                        .orderByDesc(Reservation::getCreateTime)
        );

        List<Reservation> records = reservationPage.getRecords();

        if (records.isEmpty()) {
            Page<ReservationListDTO> reservationDTOPage = new Page<>(current, size, 0);
            return ResultUtils.success(reservationDTOPage);
        }
        Page<ReservationListDTO> reservationDTOPage = new Page<>(current, size, reservationPage.getPages());

        List<Integer> userIds = records.stream().map(Reservation::getUserId).toList();
        List<Integer> ownerIds = records.stream().map(Reservation::getOwnerId).toList();
        List<Integer> spaceIds = records.stream().map(Reservation::getSpaceId).toList();

        List<User> users = userService.listByIds(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<User> owners = userService.listByIds(ownerIds);
        Map<Integer, User> ownerMap = owners.stream().collect(Collectors.toMap(User::getId, owner -> owner));
        List<ParkingSpace> parkingSpaces = parkingSpaceService.listByIds(spaceIds);
        Map<Integer, ParkingSpace> parkingSpaceMap = parkingSpaces.stream().collect(Collectors.toMap(ParkingSpace::getId, parkingSpace -> parkingSpace));


        List<ReservationListDTO> reservationListDTOS = records.stream().map(reservation -> {
            ReservationListDTO reservationListDTO = new ReservationListDTO();
            BeanUtils.copyProperties(reservation, reservationListDTO);
            reservationListDTO.setUserName(userMap.get(reservation.getUserId()).getName());
            reservationListDTO.setOwnerName(ownerMap.get(reservation.getOwnerId()).getName());
            reservationListDTO.setParkingSpacePhoto(parkingSpaceMap.get(reservation.getSpaceId()).getParkPhoto());
            return reservationListDTO;
        }).toList();

        reservationDTOPage.setRecords(reservationListDTOS);

        return ResultUtils.success(reservationDTOPage);
    }

}
