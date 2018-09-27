package com.dcpro.demo.controller.po;
import java.util.Date;
public class Driver {
	 private String id;

	    private String name;

	    private String mobile;
	    
	    private String password;//密码 手机端

	    private String carNumber;
	    
	    private String carType;//车型

	    private String workDate;

	    private String state;

	    private String createBy;

	    private Date createTime;

	    private String updateBy;

	    private Date updateTime;
	    //新增
	    private Date taskTime;
	    private String stationId;
	    private Date  instationTime;
	    //新增停靠状态 1-已停靠站  0-未停靠站
	    private String stopState;
	    private String DriverId;
	    private String message;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	        this.mobile = mobile == null ? null : mobile.trim();
	    }

	    public String getCarNumber() {
	        return carNumber;
	    }

	    public void setCarNumber(String carNumber) {
	        this.carNumber = carNumber == null ? null : carNumber.trim();
	    }

	    public String getWorkDate() {
	        return workDate;
	    }

	    public void setWorkDate(String workDate) {
	        this.workDate = workDate == null ? null : workDate.trim();
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state == null ? null : state.trim();
	    }

	    public String getCreateBy() {
	        return createBy;
	    }

	    public void setCreateBy(String createBy) {
	        this.createBy = createBy == null ? null : createBy.trim();
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public String getUpdateBy() {
	        return updateBy;
	    }

	    public void setUpdateBy(String updateBy) {
	        this.updateBy = updateBy == null ? null : updateBy.trim();
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

	    public String getCarType() {
	        return carType;
	    }

	    public void setCarType(String carType) {
	        this.carType = carType;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public Date getTaskTime() {
	        return taskTime;
	    }

	    public void setTaskTime(Date taskTime) {
	        this.taskTime = taskTime;
	    }

	    public String getStopState() {
	        return stopState;
	    }

	    public void setStopState(String stopState) {
	        this.stopState = stopState;
	    }

		public Date getInstationTime() {
			return instationTime;
		}

		public void setInstationTime(Date instationTime) {
			this.instationTime = instationTime;
		}

		public String getStationId() {
			return stationId;
		}

		public void setStationId(String stationId) {
			this.stationId = stationId;
		}

		public String getDriverId() {
			return DriverId;
		}

		public void setDriverId(String driverId) {
			DriverId = driverId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
}
