package com.hhf.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhf.common.util.DateUtils;
import com.hhf.constants.user.UserConstants;
import com.hhf.model.user.UserAddress;
import com.hhf.service.user.IAddressService;
import com.hhf.user.dao.UserAddressMapper;

@Service("addressService")
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	private UserAddressMapper userAddressMapper;

	@Override
	public UserAddress getAddressById(long addressId) {
		return this.userAddressMapper.selectByPrimaryKey(addressId);
	}

	@Override
	public List<UserAddress> getAddressesByUserId(long userId) {
		return this.userAddressMapper.getAddressByUserId(userId);
	}

	@Override
	public int addAddress(UserAddress address) {
		if(address !=null){
			address.setCreateDate(DateUtils.currentDate());
			if(UserConstants.ADDRESS_IS_DEFAULT.equals(address.getIsDefault())){
				this.userAddressMapper.resetDefault(address.getUserId());
			}
			return this.userAddressMapper.insertSelective(address);
		}
		return 0;
	}

	@Override
	public int delAddress(long addrId) {
		UserAddress address = new UserAddress();
		address.setAddrId(addrId);
		address.setUpdateDate(DateUtils.currentDate());
		address.setStatus(UserConstants.ADDRESS_STATUS_INVALID);
		return this.userAddressMapper.updateByPrimaryKeySelective(address);
	}

	@Override
	public int updateAddress(UserAddress address) {
		if(address!=null&&address.getAddrId()!=null){
			address.setUpdateDate(DateUtils.currentDate());
			if(UserConstants.ADDRESS_IS_DEFAULT.equals(address.getIsDefault())){
				this.userAddressMapper.resetDefault(address.getUserId());
			}
			
			return this.userAddressMapper.updateByPrimaryKeySelective(address);
		}
		return 0;
	}

	@Override
	public void setDefault(String addrId, String userId) {
		this.userAddressMapper.resetDefault(Long.parseLong(userId));
		this.userAddressMapper.setDefault(addrId,userId);
	}

	@Override
	public void delAddr(Long addrId, long userId) {
		this.userAddressMapper.delAddr(addrId,userId);
	}

	@Override
	public UserAddress getAddressByIdUid(Long addrId, long userId) {
		return this.userAddressMapper.getAddrByIdUid(addrId,userId);
	}

}
