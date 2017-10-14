package com.framework.model;

import java.util.Date;

public class SysIcons extends SysIconsKey {
    private String code;

    private String resourceId;

    private Date createDate;

    private String createBy;

    private Date modifyDate;

    private String modifyBy;

    private byte[] resource;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public byte[] getResource() {
        return resource;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }
}