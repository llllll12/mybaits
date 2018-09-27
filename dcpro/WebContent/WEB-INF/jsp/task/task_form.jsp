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
				<form action="" method="post" id="task_form"
					name="task_form">
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">乘客联系方式*</label> 
						<input type="text" class="form-control col-lg-2" id="custMobile" name="custMobile">
					</div>
					<div style="height: 10px">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="stationId">站点*</label> 
						<select id="stationId" class="form-control col-lg-2" name="stationId">
	                    </select>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" charset="utf8" src="js/jquery-1.12.4.js"></script>

<script type="text/javascript">
 	 $(function () {
        $('form').bootstrapValidator({
　　　　　　　　 message: '验证',
            　	   feedbackIcons: {
                　　　　　　　　valid: 'glyphicon glyphicon-ok',
                　　　　　　　　invalid: 'glyphicon glyphicon-remove',
                　　　　　　　　validating: 'glyphicon glyphicon-refresh'
            　　　　　　　　   },
            fields: {
                custMobile:{
                	message: '请输入手机号码',
                    validators: {
                        notEmpty: {
                            message: '请输入手机号码'
                        },
                        regexp: {
                            regexp: /^1[3|4|5|7|8][0-9]{9}$/,
                            message: '请检查手机号码格式'
                        }
                    }
                }
            },
            submitHandler: function(validator, form, submitButton) {  
                validator.defaultSubmit();  
            }
        });
    });
    function indexOf(data, str) {
		for (var i = 0; i < data.length; i++) { 
			if (str == data[i]) { 
				return i;
			} 
		}
		return -1;
	}
 </script>