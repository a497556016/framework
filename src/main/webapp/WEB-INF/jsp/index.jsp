<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>管理模板</title>
<jsp:include page="common/head.jsp"></jsp:include>


<style type="text/css">
	.menu{
		background-color: #eee;
		font-size: 16px;
	}
	.menu ul{
		color : #66a;
	}
	.menu ul li{
		height : 60px;
		line-height: 30px;
	}
	.menu ul li :hover{
		color : blue;
	}
	.active-menu{
		background-color: red;
	}
	
	.logo{
		float:left;
		width:230px;
		background:#66e;
		height:100%;
	}
	.first-menu{
		float:left;
		width:100px;
		height:100%;
		background:#1e76c3;
		cursor: pointer;
	}
	.first-menu .icon{
		text-align: center;
		font-size: 12px;
		margin-top : 5px;
	}
	.first-menu .text{
		text-align: center;
		font-size: 15px;
		margin-top : 5px;
	}
	a.cls1{
		color : #ddd;
	}
	a.cls1 :HOVER{
		background:#a66;
		color:white;
	}
	.split{
		float:left;
		width:1px;
		background:#aaa;
		height:100%;
	}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/index.js"></script>

</head>
<body>
<%=session.getId() %>
</body>
</html>