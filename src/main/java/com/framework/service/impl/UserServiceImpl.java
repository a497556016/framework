package com.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysUserInfoListVo;
import com.framework.bean.vo.SysUserInfoVo;
import com.framework.constant.CommonConstant.FileBusinessType;
import com.framework.dao.SysResourceMapper;
import com.framework.dao.SysRoleMapper;
import com.framework.dao.SysUserInfoMapper;
import com.framework.dao.SysUserRoleRelationMapper;
import com.framework.model.SysCommonFileInfo;
import com.framework.model.SysRole;
import com.framework.model.SysUserInfo;
import com.framework.model.SysUserRoleRelation;
import com.framework.service.CommonService;
import com.framework.service.UserService;
import com.framework.util.LoginUtils;
import com.framework.util.MD5Util;
import com.framework.util.PageFactory;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
	@Autowired
	private SysResourceMapper sysResourceMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserRoleRelationMapper sysUserRoleRelationMapper;
	@Autowired
	private CommonService commonService;
	
	private BeanCopier userInfoModelToVo = BeanCopier.create(SysUserInfo.class, SysUserInfoVo.class, false);
	private BeanCopier userInfoVoToModel = BeanCopier.create(SysUserInfoVo.class, SysUserInfo.class, false);
	
	
	
	public void queryUserList(Page<SysUserInfoListVo> page, SysUserInfo userInfo) throws Exception {
		
		com.baomidou.mybatisplus.plugins.Page<SysUserInfoListVo> mppage = PageFactory.createFrom(page);
		
		List<SysUserInfoListVo> userInfos = sysUserInfoMapper.queryUserList(mppage, userInfo);

		page.setRows(userInfos);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<SysUserInfoVo> addUser(SysUserInfoVo userInfo) throws Exception {
		ResultMessage<SysUserInfoVo> resultMessage = new ResultMessage<>();
		userInfo.setPassword(MD5Util.encode(userInfo.getPassword()));
		EntityWrapper<SysUserInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("person_code", userInfo.getPersonCode());
		List<SysUserInfo> infos = sysUserInfoMapper.selectList(wrapper);
		if(CollectionUtils.isNotEmpty(infos)){
			resultMessage.setMessage("用户名："+userInfo.getPersonCode()+"已经存在！");
			return resultMessage;
		}
		
		SysUserInfo sysUserInfo = new SysUserInfo();
		userInfoVoToModel.copy(userInfo, sysUserInfo, null);
		sysUserInfo.setCreateDate(new Date());
		sysUserInfo.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
		int i = SqlHelper.sqlSession(SysUserInfo.class, true).insert("com.framework.dao.SysUserInfoMapper.insert", sysUserInfo);
//		int i = sysUserInfoMapper.insert(sysUserInfo);
		if(i>0){
			List<String> roleCodes = userInfo.getRoleCodes();
			if(null!=roleCodes){
				for (String roleCode : roleCodes) {
					SysUserRoleRelation roleRelation = new SysUserRoleRelation();
					roleRelation.setRoleCode(roleCode);
					roleRelation.setPersonCode(sysUserInfo.getPersonCode());
					sysUserRoleRelationMapper.insert(roleRelation);
				}
			}
			resultMessage.setSuccess(true);
			resultMessage.setModel(userInfo);
			try {
				if(null!=userInfo.getUserPhoto()&&null!=userInfo.getUserPhoto().getId()){
					commonService.updateFileType(userInfo.getUserPhoto().getId(), sysUserInfo.getId()+"", FileBusinessType.USER_PHOTO.type());
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return resultMessage;
	}


	public ResultMessage<?> deleteUsers(List<SysUserInfoVo> sysUserInfoVos) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (SysUserInfo sysUserInfo : sysUserInfoVos) {
			if(null!=sysUserInfo.getId()){
				count += sysUserInfoMapper.deleteById(sysUserInfo.getId());
			}else{
				Map<String, Object> columnMap = new HashMap<>();
				columnMap.put("person_code", sysUserInfo.getPersonCode());
				count += sysUserInfoMapper.deleteByMap(columnMap);
			}
			//删除角色所在的用户
			EntityWrapper<SysUserRoleRelation> wrapper = new EntityWrapper<>();
			wrapper.eq("person_code", sysUserInfo.getPersonCode());
			sysUserRoleRelationMapper.delete(wrapper);
			//删除照片
			commonService.deleteCommonFileInfo(sysUserInfo.getId().toString(), FileBusinessType.USER_PHOTO.type());
		}
		if(count==sysUserInfoVos.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}


	@Override
	public void roleAssign(List<String> roleCodes, String personCode) throws Exception {
		for (String roleCode : roleCodes) {
			SysUserRoleRelation record = new SysUserRoleRelation();
			record.setRoleCode(roleCode);
			record.setPersonCode(personCode);
			record.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
			record.setCreateDate(new Date());
			sysUserRoleRelationMapper.insert(record);
		}
		
	}


	@Override
	public List<String> getUserRoleCodes(String personCode) throws Exception {
		EntityWrapper<SysUserRoleRelation> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("person_code", personCode);
		List<SysUserRoleRelation> relations = sysUserRoleRelationMapper.selectList(entityWrapper);
		List<String> roleCodes = new ArrayList<>();
		for (SysUserRoleRelation sysUserRoleRelation : relations) {
			roleCodes.add(sysUserRoleRelation.getRoleCode());
		}
		return roleCodes;
	}


	@Override
	public SysUserInfoVo getUserById(String id) throws Exception {
		SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(NumberUtils.toInt(id));
		SysCommonFileInfo commonFileInfo = commonService.getCommonFileInfo(sysUserInfo.getId().toString(), FileBusinessType.USER_PHOTO.type());
		
		SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
		
		userInfoModelToVo.copy(sysUserInfo, sysUserInfoVo, null);;
		if(null!=commonFileInfo){
			sysUserInfoVo.setUserPhoto(commonFileInfo);
		}
		return sysUserInfoVo;
	}


	@Override
	public ResultMessage<SysUserInfoVo> updateUser(SysUserInfoVo userInfo) throws Exception {
		ResultMessage<SysUserInfoVo> resultMessage = new ResultMessage<>();
		
		if(StringUtils.isNotEmpty(userInfo.getPassword())){
			userInfo.setPassword(MD5Util.encode(userInfo.getPassword()));
		}else{
			userInfo.setPassword(null);
		}
		
		SysUserInfo sysUserInfo = new SysUserInfo();
		userInfoVoToModel.copy(userInfo, sysUserInfo, null);
		int i = sysUserInfoMapper.updateByPrimaryKeySelective(sysUserInfo);
		if(i>0){
			List<String> roleCodes = userInfo.getRoleCodes();
			if(null!=roleCodes){
				Map<String, Object> columnMap = new HashMap<>();
				columnMap.put("person_code", sysUserInfo.getPersonCode());
				sysUserRoleRelationMapper.deleteByMap(columnMap);
				for (String roleCode : roleCodes) {
					SysUserRoleRelation roleRelation = new SysUserRoleRelation();
					roleRelation.setRoleCode(roleCode);
					roleRelation.setPersonCode(sysUserInfo.getPersonCode());
					sysUserRoleRelationMapper.insert(roleRelation);
				}
			}
			try {
				if(null!=userInfo.getUserPhoto().getId()){
					commonService.updateFileType(userInfo.getUserPhoto().getId(), userInfo.getId()+"", FileBusinessType.USER_PHOTO.type());
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			resultMessage.setSuccess(true);
			resultMessage.setModel(userInfo);
		}
		return resultMessage;
	}


	@Override
	public void lockUser(Integer id, boolean locked) throws Exception {
		SysUserInfo sysUserInfo = new SysUserInfo();
		sysUserInfo.setId(id);
		sysUserInfo.setLocked(locked);
		sysUserInfoMapper.updateByPrimaryKeySelective(sysUserInfo);
	}


	@Override
	public SysUserInfoVo getUser(SysUserInfo user) throws Exception {
		SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
		SysUserInfo sysUserInfo = sysUserInfoMapper.selectOne(user);
		userInfoModelToVo.copy(sysUserInfo, sysUserInfoVo, null);
		List<SysRole> roles = sysUserRoleRelationMapper.selectRoleByPersonCode(sysUserInfo.getPersonCode());
		List<String> roleCodes = new ArrayList<>();
		for(SysRole role : roles){
			roleCodes.add(role.getRoleCode());
		}
		sysUserInfoVo.setRoleCodes(roleCodes);
		
		SysCommonFileInfo commonFileInfo = commonService.getCommonFileInfo(sysUserInfo.getId().toString(), FileBusinessType.USER_PHOTO.type());
		if(null!=commonFileInfo){
			sysUserInfoVo.setUserPhoto(commonFileInfo);
		}
		return sysUserInfoVo;
	}


}
