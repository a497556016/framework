package com.framework.bean.common;

public class FileInfo {
	private String name;//文件名称
	private String path;//文件路径
	private boolean isDirectory;//是否为目录
	private boolean isFile;//是否为文件
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
}
