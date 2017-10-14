<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>会话超时</title>
      <script language="javascript">
	    alert("会话超时,请重新登录系统。");
	    if(parent)
			parent.window.location="${ctx}/user/login";
		if(parent.parent)
			parent.parent.window.location="${ctx}/user/login";
     </script>
  </head>
  
  <body>
  </body>
</html>
