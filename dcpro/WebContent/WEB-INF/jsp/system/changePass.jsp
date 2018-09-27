<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-content" style="height: 150px">
				<form action="updatePwd" method="post" id="upwd_form" name="upwd_form">
				    <div class="form-group has-success">
						<label class="control-label" for="department_name">原密码:</label> 
						<input type="password" class="form-control col-lg-2" id="oldPass"
							name="oldPass">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="department_name">新密码:</label> 
						<input
							type="password" class="form-control col-lg-2" id="newPass"
							name="newPass">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">确认密码:</label> <input
							type="password" class="form-control col-lg-2" id="reNewPass" name="reNewPass">
					</div>

				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function () {
    $('form').bootstrapValidator({
　　　　 message: 'This value is not valid',
        　	   feedbackIcons: {
            　　　　　　　　valid: 'glyphicon glyphicon-ok',
            　　　　　　　　invalid: 'glyphicon glyphicon-remove',
            　　　　　　　　validating: 'glyphicon glyphicon-refresh'
        　　　　　　　　   },
        fields: {
        	oldPass:{
            	message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '原密码不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 10,
                        message: '密码必须为3到10位'
                    }
                }
            },
            newPass:{
            	message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 10,
                        message: '密码必须为3到10位'
                    }
                }
            },
            reNewPass:{
            	message: '确认密码验证失败',
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 10,
                        message: '确认密码必须为3到10位'
                    },
                    identical: {
                    	field: 'newPass',
                    	message: '两次输入密码不一致'
                    }
                }
            }
        },
        submitHandler: function(validator, form, submitButton) {  
            validator.defaultSubmit();  
        }
    });
});
 </script>
