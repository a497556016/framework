package com.${packageName}.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.${packageName}.model.${className};
import com.${packageName}.model.vo.${className}VO;

public interface ${className}Service {

	void query${className}List(Page<${className}VO> page, Map<String,Object> params) throws Exception;

	ResultMessage<${className}VO> add${className}(${className}VO ${className?uncap_first}) throws Exception;

	ResultMessage<?> delete${className}s(List<${className}VO> ${className?uncap_first}s) throws Exception;

	ResultMessage<?> update${className}(${className}VO ${className?uncap_first}) throws Exception;

	${className}VO get${className}(${className}VO ${className?uncap_first}) throws Exception;

}
