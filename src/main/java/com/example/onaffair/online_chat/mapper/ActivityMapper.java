package com.example.onaffair.online_chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.onaffair.online_chat.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}
