package com.allen.part.controller;

import com.allen.part.common.BaseResponse;
import com.allen.part.common.DeleteRequest;
import com.allen.part.common.ErrorCode;
import com.allen.part.common.ResultUtils;
import com.allen.part.exception.BusinessException;
import com.allen.part.exception.ThrowUtils;
import com.allen.part.model.dto.parkingSpace.ParkingSpaceQueryRequest;
import com.allen.part.model.entity.ParkingSpace;
import com.allen.part.model.entity.User;
import com.allen.part.service.ParkingSpaceService;
import com.allen.part.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车位接口
 */
@RestController
@RequestMapping("/parkingSpace")
@Slf4j
public class ParkingSpaceController {

    @Resource
    private ParkingSpaceService parkingSpaceService;

    @Resource
    private UserService userService;


    /**
     * 创建车位
     */
    @PostMapping("/add")
    public BaseResponse<Long> addParkingSpace(@RequestBody ParkingSpace parkingSpace, HttpServletRequest request) {
        ThrowUtils.throwIf(parkingSpace == null, ErrorCode.PARAMS_ERROR);

        //  填充默认值
        User loginUser = userService.getLoginUser(request);
        parkingSpace.setUserId(loginUser.getId());

        // 写入数据库
        boolean result = parkingSpaceService.save(parkingSpace);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 返回新写入的数据 id
        long newParkingSpaceId = parkingSpace.getId();
        return ResultUtils.success(newParkingSpaceId);
    }

    /**
     * 删除车位
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteParkingSpace(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        ParkingSpace oldParkingSpace = parkingSpaceService.getById(id);
        ThrowUtils.throwIf(oldParkingSpace == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = parkingSpaceService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新车位
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateParkingSpace(@RequestBody ParkingSpace parkingSpace) {
        // 操作数据库
        boolean result = parkingSpaceService.updateById(parkingSpace);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取车位
     */
    @GetMapping("/get")
    public BaseResponse<ParkingSpace> getParkingSpaceById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        ParkingSpace service = parkingSpaceService.getById(id);
        return ResultUtils.success(service);
    }

    /**
     * 分页获取车位列表
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<ParkingSpace>> listParkingSpaceByPage(@RequestBody ParkingSpaceQueryRequest parkingSpaceQueryRequest) {
        long current = parkingSpaceQueryRequest.getPageNum();
        long size = parkingSpaceQueryRequest.getPageSize();
        // 查询数据库
        Page<ParkingSpace> parkingSpacePage = parkingSpaceService.page(new Page<>(current, size),
                new LambdaQueryWrapper<ParkingSpace>()
                        .eq(parkingSpaceQueryRequest.getUserId() != null,
                                ParkingSpace::getUserId, parkingSpaceQueryRequest.getUserId())
                        .eq(parkingSpaceQueryRequest.getIsAvailable() != null,
                                ParkingSpace::getIsAvailable, parkingSpaceQueryRequest.getIsAvailable())
                        .eq(parkingSpaceQueryRequest.getPriceType() != null,
                                ParkingSpace::getPriceType, parkingSpaceQueryRequest.getPriceType())
                        .like(parkingSpaceQueryRequest.getSearchText() != null
                              && !parkingSpaceQueryRequest.getSearchText().isEmpty(),
                                ParkingSpace::getAddressDescription, parkingSpaceQueryRequest.getSearchText())
                        .orderByDesc(ParkingSpace::getUpdateTime)

        );
        BigDecimal longitude = parkingSpaceQueryRequest.getLongitude();
        BigDecimal latitude = parkingSpaceQueryRequest.getLatitude();

        // 通过经纬度计算最近距离排序
        List<ParkingSpace> sortedRecords = parkingSpacePage.getRecords().stream()
                .peek(parkingSpace -> {
                    BigDecimal distance = parkingSpaceService.getDistance(longitude, latitude, parkingSpace.getLongitude(), parkingSpace.getLatitude());
                    parkingSpace.setDistance(distance);
                })
                .sorted(Comparator.comparing(ParkingSpace::getDistance))
                .collect(Collectors.toList());

        // 更新分页结果中的记录
        parkingSpacePage.setRecords(sortedRecords);
        return ResultUtils.success(parkingSpacePage);
    }

}
