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
				<form action="fee/add.do" method="post" id="fee_form"
					name="fee_form">
					<div class="form-group has-success">
						<label class="control-label" for="user_name">司机姓名*</label>
						<input type="text" class="form-control col-lg-2" id="name" readonly>
						<input type="hidden" class="form-control col-lg-2" id="driverId" name="driverId">
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
					
					<div class="form-group has-success">
						<label class="control-label" for="user_name">缴费金额</label> 
						<input type="text" class="form-control col-lg-2" id="fee" name="fee">
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
            	fee:{
                	message: '请输入缴费金额',
                    validators: {
                        notEmpty: {
                            message: '请输入缴费金额'
                        },
                        numeric: {
                        	message: '缴费金额格式不正确'
                        },
                        greaterThan: {
                            value: 0.1,
                            inclusive: false,
                            message: '缴费金额必须大于0'
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