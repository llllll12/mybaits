package com.dcpro.demo.controller.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.ReadyTask;
import com.dcpro.demo.controller.po.Station;
import com.dcpro.demo.controller.po.Task;

public interface StationDao {
	List<Station> getAllStation() throws ClassNotFoundException, SQLException;
	List<Driver> searchAllDriver(String id) throws ClassNotFoundException, SQLException ;
	boolean addStation(Station station) throws Exception;
	void adddriver(ReadyTask readyTask, Connection con) throws Exception;
	String delStation(String id) throws ClassNotFoundException, SQLException;
	int getStationcount(String id) throws ClassNotFoundException, SQLException;
	List<Map<String, Object>> getStationname() throws ClassNotFoundException, SQLException ;
	Station getStationById(String id) throws ClassNotFoundException, SQLException;
	boolean UpdateStation(Station station) throws ClassNotFoundException, SQLException;
	public List<Driver> selectALL(String stationId) throws Exception;
	int getCountBySameStation(String name) throws ClassNotFoundException, SQLException;
	void deleteById(String id, String stationId, Connection con)throws ClassNotFoundException, SQLException;
	int listDriver(String stationId, String driverId) throws ClassNotFoundException, SQLException;
}

