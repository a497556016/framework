<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../../common/head.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/onlineProduce/form/editForm.js"></script>
<script type="text/javascript">
var form = eval(${form});
var fields = eval(${fields});
Ext.onReady(function(){
	Ext.create("App.LineProduce.EditForm.Viewport",{
		type : 'update'
	});
});
</script>
</head>
<body>

</body>
</html>