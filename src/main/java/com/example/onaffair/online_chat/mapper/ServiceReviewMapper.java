package com.example.onaffair.online_chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.ServiceReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceReviewMapper extends BaseMapper<ServiceReview> {
    
    /**
     * 根据维修请求ID获取评价
     */
    @Select("SELECT * FROM service_review WHERE request_id = #{requestId}")
    ServiceReview selectByRequestId(@Param("requestId") Long requestId);
    
    /**
     * 获取所有评价列表（分页）
     */
    @Select("SELECT sr.*, rr.user_account, rr.appliance_type, rr.appliance_model, rr.technician_account " +
            "FROM service_review sr " +
            "LEFT JOIN repair_request rr ON sr.request_id = rr.request_id " +
            "ORDER BY sr.created_at DESC")
    IPage<ServiceReview> selectAllWithRequestInfo(Page<ServiceReview> page);
    
    /**
     * 根据维修员账号获取评价列表
     */
    @Select("SELECT sr.*, rr.user_account, rr.appliance_type, rr.appliance_model " +
            "FROM service_review sr " +
            "LEFT JOIN repair_request rr ON sr.request_id = rr.request_id " +
            "WHERE rr.technician_account = #{technicianAccount} " +
            "ORDER BY sr.created_at DESC")
    List<ServiceReview> selectByTechnicianAccount(@Param("technicianAccount") String technicianAccount);
}