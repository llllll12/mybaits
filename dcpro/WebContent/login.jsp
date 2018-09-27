<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@  taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c' %>
<!DOCTYPE html>
<html lang="utf-8">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录界面</title>
<!-- Bootstrap -->
<link
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	
		<form class="form-horizontal" action="/dcpro/CsServlet?action=login"  method="post">
	
	 
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="username" name="username"
					placeholder="username" required="required">
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="password" name="password"
					placeholder="Password" required="required">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10"  >
				<input type="text" class="form-control" id="yzm" name="captcha" style="width: 46%; display: inline-block"
					placeholder="验证码"  required="required">
					<img  src="/dcpro/captcha.jpg"  >

			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10"  >
		 <div style="color:red">${filtermsg}</div>
		<div style="color: red">${loginmsg }</div>
		
			</div>
			</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">登录</button>
			</div>
		</div>
		
	</form>

	</div>
	<script type="text/javascript">
		$(function() {
			$('#kaptchaImage').click(
					function() {
						$(this).attr(
								'src',
								'/dcproject/captcha.jpg?'
										+ Math.floor(Math.random() * 100));
					})
		});
	</script>

</body>
</html>