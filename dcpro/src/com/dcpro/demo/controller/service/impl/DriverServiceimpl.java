package com.dcpro.demo.controller.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.dcpro.demo.controller.dao.DriverDao;
import com.dcpro.demo.controller.dao.impl.DrivaerDaoimpl;
import com.dcpro.demo.controller.po.Driver;
import com.dcpro.demo.controller.po.Notice;
import com.dcpro.demo.controller.service.DriverService;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.UUIDTool;

public class DriverServiceimpl implements DriverService {

	@Override
	public List<Driver> getAllDriver() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DriverDao dao = new DrivaerDaoimpl();
		List<Driver> driverList = dao.getAllDriver();
		return driverList;
	}

	@Override
	public boolean addDriver(String name, String mobile, String carNumber, String carType, String workDate,
			String creatById) throws Exception {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
		driver.setName(name);
		driver.setCarNumber(carNumber);
		driver.setCarType(carType);
		driver.setMobile(mobile);
		driver.setCreateBy(creatById);
		driver.setWorkDate(workDate);
       
		driver.setId(UUIDTool.getUUID());
		driver.setState(Constant.WAIT_STATE);
		driver.setCreateTime(new Date());
		driver.setPassword(Constant.DEFAULT_PASSWORD);
		driver.setStopState(Constant.NOT_STOP);

		DriverDao dao = new DrivaerDaoimpl();
		return dao.addDriver(driver);
	}

	@Override
	public Driver getDriverById(String id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DriverDao dao = new DrivaerDaoimpl();
		Driver driver = dao.getDriverById(id);
		return driver;
	}

	@Override
	public String updateDriver(Driver newdriver, Driver olddriver) throws Exception {
		DriverDao dao = new DrivaerDaoimpl();
		if (!newdriver.getName().equals(olddriver.getName())) {
			int count1 = dao.getCountBySameName(newdriver.getName());

			if (count1 >= 1) {
				return Constant.DRIVER_NAME_REPEAT;
			}

		}  if (!newdriver.getMobile().equals(olddriver.getMobile())) {
			int count2 = dao.getCountBySameMobile(newdriver.getMobile());
			if (count2 >= 1) {
				return Constant.DRIVER__MOBILE_REPEAT;
			}
		} else if (!newdriver.getCarNumber().equals(olddriver.getCarNumber())) {
			int count3 = dao.getCountBySameCarNumber(newdriver.getCarNumber());
			if (count3 >= 1) {
				return Constant.DRIVER__CARNUM_REPEAT;
			}
		}
		boolean flag = false;
		flag=dao.UpdateDriver(newdriver);
		if (flag) {
			
			return "修改成功";
			
		} else {
			return "修改失败";
		}
	}

	@Override
	public boolean delDriver(String id) throws Exception {
		// TODO Auto-generated method stub
		DriverDao dao = new DrivaerDaoimpl();
		boolean flag = false;
		flag=dao.delDriver(id);
		return flag;
	}

	@Override
	public boolean statesDriver(Driver driver) throws Exception {
		// TODO Auto-generated method stubz
		DriverDao dao = new DrivaerDaoimpl();
		boolean flag = dao.statesDriver(driver);
		return flag;
	}

	@Override
	public boolean deleteDriverCheck(String id) throws ClassNotFoundException, SQLException {
		 DrivaerDaoimpl dao=new DrivaerDaoimpl();
		 return  dao.deleteDriverCheck(id);
	}

	@Override
	public boolean addDriverDao(Driver driver, String stationId) throws ClassNotFoundException, SQLException {
		DriverDao dOrderDao =new DrivaerDaoimpl();
		return dOrderDao.addDriverDao(driver, stationId);
	}

	@Override
	public List<Notice> getNoticeById(String noticeId) throws ClassNotFoundException, SQLException {
		DriverDao dao = new DrivaerDaoimpl();
		List<Notice> noticeList = dao.getAllNoticer();
		return noticeList;
	}

}
