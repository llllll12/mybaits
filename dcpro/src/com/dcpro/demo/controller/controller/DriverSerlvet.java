package com.dcpro.demo.controller.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.po.Customer;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.service.DriverService;
import com.dcpro.demo.controller.service.impl.DriverServiceimpl;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DataTablePageUtil;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class DriverSerlvet
 */
@WebServlet("/DriverSerlvet")
public class DriverSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession session=request.getSession();
		session.setAttribute("addDriverMessage", null);
		session.setAttribute("updateDriverMessage", null);
	    session.setAttribute("delDriverMessage", null);
		String action=request.getParameter("action");
		switch (action) {
		case "list":
			try {
				getAllDriver(request,response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
           case"add":try {
				doAddDriver(request,response);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
           case "getDriverById":
        	   getDriverById(request,response);break;
           case "update":
        	   doUpdateDriver(request,response);break;
           case "delDriver":
			try {
				dodelDriver(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
           case"statesDriver":
        	   changestates(request,response);break;
		default:
			break;
		}
	}

	private void changestates(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		String state=request.getParameter("status");
	
		Driver newdriver=new Driver();
		newdriver.setId(id);
		newdriver.setState(state);
	    DriverService service=new DriverServiceimpl();
	    boolean flag=false;  
	    try {
			flag=service.statesDriver(newdriver);
			System.out.println(flag);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    ObjectMapper mapper=new ObjectMapper();
		String driverListString=null;
		try {
			driverListString=mapper.writeValueAsString(flag);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(driverListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	private void dodelDriver(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
	    DriverService service=new DriverServiceimpl();
	    boolean flag=false;
	    flag=service.deleteDriverCheck(id);
	    if (flag=true) {
	    try {
			flag=service.delDriver(id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		}
	    
	    ObjectMapper mapper=new ObjectMapper();
		String driverListString=null;
		try {
			driverListString=mapper.writeValueAsString(flag);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(driverListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	private void doUpdateDriver(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	  String name=request.getParameter("name");
	  String id=request.getParameter("id");
	  String mobile=request.getParameter("mobile");
	  String car_number=request.getParameter("carNumber");
	  String car_type=request.getParameter("carType");
	  String work_date=request.getParameter("workDate");
	 // String updateTime=request.getParameter("updatetime");
	  
	  
	  HttpSession session=request.getSession();
	// 获取已登录用户的信息
	  Customer cs = (Customer) session.getAttribute("loginuser");
	  String updateById = cs.getId();

	  Driver newdriver=new Driver();
	  newdriver.setId(id);
	  newdriver.setName(name);
	  newdriver.setMobile(mobile);
	  newdriver.setCarNumber(car_number);
	  newdriver.setCarType(car_type);
	  newdriver.setWorkDate(work_date);
	  newdriver.setUpdateTime(new Date());
	  newdriver.setUpdateBy(updateById);
	  //String name=request.getParameter("name");
	  
	  Driver olddriver=(Driver) session.getAttribute("updateDriver");
	  DriverService service=new DriverServiceimpl();
	  String result=null;
	  try {
		result=service.updateDriver(newdriver, olddriver);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  session.setAttribute("updateDriverMessage", result);
	  	 
	  try {
		request.getRequestDispatcher("WEB-INF/jsp/driver/driver_list.jsp").forward(request, response);
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	private void getDriverById(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		DriverService service=new DriverServiceimpl();
		Driver driver=null;
		try {
			driver =service.getDriverById(id);
			HttpSession session=request.getSession();
			session.setAttribute("updateDriver", driver);
			} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(null!=driver) {
			ObjectMapper mapper=new ObjectMapper();
			String driverListString=null;
			try {
				driverListString=mapper.writeValueAsString(driver);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				response.getWriter().write(driverListString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void doAddDriver(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String mobile=request.getParameter("mobile");
		String carNumber=request.getParameter("carNumber");
		String carType=request.getParameter("carType");
		//workDate
		String workDate=request.getParameter("workDate");
		//获取已登录用户的信息
		HttpSession session=request.getSession();
		Customer csCustomer=(Customer) session.getAttribute("loginuser");
		String creatById=csCustomer.getId();
		System.out.println("我的id是"+creatById);
		boolean result=false;
		DriverService service=new DriverServiceimpl();
		
		result=service.addDriver(name, mobile, carNumber, carType, workDate, creatById);
		if(result) {	
			session.setAttribute("addDriverMessage", Constant.ADDDRIVERMSG_SUCCESS);
	
		}else {
			session.setAttribute("addDriverMessage", Constant.DRIVER__MOBILE_REPEAT1);

		}
		request.getRequestDispatcher("WEB-INF/jsp/driver/driver_list.jsp").forward(request, response);
		
	}

	private void getAllDriver(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DriverService service=new DriverServiceimpl();
		List<Driver> driverlist=service.getAllDriver();
		DataTablePageUtil<Driver> data=new DataTablePageUtil<Driver>();
		data.setData(driverlist);
		//将集合转换成json字符串
		ObjectMapper mapper=new ObjectMapper();
		String driverListString=null;
		try {
			driverListString=mapper.writeValueAsString(data);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driverListString);
		try {
			response.getWriter().write(driverListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
