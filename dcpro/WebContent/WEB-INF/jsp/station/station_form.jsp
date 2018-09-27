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
				<form action="" method="post" id="station_form"
					name="station_form">
					<div class="form-group has-success">
						<label class="control-label" for="user_name">车站名称*</label>
						<input type="hidden" name="id" id="id" /> 
						<input type="text" class="form-control col-lg-2" id="stationName" name="stationName">
					</div>
					<div class="form-group has-success">
						<label class="control-label" for="contact_name">承载最大量*</label> 
						<input type="text" class="form-control col-lg-2" id="total" name="total">
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
            	stationName: {
                    message: '请输入车站名称',
                    validators: {
                        notEmpty: {
                            message: '请输入车站名称'
                        }//,
                     /*    remote:{
                         	message: '该账号已存在',
                         	url:'station/checkExis.do',
                         	delay:2000,
                         	type: 'POST',//请求方式
                            data: function(validator) {
                                 return {
                                	  stationName: $('#stationName').val(),
                                      id: $("#id").val()
                                 };
                            }
                         } */
                    }
                },
                total:{
                	message: '请输入最大承载量',
                    validators: {
                        notEmpty: {
                            message: '请输入最大承载量'
                        },
                        numeric: {message: '请输入数值'}
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