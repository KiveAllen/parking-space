package com.allen.part.service;

import com.allen.part.model.entity.ParkingSpace;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * 车位服务
 *
 */
public interface ParkingSpaceService extends IService<ParkingSpace> {

    BigDecimal getDistance(BigDecimal longitude, BigDecimal latitude, BigDecimal longitude1, BigDecimal latitude1);
}
