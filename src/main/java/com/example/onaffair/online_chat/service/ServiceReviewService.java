package com.example.onaffair.online_chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.ServiceReview;

import java.util.List;

public interface ServiceReviewService {
    
    /**
     * 提交服务评价
     */
    boolean submitServiceReview(ServiceReview serviceReview);
    
    /**
     * 根据维修请求ID获取评价
     */
    ServiceReview getReviewByRequestId(Long requestId);
    
    /**
     * 获取所有评价列表（分页）
     */
    IPage<ServiceReview> getAllReviews(Page<ServiceReview> page);
    
    /**
     * 根据维修员账号获取评价列表
     */
    List<ServiceReview> getReviewsByTechnician(String technicianAccount);
    
    /**
     * 根据ID获取评价详情
     */
    ServiceReview getReviewById(Long reviewId);
    
    /**
     * 更新评价
     */
    boolean updateReview(ServiceReview serviceReview);
    
    /**
     * 删除评价
     */
    boolean deleteReview(Long reviewId);
}