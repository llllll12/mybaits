package com.dcpro.demo.controller.po;

import java.util.Date;

public class Order {
	private String id;
	private String driverId;
	private String driverMobile;
	private String stationId;
	private Date instationTime;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public Date getInstationTime() {
		return instationTime;
	}
	public void setInstationTime(Date instationTime) {
		this.instationTime = instationTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
