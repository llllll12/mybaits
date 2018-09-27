<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>
</head>
<body class="no-skin">

	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	<div id="websocket_button"></div>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<!-- 左侧菜单 -->
		<%@ include file="left.jsp"%>

		<div class="main-content">
			<div class="main-content-inner">

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="ace-settings-container" id="ace-settings-container">
						<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
							id="ace-settings-btn">
							<i class="ace-icon fa fa-cog bigger-130"></i>
						</div>

						<div class="ace-settings-box clearfix" id="ace-settings-box">
							<div class="pull-left width-50">
								<!-- #section:settings.skins -->
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9">#438EB9</option>
											<option data-skin="skin-1" value="#222A2D">#222A2D</option>
											<option data-skin="skin-2" value="#C6487E">#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; 选择皮肤</span>
								</div>

								<!-- #section:settings.breadcrumbs -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-breadcrumbs" /> <label class="lbl"
										for="ace-settings-breadcrumbs">固定面包屑</label>
								</div>

								<!-- #section:settings.container -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-add-container" /> <label class="lbl"
										for="ace-settings-add-container"> 居中风格 </label>
								</div>

							</div>

							<div class="pull-left width-50">
								<!-- #section:basics/sidebar.options -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-hover" /> <label class="lbl"
										for="ace-settings-hover">折叠菜单</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-compact" /> <label class="lbl"
										for="ace-settings-compact">压缩菜单</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-highlight" /> <label class="lbl"
										for="ace-settings-highlight">弹出风格</label>
								</div>

							</div>
						</div>
					</div>
					<div>
						<iframe name="mainFrame" id="mainFrame" frameborder="0" 
							SCROLLING="no" src="default.jsp"
							style="width: 98%; height: 700px"></iframe>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->

		</div>
	</div>
	<!-- /.main-content -->

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="foot.jsp"%>
	<!-- *************************修改密码******************************************* -->
	<div class="modal fade" id="changePwdModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3>用户密码修改</h3>
				</div>
				<div class="modal-body">
					<jsp:include page="../system/changePass.jsp"></jsp:include>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0)" class="btn btn-default modal-close"
						data-dismiss="modal">关闭</a> <a href="javascript:void(0)"
						class="btn btn-primary" id="btn_pwd_submit">确认提交</a>
				</div>
			</div>
		</div>
	</div>
	<!-- *************************修改密码******************************************* -->
	<!-- *************************修改个人信息******************************************* -->
	<div class="modal fade" id="changeInforModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3>修改个人信息</h3>
				</div>
				<div class="modal-body">
					<input type="hidden" id="rname" value="${admin.name }"> <input
						type="hidden" id="rmobile" value="${admin.mobile }">
					<jsp:include page="../system/change.jsp"></jsp:include>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0)" class="btn btn-default modal-close"
						data-dismiss="modal">关闭</a> <a href="javascript:void(0)"
						class="btn btn-primary" id="btn_infor_submit">确认提交</a>
				</div>
			</div>
		</div>
	</div>
	<!-- *************************修改个人信息******************************************* -->
</body>

<script type="text/javascript">
	function setIframeHeight(iframe) {
		if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
		iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		}
		}
	};
	window.onresize=function(){  
		setIframeHeight();  
	} 
	function openChangePwd() {
		// 隐藏所有校验图标和内容
		$("#oldPass").val("");
		$("#newPass").val("");
		$("#reNewPass").val("");
		$('#changePwdModal').modal('show');// 显示修改密码
	}
	function openChangeInfor() {
		// 隐藏所有校验图标和内容
		$("#name").val($("#rname").val());
		$("#mobile").val($("#rmobile").val());
		$('#changeInforModal').modal('show');// 显示修改个人信息
	}
	$(function(){
		$("#btn_pwd_submit").unbind("click");
		$("#btn_pwd_submit").click(function(){
				$.ajax({
					type: "POST",
					url : '<%=request.getContextPath()%>/cs/editPass.do',
					async : false,
					data:{
						"oldPass":$("#oldPass").val(),
						"newPass":$("#newPass").val()
					},
					dataType : "json",
					success : function(data) {
						if(data.status=="1"){
							bootbox.alert({
								buttons : {
									ok : {
										label : '确定',
										className : 'btn-myStyle'
									}
								},
								message : "修改密码成功",
								title : "操作消息",
							});
							// 关闭当前的窗口
							$('#changePwdModal').modal('hide');// 
						}else if(data.status=="-1"){
							bootbox.alert({
								buttons : {
									ok : {
										label : '确定',
										className : 'btn-myStyle'
									}
								},
								message : data.message,
								title : "操作消息",
							});
							// 关闭当前的窗口
							//$('#changePwdModal').modal('show');// 
						}else{
							bootbox.alert({
								buttons : {
									ok : {
										label : '确定',
										className : 'btn-myStyle'
									}
								},
								message : "修改密码失败",
								title : "操作消息",
							});
							// 关闭当前的窗口
							$('#changePwdModal').modal('hide');// 
						}
					},
					error : function() {
						alert("error");
					}
				});
		});	
				//修改个人信息
				$("#btn_infor_submit").unbind("click");
				$("#btn_infor_submit").click(function(){
				$.ajax({
					type: "POST",
					url : '<%=request.getContextPath()%>
	/cs/editInfor.do',
				async : false,
				data : {
					"name" : $("#name").val(),
					"mobile" : $("#mobile").val()
				},
				dataType : "json",
				success : function(data) {
					bootbox.alert({
						buttons : {
							ok : {
								label : '确定',
								className : 'btn-myStyle'
							}
						},
						message : data.message,
						title : "操作消息",
					});
					// 关闭当前的窗口
					$('#changeInforModal').modal('hide');// 
				},
				error : function() {
					alert("error");
				}
			});
		});
	});
</script>
</html>