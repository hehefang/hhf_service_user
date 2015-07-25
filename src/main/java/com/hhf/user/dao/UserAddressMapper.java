package com.hhf.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhf.model.user.UserAddress;

public interface UserAddressMapper {
    int deleteByPrimaryKey(long addrId);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(long addrId);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

	List<UserAddress> getAddressByUserId(@Param("userId")long userId);

	int resetDefault(@Param("userId")Long userId);

	int setDefault(@Param("addrId")String addrId, @Param("userId")String userId);

	int delAddr(@Param("addrId")Long addrId, @Param("userId")long userId);

	UserAddress getAddrByIdUid(@Param("addrId")Long addrId, @Param("userId")long userId);
}