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
  		    <button type="button" class="btn btn-primary" onclick="openTask()">
  					<span class="glyphicon glyphicon-send"></span> 添加
  		    </button>
    	</div>
    </div>
   
    <div class="row">
         <div class="box col-md-12">
             <table id="taskTable" class="cell-border hover stripe">
			        <thead>
			            <tr>
				        	<th>编号</th>
				            <th>乘客联系方式</th>
				            <th>停靠站</th>
				            <th>创建时间</th>
				            <th>接单时间</th>
				            <th>任务状态</th>
				            <th>司机</th>
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

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>任务管理</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="task_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_task_submit">确认提交</a>
                </div>
            </div>
        </div>
    </div>
   <!-- /.content-wrapper -->
    <!-- 司机信息查看 -->
      <div class="modal fade" id="driverModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>司机信息查看</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="driver_infor.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                </div>
            </div>
        </div>
    </div>
   <!-- /.content-wrapper -->
   
  <%@ include file="../index/foot.jsp"%>
  <!-- 提示信息 -->
  <c:if test="${msg!=null }">
        <input type="hidden" id="msg" value="${msg }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#msg").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
  <script type="text/javascript">
  var taskTable;
  $(function(){
	  //消息列表分页展示
	  taskTable=$('#taskTable').dataTable({
	    	processing: true,
	    	paginationType:'simple_numbers',
	        serverSide: false,
	        scrollX: true,
	        ordering: false,
	        autoWidth: false,
	        bLengthChange: false,
	        ajax:{
	            url: 'TaskServlet?action=list',
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
			            { "data": "custMobile"},
						{ "data": "stationName"},
						{ "data": "createTime",
						       "render" : function(data,type, row, meta) {
									return dateFormat(data,'yyyy-MM-dd HH:mm:ss');
								}
						},
						{ "data": "recTime",
						       "render" : function(data,type, row, meta) {
									return data==null?"":dateFormat(data,'yyyy-MM-dd HH:mm:ss');
								}
						},
						{ "data": "taskState",
						       "render" : function(data,type, row, meta) {
						    	   if(data=='p'){
						    		   return "<span class='label label-info arrowed-right arrowed-in'>待派单</span>";
						    	   }else if(data=='f'){
						    		   return "<span class='label label-success arrowed-right arrowed-in'>已完成</span>";
						    	   }else if(data=='r'){
						    		   return "<span class='label label-primary arrowed-right arrowed-in'>正在进行</span>";
						    	   }else if(data=='d'){
						    		   return "<span class='label label-warning arrowed-right arrowed-in'>待接单</span>";
						    	   }else{
						    		   return "<span class='label label-danger arrowed-right arrowed-in'>未知</span>"; 
						    	   }
								}
						}
			        ],
			  	  "columnDefs": [
						{"targets": [6],
						 "render" : function(data,type,row, meta) {
							 var str;
							 if(row.driverId==null){
								 str='无';
							 }else{
						      	str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="showDriver(\''+row.driverId+'\')">'+row.driverName+'</button>';
							 }
						     return str;
						   }
						 },
						 {
						  "targets": [7],
						  "render" : function(data,type,row, meta) {
							  var str;
							  if(row.taskState=='c'||row.taskState=='c'||row.taskState=='c'){
								  str="";
							  }else{
						      	   str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="cancelTask(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>取消任务</button>';
							  }
						      return str;
							}
						 }
				     ],
			        "fnDrawCallback": function(){
			      	　　var api = this.api();
		        	　　//var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
		        	　　api.column(0).nodes().each(function(cell, i) {
		        	　　　　//此处 startIndex + i + 1;会出现翻页序号不连续，主要是因为startIndex 的原因,去掉即可。
		        	　　　　//cell.innerHTML = startIndex + i + 1;
		        	　　　　cell.innerHTML =  i + 1;
		        	　　}); 
			        	}
	    });
	});

	</script>
</body>
</html>