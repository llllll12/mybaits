package com.dcpro.demo.controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dcpro.demo.controller.dao.ReadyTaskInterface;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.util.DBUtil;



public class ReadyTaskDaoimpl implements ReadyTaskInterface{

	@Override
	public void insert(com.dcpro.demo.controller.po.ReadyTask record,Connection con) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		// Á´½ÓÊý¾Ý¿â
		
		con = DBUtil.getConnection();
		String sql = "INSERT INTO tbl_readytask(ID,CUST_MOBILE,DRIVER_ID,DRIVER_MOBILE,STATION_ID,TYPE,TASK_STATE,WAITE_TIME,CREATE_TIME,TASK_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, record.getId());
		pstmt.setString(2, record.getCustMobile());
		pstmt.setString(3, record.getDriverId());
		pstmt.setString(4, record.getDriverMobile());
		pstmt.setString(5, record.getStationId());
		pstmt.setString(6, record.getType());
		pstmt.setString(7, record.getTaskState());
		pstmt.setTimestamp(8, new Timestamp(record.getWaitTime().getTime()));
		pstmt.setTimestamp(9, new Timestamp(record.getCreatTime().getTime()));
		pstmt.setString(10, record.getTaskId());
		pstmt.executeUpdate();
	}

	@Override
	public List<com.dcpro.demo.controller.po.ReadyTask> selectTime() throws Exception {
		List<com.dcpro.demo.controller.po.ReadyTask> readyTaskList=new ArrayList<com.dcpro.demo.controller.po.ReadyTask>();
		Connection conn=DBUtil.getConnection();
		String sql="select id,driver_id,task_id,station_id,waite_time,CREATE_TIME from tbl_readytask ORDER BY CREATE_TIME limit 5 for update";
		PreparedStatement prest=conn.prepareStatement(sql);
		ResultSet rs;
	    rs=prest.executeQuery();
		while(rs.next()) {
			Date date=new Date();
			if((date.getTime()-rs.getDate("CREATE_TIME").getTime())>15000) {
				System.out.println(date.getTime()-rs.getDate("CREATE_TIME").getTime());
				com.dcpro.demo.controller.po.ReadyTask readyTask=new com.dcpro.demo.controller.po.ReadyTask();
			    readyTask.setId(rs.getString("id"));
			    readyTask.setTaskId(rs.getString("task_id"));
			    readyTask.setDriverId(rs.getString("driver_id"));
			    readyTask.setStationId(rs.getString("station_id"));
			    readyTaskList.add(readyTask);
			}
		}
		DBUtil.close(rs, prest, conn);
		return readyTaskList;
	}

	public boolean deleteByid(String id, Connection con) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		int count=-1;
		boolean flag=false;
		PreparedStatement prest;
		String sql="delete from tbl_readytask WHERE id=?";
		prest=con.prepareStatement(sql);
		prest.setString(1, id);
		count=prest.executeUpdate();
		if(count==1) {
			flag=true;
			}
		if (prest != null) {
			prest.close();
		}
		return flag;

	}

	@Override
	public ReadyTask getReadyTask(String driverId, Connection con) throws ClassNotFoundException, SQLException {
		Connection conn=DBUtil.getConnection();
		String sql="select id,driver_id,task_id,station_id,TASK_STATE from tbl_readytask WHERE TASK_ID=? for update";
		PreparedStatement prest=conn.prepareStatement(sql);
		prest.setString(1, driverId);
		ResultSet rs;
	    rs=prest.executeQuery();
		ReadyTask readyTask=new ReadyTask();
		while(rs.next()) {
		
			    readyTask.setId(rs.getString("id"));
			    readyTask.setTaskId(rs.getString("task_id"));
			    readyTask.setDriverId(rs.getString("driver_id"));
			    readyTask.setStationId(rs.getString("station_id"));
			    readyTask.setTaskState(rs.getString("task_state"));
			}
		
		return readyTask;
	}

	}
	

