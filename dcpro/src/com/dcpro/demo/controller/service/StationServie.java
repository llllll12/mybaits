package com.dcpro.demo.controller.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Station;


public interface StationServie {
	List<Station> getAllStation() throws ClassNotFoundException, SQLException;
	boolean addStation(String station_name,String total,String createBy) throws Exception ;
	List<Driver> getAllDriver(String id) throws ClassNotFoundException, SQLException;
	List<Map<String, Object>> getStationname() throws ClassNotFoundException, SQLException ;
	String delStation(String id) throws Exception;
	Station getStationById(String id ) throws ClassNotFoundException, SQLException;
	String updateStation(Station newStation,Station oldStation) throws Exception;
	String getDriverCount(String id) throws ClassNotFoundException, SQLException;
}
