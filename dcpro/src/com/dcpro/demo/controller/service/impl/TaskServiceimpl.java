package com.dcpro.demo.controller.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;



import com.dcpro.demo.controller.dao.TaskDao;
import com.dcpro.demo.controller.dao.impl.TaskDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Task;
import com.dcpro.demo.controller.service.TaskService;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.UUIDTool;

public class TaskServiceimpl implements TaskService{

	@Override
	public List<Task> getAllTask() throws ClassNotFoundException, SQLException {
		TaskDao dao=new TaskDaoimpl();
		return dao.getAllTask();
	}

	@Override
	public boolean addTask(String custMobile, String stationId, String createById) throws Exception {
		boolean flag = false;
		Task task = new Task();
		task.setId(UUIDTool.getUUID());
		task.setCustMobile(custMobile);
		task.setStationId(stationId);
		task.setTaskState(Constant.TASK_WAIT_DISPATCH);
		task.setCreateBy(createById);
		task.setCreateTime(new Date());
		TaskDao dao = new TaskDaoimpl();
		flag = dao.addTask(task);
		
		return flag;

	}
	@Override
	public String delTask(String id) throws Exception {
	
       TaskDao dao = new TaskDaoimpl();
		return dao.delTask(id);

	}

	@Override
	public boolean moblieCheck(String custMobile) throws ClassNotFoundException, SQLException {
		TaskDao dao = new TaskDaoimpl();
		boolean flag = false;
		flag = dao.moblieCheck(custMobile);
		return flag;
	}

	public List<Task> searchTask(Driver driver, Date createTime) throws ClassNotFoundException, SQLException {
		TaskDaoimpl dao=new TaskDaoimpl();
		if(null==createTime) {	
			return dao.searchTask(driver.getId());
		}else {
			return dao.searchTimeTask(driver.getId(), createTime);
		}		
	}

	public boolean searchTask(String driverId, String driverState) throws ClassNotFoundException, SQLException {
		TaskDaoimpl dao=new TaskDaoimpl();
		return dao.searchState(driverId,driverState);
	}

	public List<Task> getNewTask(String id) throws ClassNotFoundException, SQLException {
		TaskDaoimpl dao=new TaskDaoimpl();
		return dao.getNewTask(id);
	}

	
}
