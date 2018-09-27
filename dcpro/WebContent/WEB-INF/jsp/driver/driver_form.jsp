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
				<form action="" method="post" id="driver_form"
					name="driver_form">
					<div class="form-group has-success">
						<label class="control-label" for="user_name">司机姓名*</label>
						<input type="hidden" name="id" id="id" /> 
						<input type="text" class="form-control col-lg-2" id="name" name="name">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">手机号码*</label> 
						<input type="text" class="form-control col-lg-2" id="mobile" name="mobile">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">车牌号*</label> 
						<input type="text" class="form-control col-lg-2" id="carNumber" name="carNumber">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="user_name">车型*</label> 
						<input type="text" class="form-control col-lg-2" id="carType" name="carType">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="user_name">是否夜班*</label> 
						<select id="workDate" class="form-control" name="workDate">
	                         <option value="1">是</option>
	                         <option value="0">否</option>
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
            	name: {
                    message: '请输入司机姓名',
                    validators: {
                        notEmpty: {
                            message: '请输入司机姓名'
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
                },
                carNumber:{
                	message: '请输入车牌号',
                    validators: {
                        notEmpty: {
                            message: '请输入车牌号'
                        },
                        regexp: {
                            regexp: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/,
                            message: '请检查车牌号格式'
                        }
                    }
                },
                carType:{
                	message: '请输入车型',
                    validators: {
                        notEmpty: {
                            message: '请输入车型'
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