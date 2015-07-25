package com.hhf.user.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhf.common.mybatis.Page;
import com.hhf.common.util.DateUtils;
import com.hhf.constants.SystemConstants;
import com.hhf.model.user.User;
import com.hhf.model.user.UserExt;
import com.hhf.service.user.IUserService;
import com.hhf.user.dao.UserExtMapper;
import com.hhf.user.dao.UserMapper;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserExtMapper userExtMapper;

	@Override
	public boolean uniqueUserName(String userName) {
		int count = this.userMapper.userCount(userName);
		if (count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public User getUserById(Long userId) {
		return this.userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Long register(User user) {
		if (user != null) {
			// 注册日期
			user.setRegDate(DateUtils.currentDate());
			String userKey = RandomStringUtils.randomAlphanumeric(20);
			String pwd = DigestUtils.md5Hex(DigestUtils.md5Hex(userKey
					+ SystemConstants.WEB_KEY)
					+ user.getPwd());
			user.setPwdKey(userKey);
			user.setPwd(pwd);
			this.userMapper.insertSelective(user);
			return user.getUserId();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtils.randomAlphanumeric(20));
	}

	@Override
	public User getUserByUserName(String userName) {
		return this.userMapper.selectUserByUserName(userName);
	}

	@Override
	public int updateUser(User user) {
		if (user != null && user.getUserId() != null) {
			return this.userMapper.updateByPrimaryKeySelective(user);
		}
		return 0;
	}

	@Override
	public boolean uniqueMobile(String mobile) {
		int count = this.userMapper.mobileCount(mobile);
		if (count > 0) {
			return false;
		}
		return true;
	}

	@Override
	public User getUserByMobile(String mobile) {
		return this.userMapper.selectUserByMobile(mobile);
	}

	@Override
	public int chgPwdById(long userId, String pwd) {
		User user = this.getUserById(userId);

		return chgPwd(user, pwd);
	}

	private int chgPwd(User user, String pwd) {
		if (user != null) {
			String pwdKey = user.getPwdKey();
			pwd = DigestUtils.md5Hex(DigestUtils.md5Hex(pwdKey
					+ SystemConstants.WEB_KEY)
					+ pwd);
			user.setPwd(pwd);
			return this.updateUser(user);
		}
		return 0;
	}

	@Override
	public int chgPwd(String mobile, String pwd) {
		User user = this.getUserByMobile(mobile);
		return chgPwd(user, pwd);
	}

	@Override
	public User getUserInfoById(long userId) {
		return this.userMapper.getUserInfoById(userId);
	}

	@Override
	public int updateUserExt(UserExt userExt) {
		if (userExt != null && userExt.getUserId() != null) {
			UserExt ueTemp = this.userExtMapper.selectByPrimaryKey(userExt
					.getUserId());
			if (ueTemp != null) {
				this.userExtMapper.updateByPrimaryKeySelective(userExt);
			} else {
				this.userExtMapper.insertSelective(userExt);
			}
		}
		return 0;
	}

	@Override
	public Page<User> queryUserByPage(UserQueryCondition cond, int... page) {
		Page<User> pg = new Page<User>();

		if (page != null && page.length > 0) {
			pg.setCurrentPageNo(page[0]);
			if (page.length > 1)
				pg.setPageSize(page[1]);
		}

		pg.setResult(userMapper.queryUserByPage(cond, pg));

		return pg;
	}

}
