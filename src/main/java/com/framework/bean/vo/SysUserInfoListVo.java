package com.framework.bean.vo;

public class SysUserInfoListVo {
	private Integer id;
	
	private String personCode;

    private String lastName;

    private String email;
    
    private String mobilePhone;

    private Boolean locked;
    
    private String roleNames;
    
    private String deptName;

	public Integer getId() {
		return id;
	}

	public String getPersonCode() {
		return personCode;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getLocked() {
		return locked;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysUserInfoListVo [id=");
		builder.append(id);
		builder.append(", personCode=");
		builder.append(personCode);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobilePhone=");
		builder.append(mobilePhone);
		builder.append(", locked=");
		builder.append(locked);
		builder.append(", roleNames=");
		builder.append(roleNames);
		builder.append(", deptName=");
		builder.append(deptName);
		builder.append("]");
		return builder.toString();
	}
}
