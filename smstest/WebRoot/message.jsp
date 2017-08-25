<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>虚拟接口</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <h1 align="center">虚拟接口信息展示</h1>
    <table style="height: 300px; width: 950px; ">
    <tr><td width="30%" height="20px">接口名：</td><td width="70%"><%=request.getAttribute("jkmc") %></td></tr>
    <tr><td  height="20px">法院代码：</td><td><%=request.getAttribute("fydm") %></td></tr>
    <tr><td  height="20px">人员代码：</td><td><%=request.getAttribute("czrydm") %></td></tr>
    <tr><td  height="20px">人员姓名：</td><td><%=request.getAttribute("czryxm") %></td></tr>
    <tr><td  height="20px">案件信息未解密：</td><td height="20px"><textarea  height="100%"  width="100%" style="height: 40px; width: 650px; "> <%=request.getAttribute("ajxx1") %></textarea></td></tr>
     <tr><td >案件信息已解码：</td><td ><textarea  height="100%"  width="100%" style="height: 400px; width: 650px; "> <%=request.getAttribute("ajxx") %></textarea></td></tr>
    </table>
  </body>
</html>
