//添加司机
function openDriver() {
	$("#driver_form").attr("action", "DriverSerlvet?action=add");
	$("#name").val("");//
	$("#mobile").val("");
	$("#carNumber").val("");
	$("#carType").val("");
	$('#myModal').modal('show');// 显示修改
}
//添加客服
function openCs() {
	$("#cs_form").attr("action", "CsServlet?action=addcs");
	$("#name").val("");//
	$("#mobile").val("");
	$("#account").val("");
	$('#myModal').modal('show');// 显示修改
}
//添加系统消息
function openMsg() {
	$("#msg_form").attr("action", "msg/add.do");
	$("#content").html("");
	$('#myModal').modal('show');// 显示修改
}

//添加车站
function openStation() {
	$("#station_form").attr("action", "StationServlet?action=addstation");
	$("#stationName").val("");
	$("#total").val("");
	$('#myModal').modal('show');// 显示修改
}
//添加任务
function openTask() {
	$("#task_form").attr("action", "TaskServlet?action=addlist");
	$("#custMobile").val("");
	$.ajax({
		url : 'StationServlet?action=getallstation',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(d, textStatus, jqXHR) {
			$("#stationId").select2({
				  data: d
			});
		},
		error : function(xhr, textStatus) {
			alert("加载站点信息失败");
		}
	});
	$('#myModal').modal('show');// 显示修改
}

//打开编辑
function editDriver(id) {
	$.ajax({
		url : 'DriverSerlvet?action=getDriverById',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + id,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#driver_form").attr("action", "DriverSerlvet?action=update");
			$("#name").val(data.name);//
			$("#mobile").val(data.mobile);
			$("#carNumber").val(data.carNumber);
			$("#carType").val(data.carType);
			$("#workDate").val(data.workDate);
			$("#id").val(data.id);
			$('#myModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("操作失败");
		}
	});
}

//缴费
function openFee(driverId) {
	$.ajax({
		url : 'driver/id2bean.do',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + driverId,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#fee_form").attr("action", "fee/add.do");
			$("#name").val(data.name);
			$("#mobile").val(data.mobile);
			$("#carNumber").val(data.carNumber);
			$("#carType").val(data.carType);
			$("#driverId").val(driverId);
			if(data.workDate=='1'){
				$("#workdate").val("夜班");
			}else{
				$("#workdate").val("白班");
			}
			$("#fee").val("");
			$('#myModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("加载计费信息失败");
		}
	});
}

//修改缴费
function editFee(driverId,fee) {
	$.ajax({
		url : 'driver/id2bean.do',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + driverId,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#fee_form").attr("action", "fee/edit.do");
			$("#name").val(data.name);
			$("#mobile").val(data.mobile);
			$("#carNumber").val(data.carNumber);
			$("#carType").val(data.carType);
			$("#driverId").val(driverId);
			if(data.workDate=='1'){
				$("#workdate").val("夜班");
			}else{
				$("#workdate").val("白班");
			}
			$("#fee").val(fee);
			$('#myModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("加载计费信息失败");
		}
	});
}

//查看司机信息===任务
function showDriver(driverId) {
	$.ajax({
		url : 'DriverSerlvet?action=getDriverById',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + driverId,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#name").val(data.name);
			$("#mobile").val(data.mobile);
			$("#carNumber").val(data.carNumber);
			$("#carType").val(data.carType);
			if(data.workDate=='1'){
				$("#workdate").val("夜班");
			}else{
				$("#workdate").val("白班");
			}
			$('#driverModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("加载司机信息失败");
		}
	});
}

//取消任务===需要深思
function cancelTask(driverId) {
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定取消任务吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'TaskServlet?action=delTask',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + driverId,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : data.message,
							title : "操作消息",
						});
						// 刷新当前页面
						taskTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message :data.message,
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}


//打开编辑站点
function editStation(id) {
	$.ajax({
		url : 'StationServlet?action=getStationById',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + id,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#station_form").attr("action", "StationServlet?action=editStation");
			$("#stationName").val(data.stationName);
			$("#total").val(data.total);
			$("#id").val(data.id);
			$('#myModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("操作失败");
		}
	});
}

//打开编辑
function editCs(id) {
	$.ajax({
		url : 'CsServlet?action=getCsById',
		type : 'POST', // GET
		async : true, // 或false,是否异步
		data : 'id=' + id,
		timeout : 5000, // 超时时间
		dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data, textStatus, jqXHR) {
			$("#cs_form").attr("action", "CsServlet?action=updatecs");
			$("#name").val(data.name);//
			$("#mobile").val(data.mobile);
			$("#account").val(data.account);
			$("#id").val(data.id);
			$('#myModal').modal('show');// 显示修改
		},
		error : function(xhr, textStatus) {
			alert("操作失败");
		}
	});
}
//删除司机
function delDriver(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要删除该司机吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'DriverSerlvet?action=delDriver',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "删除成功",
							title : "操作消息",
						});
						// 刷新当前页面
						driverTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "任务未完成，删除失败",
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}

//改变司机状态
function statusDriver(d,s){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确认本操作吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'DriverSerlvet?action=statesDriver',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : {id:d,status:s},
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "修改成功",
							title : "操作消息",
						});
						// 刷新当前页面
						driverTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "修改失败",
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}

//删除客服
function delCs(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要删除该客服吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'CsServlet?action=delcs',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "删除成功",
							title : "操作消息",
						});
						// 刷新当前页面
						csTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "删除失败",
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}
//删除系统消息
function delMsg(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要删除该消息吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'msg/del.do',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : data.message,
							title : "操作消息",
						});
						// 刷新当前页面
						msgTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : data.message,
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}

//删除车站
function delStation(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要删除该站点吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'StationServlet?action=delstation',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message :data.message,
							title : "操作消息",
						});
						// 刷新当前页面
						window.location.href="StationServlet?action=stationlist";
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message :data.message,
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}

//强制从排队中清除
function testDel(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要删除该站点吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'outStack.do',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'driverId=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : data.message,
							title : "操作消息",
						});
						// 刷新当前页面
						driverTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : '请求失败',
							title : "操作消息",
						});
						// 刷新当前页面
						driverTable.api().ajax.reload();
					}
				});
			}
		},
		title : "确认框",
	});
}

//清理堵塞数据
function clearData(){
	   bootbox.confirm({
			buttons : {
				confirm : {
					label : '确定',
					className : 'btn-myStyle'
				},
				cancel : {
					label : '取消',
					className : 'btn-default'
				}
			},
			message : '确定要清理堵塞数据吗?',
			callback : function(result) {
				if (result) {
					// 发送请求删除数据
					$.ajax({
						url : 'clearData.do',
						type : 'POST', // GET
						timeout : 5000, // 超时时间
						dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
						success : function(data, textStatus, jqXHR) {
							bootbox.alert({
								buttons : {
									ok : {
										label : '确定',
										className : 'btn-myStyle'
									}
								},
								message : data.message,
								title : "操作消息",
							});
						},
						error : function(xhr, textStatus) {
							bootbox.alert({
								buttons : {
									ok : {
										label : '确定',
										className : 'btn-myStyle'
									}
								},
								message : '请求服务器失败',
								title : "操作消息",
							});
						}
					});
				}
			},
			title : "确认框",
		});
}

//重置密码
function resetPwd(id){
	bootbox.confirm({
		buttons : {
			confirm : {
				label : '确定',
				className : 'btn-myStyle'
			},
			cancel : {
				label : '取消',
				className : 'btn-default'
			}
		},
		message : '确定要重置密码吗?',
		callback : function(result) {
			if (result) {
				// 发送请求删除数据
				$.ajax({
					url : 'CsServlet?action=resetPwd',
					type : 'POST', // GET
					async : true, // 或false,是否异步
					data : 'id=' + id,
					timeout : 5000, // 超时时间
					dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
					success : function(data, textStatus, jqXHR) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message : "重置密码成功",
							title : "操作消息",
						});
						// 刷新当前页面
						csTable.api().ajax.reload();
					},
					error : function(xhr, textStatus) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定',
									className : 'btn-myStyle'
								}
							},
							message :  "重置密码失败",
							title : "操作消息",
						});
					}
				});
			}
		},
		title : "确认框",
	});
}




$(function(){
	//提交计费信息
	$("#btn_fee_submit").unbind("click");
	$("#btn_fee_submit").click(function(e) {
		e.preventDefault();
		$('#fee_form').data('bootstrapValidator').validate();
		if (!$('#fee_form').data('bootstrapValidator').isValid()) {
			return;
		}
		document.fee_form.submit();
	});
	
	//提交添加任务
	$("#btn_task_submit").unbind("click");
	$("#btn_task_submit").click(function(e) {
		e.preventDefault();
		$('#task_form').data('bootstrapValidator').validate();
		if (!$('#task_form').data('bootstrapValidator').isValid()) {
			return;
		}
		document.task_form.submit();
	});
	//提交添加司机
	$("#btn_driver_submit").unbind("click");
	$("#btn_driver_submit").click(function(e) {
		e.preventDefault();
		$('#driver_form').data('bootstrapValidator').validate();
		if (!$('#driver_form').data('bootstrapValidator').isValid()) {
			return;
		}
		document.driver_form.submit();
	});
	//提交添加客服
	$("#btn_cs_submit").unbind("click");
	$("#btn_cs_submit").click(function(e) {
		e.preventDefault();
		$('#cs_form').data('bootstrapValidator').validate();
		if (!$('#cs_form').data('bootstrapValidator').isValid()) {
			return;
		}
		document.cs_form.submit();
	});
	//站点信息提交
	$("#btn_station_submit").unbind("click");
	$("#btn_station_submit").click(function(e) {
		e.preventDefault();
		$('#station_form').data('bootstrapValidator').validate();
		if (!$('#station_form').data('bootstrapValidator').isValid()) {
			return;
		}
		document.station_form.submit();
	});
	//提交系统消息
	$("#btn_msg_submit").unbind("click");
	$("#btn_msg_submit").click(function(e) {
		e.preventDefault();
		var t="";
		var t_content=$("#content").val();
		alert(t_content);
		if(t_content.length<=20){
			t = t_content.substr(0,t_content.length);
		}else{
			t = t_content.substr(0,20);
		}
    	var con = $("#content").val();
    	//异步请求提交参数
    	$.ajax({
			url : 'msg/add.do',
			type : 'POST', // GET
			async : true, // 或false,是否异步
			data : {
				title:t,
				content:con
			},
			timeout : 5000, // 超时时间
			dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
			success : function(data, textStatus, jqXHR) {
				$('#myModal').modal('hide');// 关闭
				
				bootbox.alert({
					buttons : {
						ok : {
							label : '确定',
							className : 'btn-myStyle'
						}
					},
					message : data.message,
					title : "操作消息",
				});
				// 刷新当前页面
				msgTable.api().ajax.reload();
			},
			error : function(xhr, textStatus) {
				bootbox.alert({
					buttons : {
						ok : {
							label : '确定',
							className : 'btn-myStyle'
						}
					},
					message : "出现错误",
					title : "操作消息",
				});
			}
		});
	});
});


