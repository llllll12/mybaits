package com.dcpro.demo.controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.dcpro.demo.controller.dao.StationDao;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Station;
import com.dcpro.demo.controller.po.Task;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;

public class StationDaoimpl implements StationDao{

	@Override
	public List<Station> getAllStation() throws ClassNotFoundException, SQLException {
		List<Station> list=new ArrayList<Station>();
		Station station=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID, STATION_NAME,TOTAL,STATE,CREATE_BY,CREATE_TIME FROM tbl_station WHERE STATE!='0' order by CREATE_TIME desc";
		pstmt = con.prepareStatement(sql);
	
		rs = pstmt.executeQuery();
		while(rs.next()) {
			station=new Station();
			station.setId(rs.getString("id"));
			station.setStationName(rs.getString("station_name"));
			station.setTotal(rs.getString("total"));
			station.setState(rs.getString("state"));
			station.setState(rs.getString("create_by"));
			station.setCreateTime(rs.getDate("create_time"));
			station.setOccupied(getStationcount(rs.getString("id")));

			list.add(station);
		}
		
		DBUtil.close(rs, pstmt, con);
		return list;
	}

	@Override
	public List<Driver> searchAllDriver(String id) throws ClassNotFoundException, SQLException {
		List<Driver> list=new ArrayList<Driver>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con =DBUtil.getConnection();
		String sql="SELECT `NAME`,mobile,car_number,car_type,"
				+ "INSTATION_TIME FROM  tbl_driverorder,tbl_driver "
				+ "WHERE tbl_driver.id = tbl_driverorder.DRIVER_ID && station_id=? "
				+ "ORDER BY instation_time";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Driver driver = new Driver();
			//driver.setId(rs.getString("id"));
			driver.setName(rs.getString("name"));
			driver.setMobile(rs.getString("mobile"));
			driver.setCarNumber(rs.getString("car_number"));
			driver.setCarType(rs.getString("car_type"));
			driver.setInstationTime(rs.getDate("INSTATION_TIME"));
			list.add(driver);
		}
		DBUtil.close(rs, pstmt, con);
		return list;

	}

	@Override
	public boolean addStation(Station station) throws Exception {
		StationDao dao=new StationDaoimpl();
		if(dao.getCountBySameStation(station.getStationName())==1) {
			 return false;
		}else {

	    Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "INSERT into tbl_station(ID,STATION_NAME,TOTAL,STATE,CREATE_BY,CREATE_TIME) VALUES (?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
	    pstmt.setString(1, station.getId());
	    pstmt.setString(2, station.getStationName());
	    pstmt.setString(3, station.getTotal());
	    pstmt.setString(4, station.getState());
	    pstmt.setString(5, station.getcreateBy());
	    pstmt.setTimestamp(6,new Timestamp (station.getCreateTime().getTime()));

	    boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
		}
	}

	@Override
	public String delStation(String id) throws ClassNotFoundException, SQLException {
	
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_station SET STATE=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1,Constant.DELETE_STATE );
		pstmt .setString(2,id );
		int count=pstmt.executeUpdate();
		if (count > 0) {
			return Constant.STATION_ESC_SECCESS;
		}
		DBUtil.close(pstmt, con);
		return Constant.STATION_ESC_ERROR2;
	}

	@Override
	public int getStationcount(String id) throws ClassNotFoundException, SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM tbl_driverorder WHERE STATION_ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		DBUtil.close(rs, pstmt, con);
		return count;

	}

	@Override
	public List<Map<String, Object>> getStationname() throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT id,STATION_NAME FROM tbl_station WHERE STATE='1'";
		stmt = con.createStatement();
		
		// 处理结果集
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", rs.getString("id"));
			map.put("text", rs.getString("STATION_NAME"));
			list.add(map);
			
		}
		DBUtil.close(rs, stmt, con);
		return list;
	}


	@Override
	public Station getStationById(String id) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 链接数据库
		con = DBUtil.getConnection();
		String sql = "SELECT STATION_NAME,TOTAL from tbl_station WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		Station station = null;
		if (rs.next()) {
			station = new Station();
			station.setStationName(rs.getString("station_name"));
			station.setTotal(rs.getString("total"));
		}
		DBUtil.close(rs, pstmt, con);
		return station;

	}

	@Override
	public boolean UpdateStation(Station station) throws ClassNotFoundException, SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = -1;
		// 链接数据库
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_station SET STATION_NAME=?,TOTAL=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, station.getStationName());
		pstmt.setString(2, station.getTotal());
		pstmt.setString(3, station.getId());
		count = pstmt.executeUpdate();

		if (count > 0) {
			flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;

	}

	@Override
	public void deleteById(String driverId,String stationId, Connection con) throws ClassNotFoundException, SQLException {
		
		PreparedStatement pstmt = null;
		String sql="DELETE FROM tbl_driverorder WHERE DRIVER_ID=? and STATION_ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, driverId);
		pstmt.setString(2, stationId);
		pstmt.executeUpdate();
	}

	@Override
	public List<Driver> selectALL(String stationId) throws Exception {
		// TODO Auto-generated method stub
		List<Driver> list=new ArrayList<Driver>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql="SELECT * from tbl_driverorder WHERE STATION_ID=? ORDER BY INSTATION_TIME asc limit 1";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, stationId);
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Driver order = new Driver();
			order.setId(rs.getString("id"));
			order.setDriverId(rs.getString("DRIVER_ID"));
			order.setMobile(rs.getString("DRIVER_MOBILE"));
			order.setStationId(rs.getString("STATION_ID"));
			order.setInstationTime(rs.getDate("INSTATION_TIME"));
			order.setCarType(rs.getString("type"));
			list.add(order);
		}
		DBUtil.close(rs, pstmt, con);
		return list;
    }

	@Override
	public void adddriver(ReadyTask readyTask, Connection con) throws Exception {
		PreparedStatement pstmt = null;
	
		String sql = "INSERT INTO  tbl_driverorder (ID,DRIVER_ID,DRIVER_MOBILE,STATION_ID,INSTATION_TIME,TYPE)VALUES(?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);	
	    pstmt.setString(1, readyTask.getId());
	    pstmt.setString(2, readyTask.getDriverId());
	    pstmt.setString(3, readyTask.getDriverMobile());
	    pstmt.setString(4, readyTask.getStationId());
	    pstmt.setTimestamp(6,new Timestamp (readyTask.getInstationTime().getTime()));
		int count=pstmt.executeUpdate();	
	}

	@Override
	public int getCountBySameStation(String name) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_station WHERE STATION_NAME=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, name);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public int listDriver(String stationId, String driverId) throws ClassNotFoundException, SQLException {
		//List<Driver> list=new ArrayList<Driver>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=0;
		conn = DBUtil.getConnection();
		String sql = "select m.mingci from(" + 
				"select d.*,@a := @a+1 mingci from" + 
				"(select * from tbl_driverorder where STATION_ID=?) d,(select @a := 0) r ORDER BY d.INSTATION_TIME asc" + 
				")m where DRIVER_ID=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, stationId);
		pstmt.setString(2, driverId);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		DBUtil.close(rs, pstmt, conn);
		return count;

	}

	public int getDriverCount(String id) throws ClassNotFoundException, SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM tbl_driverorder WHERE STATION_ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		DBUtil.close(rs, pstmt, con);
		return count;
	}

}

