package com.allen.part.model.dto.reservation;

import com.allen.part.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询订单请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReservationQueryRequest extends PageRequest implements Serializable {

    /**
     * 预订车位的租客用户ID
     */
    private Integer userId;

    /**
     * 拥有者id
     */
    private Integer ownerId;

    /**
     * 预订状态，1: 已预订, 2: 已取消, 3: 已完成
     */
    private Integer reservationStatus;

    private static final long serialVersionUID = 1L;
}