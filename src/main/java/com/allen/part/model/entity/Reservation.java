package com.allen.part.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@TableName(value ="reservation")
@Data
public class Reservation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 预订车位的租客用户ID
     */
    private Integer userId;

    /**
     * 被预订的车位ID
     */
    private Integer spaceId;

    /**
     * 车位拥有者ID
     */
    private Integer ownerId;

    /**
     * 预订起始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date reservationTimeStart;

    /**
     * 预订结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date reservationTimeEnd;

    /**
     * 预订状态，1: 已预订, 2: 已取消, 3: 已完成
     */
    private Integer reservationStatus;

    /**
     * 预订总费用
     */
    private BigDecimal totalCost;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}