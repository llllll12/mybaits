package com.dcpro.demo.controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dcpro.demo.controller.dao.TaskDao;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Task;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;

public class TaskDaoimpl implements TaskDao {

	@Override
	public List<Task> getAllTask() throws ClassNotFoundException, SQLException {
		List<Task> list=new ArrayList<Task>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT t.ID,d.`NAME`,t.CUST_MOBILE,t.TASK_STATE,t.CREATE_TIME,t.REC_TIME,s.STATION_NAME,t.DRIVER_ID FROM tbl_task t LEFT JOIN tbl_station s on t.STATION_ID=s.ID LEFT JOIN tbl_driver d on t.DRIVER_ID=d.ID WHERE TASK_STATE!='c' ORDER BY CREATE_TIME desc";
		pstmt = con.prepareStatement(sql);
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Task task = new Task();
			task.setId(rs.getString("id"));
			task.setDriverName(rs.getString("name"));
			task.setCustMobile(rs.getString("cust_mobile"));
			task.setTaskState(rs.getString("TASK_STATE"));
			task.setCreateTime(rs.getDate("CREATE_TIME"));
			task.setRecTime(rs.getDate("REC_TIME"));
			task.setStationName(rs.getString("STATION_NAME"));
			task.setDriverId(rs.getString("DRIVER_ID"));
			list.add(task);
		}

		DBUtil.close(rs, pstmt, con);
		return list;
	}

	@Override
	public boolean addTask(Task task) throws Exception {
		
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = -1;
		// 链接数据库
		con = DBUtil.getConnection();
		String sql = "INSERT INTO tbl_task(ID,CUST_MOBILE,STATION_ID,TASK_STATE,CREATE_BY,CREATE_TIME) VALUES(?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, task.getId());
		pstmt.setString(2, task.getCustMobile());
		pstmt.setString(3, task.getStationId());
		pstmt.setString(4, task.getTaskState());
		pstmt.setString(5, task.getCreateBy());
		pstmt.setTimestamp(6, new Timestamp(task.getCreateTime().getTime()));
		count = pstmt.executeUpdate();
		if (count > 0) {
			flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;

	}

	@Override
	public String delTask(String id) throws Exception {
		String state = getTaskState(id);
		if (state.equals(Constant.TASK_SUCCESS)) {
			return Constant.TASKMSG_SUCCESS_NOTESC;
		} else if (state.equals(Constant.TASK_WOKING)) {
			return Constant.TASKMSG_WOKING_NOTESC;
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		// 链接数据库
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_task SET task_state=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, Constant.TASK_ESC);
		pstmt.setString(2, id);

		int count = pstmt.executeUpdate();

		if (count > 0) {
			return Constant.TASKMSG_ESC_SECCESS;
		}
		DBUtil.close(pstmt, con);
		return Constant.TASKMSG_ESC_ERROR;
	}

	@Override
	public String getTaskState(String id) throws Exception {
		String state = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		// 发送并执行sql
		String sql = "SELECT task_state FROM tbl_task WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			state = rs.getString(1);
		}
		return state;
	}
	
	@Override
	public List<Task> selectWaitPai() throws ClassNotFoundException, SQLException {
        List<Task> list=new ArrayList<Task>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT *FROM tbl_task  WHERE TASK_STATE='p' ORDER BY CREATE_TIME ASC LIMIT 5 FOR UPDATE ";
		pstmt = con.prepareStatement(sql);
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Task task = new Task();
			task.setId(rs.getString("id"));
			task.setCustMobile(rs.getString("cust_mobile"));
			task.setTaskState(rs.getString("TASK_STATE"));
			task.setCreateTime(rs.getDate("CREATE_TIME"));
			task.setRecTime(rs.getDate("REC_TIME"));
			task.setStationId(rs.getString("STATION_ID"));
			task.setDriverId(rs.getString("DRIVER_ID"));
			list.add(task);
		}

		DBUtil.close(rs, pstmt, con);
		return list;
	}

	@Override
	public void updateTaskState(Task task, Connection con) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = null;
		String sql = "UPDATE tbl_task SET TASK_STATE=?, DRIVER_ID=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, task.getTaskState());
		pstmt.setString(2, task.getDriverId());
		pstmt.setString(3, task.getId());
		// 处理结果集
		int count=-1;
		count = pstmt.executeUpdate();
	
		boolean flag=false;
		if (count > 0) {
			flag = true;
		}
		//return flag;
		System.out.println(flag);
		
	}

	public List<ReadyTask> getAllOutTimeReadyTask() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<ReadyTask> readyTaskList=new ArrayList<ReadyTask>();
		Connection conn=DBUtil.getConnection();
		String sql="select id,driver_id,task_id,station_id,waite_time,CREATE_TIME from tbl_readytask ORDER BY CREATE_TIME limit 5 for update";
		PreparedStatement prest=conn.prepareStatement(sql);
		ResultSet rs;
	    rs=prest.executeQuery();
		while(rs.next()) {
			Date date=new Date();
			if((date.getTime()-rs.getDate("CREATE_TIME").getTime())>15000) {
				System.out.println(date.getTime()-rs.getDate("CREATE_TIME").getTime());
			    ReadyTask readyTask=new ReadyTask();
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

	public void updateOutTimeTask(String taskId,Connection con) throws Exception {
	
		PreparedStatement prest;
		String sql="UPDATE tbl_task SET task_state='p',DRIVER_ID=NULL,REC_TIME=NULL WHERE id=?";
		prest=con.prepareStatement(sql);
		prest.setString(1, taskId);
		prest.executeUpdate();

	}

	public boolean deleteReadyTask(String id,Connection con) throws Exception{
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

	public void editJieTime(String taskId, String driverId, Connection con) throws SQLException {
		PreparedStatement prest;
		String sql="  UPDATE tbl_task SET DRIVER_ID=?,task_state=?,REC_TIME=NOW() WHERE id=?";
		prest=con.prepareStatement(sql);
		prest.setString(1, driverId);
		prest.setString(2, Constant.TASK_WOKING);	
		prest.setString(3,taskId );
		prest.executeUpdate();
	}

	public boolean deleteTask(String id, Connection con) throws SQLException {
		
		int count=-1;
		boolean flag=false;
		PreparedStatement prest;
		String sql="delete from tbl_readytask WHERE  task_id=?";
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

	public boolean finishWork(String driverId, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		String sql = "UPDATE tbl_task SET TASK_STATE=? WHERE DRIVER_ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, Constant.TASK_SUCCESS);
		pstmt.setString(2, driverId);
		// 处理结果集
		int count=-1;
		count = pstmt.executeUpdate();
	
		boolean flag=false;
		if (count > 0) {
			flag = true;
		}
		return flag;
	}
	public boolean cancle(String driverId) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		Connection conn=null;
		PreparedStatement pstmt=null;
		conn=DBUtil.getConnection();
		boolean flag=false;
		int count=0;
		String sql="UPDATE tbl_task SET TASK_STATE='m' WHERE DRIVER_ID=? and TASK_STATE='f'";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, driverId);
		count=pstmt.executeUpdate();
		

		if (count > 0) {
			flag = true;
		}
		return flag;
		//DBUtil.close(pstmt, conn);
	}

	@Override
	public boolean moblieCheck(String custMobile) throws ClassNotFoundException, SQLException {
		boolean flag = true;
		int count = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		// 发送并执行sql
		String sql = "SELECT COUNT(*) FROM tbl_task WHERE CUST_MOBILE=? and (TASK_STATE!='c' or TASK_STATE!='f')";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, custMobile);
		// 处理结果集
		rs = pstmt.executeQuery();
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	public int getTaskState1(String id) throws ClassNotFoundException, SQLException {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=-1;
		// 连接数据库
		conn=DBUtil.getConnection();
		String sql="select count(*) from tbl_task where STATION_ID=?"
				+ " and (TASK_STATE='p' or TASK_STATE='d')";
		pstmt=conn.prepareStatement(sql);
		//发送并执行sql
		pstmt.setString(1, id);
		rs=pstmt.executeQuery();
		//处理结果集
		if (rs.next()) {
			count=rs.getInt(1);
		}
		DBUtil.close(rs, pstmt, conn);
		return count;

	}

	@Override
	public List<Task> searchTask(String id) throws ClassNotFoundException, SQLException {
        List<Task> list=new ArrayList<Task>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT t.ID,d.`NAME`,t.CUST_MOBILE,t.TASK_STATE,t.CREATE_TIME,t.REC_TIME,s.STATION_NAME,t.DRIVER_ID FROM tbl_task t LEFT JOIN tbl_station s on t.STATION_ID=s.ID LEFT JOIN tbl_driver d on t.DRIVER_ID=d.ID WHERE DRIVER_ID=? AND TASK_STATE!='m'  ORDER BY CREATE_TIME desc";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Task task = new Task();
			task.setId(rs.getString("id"));
			task.setDriverName(rs.getString("name"));
			task.setCustMobile(rs.getString("cust_mobile"));
			task.setTaskState(rs.getString("TASK_STATE"));
			task.setCreateTime(rs.getDate("CREATE_TIME"));
			task.setRecTime(rs.getDate("REC_TIME"));
			task.setStationName(rs.getString("STATION_NAME"));
			task.setDriverId(rs.getString("DRIVER_ID"));
			list.add(task);
		}

		DBUtil.close(rs, pstmt, con);
		return list;
	}
	public List<Task> searchTimeTask(String id,Date createTime) throws ClassNotFoundException, SQLException {
        List<Task> list=new ArrayList<Task>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT t.ID,d.`NAME`,t.CUST_MOBILE,t.TASK_STATE,t.CREATE_TIME,t.REC_TIME,s.STATION_NAME,t.DRIVER_ID FROM tbl_task t LEFT JOIN tbl_station s on t.STATION_ID=s.ID LEFT JOIN tbl_driver d on t.DRIVER_ID=d.ID WHERE DRIVER_ID=? AND t.CREATE_TIME BETWEEN ? AND NOW()  ORDER BY CREATE_TIME desc";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setTimestamp(2,new Timestamp(createTime.getTime()));
		// 处理结果集
		rs = pstmt.executeQuery();
		while (rs.next()) {
			Task task = new Task();
			task.setId(rs.getString("id"));
			task.setDriverName(rs.getString("name"));
			task.setCustMobile(rs.getString("cust_mobile"));
			task.setTaskState(rs.getString("TASK_STATE"));
			task.setCreateTime(rs.getDate("CREATE_TIME"));
			task.setRecTime(rs.getDate("REC_TIME"));
			task.setStationName(rs.getString("STATION_NAME"));
			task.setDriverId(rs.getString("DRIVER_ID"));
			list.add(task);
		}

		DBUtil.close(rs, pstmt, con);
		return list;
	}

	public boolean searchState(String id, String driverState) throws ClassNotFoundException, SQLException {
		Connection con=null;
		ResultSet rs=null;
		int count=-1;
		// 连接数据库
		con=DBUtil.getConnection();
		PreparedStatement pstmt = null;
		if(!Constant.TASK_SUCCESS.equals(driverState)) {
			String sql = "UPDATE tbl_task SET TASK_STATE=? WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Constant.TASK_SUCCESS);
			pstmt.setString(2, id);
			// 处理结果集
	        count = pstmt.executeUpdate();	
		}else {
			count=0;
			
		}
		boolean flag=false;
		if (count > 0) {
			flag = true;
		}
		return flag;
		
	}

	public List<Task> getNewTask(String id) throws ClassNotFoundException, SQLException {
		 List<Task> list=new ArrayList<Task>();
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			con = DBUtil.getConnection();
			String sql = "SELECT t.ID,d.`NAME`,t.CUST_MOBILE,t.TASK_STATE,t.CREATE_TIME,t.REC_TIME,s.STATION_NAME,t.DRIVER_ID FROM tbl_task t LEFT JOIN tbl_station s on t.STATION_ID=s.ID LEFT JOIN tbl_driver d on t.DRIVER_ID=d.ID WHERE DRIVER_ID=? AND TASK_STATE!='m' ORDER BY CREATE_TIME desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 处理结果集
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Task task = new Task();
				task.setId(rs.getString("id"));
				task.setDriverName(rs.getString("name"));
				task.setCustMobile(rs.getString("cust_mobile"));
				task.setTaskState(rs.getString("TASK_STATE"));
				task.setCreateTime(rs.getDate("CREATE_TIME"));
				task.setRecTime(rs.getDate("REC_TIME"));
				task.setStationName(rs.getString("STATION_NAME"));
				task.setDriverId(rs.getString("DRIVER_ID"));
				list.add(task);
			}

			DBUtil.close(rs, pstmt, con);
			return list;
	}

	

}
