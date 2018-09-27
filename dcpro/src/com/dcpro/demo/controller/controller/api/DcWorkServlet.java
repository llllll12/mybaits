package com.dcpro.demo.controller.controller.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dcpro.demo.controller.dao.DriverDao;
import com.dcpro.demo.controller.dao.StationDao;
import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.dao.impl.ReadyTaskDaoimpl;
import com.dcpro.demo.controller.dao.impl.StationDaoimpl;
import com.dcpro.demo.controller.dao.impl.TaskDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Notice;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Station;
import com.dcpro.demo.controller.po.Task;
import com.dcpro.demo.controller.service.DriverService;
import com.dcpro.demo.controller.service.TaskService;
import com.dcpro.demo.controller.service.impl.DriverServiceimpl;
import com.dcpro.demo.controller.service.impl.TaskServiceimpl;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.DataTablePageUtil;
import com.dcpro.demo.controller.util.RedisUtil;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class DcWorkServlet
 */
@WebServlet("/DcWorkServlet")
public class DcWorkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action=request.getParameter("action");
		switch (action) {
		case "judan":
            	this.judan(request,response);
			break;
		case "jiedan":	
			try {
				this.jiedan(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
       case "finish":
			try {
				this.finish(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
       case "stop":
    	   driverstop(request,response);break;
       case "canclestop":
    	   canclestop(request,response);break;
       case "searchTask":
			try {
				searchTask(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
			
       case "searchDriver":
			try {
				searchDriver(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
       case "delTask" :
			try {
				delTask(request,response);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       case "changeState":
			
			try {
				changeState(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
       case "noticeMsg":
			try {
				noticeMsg(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
       case "changeDriver":
    	   changeDriver(request,response);
		default:
			break;
		}
	}
	private void changeDriver(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String driverId = request.getParameter("driverId");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String carNumber = request.getParameter("carNumber");
		String carType = request.getParameter("carType");
		String workDate = request.getParameter("workDate");
		
		Driver newDriver=new Driver();
		newDriver.setId(driverId);
		newDriver.setName(name);
		newDriver.setMobile(mobile);
		newDriver.setCarNumber(carNumber);
		newDriver.setCarType(carType);
		newDriver.setWorkDate(workDate);
		
		Driver oldDriver=new Driver();
		DriverService service=new DriverServiceimpl();
		String result=null;
		try {
			oldDriver=service.getDriverById(driverId);
			result=service.updateDriver(newDriver, oldDriver);
			Driver driver=new Driver();
			driver.setMessage(result);
			
			ObjectMapper mapper = new ObjectMapper();
			String driMsg = null;
			driMsg = mapper.writeValueAsString(driver);
			response.getWriter().write(driMsg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	private void delTask(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		String id = request.getParameter("id");
		boolean flag=false;
		TaskDaoimpl taskDaoimpl=new TaskDaoimpl();
		flag=taskDaoimpl.cancle(id);
		System.out.println(flag);
		
		if(flag) {
			response.getWriter().write("{'cancleMsg':'清空任务成功'}");
		}else {
			response.getWriter().write("{'cancleMsg':'清空任务失败'}");
		}
		
		/*TaskServiceimpl service = new TaskServiceimpl();
		List<Task> taskList = service.getNewTask(id);
		DataTablePageUtil<Task> data = new DataTablePageUtil<Task>();
		data.setData(taskList);
		ObjectMapper mapper = new ObjectMapper();
		String taskListString = null;
		try {
			taskListString = mapper.writeValueAsString(data);
			response.getWriter().write(taskListString);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}


	private void noticeMsg(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String noticeId=request.getParameter("noticeId");
		DriverService service=new DriverServiceimpl();
		//Notice notice=new Notice();
		List<Notice> noticelist=service.getNoticeById(noticeId);
		DataTablePageUtil<Notice> data=new DataTablePageUtil<Notice>();
		data.setData(noticelist);
		ObjectMapper mapper = new ObjectMapper();
		String driverString = null;
		try {
			driverString = mapper.writeValueAsString(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(driverString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void changeState(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		String taskId=request.getParameter("taskId");
		String driverState=request.getParameter("driverState");
	
		TaskServiceimpl service2=new TaskServiceimpl();
		boolean flag=false;
		flag=service2.searchTask(taskId,driverState);
		System.out.println(taskId);
		System.out.println(driverState);
		System.out.println(flag);
		if(flag==true) {
			 try {
				response.getWriter().write("{'stateMsg':'司机已完成'}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//response.getWriter().write("{'stateMsg':'错误！'}");
			System.out.println("错误");
		}
				
	}


	private void searchDriver(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String driverId=request.getParameter("driverId");
		DriverService service=new DriverServiceimpl();
		Driver driver=new Driver();
		driver=service.getDriverById(driverId);
		ObjectMapper mapper = new ObjectMapper();
		String driverString = null;
		try {
			driverString = mapper.writeValueAsString(driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(driverString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchTask(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String driverId=request.getParameter("driverId");
		String createTime1=request.getParameter("createTime");
		SimpleDateFormat time  = new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date createTime = null;
		try {
			createTime =  time.parse(createTime1);
			System.out.println(createTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DriverService service=new DriverServiceimpl();
		Driver driver=new Driver();
		driver=service.getDriverById(driverId);
		TaskServiceimpl service2=new TaskServiceimpl();
		List<Task> taskList=service2.searchTask(driver,createTime);
		DataTablePageUtil<Task> data = new DataTablePageUtil<Task>();
		data.setData(taskList);
		ObjectMapper mapper = new ObjectMapper();
		String taskListString = null;
		try {
			taskListString = mapper.writeValueAsString(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(taskListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void canclestop(HttpServletRequest request, HttpServletResponse response) {
		String driverId=request.getParameter("driverId");
		String stationId=request.getParameter("stationId");
		//String newmobile=request.getParameter("mobile");
		Connection con=null;
		Driver driver=new Driver();
		DrivaerDaoimpl dDao=new DrivaerDaoimpl();
		Station station=new Station();
		StationDao staDao=new StationDaoimpl();
		try {
			con=DBUtil.getConnection();
			con.setAutoCommit(false);
			station = staDao.getStationById(stationId);
			driver=dDao.getDriverById(driverId, con);
			/*Jedis jedis=RedisUtil.getJedis();
			String mobile=jedis.get(driver.getMobile());*/
			/*if(newmobile.equals(mobile)) {
		      response.getWriter().write("{'finishMsg':'司机已登录'}");*/
			  boolean flag=false;
			if ("0".equals(driver.getStopState())) {
				response.getWriter().write("{'finishMsg':'司机未停靠'}");
			}else {
				
					driver=dDao.getDriverById(driverId, con);
					dDao.updateCancleState(driverId,con);
					flag=dDao.delStop(driverId,con);
					con.commit();
				 
					if (flag==true) {
						response.getWriter().write("{'finishMsg':'司机取消停靠成功'}");
					}else {
						response.getWriter().write("{'finishMsg':'司机取消停靠失败'}");
					}
				}
			
		/*	}else {
				response.getWriter().write("{'finishMsg':'司机未登录，请先登录'}");
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("五分钟----任务回滚");
				con.rollback();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}finally {
			try {
				con.close();
			} catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}
		
	}

	private void driverstop(HttpServletRequest request, HttpServletResponse response) {
		String driverId=request.getParameter("driverId");
		String stationId=request.getParameter("stationId");
		//String newmobile=request.getParameter("mobile");
		Connection con=null;
		Driver driver=new Driver();
		DrivaerDaoimpl dOrderDao=new DrivaerDaoimpl();
		Station station=new Station();
		StationDao staDao=new StationDaoimpl();
		try {
			con=DBUtil.getConnection();
			con.setAutoCommit(false);
			station = staDao.getStationById(stationId);
			driver=dOrderDao.getDriverById(driverId, con);
			//Jedis jedis=RedisUtil.getJedis();
			//String mobile=jedis.get(driver.getMobile());
			/*if(newmobile.equals(mobile)) {
		      response.getWriter().write("{'finishMsg':'司机已登录'}");*/
			  boolean flag=false;
			if ("1".equals(driver.getStopState())) {
				response.getWriter().write("{'finishMsg':'司机已停靠'}");
			}else {
				if (station.getOccupied()>Integer.valueOf(station.getTotal()).intValue()) {
					response.getWriter().write("{'finishMsg':'当前站点司机已满，请重新选择站点'}");
				}else {
				
					
					driver=dOrderDao.getDriverById(driverId, con);
					//dOrderDao.addDriverDao(driver, stationId, con);
					flag=dOrderDao.updateStopState(driverId,con);
					con.commit();
				 
					if (flag) {
						response.getWriter().write("{'finishMsg':'司机停靠成功'}");
					}
				}
			}
			/*}else {
				response.getWriter().write("{'finishMsg':'司机未登录，请先登录'}");
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("五分钟----任务回滚");
				con.rollback();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}finally {
			try {
				con.close();
			} catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}
		
	}
	
    private void finish(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {

		String driverId = request.getParameter("driverId");

		TaskDaoimpl taskDao = new TaskDaoimpl();
		Connection con = null;
		boolean flag=false;
		con = DBUtil.getConnection();
		try {

			con.setAutoCommit(false);

			flag=taskDao.finishWork(driverId, con);
			con.commit();
			if(flag=true) {
				response.getWriter().write("{'jieDanmsg':'订单已完成  '}");
			}else {
				response.getWriter().write("{'jieDanmsg':'网络出问题了，请稍后再试  '}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {

				System.out.println("五分钟任务要回滚");
				con.rollback();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}
	}

	private void jiedan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String taskId=request.getParameter("taskId");
	
		ReadyTaskDaoimpl dao=new ReadyTaskDaoimpl();
		TaskDaoimpl taskDao=new TaskDaoimpl();
		
		ReadyTask readyTask=new ReadyTask();
		boolean flag=false;
		Connection con = null;
		try {
			
	        con = DBUtil.getConnection();
			
			con.setAutoCommit(false);
			
			readyTask=dao.getReadyTask(taskId, con);
			taskDao.editJieTime(readyTask.getTaskId(),readyTask.getDriverId(),con);
			//flag=taskDao.deleteTask(readyTask.getId(),con);
		    flag=true;
			con.commit();
			if(flag=true) {
				response.getWriter().write("{'jieDanmsg':'接单成功  '}");
			}else {
				response.getWriter().write("{'jieDanmsg':'网络出问题了，请稍后再试  '}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				
				System.out.println("五分钟任务要回滚");
				con.rollback();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		} finally {
			try {
				con.close();
			} catch (SQLException e3) {
				// TODO: handle exception
				e3.printStackTrace();
			}
		}   
		
	}

	private void judan(HttpServletRequest request, HttpServletResponse response) throws IOException {
	String taskId=request.getParameter("taskId");
	ReadyTaskDaoimpl dao=new ReadyTaskDaoimpl();
	TaskDaoimpl taskDao=new TaskDaoimpl();
	DrivaerDaoimpl driverDao=new DrivaerDaoimpl();
	
	ReadyTask readyTask=new ReadyTask();
	Connection con = null;
   
	try {
		 boolean flag=false;
		con = DBUtil.getConnection();
		
		con.setAutoCommit(false);
		// 1.将tbl_task表中状态task_state设置成p
		readyTask=dao.getReadyTask(taskId, con);
		System.out.println("======="+readyTask);
		taskDao.updateOutTimeTask(readyTask.getTaskId(),con);
		// 2.将tbl_driverOrder表中新增一条记录 。
		// 其中instation_time（当前时间）driver的各项信息（通过driver_id获取），
		System.out.println("======="+readyTask.getDriverId());
		Driver driver = driverDao.getDriverById(readyTask.getDriverId(),con);
		System.out.println("======="+driver.getId());
		driverDao.addDriverDao(driver, readyTask.getStationId(),con);
		// 3.将这条信息移除
		flag=taskDao.deleteReadyTask(readyTask.getTaskId(),con);
		if(flag=true) {
			response.getWriter().write("{'juDanmsg':'拒单成功  '}");
		}else {
			response.getWriter().write("{'juDanmsg':'网络出问题了，请稍后再试  '}");
		}
		con.commit();
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		try {
			
			System.out.println("五分钟任务要回滚");
			con.rollback();
		} catch (SQLException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	} finally {
		try {
			con.close();
		} catch (SQLException e3) {
			// TODO: handle exception
			e3.printStackTrace();
		}
	}   
	
	}

}
	

