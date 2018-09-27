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
    	<div class="box col-md-12">
  		    <button type="button" class="btn btn-primary" onclick="openMsg()">
  					<span class="glyphicon glyphicon-send"></span> 添加
  		    </button>
    	</div>
    </div>
   
    <div class="row">
         <div class="box col-md-12">
             <table id="msgTable" class="cell-border hover stripe">
			        <thead>
			            <tr>
				        	<th>编号</th>
				            <th>发布人</th>
				            <th>发布时间</th>
				            <th>消息内容</th>
				            <th></th>
	        			</tr>
			        </thead>
			    </table>
         </div>
 	
	</div>
    </section>
  </div>
  <!-- /.content-wrapper -->
  <%@ include file="../index/foot.jsp"%>
  <!-- 添加窗口 -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>系统消息管理</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="msg_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_msg_submit">确认提交</a>
                </div>
            </div>
        </div>
    </div>
  
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
  var msgTable;
  $(function(){
	  //消息列表分页展示
	  msgTable=$('#msgTable').dataTable({
	    	processing: true,
	    	paginationType:'simple_numbers',
	        serverSide: true,
	        scrollX: true,
	        ordering: false,
	        autoWidth: false,
	        bLengthChange: false,
	        ajax:{
	            url: 'msg/list.do',
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
			            { "data": "publisher"},
						{ "data": "createTime",
						       "render" : function(data,type, row, meta) {
									return dateFormat(data,'yyyy-MM-dd');
								}
						},
						{ "data": "content"}
			        ],
			  "columnDefs": [
			            {
			            	"targets": [4],
			            	"render" : function(data,type,row, meta) {
			            		var str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="delMsg(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>删除</button>';
			            		return str;
							},
							"width": "70px"
			            },
			            { "width": "30px", "targets": 0 },
			            { "width": "100px", "targets": 1 },
			            { "width": "100px", "targets": 2 }
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