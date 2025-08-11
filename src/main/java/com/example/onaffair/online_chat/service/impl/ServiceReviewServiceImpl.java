package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.ServiceReview;
import com.example.onaffair.online_chat.mapper.ServiceReviewMapper;
import com.example.onaffair.online_chat.service.ServiceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceReviewServiceImpl implements ServiceReviewService {
    
    @Autowired
    private ServiceReviewMapper serviceReviewMapper;
    
    @Override
    public boolean submitServiceReview(ServiceReview serviceReview) {
        serviceReview.setCreatedAt(LocalDateTime.now());
        return serviceReviewMapper.insert(serviceReview) > 0;
    }
    
    @Override
    public ServiceReview getReviewByRequestId(Long requestId) {
        return serviceReviewMapper.selectByRequestId(requestId);
    }
    
    @Override
    public IPage<ServiceReview> getAllReviews(Page<ServiceReview> page) {
        return serviceReviewMapper.selectAllWithRequestInfo(page);
    }
    
    @Override
    public List<ServiceReview> getReviewsByTechnician(String technicianAccount) {
        return serviceReviewMapper.selectByTechnicianAccount(technicianAccount);
    }
    
    @Override
    public ServiceReview getReviewById(Long reviewId) {
        return serviceReviewMapper.selectById(reviewId);
    }
    
    @Override
    public boolean updateReview(ServiceReview serviceReview) {
        return serviceReviewMapper.updateById(serviceReview) > 0;
    }
    
    @Override
    public boolean deleteReview(Long reviewId) {
        return serviceReviewMapper.deleteById(reviewId) > 0;
    }
}