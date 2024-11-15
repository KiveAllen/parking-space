package com.allen.part.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName feedback
 */
@TableName(value ="feedback")
@Data
public class Feedback implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评价的租客用户ID
     */
    private Integer userId;

    /**
     * 被评价的车位ID
     */
    private Integer spaceId;

    /**
     * 评分，1 - 5星
     */
    private Integer rating;

    /**
     * 文字评价内容
     */
    private String commentText;

    /**
     * 上传的评价图片链接（如果有）
     */
    private String commentImage;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}