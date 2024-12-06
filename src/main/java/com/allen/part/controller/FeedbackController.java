package com.allen.part.controller;

import com.allen.part.common.BaseResponse;
import com.allen.part.common.DeleteRequest;
import com.allen.part.common.ErrorCode;
import com.allen.part.common.ResultUtils;
import com.allen.part.exception.BusinessException;
import com.allen.part.exception.ThrowUtils;
import com.allen.part.model.dto.feedback.FeedbackAddRequest;
import com.allen.part.model.dto.feedback.FeedbackQueryRequest;
import com.allen.part.model.entity.Feedback;
import com.allen.part.model.entity.User;
import com.allen.part.service.FeedbackService;
import com.allen.part.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 评论接口
 */
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private UserService userService;


    /**
     * 创建评论
     */
    @PostMapping("/add")
    public BaseResponse<Long> addFeedback(@RequestBody FeedbackAddRequest feedbackAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(feedbackAddRequest == null, ErrorCode.PARAMS_ERROR);

        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackAddRequest, feedback);

        User loginUser = userService.getLoginUser(request);
        feedback.setUserId(loginUser.getId());

        // 写入数据库
        boolean result = feedbackService.save(feedback);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 返回新写入的数据 id
        long newFeedbackId = feedback.getId();
        return ResultUtils.success(newFeedbackId);
    }

    /**
     * 删除评论
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFeedback(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();

        // 判断是否存在
        Feedback oldFeedback = feedbackService.getById(id);
        ThrowUtils.throwIf(oldFeedback == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = feedbackService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(true);
    }


    /**
     * 分页获取评论列表
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Feedback>> listFeedbackByPage(@RequestBody FeedbackQueryRequest feedbackQueryRequest) {
        long current = feedbackQueryRequest.getPageNum();
        long size = feedbackQueryRequest.getPageSize();
        // 查询数据库
        Page<Feedback> feedbackPage = feedbackService.page(new Page<>(current, size),
                new LambdaQueryWrapper<Feedback>()
                        .eq(Feedback::getSpaceId, feedbackQueryRequest.getSpaceId())
        );
        return ResultUtils.success(feedbackPage);
    }

    /**
     * 获取评论
     */
    @GetMapping("/get")
    public BaseResponse<Feedback> getFeedbackById(@RequestParam long reservationId) {
        if (reservationId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Feedback feedback = feedbackService.getOne(new LambdaQueryWrapper<Feedback>()
                .eq(Feedback::getReservationId, reservationId)
        );
        return ResultUtils.success(feedback);
    }

}
