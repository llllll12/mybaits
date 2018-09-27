<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
	<%@ include file="../index/top.jsp"%>
</head>
<body>

<!-- ************************************************************************************************************ -->
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <section class="content padding-0">
    <!--   删除暂时不做，后期扩展 -->
    <div class="row">
    	<div class="box col-md-12 text-right">
  		    <button type="button" class="btn btn-primary" onclick="openDriver()">
  					<span class="glyphicon glyphicon-send"></span> 添加
  		    </button>
    	</div>
    </div>
   
    <div class="row">
         <div class="box col-md-12">
             <table id="driverTable" class="cell-border hover stripe">
			        <thead>
			            <tr>
				        	<th>编号</th>
				            <th>姓名</th>
				            <th>电话</th>
				            <th>平台密码</th>
				            <th>车牌号码</th>
				            <th>车型</th>
				            <th>是否夜班</th>
				            <th>是否审核</th>
				            <th>创建日期</th>
				            <th></th>
	        			</tr>
			        </thead>
			    </table>
         </div>
 	
	</div>
    </section>
  </div>
 
  <!-- 添加窗口 -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
         <!-- Accessible Rich Internet Applications 这一套协定是w3和apple为了残疾人士无障碍使用网站  例如视障人士, 在读到这一句
              aria-hidden="true的时候, 就会自动忽略跳过 -->

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>司机管理</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="driver_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_driver_submit">确认提交</a>
                </div>
            </div>
        </div>
    </div>
   <!-- /.content-wrapper -->
  <%@ include file="../index/foot.jsp"%>
  <!-- 提示信息 -->
  <c:if test="${addDriverMessage!=null }">
        <input type="hidden" id="addDrivermsg" value="${addDriverMessage }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#addDrivermsg").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
     <c:if test="${updateDriverMessage!=null }">
        <input type="hidden" id="updateDriverMessage" value="${updateDriverMessage }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#updateDriverMessage").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
     <c:if test="${delDriverMessage!=null }">
        <input type="hidden" id="delDriverMessage" value="${delDriverMessage }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#delDriverMessage").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
  <script type="text/javascript">
  var driverTable;
  $(function(){
	  //消息列表分页展示
	  driverTable=$('#driverTable').dataTable({
	    	processing: true,//是否显示处理状态
	    	paginationType:'simple_numbers',
	        serverSide: false,//true代表后台处理分页，false代表前台处理分页 
	        scrollX: true,//设置水平滚动
	        ordering: false,//是否允许Datatables开启排序
	        autoWidth: false,//控制Datatables是否自适应宽度
	        bLengthChange: false,
	        ajax:{
	            url: 'DriverSerlvet?action=list',
	            type: 'POST'
	        },
	        language: {
	          	"Processing":   "处理中...",
	            "lengthMenu":   "显示 _MENU_ 项结果",
	            "zeroRecords":  "没有匹配结果",
	            "info":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	            "infoEmpty":    "显示第 0 至 0 项结果，共 0 项",
	            "infoFiltered": "(由 _MAX_ 项结果过滤)",
	            "infoPostFix":  "",
	            "search":       "搜索:",
	            "url":          "",
	            "emptyTable":     "表中数据为空",
	            "loadingRecords": "载入中...",
	            "thousands":  ",",
	            "paginate": {
	                "first":    "首页",
	                "previous": "上页",
	                "next":     "下页",
	                "last":     "末页"
	            },
	            "aria": {
	                "sortAscending":  ": 以升序排列此列",
	                "sortDescending": ": 以降序排列此列"
	            }
			},
			"columns": [
			            { "data": null,"targets": 0},
			            { "data": "name"},
						{ "data": "mobile"},
						{ "data": "password"},
						{ "data": "carNumber" },
						{ "data": "carType" },
						{ "data": "workDate",
						       "render" : function(data,type, row, meta) {
						    	   if(data=='1'){
						    		   return "<span class='label label-default arrowed-right arrowed-in'>夜班</span>";
						    	   }else{
						    		   return "<span class='label label-danger arrowed-right arrowed-in'>白班</span>";
						    	   }
								}
						},
						{ "data": "state",
						       "render" : function(data,type, row, meta) {
						    	   if(data=='1'){
						    		   return "<span class='label label-default arrowed-right arrowed-in'>已审核</span>";
						    	   }else if(data=='7'){
						    		   return "<span class='label label-success arrowed-right arrowed-in'>待审核</span>";
						    	   }else if(data=='8'){
						    		   return "<span class='label label-danger arrowed-right arrowed-in'>已拒绝</span>";
						    	   }else{
						    		   return "<span class='label label-success arrowed-right arrowed-in'>已删除</span>";
						    	   }
								}
						},
						{ "data": "createTime",
						       "render" : function(data,type, row, meta) {
									return dateFormat(data,'yyyy-MM-dd');
								}
						}
			        ],
			  "columnDefs": [//设置列定义初始化属性
			            {
			            	"targets": [9],
			            	"render" : function(data,type,row, meta) {
			            		var str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="editDriver(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>编辑</button>';
			            		str+='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="delDriver(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>删除</button>';
			            		if(row.state=='7'){
			            			str+='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="statusDriver(\''+row.id+'\',\'1\')"><i class="glyphicon glyphicon-pencil"></i>通过</button>';
				            		str+='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="statusDriver(\''+row.id+'\',\'8\')"><i class="glyphicon glyphicon-pencil"></i>拒绝</button>';
			            		}
			            		return str;
							}
			            }
			        ],
			        //回掉函数，用来设置行号
			        "fnDrawCallback": function(){
			        	　　var api = this.api();
			        	　　api.column(0).nodes().each(function(cell, i) {
			        	　　　　cell.innerHTML =  i + 1;
			        	　　}); 
			        	}
	    });
	});

	</script>
</body>
</html>