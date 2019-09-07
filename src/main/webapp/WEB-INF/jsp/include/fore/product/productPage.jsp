<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模仿天猫官网 ${p.name}</title>
</head>
<body>
<div class="categoryPictureInProductPageDiv">
    <img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div>
<div class="productPageDiv">
 
    <%@include file="imgAndInfo.jsp" %>
     
    <%@include file="productReview.jsp" %>
     
    <%@include file="productDetail.jsp" %>
</div>
</body>
</html>