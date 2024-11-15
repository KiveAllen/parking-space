package com.allen.part.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allen.part.mapper.ReservationMapper;
import com.allen.part.model.entity.Reservation;
import com.allen.part.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现
 *
 */
@Service
@Slf4j
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

}
