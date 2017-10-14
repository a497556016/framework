package com.framework.bean.common;

import java.util.List;

public class Page<T> {
	private int page;
	private int offset;
	private int limit;
	
	private String sort;
	private String order;
	
	private List<T> rows;
	private long total;
	
	private String message;
	private String code;
	private boolean success;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getOffset() {
		if(this.page>0&&this.offset==0&&this.limit>0){
			return ((this.page-1)*this.limit);
		}
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
		/*if(null==sort){
			return sort;
		}
		char[] cs = sort.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : cs) {
			if(c>='A'&&c<='Z'){
				sb.append("_"+(c+"").toLowerCase());
			}else{
				sb.append(c);
			}
		}
		return sb.toString();*/
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public String getMessage() {
		return message;
	}
	public String getCode() {
		return code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [page=");
		builder.append(page);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", order=");
		builder.append(order);
		builder.append(", rows=");
		builder.append(rows);
		builder.append(", total=");
		builder.append(total);
		builder.append(", message=");
		builder.append(message);
		builder.append(", code=");
		builder.append(code);
		builder.append(", success=");
		builder.append(success);
		builder.append("]");
		return builder.toString();
	}

	
}
