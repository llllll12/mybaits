package com.dcpro.demo.controller.controller.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.dcpro.demo.controller.dao.StationDao;
import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.dao.impl.StationDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Station;
import com.dcpro.demo.controller.service.DriverService;
import com.dcpro.demo.controller.service.impl.DriverServiceimpl;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.DataTablePageUtil;

/**
 * Servlet implementation class StationapiServlet
 */
@WebServlet("/StationapiServlet")
public class StationapiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		switch (action) {
		case "search":
			try {
				this.searchStation(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "searchOccupied":
			try {
				searchOccupied(request,response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;
		}
	}


	private void searchOccupied(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String driverId = request.getParameter("driverId");
		String stationId = request.getParameter("stationId");
		StationDao stationDao = new StationDaoimpl();
		List<Station> list = new ArrayList<Station>();
		list = stationDao.getAllStation();
		DriverService service=new DriverServiceimpl();
		Driver driver=new Driver();
		driver=service.getDriverById(driverId);
		service.addDriverDao(driver, stationId);
		boolean flag=false;
		DrivaerDaoimpl dOrderDao=new DrivaerDaoimpl();
		flag=dOrderDao.updateStopState(driverId,DBUtil.getConnection());
		if(flag==true) {
			System.out.println("修改状态成功");
		}else {
			System.out.println("修改状态失败");
		}
		DataTablePageUtil<Station> data = new DataTablePageUtil<Station>();
		data.setData(list);
		for (Station station : list) {
			if (station.getId().equals(stationId)) {
				int rank;
				rank = stationDao.listDriver(stationId, driverId);;
				station.setOccupied(rank);
			} else {
				station.setOccupied(0);
			}
		}
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


	private void searchStation(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		String driverId = request.getParameter("driverId");
		String stationId = request.getParameter("stationId");
		StationDao stationDao = new StationDaoimpl();
		List<Station> list = new ArrayList<Station>();
		list = stationDao.getAllStation();
		///if (list.size() > 0) {
			// 遍历这些信息
			//for (Station station : list) {
				//if (station.getId().equals(stationId)) {
				//	int occupied = stationDao.listDriver(stationId, driverId);
				//	station.setOccupied(occupied);
				//} else {
				//	station.setOccupied(0);
				//}
				DataTablePageUtil<Station> data=new DataTablePageUtil<Station>();
				data.setData(list);
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
		//	}
		//}

	}
}
