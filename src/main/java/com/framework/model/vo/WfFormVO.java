package com.framework.model.vo;

import java.util.*;
import com.framework.model.WfForm;

public class WfFormVO extends WfForm {
	private List<WfFormFieldVO> fields;

	public List<WfFormFieldVO> getFields() {
		return fields;
	}

	public void setFields(List<WfFormFieldVO> fields) {
		this.fields = fields;
	}
}