package com.framework.service;

import java.io.InputStream;
import java.util.List;

import com.framework.model.SysCommonFileInfo;

public interface CommonService {
	SysCommonFileInfo saveImageFile(String fileName, InputStream inputStream) throws Exception;

	SysCommonFileInfo saveCommonFileInfo(String fileType,String fileName, InputStream inputStream) throws Exception;
	
	String[] saveFile(String fileType,String fileName,InputStream inputStream,String... waterText) throws Exception;
	
	void updateFileType(Integer fileId,String businessId,String businessType) throws Exception;
	
	List<SysCommonFileInfo> findCommonFileInfo(String businessId,String businessType) throws Exception;
	
	SysCommonFileInfo getCommonFileInfo(String businessId,String businessType) throws Exception;
	
	void deleteCommonFileInfo(String businessId,String businessType) throws Exception;
}
