package com.dcpro.demo.controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.dcpro.demo.controller.dao.DriverDao;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Notice;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.UUIDTool;
import com.sun.org.apache.regexp.internal.recompile;

public class DrivaerDaoimpl implements DriverDao {

	@Override
	public List<Driver> getAllDriver() throws ClassNotFoundException, SQLException {
		List<Driver> list=new ArrayList<Driver>();
		// TODO Auto-generated method stub	
		Driver driver=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID,`NAME`,MOBILE,`PASSWORD`,CAR_NUMBER,CAR_TYPE,WORK_DATE,STATE,CREATE_TIME, UPDATE_TIME from tbl_driver WHERE STATE!='0' order by UPDATE_TIME desc";
		pstmt = con.prepareStatement(sql);
	
		rs = pstmt.executeQuery();
		while(rs.next()) {
			driver=new Driver();
			driver.setId(rs.getString("id"));
			driver.setName(rs.getString("name"));
			driver.setMobile(rs.getString("mobile"));
			driver.setPassword(rs.getString("password"));
			driver.setCarNumber(rs.getString("car_number"));
			driver.setCarType(rs.getString("car_type"));
			driver.setWorkDate(rs.getString("work_date"));
			driver.setState(rs.getString("state"));
			driver.setCreateTime(rs.getDate("create_time"));
			driver.setUpdateTime(rs.getDate("update_time"));
			list.add(driver);
		}
		
		DBUtil.close(rs, pstmt, con);
		return list;

	}

	@Override
	public boolean addDriver(Driver driver) throws Exception {
		DriverDao dao=new DrivaerDaoimpl();
		
		if(dao.getCountBySameMobile(driver.getMobile())>=1 || dao.getCountBySameCarNumber(driver.getCarNumber())>=1) {
			
			 return false;
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "INSERT into tbl_driver(ID,`NAME`,MOBILE,`PASSWORD`,CAR_NUMBER,CAR_TYPE,WORK_DATE,STATE,CREATE_TIME ,CREATE_BY,STOP_STATE) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1, driver.getId());
		pstmt .setString(2, driver.getName());
		pstmt .setString(3, driver.getMobile());
		pstmt .setString(4, driver.getPassword());
		pstmt .setString(5, driver.getCarNumber());
		pstmt .setString(6, driver.getCarType());
		pstmt .setString(7, driver.getWorkDate());
		pstmt .setString(8, driver.getState());
		//将util.state转换成sql.state
		//java.util.Date date=driver.getCreateTime();
		//java.sql.Date=new java.sql.Date(date.getTime());
		
		pstmt .setTimestamp(9,new Timestamp(driver.getCreateTime().getTime()));
		pstmt .setString(10,driver.getCreateBy());
		pstmt .setString(11, driver.getStopState());
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
		
	}

	@Override
	public Driver getDriverById(String id ) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Driver driver2=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID,`NAME`,PASSWORD,CREATE_TIME,MOBILE,CAR_NUMBER,CAR_TYPE,WORK_DATE,STOP_STATE from tbl_driver WHERE ID=? ";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			driver2 = new Driver();
			driver2.setId(rs.getString("id"));
			driver2.setName(rs.getString("name"));
			driver2.setPassword(rs.getString("password"));
			driver2.setMobile(rs.getString("mobile"));
			driver2.setCarNumber(rs.getString("car_number"));
			driver2.setCarType(rs.getString("car_type"));
			driver2.setWorkDate(rs.getString("work_date"));
			driver2.setCreateTime(rs.getDate("create_time"));
			driver2.setStopState(rs.getString("stop_state"));
		}
		DBUtil.close(rs, pstmt, con);
		return driver2;
	
	}

	@Override
	public int getCountBySameName(String name) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_driver WHERE `NAME`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, name);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public int getCountBySameMobile(String mobile) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_driver WHERE `MOBILE`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, mobile);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public int getCountBySameCarNumber(String number) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_driver WHERE `CAR_NUMBER`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, number);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public boolean UpdateDriver(Driver driver) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_driver SET `NAME`=?,MOBILE=?,CAR_NUMBER=?,CAR_TYPE=?,WORK_DATE=?,UPDATE_BY=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1, driver.getName());
		pstmt .setString(2, driver.getMobile());
		pstmt .setString(3, driver.getCarNumber());
		pstmt .setString(4, driver.getCarType());
		pstmt .setString(5, driver.getWorkDate());
		pstmt .setString(6, driver.getUpdateBy());
		//pstmt .setTimestamp(7,new Timestamp(driver.getUpdateTime().getTime()));
		pstmt.setString(7, driver.getId());
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

	@Override
	public boolean delDriver(String id) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_driver SET STATE=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1,Constant.DELETE_STATE );
		pstmt .setString(2,id );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

	@Override
	public boolean statesDriver( Driver driver) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_driver SET STATE=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1, driver.getState());
		pstmt .setString(2,driver.getId() );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

	public Driver getDriverById(String driverId, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		Driver driver2=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT ID,`NAME`,MOBILE,CAR_NUMBER,CAR_TYPE,WORK_DATE,STOP_STATE from tbl_driver WHERE ID=? ";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, driverId);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			driver2 = new Driver();
			driver2.setId(rs.getString("id"));
			driver2.setName(rs.getString("name"));
			driver2.setMobile(rs.getString("mobile"));
			driver2.setCarNumber(rs.getString("car_number"));
			driver2.setCarType(rs.getString("car_type"));
			driver2.setWorkDate(rs.getString("work_date"));
			driver2.setStopState(rs.getString("stop_state"));
		}
		return driver2;
	}

	public boolean addDriverDao(Driver driver, String stationId,Connection con) throws Exception{
		// TODO Auto-generated method stub
		boolean flag=false;
		PreparedStatement prest;
		String sql="insert into tbl_driverorder"
				+ " (id,driver_id,driver_mobile,station_id,instation_time,type) "
				+ "values"
				+ " (?,?,?,?,?,?)";
		prest=con.prepareStatement(sql);
		prest.setString(1, UUIDTool.getUUID());
		prest.setString(2, driver.getId());
		prest.setString(3, driver.getMobile());
		prest.setString(4, stationId);
		prest.setTimestamp(5,new java.sql.Timestamp(new java.util.Date().getTime()));
		prest.setString(6,"A");
		int count=prest.executeUpdate();
		if(count>0) {
			flag=true;
		}
		if (prest != null) {
			prest.close();
		}
		return flag;
	}

	public boolean updateStopState(String driverId, Connection con) throws ClassNotFoundException, SQLException {
		boolean flag=false;
		PreparedStatement pstmt=null;		
		// 连接数据库
		con=DBUtil.getConnection();
		String sql="update tbl_driver set STOP_STATE=1 where id=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, driverId);
		//发送并执行sql
		int count=pstmt.executeUpdate();
		//处理结果
		if (count>0) {
			flag=true;
		}
		return flag;
	

	}

	public boolean updateCancleState(String driverId, Connection con) throws ClassNotFoundException, SQLException {
		boolean flag=false;
		PreparedStatement pstmt=null;		
		// 连接数据库
		con=DBUtil.getConnection();
		String sql="update tbl_driver set STOP_STATE=0 where id=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, driverId);
		//发送并执行sql
		int count=pstmt.executeUpdate();
		//处理结果
		if (count>0) {
			flag=true;
		}
		return flag;
	
	}

	public boolean delStop(String driverId, Connection con) throws SQLException {
		/*int count=-1;
		boolean flag=false;
		PreparedStatement prest;
		String sql="delete from tbl_driverorder WHERE id=?";
		prest=con.prepareStatement(sql);
		prest.setString(1, driverId);
		count=prest.executeUpdate();
		if(count>0) {
			flag=true;
			}
		if (prest != null) {
			prest.close();
		}
		return flag;
*/		
		//Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from tbl_driverorder WHERE DRIVER_ID=?";
		pstmt = con.prepareStatement(sql);
		//pstmt .setString(1,Constant.DELETE_STATE );
		pstmt .setString(1,driverId );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		//DBUtil.close(pstmt, con);
		return flag;
		
	}

	public boolean deleteDriverCheck(String id) throws ClassNotFoundException, SQLException {
		boolean flag = true;
		int state = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		// 发送并执行sql
		String sql = "SELECT COUNT(*) FROM tbl_task WHERE DRIVER_ID=? and (TASK_STATE!='d' or TASK_STATE!='r')";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			state = rs.getInt(1);
		}
		if (state > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean addDriverDao(Driver driver, String stationId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
        Connection conn=null;
		
		boolean flag=false;
		PreparedStatement pstmt;
		// 连接数据库
		conn=DBUtil.getConnection();
		String sql="insert into tbl_driverorder"
				+ " (id,driver_id,driver_mobile,station_id,instation_time,type) "
				+ "values(?,?,?,?,?,?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, UUIDTool.getUUID());
		pstmt.setString(2, driver.getId());
		pstmt.setString(3, driver.getMobile());
		pstmt.setString(4, stationId);
		pstmt.setTimestamp(5,new java.sql.Timestamp(new java.util.Date().getTime()));
		pstmt.setString(6,"A");
		int count=pstmt.executeUpdate();
		if(count>0) {
			flag=true;
		}
		if (pstmt != null) {
			pstmt.close();
		}
		//关闭连接
		DBUtil.close(pstmt, conn);
		return flag;

	}

	@Override
	public List<Notice> getAllNoticer() throws ClassNotFoundException, SQLException {
		List<Notice> list=new ArrayList<Notice>();
		// TODO Auto-generated method stub	
		Notice notice=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID,TITLE,CONTENT,STATE,CREATE_BY,CREATE_TIME FROM tbl_notice";
		pstmt = con.prepareStatement(sql);
	
		rs = pstmt.executeQuery();
		while(rs.next()) {
			notice=new Notice();
			notice.setId(rs.getString("id"));
			notice.setTitle(rs.getString("title"));
			notice.setContent(rs.getString("content"));
			notice.setState(rs.getString("state"));
			notice.setCreate_by(rs.getString("create_by"));
			notice.setCreate_time(rs.getDate("create_time"));
			
			list.add(notice);
		}
		
		DBUtil.close(rs, pstmt, con);
		return list;
	}

	

}
