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
<title>数据库连接查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	/** sxsj 为页面访问信息是否刷新的时间 1000 =1s 当前为没8s访问服务器  **/
	var sxsj = 20000;

	$(function() {
	sx();
		setInterval(sx, sxsj);
	});
	function sx() {
		$.get('./sys?action=getXx',null,function(data){  
		         $("#centan").html('');  
       			 $("#centan").append(data);  
          }); 

	}
	function killAll(ip) {
	$.post('./sys?action=kill',
						{"ip" : ip},
	function(result) {
				 if(result!=null){
				 alert("成功干掉了");
					$("#centan").html('');  
       			    $("#centan").append(result);  
       			 }
				     }, "text"); 
	}
</script>
<style type="text/css">
div {
	border-bottom-color: #e5e5e5;
	border-bottom-style: none;
	border-bottom-width: 1px;
	border-left-color: #e5e5e5;
	border-left-style: solid;
	border-left-width: 1px;
	border-right-color: #e5e5e5;
	border-right-style: solid;
	border-right-width: 1px;
	border-top-color: #e5e5e5;
	border-top-style: solid;
	border-top-width: 1px;
	border-bottom-color: #e5e5e5;
	border-bottom-style: solid;
	border-botton-width: 1px;
	width: 980px;
	clear: both;
	font-family: sans-serif;
	font-size: 12px;
	margin-top: 0px;
	margin-right: auto;
	margin-left: auto;
	margin-bottom: 20px;
}

tr {
	color: #353735;
	background-color: rgb(238, 244, 248);
	padding-top: 6px;
	padding-right: 6px;
	padding-bottom: 6px;
	padding-left: 6px;
	font-size: 14px;
	width:100%;
	font-weight: 800;
	overflow: hidden;
	margin-top: 10px;
	margin-right: 0px;
	margin-left: 0px;
	margin-bottom: 10px;
	line-height: 1.5em;
	font-family: sans-serif;
	direction: ltr;
	border-bottom-width: 1px;
	border-bottom-style: none;
	border-bottom-color: #aaa;
	background-repeat: repeat;
	background-position-x: 0%;
	background-position-y: 0%;
}

t {
	color: #626262;
	font-size: 12px;
	margin-top: 0px;
	margin-right: 0px;
	margin-left: 8px;
	margin-bottom: 0px;
	line-height: 23px;
	font-family: sans-serif;
	direction: ltr;
}

td {
    text-align: center;
	font-weight: 800;
	width:25%;
	line-height: 1.2em;
	font-family: 微软雅黑;
	font-size: 16px;
	background-attachment: scroll;
	background-color: transparent;
	background-repeat: repeat;
	border-bottom-color: #e5e5e5;
	border-bottom-style: solid;
	border-botton-width: 0.5px;
	color: black;
	margin-top: 10px;
	margin-right: 0px;
	margin-left: 0px;
	margin-bottom: 20px;
	padding-top: 0px;
	padding-right: 0px;
	padding-bottom: 10px;
	padding-left: 10px;
}

t {
	font-family: 微软雅黑;
	font-size: 8px;
	color: green;
}
</style>
</head>
<body id="sss">
	<div id='centan'></div>
</body>
</html>