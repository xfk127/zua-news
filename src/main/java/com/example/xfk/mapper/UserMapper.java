package com.example.xfk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xfk.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}