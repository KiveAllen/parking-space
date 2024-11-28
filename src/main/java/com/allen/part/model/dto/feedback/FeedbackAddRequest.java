package com.allen.part.model.dto.feedback;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建评论请求
 *
 */
@Data
public class FeedbackAddRequest implements Serializable {

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
     * 评价关联的预约记录ID
     */
    private Integer reservationId;

    private static final long serialVersionUID = 1L;
}