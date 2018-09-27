package com.dcpro.demo.controller.listener;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dcpro.demo.controller.dao.StationDao;
import com.dcpro.demo.controller.dao.TaskDao;
import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.dao.impl.ReadyTaskDaoimpl;
import com.dcpro.demo.controller.dao.impl.StationDaoimpl;
import com.dcpro.demo.controller.dao.impl.TaskDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Task;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.PushUtils;
import com.dcpro.demo.controller.util.RedisUtil;
import com.dcpro.demo.controller.util.UUIDTool;


public class Opera {
	
	public void  operatWaitePai() throws Exception {
		/**
		 * 对于所有的待派单的任务，只在任务表中有数据
		 * 1、查询5条待派的任务，分布指派任务。
		 * 1.1、查询待派任务单所在的站点id，获得在这个站点排队的第一个司机。进行派单.
		 * 1.1.1 获得当前司机的token，将任务信息借助信鸽推送到当前司机手机上。
		 * 1.1.2将当前任务状态改为带接单，并且将司机id保存到任务列表上。
		 * 1.2.3将司机从站点排队表中删除，防止重复派单
		 * 1.2.4将派送的任务保存到已派送任务单中：tbl_readyTask，并保存创建时间。以备超时处理。
		 */
		// 取出任务列表中最近的5条任务进行派单--行级锁
		TaskDao taskDao = new TaskDaoimpl();
		StationDao orderDao = new StationDaoimpl();
		List<Task> taskList = taskDao.selectWaitPai();
		for (Task task : taskList) {
			
			System.out.println("任务ID:" + task.getId() + "=======[任务开始派单]");
			// 开始寻找对应站点的第一个司机(改为 取出一个司机并加锁)
			List<Driver> drivers = orderDao.selectALL(task.getStationId());
			if (drivers.size() > 0) {
				Driver driver=drivers.get(0);		
				// 此时有司机在港 设置任务状态为待接单。
				task.setTaskState(Constant.TASK_WAIT_RECEIPT);// 待接单状态
				// 并且将司机id保存到任务信息表中，表示派给了哪个司机
				task.setDriverId(driver.getDriverId());
				
				// TODO 目前仅仅是修改任务单的状态为待接单状态，后期要改成更新状态和司机id
				Connection con = null;
				try {
					con =  DBUtil.getConnection();
					con.setAutoCommit(false);
					taskDao.updateTaskState(task,con);

					// 平台需要PUSH一条信息给司机，每一个用户的token不一样。这个需要登陆的时候生成，有安卓端进行生成，并登陆时传递到后台服务器，后台服务器获得后根据手机号保存到redis中。使用的时候根据手机号取出并做消息推送。
					// 目前仅仅是获取一条模拟的token，可以使用模拟器接受
					String token = "8da00a585d9af741b0eb4c2d753e2bd32d28a2ea";
					//String token=RedisUtil.getJedis().get(driver.getMobile());
					// 根据token来进行消息推送
					PushUtils push=new PushUtils();
					push.pushAndroidByToken(token, task.getId());
					System.out.println("任务ID:" + task.getId() + ",派给司机手机号:" + driver.getMobile());
					// 插入一条信息在待发送消息列表中
					ReadyTask record = new ReadyTask();
					record.setId(UUIDTool.getUUID());
					record.setCustMobile(task.getCustMobile());
					record.setDriverId(driver.getDriverId());
					record.setDriverMobile(driver.getMobile());// 司机手机号码
					record.setStationId(task.getStationId());
					record.setTaskState("任务ID:" + task.getId());// 消息内容 ，此处列名命名有问题留待后面调整
					record.setType(driver.getCarType());// 司机设备类型
					record.setWaitTime(driver.getInstationTime());// 司机入港时间
					record.setCreatTime(new Date());// 派单时间
					// 对应任务编号
					record.setTaskId(task.getId());
					// TODO 假插入
					new ReadyTaskDaoimpl().insert(record,con);// 此表插入数据说明平台已经对此司机派单了
					System.out.println("任务ID:" + task.getId() + ",派单司机手机号:" + driver.getMobile() + "记录到待发送消息列表中[以备超时判断]");
					//
					// TODO 最后，暂时删除入港表中该司机的信息，避免平台重复对一个人派单。目前这个代码没有任何操作
					orderDao.deleteById(driver.getDriverId(),driver.getStationId(),con);

					con.commit();
					System.out.println("任务ID:" + task.getId() + "修改状态:已派单");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					try {
						System.out.println("回滚");
						con.rollback();
					} catch (SQLException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				} finally {
					try {
						con.close();
					} catch (SQLException e3) {
						// TODO: handle exception
						e3.printStackTrace();
					}
				}
				
			} else {
				// 未找到合适的司机
				System.out.println("任务单号:" + task.getId() + ",站点:" + task.getStationId() + "未有司机可以派单");
			}
		}
       
	}
	
	public void Timeopera() throws Exception {
		// 获取所有的readyTask中超时的信息date2.getTime()-date1.getTime()获得到毫秒）
					TaskDaoimpl taskDao = new TaskDaoimpl();
					DrivaerDaoimpl driverDao = new DrivaerDaoimpl();
					List<ReadyTask> readyTaskList = new ArrayList<ReadyTask>();
					readyTaskList = taskDao.getAllOutTimeReadyTask();
					if(readyTaskList.size()>0) {
					// 遍历这些信息
						for (ReadyTask readyTask : readyTaskList) {
							
							Connection con = null;
							try {
								con = DBUtil.getConnection();
								con.setAutoCommit(false);
								// 1.将tbl_task表中的driver_id，rec_time清空，并将task_state设置成p
								taskDao.updateOutTimeTask(readyTask.getTaskId(),con);
								// 2.将tbl_driverOrder表中新增一条记录 。
								// 其中instation_time（当前时间）driver的各项信息（通过driver_id获取），
								Driver driver = driverDao.getDriverById(readyTask.getDriverId(),con);
								driverDao.addDriverDao(driver, readyTask.getStationId(),con);
								// 3.将这条信息移除
								taskDao.deleteReadyTask(readyTask.getId(),con);
								con.commit();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								try {
									System.out.println("五分钟任务要回滚");
									con.rollback();
								} catch (SQLException e2) {
									// TODO: handle exception
									e2.printStackTrace();
								}
							} finally {
								try {
									con.close();
								} catch (SQLException e3) {
									// TODO: handle exception
									e3.printStackTrace();
								}
							}
							System.out.println("超时司机已返回队列");
							
						}
					}else {
						System.out.println("暂无待接单任务");
					}

				}
}
