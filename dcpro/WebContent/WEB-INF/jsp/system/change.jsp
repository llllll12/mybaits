<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-content" style="height: 150px">
				<form action="updateInfor" method="post" id="upinfor_form" name="upinfor_form">
				    <div class="form-group has-success">
						<label class="control-label" for="department_name">姓名:</label> 
						<input type="text" class="form-control col-lg-2" id="name"
							name="name">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="department_name">手机号码:</label> 
						<input
							type="text" class="form-control col-lg-2" id="mobile"
							name="mobile">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
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
        	name: {
                message: '请输入姓名',
                validators: {
                    notEmpty: {
                        message: '请输入姓名'
                    }
                }
            },
            mobile:{
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
 </script>
