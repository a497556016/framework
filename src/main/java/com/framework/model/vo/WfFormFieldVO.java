package com.framework.model.vo;

import java.util.*;
import com.framework.model.WfFormField;

public class WfFormFieldVO extends WfFormField {
	private String[] editTasks;

	public String[] getEditTasks() {
		return editTasks;
	}

	public void setEditTasks(String[] editTasks) {
		this.editTasks = editTasks;
	}
}