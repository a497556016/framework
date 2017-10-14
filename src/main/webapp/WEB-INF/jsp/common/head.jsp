<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="${ctx }/resources/extjs/ext-bootstrap.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx }/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctx }/resources/css/icon.css">
<script type="text/javascript" src="${ctx }/resources/extjs/build/packages/ux/classic/ux-debug.js"></script>
<script type="text/javascript" src="${ctx }/resources/extjs/build/classic/locale/locale-zh_CN-debug.js"></script>
<script type="text/javascript">
Ext.tip.QuickTipManager.init();
var basePath = '${ctx }';
var fileServerPath = '${fileServerPath }';
var loginUserMenu = '<%=session.getAttribute("loginUserMenu")%>';
var loginUserInfo = '<%=session.getAttribute("loginUserInfo")%>';

</script>
<script type="text/javascript" src="${ctx }/resources/js/common/funcs.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/common/utils.js"></script>