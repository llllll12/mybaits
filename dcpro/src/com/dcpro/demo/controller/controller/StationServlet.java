package com.dcpro.demo.controller.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dcpro.demo.controller.dao.impl.StationDaoimpl;
import com.dcpro.demo.controller.po.Customer;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Station;

import com.dcpro.demo.controller.service.StationServie;

import com.dcpro.demo.controller.service.impl.StationServiceimpl;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DataTablePageUtil;

/**
 * Servlet implementation class StationServlet
 */
@WebServlet("/StationServlet")
public class StationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   private String mdId;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String action=request.getParameter("action");
	switch (action) {
	case "stationlist":
		try {
			dogetstaion(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
	case"addstation":
		try {
			doaddstation(request,response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}break;
	case"driverlist":
	    try {
			dosearch(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}break;
	case "getStationById":
		getStationById(request,response);break;
	case"editStation":
		doeditstation(request,response);break;
	case"getallstation":
		try {
			getallstation(request,response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}break;
	case"delstation":
		dodelstation(request,response);break;
	default:
		break;
	}
	}

	private void dodelstation(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Station station = new Station();
		StationServie servie = new StationServiceimpl();
		String result=null;
			try {
				result = servie.delStation(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		System.out.println(result);
		station.setMessage(result);
		ObjectMapper mapper = new ObjectMapper();
		String stationListString = null;
		try {
			stationListString = mapper.writeValueAsString(station);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().write(stationListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getallstation(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, JsonGenerationException, JsonMappingException, IOException {
		StationServie servie=new StationServiceimpl();
		List<Map<String, Object>> list=servie.getStationname();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println(mapper.writeValueAsString(list));
		response.getWriter().write(mapper.writeValueAsString(list));	
	}
	private void doeditstation(HttpServletRequest request, HttpServletResponse response) {
		String stationName = request.getParameter("stationName");
		String total = request.getParameter("total");
//		String id=request.getParameter("id");
		HttpSession session = request.getSession();
		Station newStation = new Station();
		newStation.setId(mdId);
		newStation.setStationName(stationName);
		newStation.setTotal(total);
		
		Station oldStation = (Station) session.getAttribute("updatestation");
		
		StationServie service = new StationServiceimpl();
		String result = null;
		try {
			result = service.updateStation(newStation,oldStation);
			System.out.println(result);
			session.setAttribute("stationMsg", result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dogetstaion(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	private void getStationById(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		mdId = id;
		StationServie service=new StationServiceimpl();
		Station station=null;
		try {
			station =service.getStationById(id);
			HttpSession session=request.getSession();
			session.setAttribute("updatestation", station);
			} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("ggg"+station);
		if(null!=station) {
			ObjectMapper mapper=new ObjectMapper();
			String stationListString=null;
			try {
				stationListString=mapper.writeValueAsString(station);
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
				response.getWriter().write(stationListString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void dosearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StationServie service=new StationServiceimpl();
		String id=request.getParameter("id");
		/*Station station=null;
		station =service.getStationById(id);*/
		List<Driver> driverlist=service.getAllDriver(id);
		System.out.println(driverlist);
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
		try {
			response.getWriter().write(driverListString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void doaddstation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String total = request.getParameter("total");
		String station_name = request.getParameter("stationName");
		// 获取已登录用户的信息
		HttpSession session = request.getSession();
		Customer csCustomer = (Customer) session.getAttribute("loginuser");
		String createBy = csCustomer.getId();
		
		boolean result = false;
		StationServie service = new StationServiceimpl();

		result = service.addStation(station_name, total, createBy);
		if (result) {

			session.setAttribute("addstationmsg", Constant.ADDDSTATIONMSG_SUCCESS);
		} else {
			session.setAttribute("addstationmsgv", Constant.ADDDSTATIONMSG_FAUSE);
		
		}
		dogetstaion(request, response);
	}
	private void dogetstaion(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
	StationServie servie=new StationServiceimpl();
	List<Station> stationlist=servie.getAllStation();
	HttpSession session=request.getSession();
	session.setAttribute("stationlist", stationlist);
	//session.getAttribute("stationlist");
	request.getRequestDispatcher("WEB-INF/jsp/station/station_list.jsp").forward(request, response);
	}

}
