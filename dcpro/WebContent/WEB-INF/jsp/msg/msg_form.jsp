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
				<form action="msg/add.do" method="post" id="msg_form"
					name="msg_form">
					<div class="form-group">
						<div class="col-md-12">
							<textarea name="content" id="content" style="height: 200px;width: 480px"></textarea>
					    </div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//var editor = new wangEditor('content');
/**
*  打开发送消息界面

 function openMessage() {
		// 上传图片（举例）
	    //editor.config.uploadImgUrl = '/upload';
	 	// 普通的自定义菜单
	    editor.config.menus = [
	    	'fontfamily',
	        'bold',
	        'underline',
	        'italic',
	        'strikethrough',
	        'eraser',
	        'forecolor',
	        '|',
	        'quote',
	        'fontsize',
	        'head',
	        'unorderlist',
	        'orderlist',
	        'alignleft',
	        'aligncenter',
	        'alignright',
	        '|',
	        'table',
	        'img',
	        '|',
	        'fullscreen'
	     ];
		editor.create();
		// 隐藏所有校验图标和内容
		editor.clear();
	}
	*/
 	 $(function () {
 		//初始化消息框
 		//openMessage();   
    });
 
 </script>