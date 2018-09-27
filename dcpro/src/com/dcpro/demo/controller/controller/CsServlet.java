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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;



import com.dcpro.demo.controller.po.Customer;

import com.dcpro.demo.controller.service.CsServiceInterface;

import com.dcpro.demo.controller.service.impl.CsServiceImpl;

import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DataTablePageUtil;

/**
 * Servlet implementation class CsServlet
 */
@WebServlet("/CsServlet")
public class CsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取action的值 判断是啥操作
		HttpSession session=request.getSession();
		session.setAttribute("addCSMessage", null);
		session.setAttribute("updateCustomerMessage", null);
		String action = request.getParameter("action");
		switch (action) {
		case "login":
			doLogin(request,response);
			break;
		case "list":
			try {
				getAllCS(request,response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
		case "addcs":
		      try {
				AddCs(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}break;
		case "updatecs":
			System.out.println("更新1");
			updateCs(request,response);break;
		case "getCsById":
			getCsById(request,response);break;
		case "delcs":
			delcustomer(request,response);break;
	    case"resetPwd":
		    resetpwd(request,response);break;
		default:
			break;
		}
	}


	private void resetpwd(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("重置");
		String id=request.getParameter("id");
		CsServiceInterface service=new CsServiceImpl();
		    boolean flag=false;
		   
		    try {
				flag=service.restpwd(id);
				
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


	private void delcustomer(HttpServletRequest request, HttpServletResponse response) {
		
		String id=request.getParameter("id");
		
	    CsServiceInterface service=new CsServiceImpl();
	    boolean flag=false;
	   
	    try {
			flag=service.delCustomer(id);
			
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


	private void getCsById(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		CsServiceInterface service=new CsServiceImpl();
		Customer customer=null;
		try {
			customer =service.getCSById(id);
			HttpSession session=request.getSession();
			session.setAttribute("updatecs", customer);
			} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(null!=customer) {
			ObjectMapper mapper=new ObjectMapper();
			String driverListString=null;
			try {
				driverListString=mapper.writeValueAsString(customer);
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


	private void updateCs(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
		String mobile=request.getParameter("mobile");
		String account=request.getParameter("account");
		String id=request.getParameter("id");
		HttpSession session=request.getSession();
		Customer csCustomer=(Customer) session.getAttribute("loginuser");
		String updateById=csCustomer.getId();
		
		System.out.println("更新");
		CsServiceInterface service=new CsServiceImpl();
		  Customer newcustomer=new Customer();
		  newcustomer.setId(id);
		  newcustomer.setName(name);
		  newcustomer.setMobile(mobile);
		  newcustomer.setAccount(account);
		  newcustomer.setUpdateTime(new Date());
		  newcustomer.setUpdateBy(updateById);

		  Customer oldcustomer=(Customer) session.getAttribute("updatecs");
		  String result=null;
		  try {
			result=service.updateCustomer(newcustomer, oldcustomer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  session.setAttribute("updateCustomerMessage", result);
		  	 
		  try {
			request.getRequestDispatcher("WEB-INF/jsp/cs/cs_list.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void AddCs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		CsServiceInterface service=new CsServiceImpl();
		String account=request.getParameter("account");
		String name=request.getParameter("name");
		String mobile=request.getParameter("mobile");
		HttpSession session=request.getSession();
		
		Customer csCustomer=(Customer) session.getAttribute("loginuser");
		String creatById=csCustomer.getId();
		
		boolean result=false;
		result=service.addCs(account, name, mobile,creatById);
        System.out.println("添加");
		if (result) {
			session.setAttribute("addCSMessage", Constant.ADDDCSMSG_SUCCESS);
			
		}else {
			session.setAttribute("addCSMessage", Constant.ADDDCSMSG_FAUSE1);
		}		
	    request.getRequestDispatcher("WEB-INF/jsp/cs/cs_list.jsp").forward(request, response);	
	}


	private void getAllCS(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		CsServiceInterface serviceImpl=new CsServiceImpl();
		List<Customer> cslist=serviceImpl.getAllCS();
		DataTablePageUtil<Customer> data=new DataTablePageUtil<Customer>();
		data.setData(cslist);
		//将集合转换成json字符串
		ObjectMapper mapper=new ObjectMapper();
		String cslist1=null;
		try {
			cslist1=mapper.writeValueAsString(data);
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
			response.getWriter().write(cslist1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Customer customer = null;
		//获取用户名密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//获取验证码呢
		String captcha = request.getParameter("captcha");
		HttpSession session = request.getSession();
		//判断验证码是否一致
		//从session中获取真实的验证码
		String ori_captcha =(String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//两个验证码比对
		if (captcha.equals(ori_captcha)) {
			
			CsServiceInterface csService = new CsServiceImpl();
			try {
				customer = csService.checkLogin(username, password);
				if (null == customer) {
					session.setAttribute("loginmsg", "用户名或密码错误");
					response.sendRedirect("/dcpro/login.jsp");
				} else {
					session.setAttribute("isLogin", "1");
					session.setAttribute("loginuser", customer);
					request.getRequestDispatcher("/WEB-INF/jsp/index/main.jsp").forward(request, response);
					
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			session.setAttribute("loginmsg", "验证码不正确");
			response.sendRedirect("/dcpro/login.jsp");
		}
	}
}
