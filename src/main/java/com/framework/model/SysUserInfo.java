package com.framework.model;

import java.io.Serializable;
import java.util.Date;

public class SysUserInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4828011846999241L;

	private Integer id;

    private String personCode;

    private String lastName;

    private String password;

    private String email;
    
    private String mobilePhone;

    private Boolean locked;

    private String deptCode;
    
    private String cardId;

    private Date createDate;

    private String createBy;

    private Date modifyDate;

    private String modifyBy;
   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

	

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysUserInfo [id=");
		builder.append(id);
		builder.append(", personCode=");
		builder.append(personCode);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobilePhone=");
		builder.append(mobilePhone);
		builder.append(", locked=");
		builder.append(locked);
		builder.append(", deptCode=");
		builder.append(deptCode);
		builder.append(", cardId=");
		builder.append(cardId);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", modifyDate=");
		builder.append(modifyDate);
		builder.append(", modifyBy=");
		builder.append(modifyBy);
		builder.append("]");
		return builder.toString();
	}
}