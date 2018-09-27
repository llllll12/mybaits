package com.dcpro.demo.controller.controller.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.service.AnDriverService;
import com.dcpro.demo.controller.service.DriverService;
import com.dcpro.demo.controller.service.api.AnDrverService;
import com.dcpro.demo.controller.service.impl.DriverServiceimpl;
import com.dcpro.demo.controller.util.RedisUtil;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class DriverServlet
 */
@WebServlet("/DriverServlet")
public class DriverServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("addDriverMessage", null);
		session.setAttribute("updateDriverMessage", null);
	    session.setAttribute("delDriverMessage", null);
		String action=request.getParameter("action");
		switch (action) {
		case "login":
			try {
				this.driverlogin(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "setPwd":
			try {
				DriverSetPwd(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "getId":
			try {
				DriverGetId(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;
		}
	}

	private void DriverGetId(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		String drivername=request.getParameter("drivername");
		String driverpwd=request.getParameter("driverpwd");
		
		AnDriverService service=new AnDrverService();
		Driver driver=null;
		driver=service.checkDriverLogin(drivername, driverpwd);
		if(null==driver) {
			System.out.println("请重新登录");
		}else {
			   String id="{'driverId':"+driver.getId()+"}";
			   System.out.println(id);
		      //response.getWriter().write(id);		    
	           response.getWriter().write("{'driverId':'"+driver.getId()+"'}");
		}
	}

	private void DriverSetPwd(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
		String driverId=request.getParameter("driverId");
		String driverPwd=request.getParameter("driverpwd");
		String driverNewPwd=request.getParameter("drivernewpwd");
		String token=request.getParameter("token");	
		
		Driver driver=null;
		boolean flag=false;
		
		DriverService service1=new DriverServiceimpl();
		AnDriverService service=new AnDrverService();
		driver=service1.getDriverById(driverId);
		/*Jedis jedis=RedisUtil.getJedis();
		String driverid=jedis.set(driver.getId(), token);*/
		if(driver==null) {
			 response.getWriter().write("{'finishMsg':'司机不存在'}");
		 }else {
			 System.out.println(driver);
			 System.out.println(driver.getPassword());
			 System.out.println(driverPwd);
			 System.out.println(driverNewPwd);
			 /*if(!driverId.equals(driverid)) {
		 
		      response.getWriter().write("{'finishMsg':'设备已在其他地方登录'}");
		     }else {*/
		        if(driverPwd.equals(driver.getPassword())) { 
		        	  
		        	 
                       flag=service.setpwd(driverId,driverNewPwd);
                       if(flag) {
              			 response.getWriter().write("{'finishMsg':'司机修改密码成功'}");
              		    }else {
              			 response.getWriter().write("{'finishMsg':'司机修改密码失败'}");
              		    }
		        }else {
			     response.getWriter().write("{'finishMsg':'原密码输入错误，修改失败'}");
		        }
		   }
	//  }

	}

	private void driverlogin(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		String drivername=request.getParameter("drivername");
		String driverpwd=request.getParameter("driverpwd");
		
		AnDriverService service=new AnDrverService();
		Driver driver=null;
		driver=service.checkDriverLogin(drivername, driverpwd);
		if(null==driver) {
			response.getWriter().write("{'driverloginMsg':'用户名和密码错误'}");
		}else {
			/*if("1".equals(driver.getStopState())) {
				response.getWriter().write("{'driverloginMsg':'已登录，不允许再次登录'}");
			}else {*/
				String token=request.getParameter("token");
				Jedis jedis=RedisUtil.getJedis();
				System.out.println(driver.getMobile());
				jedis.set(driver.getMobile(), token);
			
				response.getWriter().write("{'driverloginMsg':'登录成功' } ");
			}
		//}
	}

}
