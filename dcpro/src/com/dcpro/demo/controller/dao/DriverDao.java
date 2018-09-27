package com.dcpro.demo.controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Notice;

public interface DriverDao {
	List<Driver> getAllDriver() throws ClassNotFoundException, SQLException;
	boolean addDriver(Driver driver) throws Exception;
	Driver getDriverById(String id) throws ClassNotFoundException, SQLException;
	int getCountBySameName(String name) throws ClassNotFoundException, SQLException;
	int getCountBySameCarNumber(String number)throws ClassNotFoundException, SQLException;
	int getCountBySameMobile(String mobile) throws ClassNotFoundException, SQLException;
	boolean UpdateDriver(Driver driver) throws ClassNotFoundException, SQLException;
	boolean delDriver(String id) throws ClassNotFoundException, SQLException;
	boolean statesDriver(Driver driver) throws ClassNotFoundException, SQLException;
	boolean addDriverDao(Driver driver, String stationId) throws ClassNotFoundException, SQLException;
	List<Notice> getAllNoticer() throws ClassNotFoundException, SQLException;
	
	
}
