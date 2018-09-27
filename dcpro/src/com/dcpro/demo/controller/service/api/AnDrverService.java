package com.dcpro.demo.controller.service.api;

import java.sql.SQLException;

import com.dcpro.demo.controller.dao.AnDriverDaoInterface;
import com.dcpro.demo.controller.dao.api.AnDriverDao;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.service.AnDriverService;

public class AnDrverService implements AnDriverService{

	@Override
	public Driver checkDriverLogin(String drivername, String driverpwd) throws ClassNotFoundException, SQLException {
		  AnDriverDaoInterface dao=new AnDriverDao();
		return dao.checkDriverLogin(drivername, driverpwd);
		  
	}

	@Override
	public boolean setpwd(String driverId, String driverNewPwd) throws ClassNotFoundException, SQLException {
		AnDriverDao dao=new AnDriverDao();
		return dao.setPwd(driverId, driverNewPwd);
	}


    
}
