<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>模拟短息平台</title>
	<link rel="stylesheet" type="text/css" href="styles.css">

  </head>
  
  <body>
 	<form action="sms?action=login" method="post">
  <input type="text" name="name"> <input type="submit" value="登陆">
  </from>
   
  </body>
</html>
