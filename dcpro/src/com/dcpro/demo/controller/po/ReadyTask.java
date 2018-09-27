package com.dcpro.demo.controller.po;

import java.util.Date;

public class ReadyTask {
	 private String id;
	 private String custMobile;
	 private String driverId;
	 private String stationId;
	 private String driverMobile;
     private String type;
	 private String taskState;
	 private Date waitTime;
	 private Date creatTime;
	 private String taskId;
	 private Date  instationTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Date getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Date waitTime) {
		this.waitTime = waitTime;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getInstationTime() {
		return instationTime;
	}
	public void setInstationTime(Date instationTime) {
		this.instationTime = instationTime;
	}
	 
}
