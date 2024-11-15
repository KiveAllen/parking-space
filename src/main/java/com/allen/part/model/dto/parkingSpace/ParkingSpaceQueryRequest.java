package com.allen.part.model.dto.parkingSpace;

import com.allen.part.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 查询车位请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingSpaceQueryRequest extends PageRequest implements Serializable {

    /**
     * 关联发布车位的车主用户ID
     */
    private Integer userId;

    /**
     * 经度上限
     */
    private BigDecimal latUp;

    /**
     * 经度下限
     */
    private BigDecimal latDown;

    /**
     * 纬度上限
     */
    private BigDecimal lonUp;

    /**
     * 纬度下限
     */
    private BigDecimal lonDown;

    /**
     * 价格设置类型，1: 按天, 2: 按周, 3: 自定义时段
     */
    private Integer priceType;

    /**
     * 车位是否可预订，默认是可预订状态
     */
    private Integer isAvailable;

    private static final long serialVersionUID = 1L;
}