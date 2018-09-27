package com.dcpro.demo.controller.dao;

import java.sql.SQLException;

import com.dcpro.demo.controller.po.Driver;

public interface AnDriverDaoInterface {
	 Driver checkDriverLogin(String drivername,String driverpwd) throws ClassNotFoundException, SQLException;
}
