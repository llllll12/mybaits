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
		 * �������еĴ��ɵ�������ֻ���������������
		 * 1����ѯ5�����ɵ����񣬷ֲ�ָ������
		 * 1.1����ѯ�����������ڵ�վ��id����������վ���Ŷӵĵ�һ��˾���������ɵ�.
		 * 1.1.1 ��õ�ǰ˾����token����������Ϣ�����Ÿ����͵���ǰ˾���ֻ��ϡ�
		 * 1.1.2����ǰ����״̬��Ϊ���ӵ������ҽ�˾��id���浽�����б��ϡ�
		 * 1.2.3��˾����վ���Ŷӱ���ɾ������ֹ�ظ��ɵ�
		 * 1.2.4�����͵����񱣴浽�����������У�tbl_readyTask�������洴��ʱ�䡣�Ա���ʱ����
		 */
		// ȡ�������б��������5����������ɵ�--�м���
		TaskDao taskDao = new TaskDaoimpl();
		StationDao orderDao = new StationDaoimpl();
		List<Task> taskList = taskDao.selectWaitPai();
		for (Task task : taskList) {
			
			System.out.println("����ID:" + task.getId() + "=======[����ʼ�ɵ�]");
			// ��ʼѰ�Ҷ�Ӧվ��ĵ�һ��˾��(��Ϊ ȡ��һ��˾��������)
			List<Driver> drivers = orderDao.selectALL(task.getStationId());
			if (drivers.size() > 0) {
				Driver driver=drivers.get(0);		
				// ��ʱ��˾���ڸ� ��������״̬Ϊ���ӵ���
				task.setTaskState(Constant.TASK_WAIT_RECEIPT);// ���ӵ�״̬
				// ���ҽ�˾��id���浽������Ϣ���У���ʾ�ɸ����ĸ�˾��
				task.setDriverId(driver.getDriverId());
				
				// TODO Ŀǰ�������޸����񵥵�״̬Ϊ���ӵ�״̬������Ҫ�ĳɸ���״̬��˾��id
				Connection con = null;
				try {
					con =  DBUtil.getConnection();
					con.setAutoCommit(false);
					taskDao.updateTaskState(task,con);

					// ƽ̨��ҪPUSHһ����Ϣ��˾����ÿһ���û���token��һ���������Ҫ��½��ʱ�����ɣ��а�׿�˽������ɣ�����½ʱ���ݵ���̨����������̨��������ú�����ֻ��ű��浽redis�С�ʹ�õ�ʱ������ֻ���ȡ��������Ϣ���͡�
					// Ŀǰ�����ǻ�ȡһ��ģ���token������ʹ��ģ��������
					String token = "8da00a585d9af741b0eb4c2d753e2bd32d28a2ea";
					//String token=RedisUtil.getJedis().get(driver.getMobile());
					// ����token��������Ϣ����
					PushUtils push=new PushUtils();
					push.pushAndroidByToken(token, task.getId());
					System.out.println("����ID:" + task.getId() + ",�ɸ�˾���ֻ���:" + driver.getMobile());
					// ����һ����Ϣ�ڴ�������Ϣ�б���
					ReadyTask record = new ReadyTask();
					record.setId(UUIDTool.getUUID());
					record.setCustMobile(task.getCustMobile());
					record.setDriverId(driver.getDriverId());
					record.setDriverMobile(driver.getMobile());// ˾���ֻ�����
					record.setStationId(task.getStationId());
					record.setTaskState("����ID:" + task.getId());// ��Ϣ���� ���˴��������������������������
					record.setType(driver.getCarType());// ˾���豸����
					record.setWaitTime(driver.getInstationTime());// ˾�����ʱ��
					record.setCreatTime(new Date());// �ɵ�ʱ��
					// ��Ӧ������
					record.setTaskId(task.getId());
					// TODO �ٲ���
					new ReadyTaskDaoimpl().insert(record,con);// �˱��������˵��ƽ̨�Ѿ��Դ�˾���ɵ���
					System.out.println("����ID:" + task.getId() + ",�ɵ�˾���ֻ���:" + driver.getMobile() + "��¼����������Ϣ�б���[�Ա���ʱ�ж�]");
					//
					// TODO �����ʱɾ����۱��и�˾������Ϣ������ƽ̨�ظ���һ�����ɵ���Ŀǰ�������û���κβ���
					orderDao.deleteById(driver.getDriverId(),driver.getStationId(),con);

					con.commit();
					System.out.println("����ID:" + task.getId() + "�޸�״̬:���ɵ�");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					try {
						System.out.println("�ع�");
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
				// δ�ҵ����ʵ�˾��
				System.out.println("���񵥺�:" + task.getId() + ",վ��:" + task.getStationId() + "δ��˾�������ɵ�");
			}
		}
       
	}
	
	public void Timeopera() throws Exception {
		// ��ȡ���е�readyTask�г�ʱ����Ϣdate2.getTime()-date1.getTime()��õ����룩
					TaskDaoimpl taskDao = new TaskDaoimpl();
					DrivaerDaoimpl driverDao = new DrivaerDaoimpl();
					List<ReadyTask> readyTaskList = new ArrayList<ReadyTask>();
					readyTaskList = taskDao.getAllOutTimeReadyTask();
					if(readyTaskList.size()>0) {
					// ������Щ��Ϣ
						for (ReadyTask readyTask : readyTaskList) {
							
							Connection con = null;
							try {
								con = DBUtil.getConnection();
								con.setAutoCommit(false);
								// 1.��tbl_task���е�driver_id��rec_time��գ�����task_state���ó�p
								taskDao.updateOutTimeTask(readyTask.getTaskId(),con);
								// 2.��tbl_driverOrder��������һ����¼ ��
								// ����instation_time����ǰʱ�䣩driver�ĸ�����Ϣ��ͨ��driver_id��ȡ����
								Driver driver = driverDao.getDriverById(readyTask.getDriverId(),con);
								driverDao.addDriverDao(driver, readyTask.getStationId(),con);
								// 3.��������Ϣ�Ƴ�
								taskDao.deleteReadyTask(readyTask.getId(),con);
								con.commit();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								try {
									System.out.println("���������Ҫ�ع�");
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
							System.out.println("��ʱ˾���ѷ��ض���");
							
						}
					}else {
						System.out.println("���޴��ӵ�����");
					}

				}
}
