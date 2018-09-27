package com.dcpro.demo.controller.po;

import java.util.Date;

public class Station {
	private Date createTime;
	private String createBy;
	private String id;
	private String state;
	private String stationName;
	private String total;
	private int occupied=0;
	public String message;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getcreateBy() {
		return createBy;
	}
	public void setcreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getOccupied() {
		return occupied;
	}
	public void setOccupied(int i) {
		this.occupied = i;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
