
<%
			String pathf = request.getContextPath();
			String basePathf = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ pathf + "/";
		%>
<!--[if !IE]> -->
<script type="text/javascript" charset="utf8" src="<%=basePathf%>js/jquery-1.12.4.js"></script>
<script type="text/javascript" charset="utf8" src="<%=basePathf%>js/zy.js"></script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
window.jQuery || document.write("<script src='<%=basePathf%>static/ace/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePathf%>static/ace/js/jquery.mobile.custom.js'>"+ "<"+"/script>");
</script>
<script type="text/javascript" charset="utf8" src="plugins/dataTables-1.10.13/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf8" src="plugins/dataTables-1.10.13/media/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" charset="utf8" src="plugins/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/static/data/china.js"></script>
<!-- Select2 -->
<script
	src="<%=request.getContextPath()%>/plugins/select2/select2.full.min.js"></script>
<!-- wangEditor -->
	<script src="<%=request.getContextPath()%>/plugins/editor/js/wangEditor.js"></script>
<!-- page specific plugin scripts -->

<!-- ace scripts -->
<script src="static/ace/js/ace/elements.scroller.js"></script>
<script src="static/ace/js/ace/elements.colorpicker.js"></script>
<script src="static/ace/js/ace/elements.fileinput.js"></script>
<script src="static/ace/js/ace/elements.typeahead.js"></script>
<script src="static/ace/js/ace/elements.wysiwyg.js"></script>
<script src="static/ace/js/ace/elements.spinner.js"></script>
<script src="static/ace/js/ace/elements.treeview.js"></script>
<script src="static/ace/js/ace/elements.wizard.js"></script>
<script src="static/ace/js/ace/elements.aside.js"></script>
<script src="static/ace/js/ace/ace.js"></script>
<script src="static/ace/js/ace/ace.ajax-content.js"></script>
<script src="static/ace/js/ace/ace.touch-drag.js"></script>
<script src="static/ace/js/ace/ace.sidebar.js"></script>
<script src="static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="static/ace/js/ace/ace.submenu-hover.js"></script>
<script src="static/ace/js/ace/ace.widget-box.js"></script>
<script src="static/ace/js/ace/ace.settings.js"></script>
<script src="static/ace/js/ace/ace.settings-rtl.js"></script>
<script src="static/ace/js/ace/ace.settings-skin.js"></script>
<script src="static/ace/js/ace/ace.widget-on-reload.js"></script>
<script src="static/ace/js/ace/ace.searchbox-autocomplete.js"></script>
<script src="static/ace/js/bootbox.js"></script>
<!-- inline scripts related to this page -->


<script type="text/javascript">
	ace.vars['base'] = '..';
</script>
<script src="static/ace/js/ace/elements.onpage-help.js"></script>
<script src="static/ace/js/ace/ace.onpage-help.js"></script>

<script type="text/javascript" src="static/js/myjs/head.js"></script>
<script type="text/javascript" src="static/js/myjs/index.js"></script>
<!-- jQuery.cookie 1.4.1 -->
<script src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="plugins/attention/drag/drag.js"></script>
<script type="text/javascript" src="plugins/attention/drag/dialog.js"></script>

<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/date.util.js"></script>

<script type="text/javascript" src="js/bootstrapValidator.min.js"></script>


<!-- ztree -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/plugins/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/plugins/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="<%=request.getContextPath()%>/plugins/iCheck/icheck.min.js"></script>

<script type="text/javascript">
	jQuery(function($) {
		$('.easy-pie-chart.percentage')
				.each(
						function() {
							var $box = $(this).closest('.infobox');
							var barColor = $(this).data('color')
									|| (!$box.hasClass('infobox-dark') ? $box
											.css('color')
											: 'rgba(255,255,255,0.95)');
							var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)'
									: '#E2E2E2';
							var size = parseInt($(this).data('size')) || 50;
							$(this).easyPieChart(
									{
										barColor : barColor,
										trackColor : trackColor,
										scaleColor : false,
										lineCap : 'butt',
										lineWidth : parseInt(size / 10),
										animate : /msie\s*(8|7|6)/
												.test(navigator.userAgent
														.toLowerCase()) ? false
												: 1000,
										size : size
									});
						})

		$('.sparkline').each(
				function() {
					var $box = $(this).closest('.infobox');
					var barColor = !$box.hasClass('infobox-dark') ? $box
							.css('color') : '#FFF';
					$(this).sparkline('html', {
						tagValuesAttribute : 'data-values',
						type : 'bar',
						barColor : barColor,
						chartRangeMin : $(this).data('min') || 0
					});
				});

	})
</script>