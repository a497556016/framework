package com.framework.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.framework.constant.CommonConstant;
import com.framework.constant.CommonConstant.FileType;
import com.framework.dao.SysCommonFileInfoMapper;
import com.framework.model.SysCommonFileInfo;
import com.framework.model.SysCommonFileInfoExample;
import com.framework.service.CommonService;
import com.framework.servlet.PropertiesManger;
import com.framework.util.DateUtils;
import com.framework.util.ImageOption;
import com.framework.util.ImageUtils;
import com.framework.util.LoginUtils;

@Service
public class CommonServiceImpl implements CommonService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysCommonFileInfoMapper commonFileInfoMapper;
	
	@Override
	public SysCommonFileInfo saveImageFile(String fileName, InputStream inputStream) throws Exception {
		return saveCommonFileInfo(FileType.IMAGE.type(), fileName, inputStream);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SysCommonFileInfo saveCommonFileInfo(String fileType,String fileName, InputStream inputStream) throws Exception {
		String[] filePaths = saveFile(fileType,fileName, inputStream,DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		SysCommonFileInfo commonFileInfo = new SysCommonFileInfo();
		commonFileInfo.setFileType(fileType);
		commonFileInfo.setFileName(fileName);
		commonFileInfo.setPath(filePaths[0]);
		commonFileInfo.setWaterPath(filePaths[1]);
		commonFileInfo.setThumbPath(filePaths[2]);
		commonFileInfo.setCreateDate(new Date());
		commonFileInfo.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
		int i = commonFileInfoMapper.insert(commonFileInfo);
		checkUploadFile(commonFileInfo.getId());

		completePath(commonFileInfo);
		return commonFileInfo;
	}
	
	/**
	 * 补全文件访问路径
	 * @param commonFileInfo
	 */
	private void completePath(SysCommonFileInfo commonFileInfo){
		String fileServerPath = PropertiesManger.get("fileServerPath");
		commonFileInfo.setPath(fileServerPath+commonFileInfo.getPath());
		commonFileInfo.setWaterPath(fileServerPath+commonFileInfo.getWaterPath());
		commonFileInfo.setThumbPath(fileServerPath+commonFileInfo.getThumbPath());
	}
	
	private void checkUploadFile(Integer id){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//延遲一分鐘
					Thread.sleep(60*1000);
					SysCommonFileInfo commonFileInfo = commonFileInfoMapper.selectByPrimaryKey(id);
					if(null!=commonFileInfo
							&&(StringUtils.isEmpty(commonFileInfo.getBusinessId()))){
						//刪除文件
						String fileBasePath = PropertiesManger.get("fileBasePath");
						FileUtils.forceDelete(new File(fileBasePath+commonFileInfo.getPath()));
						FileUtils.forceDelete(new File(fileBasePath+commonFileInfo.getWaterPath()));
						FileUtils.forceDelete(new File(fileBasePath+commonFileInfo.getThumbPath()));
						commonFileInfoMapper.deleteByPrimaryKey(id);
					}
				} catch (Exception e) {
//					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}).start();
	}

	@Override
	public String[] saveFile(String fileType,String fileName, InputStream inputStream,String... waterText) throws Exception {
		String[] filePaths = new String[3];
		
		String fileDir = DateUtils.format(new Date(),"yyyyMMdd");
		filePaths[0] ="/"+fileType+"/"+fileDir+"/"+fileName;
		String fileBasePath = PropertiesManger.get("fileBasePath");
		//保存文件
		FileUtils.copyInputStreamToFile(inputStream, new File(fileBasePath+filePaths[0]));
		if(FileType.IMAGE.type().equals(fileType)){
			String waterPath = "/"+fileType+"/"+fileDir+"/water/"+fileName;
			filePaths[1] = waterPath;
			//生成水印图
			ImageUtils.markWaterMakImg(fileBasePath+filePaths[0], fileBasePath+filePaths[1], new ImageOption(), waterText);
			String thumbPath = "/"+fileType+"/"+fileDir+"/thumb/"+fileName;
			filePaths[2] = thumbPath;
			//生成缩略图
			ImageUtils.resizeImage(fileBasePath+filePaths[0], fileBasePath+filePaths[2], 200, 200);
		}
		return filePaths;
	}

	@Override
	public void updateFileType(Integer fileId,String businessId, String businessType) throws Exception {
		SysCommonFileInfo commonFileInfo = commonFileInfoMapper.selectByPrimaryKey(fileId);
		if(null!=commonFileInfo){
			commonFileInfo.setBusinessId(businessId);
			commonFileInfo.setBusinessType(businessType);
			commonFileInfoMapper.updateByPrimaryKey(commonFileInfo);
		}else{
			throw new RuntimeException("文件ID"+fileId+"不存在或者文件上傳時間已經超時！");
		}
	}

	@Override
	public List<SysCommonFileInfo> findCommonFileInfo(String businessId, String businessType) throws Exception {
		SysCommonFileInfoExample example = new SysCommonFileInfoExample();
		example.createCriteria().andBusinessIdEqualTo(businessId).andBusinessTypeEqualTo(businessType);
		example.setOrderByClause("create_date desc");
		return commonFileInfoMapper.selectByExample(example);
	}

	@Override
	public SysCommonFileInfo getCommonFileInfo(String businessId, String businessType) throws Exception {
		List<SysCommonFileInfo> commonFileInfos = this.findCommonFileInfo(businessId, businessType);
		if(!CollectionUtils.isEmpty(commonFileInfos)){
			SysCommonFileInfo commonFileInfo = commonFileInfos.get(0);
			completePath(commonFileInfo);
			return commonFileInfo;
		}
		return null;
	}

	@Override
	public void deleteCommonFileInfo(String businessId, String businessType) throws Exception {
		SysCommonFileInfoExample example = new SysCommonFileInfoExample();
		example.createCriteria().andBusinessIdEqualTo(businessId).andBusinessTypeEqualTo(businessType);
		commonFileInfoMapper.deleteByExample(example);
	}
	
	
}
