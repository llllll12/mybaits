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
             <table id="feeTable" class="cell-border hover stripe">
			        <thead>
			            <tr>
				        	<th>编号</th>
				        	<th>月份</th>
				            <th>姓名</th>
				            <th>电话</th>
				            <th>车牌号码</th>
				            <th>车型</th>
				            <th>是否夜班</th>
				            <th>缴费状态</th>
				            <th>缴费金额</th>
				            <th>缴费日期</th>
				            <th></th>
	        			</tr>
			        </thead>
			    </table>
         </div>
 	
	</div>
    </section>
  </div>
 
  <!-- 缴费窗口 -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>缴费窗口</h3>
                </div>
                <div class="modal-body">
                	<jsp:include page="fee_form.jsp"></jsp:include>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0)" class="btn btn-default modal-close" data-dismiss="modal">关闭</a>
                    <a href="javascript:void(0)" class="btn btn-primary" id="btn_fee_submit">确认提交</a>
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
  var feeTable;
  $(function(){
	  //消息列表分页展示
	  feeTable=$('#feeTable').dataTable({
	    	processing: true,
	    	paginationType:'simple_numbers',
	        serverSide: true,
	        scrollX: true,
	        ordering: false,
	        autoWidth: false,
	        bLengthChange: false,
	        ajax:{
	            url: 'fee/list.do',
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
			            { "data": "feeMonth"},
			            { "data": "name"},
						{ "data": "mobile"},
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
						{ "data": "feeState",
						       "render" : function(data,type, row, meta) {
						    	   if(data=='N'){
						    		   return "<span class='label label-danger arrowed-right arrowed-in'>尚未缴费</span>";
						    	   }else{
						    		   return "<span class='label label-success arrowed-right arrowed-in'>已缴费</span>";
						    	   }
								}
						},
						{ "data": "fee" },
						{ "data": "feeTime",
						       "render" : function(data,type, row, meta) {
						    	   if(data==null||data==""){
						    		   return "";
						    	   }else{
						    		   return dateFormat(data,'yyyy-MM-dd');
						    	   }
								}
						}
			        ],
			  "columnDefs": [
			            {
			            	"targets": [10],
			            	"render" : function(data,type,row, meta) {
			            		var str;
			            		if(row.feeState=='N'){
			            			//未缴费
			            			str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="openFee(\''+row.id+'\')"><i class="glyphicon glyphicon-yen"></i>缴费</button>';
			            		}else{
			            			str='<button type="button" class="btn btn-small btn-primary btn-edit" onclick="editFee(\''+row.id+'\','+row.fee+')"><i class="glyphicon glyphicon-yen"></i>修改</button>';
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