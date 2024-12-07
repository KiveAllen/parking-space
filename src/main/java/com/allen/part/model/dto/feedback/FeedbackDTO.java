package com.allen.part.model.dto.feedback;

import com.allen.part.model.entity.Feedback;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackDTO extends Feedback {
    /**
     * 昵称
     */
    private String name;

    /**
     * 用户头像链接
     */
    private String avatar;
}
