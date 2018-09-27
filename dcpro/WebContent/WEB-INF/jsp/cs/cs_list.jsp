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
  		    <button type="button" class="btn btn-primary" onclick="openCs()">
  					<span class="glyphicon glyphicon-send"></span> 添加
  		    </button>
    	</div>
    </div>
   
    <div class="row">
         <div class="box col-md-12">
             <table id="csTable" class="cell-border hover stripe">
			        <thead>
			            <tr>
				        	<th>编号</th>
				            <th>账号</th>
				            <th>姓名</th>
				            <th>手机号码</th>
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

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>客服管理</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="cs_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_cs_submit">确认提交</a>
                </div>
            </div>
        </div>
    </div>
   <!-- /.content-wrapper -->
  <%@ include file="../index/foot.jsp"%>
  <!-- 提示信息 -->
  <c:if test="${addCSMessage!=null }">
        <input type="hidden" id="addCSMessage" value="${addCSMessage }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#addCSMessage").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
     <c:if test="${updateCustomerMessage!=null }">
        <input type="hidden" id="updateCustomerMessage" value="${updateCustomerMessage }">
	    <script type="text/javascript">
	    	bootbox.alert({
				buttons : {
					ok : {
						label : '确定',
						className : 'btn-myStyle'
					}
				},
				message :$("#updateCustomerMessage").val(),
				title : "操作消息",
		    });
	    </script>
    </c:if>
  <script type="text/javascript">
  var csTable;
  $(function(){
	  //消息列表分页展示
	  csTable=$('#csTable').dataTable({
	    	processing: true,
	    	sPaginationType:'simple_numbers',
	        serverSide: true,
	        scrollX: true,
	        ordering: false,
	        autoWidth: false,
	        bLengthChange: false,
	        ajax:{
	            url: 'CsServlet?action=list',
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
			            { "data": "account"},
						{ "data": "name"},
						{ "data": "mobile" },
						{ "data": "createTime",
						       "render" : function(data,type, row, meta) {
									return dateFormat(data,'yyyy-MM-dd');
								}
						}
			        ],
			  "columnDefs": [
			            {
			            	"targets": [5],
			            	"render" : function(data,type,row, meta) {
			            		var str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="editCs(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>编辑</button>';
			            		str+='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="resetPwd(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>密码重置</button>';
			            		str+='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="delCs(\''+row.id+'\')"><i class="glyphicon glyphicon-pencil"></i>删除</button>';
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