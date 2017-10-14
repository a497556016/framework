package com.framework.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysApproveInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysApproveInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyIsNull() {
            addCriterion("business_key is null");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyIsNotNull() {
            addCriterion("business_key is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyEqualTo(String value) {
            addCriterion("business_key =", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyNotEqualTo(String value) {
            addCriterion("business_key <>", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyGreaterThan(String value) {
            addCriterion("business_key >", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyGreaterThanOrEqualTo(String value) {
            addCriterion("business_key >=", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyLessThan(String value) {
            addCriterion("business_key <", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyLessThanOrEqualTo(String value) {
            addCriterion("business_key <=", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyLike(String value) {
            addCriterion("business_key like", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyNotLike(String value) {
            addCriterion("business_key not like", value, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyIn(List<String> values) {
            addCriterion("business_key in", values, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyNotIn(List<String> values) {
            addCriterion("business_key not in", values, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyBetween(String value1, String value2) {
            addCriterion("business_key between", value1, value2, "businessKey");
            return (Criteria) this;
        }

        public Criteria andBusinessKeyNotBetween(String value1, String value2) {
            addCriterion("business_key not between", value1, value2, "businessKey");
            return (Criteria) this;
        }

        public Criteria andApproveCodeIsNull() {
            addCriterion("approve_code is null");
            return (Criteria) this;
        }

        public Criteria andApproveCodeIsNotNull() {
            addCriterion("approve_code is not null");
            return (Criteria) this;
        }

        public Criteria andApproveCodeEqualTo(String value) {
            addCriterion("approve_code =", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeNotEqualTo(String value) {
            addCriterion("approve_code <>", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeGreaterThan(String value) {
            addCriterion("approve_code >", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeGreaterThanOrEqualTo(String value) {
            addCriterion("approve_code >=", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeLessThan(String value) {
            addCriterion("approve_code <", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeLessThanOrEqualTo(String value) {
            addCriterion("approve_code <=", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeLike(String value) {
            addCriterion("approve_code like", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeNotLike(String value) {
            addCriterion("approve_code not like", value, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeIn(List<String> values) {
            addCriterion("approve_code in", values, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeNotIn(List<String> values) {
            addCriterion("approve_code not in", values, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeBetween(String value1, String value2) {
            addCriterion("approve_code between", value1, value2, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveCodeNotBetween(String value1, String value2) {
            addCriterion("approve_code not between", value1, value2, "approveCode");
            return (Criteria) this;
        }

        public Criteria andApproveRoleIsNull() {
            addCriterion("approve_role is null");
            return (Criteria) this;
        }

        public Criteria andApproveRoleIsNotNull() {
            addCriterion("approve_role is not null");
            return (Criteria) this;
        }

        public Criteria andApproveRoleEqualTo(String value) {
            addCriterion("approve_role =", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleNotEqualTo(String value) {
            addCriterion("approve_role <>", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleGreaterThan(String value) {
            addCriterion("approve_role >", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleGreaterThanOrEqualTo(String value) {
            addCriterion("approve_role >=", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleLessThan(String value) {
            addCriterion("approve_role <", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleLessThanOrEqualTo(String value) {
            addCriterion("approve_role <=", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleLike(String value) {
            addCriterion("approve_role like", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleNotLike(String value) {
            addCriterion("approve_role not like", value, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleIn(List<String> values) {
            addCriterion("approve_role in", values, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleNotIn(List<String> values) {
            addCriterion("approve_role not in", values, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleBetween(String value1, String value2) {
            addCriterion("approve_role between", value1, value2, "approveRole");
            return (Criteria) this;
        }

        public Criteria andApproveRoleNotBetween(String value1, String value2) {
            addCriterion("approve_role not between", value1, value2, "approveRole");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNull() {
            addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(Date value) {
            addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(Date value) {
            addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(Date value) {
            addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(Date value) {
            addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(Date value) {
            addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(List<Date> values) {
            addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(List<Date> values) {
            addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(Date value1, Date value2) {
            addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(Date value1, Date value2) {
            addCriterion("modify_date not between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNull() {
            addCriterion("modify_by is null");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNotNull() {
            addCriterion("modify_by is not null");
            return (Criteria) this;
        }

        public Criteria andModifyByEqualTo(String value) {
            addCriterion("modify_by =", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotEqualTo(String value) {
            addCriterion("modify_by <>", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThan(String value) {
            addCriterion("modify_by >", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThanOrEqualTo(String value) {
            addCriterion("modify_by >=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThan(String value) {
            addCriterion("modify_by <", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThanOrEqualTo(String value) {
            addCriterion("modify_by <=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLike(String value) {
            addCriterion("modify_by like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotLike(String value) {
            addCriterion("modify_by not like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByIn(List<String> values) {
            addCriterion("modify_by in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotIn(List<String> values) {
            addCriterion("modify_by not in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByBetween(String value1, String value2) {
            addCriterion("modify_by between", value1, value2, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotBetween(String value1, String value2) {
            addCriterion("modify_by not between", value1, value2, "modifyBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}