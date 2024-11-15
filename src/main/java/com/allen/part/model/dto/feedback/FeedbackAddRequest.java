package com.allen.part.model.dto.feedback;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private static final long serialVersionUID = 1L;
}