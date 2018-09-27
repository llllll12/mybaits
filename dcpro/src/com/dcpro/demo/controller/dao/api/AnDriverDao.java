package com.dcpro.demo.controller.dao.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dcpro.demo.controller.dao.AnDriverDaoInterface;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.MD5Util;

public class AnDriverDao implements AnDriverDaoInterface {

	@Override
	public Driver checkDriverLogin(String drivername, String driverpwd) throws ClassNotFoundException, SQLException {
		Driver driver=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT `NAME`,`PASSWORD`,CAR_NUMBER,STATE,STOP_STATE,ID,TASK_TIME,MOBILE from tbl_driver WHERE `NAME`=? AND `PASSWORD`=? AND STATE!='0'";
		pstmt = con.prepareStatement(sql);
	    pstmt.setString(1, drivername);
	    pstmt.setString(2, driverpwd);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			driver=new Driver();
			driver.setId(rs.getString("id"));
			driver.setName(rs.getString("name"));
			
			driver.setPassword(rs.getString("password"));
			driver.setCarNumber(rs.getString("car_number"));
			
			driver.setStopState(rs.getString("stop_state"));
			driver.setState(rs.getString("state"));
			driver.setTaskTime(rs.getDate("task_time"));
			driver.setMobile(rs.getString("mobile"));
	
		}
		
		DBUtil.close(rs, pstmt, con);
		return driver;
	}

	public boolean setPwd(String driverId, String driverNewPwd) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		//String md5pwd = MD5Util.makePassword(driverNewPwd);
		String sql = "UPDATE tbl_DRIVER SET PASSWORD=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1,driverNewPwd);
		pstmt .setString(2,driverId );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

}
