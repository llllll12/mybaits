package com.dcpro.demo.controller.service;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Task;

public interface TaskService {
	List<Task> getAllTask() throws ClassNotFoundException, SQLException;
	boolean addTask(String custMobile, String stationId, String createById) throws Exception ;
	String delTask(String id) throws Exception;
	boolean moblieCheck(String custMobile) throws ClassNotFoundException, SQLException;
	//List<Task> searchTask(Driver driver,Date createTime)throws ClassNotFoundException, SQLException;
	
}
