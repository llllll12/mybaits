package com.dcpro.demo.controller.service;


import java.sql.SQLException;
import java.util.List;



import com.dcpro.demo.controller.po.*;
public interface DriverService {
List<Driver> getAllDriver() throws ClassNotFoundException, SQLException;
//��װ��Ϣ�������ݿ�,�����Ƿ񱣴�ɹ�
boolean addDriver(String name,String mobile,String carNumber,
		String carType,
		String workDate,
		String creatById) throws Exception ;

//��ȡ˾����Ϣ������
Driver getDriverById(String id ) throws ClassNotFoundException, SQLException;
String updateDriver(Driver newdriver,Driver olddriver) throws Exception;
boolean delDriver(String id) throws Exception;
boolean statesDriver(Driver driver) throws Exception;
boolean deleteDriverCheck(String id) throws ClassNotFoundException, SQLException;
boolean addDriverDao(Driver driver, String stationId) throws ClassNotFoundException, SQLException;
List<Notice> getNoticeById(String noticeId) throws ClassNotFoundException, SQLException;

}
