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
 * parking_space
 */
@TableName(value ="parking_space")
@Data
public class ParkingSpace implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联发布车位的车主用户ID
     */
    private Integer userId;

    /**
     * 经度
     */
    private BigDecimal latitude;

    /**
     * 纬度
     */
    private BigDecimal longitude;

    /**
     * 地址描述
     */
    private String addressDescription;

    /**
     * 车位编号
     */
    private String parkNumber;

    /**
     * 共享价格
     */
    private BigDecimal price;

    /**
     * 价格设置类型，1: 按天, 2: 按周, 3: 自定义时段
     */
    private Integer priceType;

    /**
     * 自定义时段的起始时间（当price_type为自定义时段时使用）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date customTimeStart;

    /**
     * 自定义时段的结束时间（当price_type为自定义时段时使用）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date customTimeEnd;

    /**
     * 车位是否可预订，默认是可预订状态
     */
    private Integer isAvailable;

    /**
     * 车位图片
     */
    private String parkPhoto;

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