<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-content">
					<div class="form-group has-success">
						<label class="control-label" for="user_name">司机姓名*</label>
						<input type="text" class="form-control col-lg-2" id="name" readonly>
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">手机号码*</label> 
						<input type="text" class="form-control col-lg-2" id="mobile" readonly>
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">车牌号*</label> 
						<input type="text" class="form-control col-lg-2" id="carNumber" readonly>
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="user_name">车型*</label> 
						<input type="text" class="form-control col-lg-2" id="carType"  readonly>
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="user_name">是否夜班*</label> 
						<input type="text" class="form-control col-lg-2" id="workdate"  readonly>
					</div>
			</div>
		</div>
	</div>
</div>