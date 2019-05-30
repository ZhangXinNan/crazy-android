<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>添加种类</title>
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
				<h4 class="panel-title">添加种类</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="proAddCategory" method="post">
					<div class="form-group">
						<label for="kindName" class="col-sm-2 control-label">种类名：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="kindName" name="kindName"
								minlength="2" required>
						</div>
					</div>
					<div class="form-group">
						<label for="kindDesc" class="col-sm-2 control-label">描述：</label>
						<div class="col-sm-10">
							<textarea type="text" class="form-control" id="kindDesc" name="kindDesc"
								minlength="20" required rows="4"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" id="addCategory" class="btn btn-success">添加</button>
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