package com.allen.part.service.impl;

import com.allen.part.mapper.ParkingSpaceMapper;
import com.allen.part.model.entity.ParkingSpace;
import com.allen.part.service.ParkingSpaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 车位服务实现
 *
 */
@Service
@Slf4j
public class ParkingSpaceServiceImpl extends ServiceImpl<ParkingSpaceMapper, ParkingSpace> implements ParkingSpaceService {

    /**
     * 计算距离（单位：米）
     */
    @Override
    public BigDecimal getDistance(BigDecimal longitude, BigDecimal latitude, BigDecimal longitude1, BigDecimal latitude1) {
        final int R = 6371000; // 地球半径，单位为米

        double lat1 = Math.toRadians(latitude.doubleValue());
        double lon1 = Math.toRadians(longitude.doubleValue());
        double lat2 = Math.toRadians(latitude1.doubleValue());
        double lon2 = Math.toRadians(longitude1.doubleValue());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP);
    }
}
