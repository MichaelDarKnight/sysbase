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
		setInterval(sx, sxsj);
	});
	function sx() {
	/* 	$.post('/sms?action=getXx',
						{"param" : ""},
	function(result) {
						 if(result!=null){
								$("#centan").html(result);
								}
				     }, "text"); */
		$.get('./sms?action=getXx',null,function(data){  
       			 $("#centan").append(data);  
          }); 

	}
	function del() {
			$.get('./sms?action=del',null,function(data){  
			if(!!data){
			 $("#centan").html('');  
			}
          }); 
	}
	function delPm() {
			 $("#centan").html('');  
	}
	function ref() {
			$.get('./sms?action=getXx',null,function(data){  
       			 $("#centan").append(data);  
          }); 
	}
	function refAll() {
			$.get('./sms?action=getXxAll',null,function(data){  
       			 $("#centan").html('');  
       			 $("#centan").append(data);  
          }); 
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
			
			h2 {
				color: #353735;
				background-color: rgb(238, 244, 248);
				padding-top: 6px;
				padding-right: 6px;
				padding-bottom: 6px;
				padding-left: 6px;
				font-size: 14px;
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
			
			p {
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
			
			h1 {
				font-weight: 800;
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
				color:green;
			}
</style>
	</head>
	<body id="sss">欢迎你	<%=session.getAttribute("name") %></br>
	<input type="button" onclick="ref()" value="刷新">
	<input type="button" onclick="del()" value="删除">
	<input type="button" onclick="refAll()" value="获取全部信息">
	<input type="button" onclick="delPm()" value="清屏">
		<div  id='centan'>
		</div>
	</body>
</html>