<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结算</title>
</head>
<body>
	<%@include file="../include/fore/header.jsp"%>
	<%@include file="../include/fore/top.jsp"%>
	<%@include file="../include/fore/cart/buyPage.jsp"%>
	<%@include file="../include/fore/footer.jsp"%>
</body>
</html>