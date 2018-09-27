package com.dcpro.demo.controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dcpro.demo.controller.po.ReadyTask;

public interface ReadyTaskInterface {


	void insert(com.dcpro.demo.controller.po.ReadyTask record,Connection con) throws ClassNotFoundException, SQLException;
	public List<com.dcpro.demo.controller.po.ReadyTask> selectTime() throws Exception;
	public boolean deleteByid(String id, Connection con) throws ClassNotFoundException, SQLException;
	ReadyTask getReadyTask(String driverId, Connection con) throws ClassNotFoundException, SQLException;
}
