package com.framework.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.framework.bean.common.FileInfo;
import com.framework.bean.common.ResultMessage;
import com.framework.constant.CommonConstant;
import com.framework.model.SysCommonFileInfo;
import com.framework.service.CommonService;
import com.framework.util.CreateImageCode;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/checkCode")
	public void checkCode(){
		
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        
        
        CreateImageCode vCode = new CreateImageCode(100,40,5,10);
        session.setAttribute(CommonConstant.CHECK_CODE, vCode.getCode());
        System.out.println("验证码："+vCode.getCode());
        try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 上传文件
	 * @return
	 */
	@RequestMapping("/uploadFile")
//	@ResponseBody
	public void uploadFile(HttpServletRequest request){
		ResultMessage<List<SysCommonFileInfo>> resultMessage = new ResultMessage<>();
		try{
			MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			List<SysCommonFileInfo> fileInfos = new ArrayList<>();
			for (String key : fileMap.keySet()) {
				MultipartFile file = fileMap.get(key);
				String fileName = file.getOriginalFilename();
				if(file.getSize()>0&&StringUtils.isNotEmpty(fileName)){
					InputStream inputStream = file.getInputStream();
					SysCommonFileInfo fileInfo = commonService.saveImageFile(fileName,inputStream);
					fileInfos.add(fileInfo);
				}
			}
			resultMessage.setModel(fileInfos);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		write(resultMessage);
//		return resultMessage;
	}
	
	/**
	 * 获取文件目录下的文件列表
	 * @param pPath 目录
	 * @return
	 */
	@RequestMapping("/queryFileListByPath")
	@ResponseBody
	public ResultMessage<List<FileInfo>> queryFileListByPath(String path){
		ResultMessage<List<FileInfo>> resultMessage = new ResultMessage<>();
		try{
			List<FileInfo> fileInfos = new ArrayList<>();
			File file = new File(path);
			if(file.isDirectory()){
				File[] files = file.listFiles();
				if(null!=files){
					for (File f : files) {
						FileInfo fileInfo = new FileInfo();
						fileInfo.setFile(f.isFile());
						fileInfo.setDirectory(f.isDirectory());
						fileInfo.setName(f.getName());
						fileInfo.setPath(f.getAbsolutePath().replaceAll("\\\\", "/"));
						fileInfos.add(fileInfo);
					}
				}
				resultMessage.setModel(fileInfos);
				resultMessage.setSuccess(true);
			}else{
				resultMessage.setMessage("文件路径"+path+"不是目录");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
}
