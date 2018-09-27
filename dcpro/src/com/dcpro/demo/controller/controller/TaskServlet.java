package com.dcpro.demo.controller.controller;

import java.io.IOException;
import java.sql.SQLException;
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

import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.po.Customer;

import com.dcpro.demo.controller.po.Task;

import com.dcpro.demo.controller.service.TaskService;

import com.dcpro.demo.controller.service.impl.TaskServiceimpl;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DataTablePageUtil;


/**
 * Servlet implementation class TaskServlet
 */
@WebServlet("/TaskServlet")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		session.setAttribute("msg", null);
		String action=request.getParameter("action");
		switch (action) {
		case "list":
			try {
				getallTask(request,response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "addlist":
			try {
				doaddtask(request,response);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}break;
		case "delTask":
	          try {
					delTask(request,response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 break;
            
		default:
			break;
		}
	}

	private void delTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub	
		String id = request.getParameter("id");
		TaskService service = new TaskServiceimpl();
		Task task=new Task();	
		String result = null;
		try {
			result = service.delTask(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }	
		task.setMessage(result);
	
		ObjectMapper mapper = new ObjectMapper();
		String taskListString = null;
		try {
			taskListString = mapper.writeValueAsString(task);
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

	private void doaddtask(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String custMobile = request.getParameter("custMobile");
		String stationId = request.getParameter("stationId");
		// 获取已登录用户的信息
		HttpSession session = request.getSession();
		Customer cs = (Customer) session.getAttribute("loginuser");
		String createById = cs.getId();
		TaskService service = new TaskServiceimpl();
		
		boolean flag = false;
		flag = service.moblieCheck(custMobile);
		if(flag) {
		try {
			boolean result = false;
			result = service.addTask(custMobile,stationId,createById);
			if (result) {
				session.setAttribute("msg", Constant.ADDTASKMSG_SUCCESS);
			} else {
				session.setAttribute("msg", Constant.ADDTASKMSG_ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			session.setAttribute("msg", Constant.ADDTASKMSG_ERROR1);
		}
		try {
			request.getRequestDispatcher("WEB-INF/jsp/task/task_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private void getallTask(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		TaskService service = new TaskServiceimpl();
		List<Task> taskList = service.getAllTask();
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
		}

	
		
	}

}
