<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑分类</title>
<script type="text/javascript">
		$(function() {
			$("#editForm").submit(function(){
				if (!checkEmpty("name","分类名称")) {
					return false;
				}
				return true;
			});
		});
</script>
</head>
<body>
	<div class="workingArea">
		<ol class="breadcrumb">
			<li><a href="admin_category_list">所有分类</a></li>
			<li class="active">编辑分类</li>
		</ol>
		<div class="panel panel-warning editDiv">
			<div class="panel-heading">编辑分类</div>
			<div class="panel-body">
				<form action="admin_category_update" id="editForm" method="post" enctype="multipart/form-data">
					<table class="editTable">
						<tr>
							<td>分类名称</td>
							<td><input type="text" id="id" name="name" value="${c.name }" class="form-control"></td>
						</tr>
						<tr>
							<td>分类图片</td>
							<td>
								<input type="file" id="categoryPic" name="image" accept="image/*">
							</td>
						</tr>
						<tr class="submitTR">
							<td colspan="2" align="center">
								<input type="hidden" name="id" value="${c.id }">
								<button type="submit" class="btn btn-success">提交</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	
</body>
</html>