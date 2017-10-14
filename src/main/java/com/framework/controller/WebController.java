package com.framework.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.constant.CommonConstant;
import com.framework.util.CreateImageCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("web")
public class WebController extends BaseController {

	@RequestMapping("index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("main")
	public String main(){
		return "main";
	}
	
	@RequestMapping("/layerMap")
	public String layerMap(){
		return "map/layerMap";
	}
	
	@RequestMapping("/gaodeMap")
	public String gaodeMap(){
		return "map/gaodeMap";
	}
	
	@RequestMapping("/getMapData")
	@ResponseBody
	public List<String> getMapData() throws Exception{
		List<String> addressList = FileUtils.readLines(new File("E:\\mywork\\cwb\\address2.txt"), "gbk");
		
		return addressList;
	}
	@RequestMapping("/saveDatFile")
	public void saveDatFile(String data) throws Exception{
		data = request.getParameter("data");
		JSONArray array = JSONArray.fromObject(data);
		int size = array.size();
		List<String> lines = new ArrayList<>();
		for(int i=0;i<size;i++){
			JSONObject obj = array.getJSONObject(i);
			String code = obj.getString("code");
			String lon = obj.getString("lon");
			String lat = obj.getString("lat");
			String gridName = obj.getString("gridName");
			String str = code +"	"+lon+"	"+lat+"	"+gridName;
			lines.add(str);
		}
		File file = new File("E:\\mywork\\cwb\\gridOut1.txt");
		FileUtils.writeLines(file, lines);
	}
}
