package com.dcpro.demo.controller.service;

import java.sql.SQLException;

import com.dcpro.demo.controller.po.Driver;

public interface AnDriverService {
	public Driver checkDriverLogin(String drivername,String driverpwd) throws ClassNotFoundException, SQLException;

	public boolean setpwd(String driverId, String driverNewPwd) throws ClassNotFoundException, SQLException;
}
