package com.example.onaffair.online_chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.onaffair.online_chat.entity.Announcement;
import com.example.onaffair.online_chat.mapper.AnnouncementMapper;
import com.example.onaffair.online_chat.service.AnnouncementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Override
    public Announcement createAnnouncement(Announcement announcement, String adminAccount) {
        announcement.setAdminAccount(adminAccount);
        announcement.setCreatedAt(LocalDateTime.now());
        save(announcement);
        return announcement;
    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement) {
        updateById(announcement);
        return announcement;
    }

    @Override
    public boolean deleteAnnouncement(String announcementId) {
        return removeById(announcementId);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        return list(queryWrapper);
    }

    @Override
    public IPage<Announcement> getAnnouncementsForUser(Page<Announcement> page) {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        return page(page, queryWrapper);
    }

    @Override
    public Announcement getAnnouncementById(String announcementId) {
        return getById(announcementId);
    }
}