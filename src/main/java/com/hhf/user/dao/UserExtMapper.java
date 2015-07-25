package com.hhf.user.dao;

import com.hhf.model.user.UserExt;

public interface UserExtMapper {
    int deleteByPrimaryKey(Long userId);

	UserExt selectByPrimaryKey(Long userId);

	int updateByPrimaryKeySelective(UserExt record);

	int updateByPrimaryKey(UserExt record);

	int insert(UserExt record);

    int insertSelective(UserExt record);
}