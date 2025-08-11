package com.example.onaffair.online_chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onaffair.online_chat.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    
    /**
     * 管理员创建公告
     * @param announcement 公告信息
     * @param adminAccount 管理员账号
     * @return 创建的公告
     */
    Announcement createAnnouncement(Announcement announcement, String adminAccount);
    
    /**
     * 管理员更新公告
     * @param announcement 公告信息
     * @return 更新的公告
     */
    Announcement updateAnnouncement(Announcement announcement);
    
    /**
     * 管理员删除公告
     * @param announcementId 公告ID
     * @return 是否删除成功
     */
    boolean deleteAnnouncement(String announcementId);
    
    /**
     * 管理员获取所有公告
     * @return 公告列表
     */
    List<Announcement> getAllAnnouncements();
    
    /**
     * 用户分页获取公告
     * @param page 分页对象
     * @return 分页公告列表
     */
    IPage<Announcement> getAnnouncementsForUser(Page<Announcement> page);
    
    /**
     * 根据ID获取公告详情
     * @param announcementId 公告ID
     * @return 公告详情
     */
    Announcement getAnnouncementById(String announcementId);
}