package com.dcpro.demo.controller.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dcpro.demo.controller.dao.CsDaoInterface;
import com.dcpro.demo.controller.dao.DriverDao;
import com.dcpro.demo.controller.dao.StationDao;
import com.dcpro.demo.controller.dao.impl.CsDaoImpl;
import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.dao.impl.StationDaoimpl;
import com.dcpro.demo.controller.dao.impl.TaskDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Station;
import com.dcpro.demo.controller.service.StationServie;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.UUIDTool;

public class StationServiceimpl implements StationServie{

	@Override
	public List<Station> getAllStation() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		StationDao dao=new StationDaoimpl();
		List<Station> stationlist=dao.getAllStation();
		return stationlist;
	}

	@Override
	public boolean addStation(String station_name, String total, String createBy) throws Exception {
		
		
		Station station=new Station();
		station.setTotal(total);
		station.setStationName(station_name);
		station.setId(UUIDTool.getUUID());
		station.setState(Constant.WAIT_STATE);
		station.setCreateTime(new Date());
		station.setcreateBy(createBy);
		StationDao dao=new StationDaoimpl();
		return dao.addStation(station);
	}

	@Override
	public List<Driver> getAllDriver(String id) throws ClassNotFoundException, SQLException {
		StationDao dao=new StationDaoimpl();
		List<Driver> driverList = dao.searchAllDriver(id);
		return driverList;
	}


	@Override
	public List<Map<String, Object>> getStationname() throws ClassNotFoundException, SQLException {
		StationDao dao=new StationDaoimpl();
		return dao.getStationname();
	}

	@Override
	public String delStation(String id) throws Exception {
		StationDaoimpl dao=new StationDaoimpl();
		TaskDaoimpl dao1=new TaskDaoimpl();
		
		int count1=0;;
		int count2=0;
		count1=dao1.getTaskState1(id);
		count2=dao.getDriverCount(id);
		if(count1>0 ) {
			return Constant.STATION_ESC_ERROR1;
		}
		if(count2>0 ) {
			return Constant.STATION_ESC_ERROR;
		}
		
		return dao.delStation(id);
	}

	@Override
	public Station getStationById(String id) throws ClassNotFoundException, SQLException {
		StationDao dao=new StationDaoimpl();
		Station station=dao.getStationById(id);
		return station;
	}

	@Override
	public String updateStation(Station newStation, Station oldStation) throws Exception {
		CsDaoInterface dao=new CsDaoImpl();
		if(!newStation.getStationName().equals(oldStation.getStationName())) {
			int count = dao.getCountBySameName(newStation.getStationName());

			if (count >= 1) {
				return "该站点已存在";
			}
		}
		StationDao dao2=new StationDaoimpl();
		boolean flag = false;
		flag=dao2.UpdateStation(newStation);
		if (flag) {
			
			return "修改成功";
			
		} else {
			return "修改失败";
		}
	}

	@Override
	public String getDriverCount(String id) throws ClassNotFoundException, SQLException {
		StationDaoimpl dao=new StationDaoimpl();
		TaskDaoimpl dao1=new TaskDaoimpl();
		
		int count1=0;;
		int count2=0;
		count1=dao1.getTaskState1(id);
		count2=dao.getDriverCount(id);
		if(count1>0 ) {
			return Constant.STATION_ESC_ERROR1;
		}
		if(count2>0 ) {
			return Constant.STATION_ESC_ERROR;
		}
		return Constant.STATION_ESC_SECCESS;
		
		
	}
}
