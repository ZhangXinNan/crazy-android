<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta name="author" content="Yeeku.H.Lee(CrazyIt.org)" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>查看物品详情</title>
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
	<c:if test="${requestScope.errorTip != null}">
	<div class="alert alert-warning" role="alert">${requestScope.errorTip}</div>
	</c:if>		
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4 class="panel-title">参与竞价</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="addBid" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label">物品名：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="nameSt">${requestScope.item.name}</p>
						</div>
						<label class="col-sm-2 control-label">物品描述：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="descSt">${requestScope.item.desc}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">物品所有者：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="ownerSt">${requestScope.item.owner}</p>
						</div>
						<label class="col-sm-2 control-label">物品种类：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="kindSt">${requestScope.item.kind}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">物品起拍价：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="initPriceSt">${requestScope.item.initPrice}</p>
						</div>
						<label class="col-sm-2 control-label">物品最高价：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="maxPriceSt">${requestScope.item.maxPrice}
							<input type="hidden" name="maxPrice" value="${requestScope.item.maxPrice}"/></p>
						</div>					
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">起卖时间：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="startSt">${requestScope.item.addTime}</p>
						</div>
						<label class="col-sm-2 control-label">结束时间：</label>
						<div class="col-sm-4">
							<p class="form-control-static" id="endSt">${requestScope.item.endTime}</p>
						</div>					
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">物品备注：</label>
						<div class="col-sm-10">
							<p class="form-control-static" id="remarkSt">${requestScope.item.remark}</p>
						</div>				
					</div>
					<div class="form-group">
						<div class="col-sm-12 text-center">
							<p class="well">如果你有兴趣参与该物品竞价，请输入价格后提交，<br>
							请注意，你的价格应该大于物品的当前最高价</p>
						</div>				
					</div>
					<div class="form-group">
						<label for='bidPrice' class="col-sm-2 control-label">竞拍价：</label>
						<div class="col-sm-8">
							<input type="number" class="form-control" id="bidPrice"
								name="bidPrice" min="0" required>
							<input type="hidden" id="itemId"
								name="itemId" value="${requestScope.item.id}">
						</div>				
					</div>
					<div class="form-group">
						<div class="col-sm-8 col-sm-offset-2">
							<div class="input-group">
								<input class="form-control" id="vercode" name="vercode"
									type="text" placeholder="验证码"
									required pattern="\w{6}">
									<span class="input-group-addon" id="basic-addon2">
									验证码：<img src="auth.jpg" name="d" id="d" alt="验证码"/>
								</span>
							</div>
						</div>
					</div>														
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" id="addBid" class="btn btn-success">竞价</button>
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