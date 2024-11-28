package com.allen.part.model.dto.feedback;

import com.allen.part.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询评论请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackGetRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 被评价的车位ID
     */
    private Integer spaceId;
}