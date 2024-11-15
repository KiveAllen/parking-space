package com.allen.part.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.allen.part.model.dto.reservation.ReservationQueryRequest;
import com.allen.part.model.entity.Reservation;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单服务
 *
 */
public interface ReservationService extends IService<Reservation> {

}
