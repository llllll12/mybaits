package com.dcpro.demo.controller.service;

import java.sql.SQLException;

import java.util.List;

import com.dcpro.demo.controller.po.Customer;




public interface CsServiceInterface {
	//�Ե�½ʱ��ȡ���û�����������ҵ���߼�����
	//��δ�����
	
	Customer checkLogin(String username,String password) throws ClassNotFoundException, SQLException;
	List<Customer> getAllCS() throws ClassNotFoundException, SQLException;
	boolean addCs(String account,String name,String mobile,String createBy ) throws Exception;
	Customer getCSById(String id ) throws ClassNotFoundException, SQLException;
	String updateCustomer(Customer newcustomer,Customer oldcustomer) throws Exception;
	boolean delCustomer(String id) throws ClassNotFoundException, SQLException;
	boolean restpwd(String id) throws ClassNotFoundException, SQLException;
}
