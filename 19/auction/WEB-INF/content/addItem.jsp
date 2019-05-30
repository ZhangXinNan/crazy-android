<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>添加物品</title>
	<!-- 导入Bootstrap 3的CSS样式的样式  -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />	
	<!-- 导入Bootstrap 3的JS插件所依赖的jQuery库  -->
	<script src="jquery/jquery-3.1.1.js"></script>
	<!-- 导入Bootstrap 3的JS插件库 -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
	<div id="data" style="margin-top: 10px;">
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4 class="panel-title">添加物品</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="proAddItem" method="post">
					<div class="form-group">
						<label for="itemName" class="col-sm-2 control-label">物品名：</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="itemName" name="itemName"
								minlength="2" maxlength="10" required>
						</div>
						<label for="price" class="col-sm-2 control-label">起拍价格：</label>
						<div class="col-sm-4">
							<input type="number" min="0" class="form-control" id="initPrice"
								name="initPrice" min="0">
						</div>
					</div>
					<div class="form-group">
						<label for="avail" class="col-sm-2 control-label">有效时间：</label>
						<div class="col-sm-4">
							<select class="form-control" name="avail" id="avail">
								<option value="1" selected="selected">一天</option>
								<option value="2">二天</option>
								<option value="3">三天</option>
								<option value="4">四天</option>
								<option value="5">五天</option>
								<option value="7">一个星期</option>
								<option value="30">一个月</option>
								<option value="365">一年</option>
							</select>
						</div>
						<label for="kindId" class="col-sm-2 control-label">物品种类：</label>
						<div class="col-sm-4">
							<select class="form-control" name="kindId" id="kindId">
							<c:forEach items="${categories}" var="cate">
								<option value="${cate.id}">${cate.kindName}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="itemDesc" class="col-sm-2 control-label">物品描述：</label>
						<div class="col-sm-4">
							<textarea type="text" class="form-control" id="itemDesc" name="itemDesc"
								minlength="20" required rows="4"></textarea>
						</div>
						<label for="itemRemark" class="col-sm-2 control-label">物品备注：</label>
						<div class="col-sm-4">
							<textarea type="text" class="form-control" id="itemRemark"
								name="itemRemark" minlength="20" required rows="4"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" id="addItem" class="btn btn-success">添加</button>
							<button id="cancel" role="button" class="btn btn-danger">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="footer.jsp" %>
</div>
</body>
</html>