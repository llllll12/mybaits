package com.dcpro.demo.controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dcpro.demo.controller.po.Task;


public interface TaskDao {
	List<Task> getAllTask() throws ClassNotFoundException, SQLException;
	boolean addTask(Task task) throws Exception;
	public String delTask(String id) throws Exception;
	public String getTaskState(String id)  throws Exception;
	List<Task> selectWaitPai() throws ClassNotFoundException, SQLException;
	void updateTaskState(Task task, Connection con) throws ClassNotFoundException, SQLException;
	
	boolean moblieCheck(String custMobile) throws ClassNotFoundException, SQLException;
	List<Task> searchTask(String id)throws ClassNotFoundException, SQLException;
}
