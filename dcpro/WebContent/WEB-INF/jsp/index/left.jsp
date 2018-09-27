<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						
						<button class="btn btn-info" onclick="changeMenus();" title="切换菜单">
							<i class="ace-icon fa fa-pencil"></i>
						</button>
						
						<button class="btn btn-success" onclick="alert('这只是个图标而已,不是按钮');">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning" title="" id="adminzidian" onclick="alert('这只是个图标而已,不是按钮');">
							<i class="ace-icon fa fa-book"></i>
						</button>

						<button class="btn btn-danger" onclick="alert('这只是个图标而已,不是按钮');">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->

					<ul class="nav nav-list">
						<!--  
						<li>
							<a href="typography.html">
								<i class="glyphicon glyphicon-adjust"></i>
								<span class="menu-text"> 文字排版 </span>
							</a>
						</li>
						-->
						<c:if test="${loginuser.isAdmin=='a' }">
						<li>
							<a href="#" class="dropdown-toggle">
								<i class="glyphicon glyphicon-th-large"></i>
								<span class="menu-text"> 基础信息</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li>
									<a href="GoTo?name=driver/driver_list" target="mainFrame">
										<i class="icon-cog"></i>
										司机管理
									</a>
								</li>
								
								<li>
									<a href="GoTo?name=cs/cs_list" target="mainFrame">
										<i class="icon-double-angle-right"></i>
										客服管理
									</a>
								</li>
								<li>
									<a href="StationServlet?action=stationlist" target="mainFrame">
										<i class="icon-double-angle-right"></i>
										站点管理
									</a>
								</li>
							</ul>
						</li>
						</c:if>
						<!-- 信息发布 -->
						<li>
							<a href="#" class="dropdown-toggle">
								<i class="glyphicon glyphicon-th-large"></i>
								<span class="menu-text"> 信息管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
							    <li>
									<a href="GoTo?name=task/task_list" target="mainFrame">
										<i class="icon-cog"></i>
										发布任务
									</a>
								</li>
								<li>
									<a href="GoTo/red.do?name=msg/msg_list" target="mainFrame">
										<i class="icon-cog"></i>
										发布消息
									</a>
								</li>

							</ul>
						</li>
					    <!-- 计费列表 -->
						<li>
							<a href="#" class="dropdown-toggle">
								<i class="glyphicon glyphicon-th-large"></i>
								<span class="menu-text"> 计费信息</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
							    <li>
									<a href="page/red.do?name=fee/fee_list" target="mainFrame">
										<i class="icon-cog"></i>
										缴费管理
									</a>
								</li>
								<li>
									<a href="page/red.do?name=fee/fee_hisList" target="mainFrame">
										<i class="icon-cog"></i>
										历史账单
									</a>
								</li>
								
								<li>
									<a href="page/red.do?name=system/driver_list" target="mainFrame">
										<i class="icon-cog"></i>
										系统设置
									</a>
								</li>
					
					</ul>
					<!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
