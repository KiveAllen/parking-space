package com.allen.part.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allen.part.mapper.FeedbackMapper;
import com.allen.part.model.entity.Feedback;
import com.allen.part.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 评论服务实现
 *
 */
@Service
@Slf4j
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
