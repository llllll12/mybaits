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
  <div class="container-fluid">
   <div class="row">
    	<div class="box col-md-12 text-right">
  		    <button type="button" class="btn btn-primary" onclick="openStation()">
  					<span class="glyphicon glyphicon-send"></span> 添加站点
  		    </button>
    	</div>
    </div>
    <br/>
    </div>
    <div class="row">
      <c:forEach items="${stationlist }" var="bean">
	  <div class="col-md-3">
	    <div class="thumbnail">
	      <div class="caption">
	        <button type="button" class="close" aria-label="Close" onclick="delStation('${bean.id }')"><span aria-hidden="true">&times;</span></button>
	        <h3 class="text-center">${bean.stationName }</h3>
	        <p class="text-center">
	           <span class="badge">${bean.occupied }/${bean.total }</span>
	        </p>
	        <p class="text-center">
	           <button type="button" class="btn btn-primary" onclick="editStation('${bean.id }')">
  					<span class="glyphicon glyphicon-asterisk"></span> 编辑
  		       </button> 
  		       <button type="button" class="btn btn-primary" onclick="showStation('${bean.id }')">
  					<span class="glyphicon glyphicon-asterisk"></span> 查看
  		       </button>
	        </p>
	      </div>
	    </div>
	  </div>
	  </c:forEach>
     </div>
   <!-- /.content-wrapper -->
  <%@ include file="../index/foot.jsp"%>
    <!-- 提示信息 -->
  <c:if test="${addstationmsg!=null }">
        <input type="hidden" id="msg" value="${addstationmsg }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#addstationmsg").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
     <c:if test="${stationMsg!=null }">
        <input type="hidden" id="stationMsg" value="${stationMsg }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#stationMsg").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
    
    <!-- 添加窗口 -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>站点信息</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="station_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_station_submit">确认提交</a>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 查看窗口 -->
	<div class="modal fade" id="stationModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
					<table id="taskDriverTable" class="cell-border hover stripe">
						<thead>
							<tr>
								<th>编号</th>
								<th>姓名</th>
								<th>电话</th>
								<th>车牌号码</th>
								<th>车型</th>
								<th>排位</th>
							</tr>
						</thead>
					</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var taskDriverTable;
//查看站点
function showStation(id) {
	$('#stationModal').modal('show');// 显示修改
	
	taskDriverTable=$('#taskDriverTable').dataTable({
	    	processing: true,
	    	paginationType:'simple_numbers',
	        serverSide: true,
	        scrollX: true,
	        ordering: false,
	        destroy: true,
	        autoWidth: false,
	        ajax:{
	            url: 'StationServlet?action=driverlist',
	            type: 'POST',
	            data: {
                	"id": id
            	}
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
						{ "data": "carNumber" },
						{ "data": "carType" },
						{ "data": null,"targets": 5},
			        ],
			"fnDrawCallback": function(){
			     var api = this.api();
			     var startIndex= api.context[0]._iDisplayStart;//获取到本页开始的条数
			     api.column(0).nodes().each(function(cell, i) {
			     	//此处 startIndex + i + 1;会出现翻页序号不连续，主要是因为startIndex 的原因,去掉即可。
			     	//cell.innerHTML = startIndex + i + 1;
			     	cell.innerHTML =  i + 1;
			   	 }); 
			     api.column(5).nodes().each(function(cell, i) {
				    //此处 startIndex + i + 1;会出现翻页序号不连续，主要是因为startIndex 的原因,去掉即可。
				    cell.innerHTML = startIndex + i + 1;
				 });
			}
	    });
	
}
</script>
</html>