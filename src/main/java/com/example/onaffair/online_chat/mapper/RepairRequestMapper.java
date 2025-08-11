package com.example.onaffair.online_chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.RepairRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RepairRequestMapper extends BaseMapper<RepairRequest> {
    
    /**
     * 根据用户账号获取维修请求列表
     */
    @Select("SELECT * FROM repair_request WHERE user_account = #{userAccount} ORDER BY created_at DESC")
    List<RepairRequest> selectByUserAccount(@Param("userAccount") String userAccount);
    
    /**
     * 根据维修员账号获取分配的任务列表
     */
    @Select("SELECT * FROM repair_request WHERE technician_account = #{technicianAccount} ORDER BY created_at DESC")
    List<RepairRequest> selectByTechnicianAccount(@Param("technicianAccount") String technicianAccount);
    
    /**
     * 根据状态获取维修请求列表（分页）
     */
    @Select("<script>" +
            "SELECT * FROM repair_request " +
            "<where>" +
            "<if test='status != null and status != \"\"'>" +
            "AND request_status = #{status}" +
            "</if>" +
            "</where>" +
            "ORDER BY created_at DESC" +
            "</script>")
    IPage<RepairRequest> selectByStatus(Page<RepairRequest> page, @Param("status") String status);
    
    /**
     * 根据维修员账号和状态获取任务列表
     */
    @Select("<script>" +
            "SELECT * FROM repair_request " +
            "WHERE technician_account = #{technicianAccount}" +
            "<if test='status != null and status != \"\"'>" +
            "AND request_status = #{status}" +
            "</if>" +
            "ORDER BY created_at DESC" +
            "</script>")
    List<RepairRequest> selectByTechnicianAndStatus(@Param("technicianAccount") String technicianAccount, 
                                                   @Param("status") String status);
}