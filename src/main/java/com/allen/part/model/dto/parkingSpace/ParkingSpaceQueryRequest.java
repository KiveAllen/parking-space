package com.allen.part.model.dto.parkingSpace;

import com.allen.part.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 查询车位请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingSpaceQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键词
     */
    private String searchText;
    /**
     * 关联发布车位的车主用户ID
     */
    private Integer userId;
    /**
     * 价格设置类型，1: 按天, 2: 按小时, 3: 自定义时段
     */
    private Integer priceType;
    /**
     * 车位是否可预订，默认是可预订状态
     */
    private Integer isAvailable;
    /**
     * 用户经度
     */
    private BigDecimal longitude;
    /**
     * 用户纬度
     */
    private BigDecimal latitude;

    private static final long serialVersionUID = 1L;
}