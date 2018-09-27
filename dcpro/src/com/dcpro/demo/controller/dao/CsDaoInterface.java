package com.dcpro.demo.controller.dao;

import java.sql.SQLException;
import java.util.List;

import com.dcpro.demo.controller.po.Customer;



public interface CsDaoInterface {
	Customer checkLogin(Customer cs) throws ClassNotFoundException, SQLException;
	List<Customer> getAllCS() throws ClassNotFoundException, SQLException;
	boolean addCs(Customer customer) throws ClassNotFoundException, SQLException;
	Customer getCustomerById(String id) throws ClassNotFoundException, SQLException;
	int getCountBySameName(String name) throws ClassNotFoundException, SQLException;
	int getCountBySameAccount(String number)throws ClassNotFoundException, SQLException;
	int getCountBySameMobile(String mobile) throws ClassNotFoundException, SQLException;
	boolean UpdateCustomer(Customer customer) throws ClassNotFoundException, SQLException;
	boolean delCustomer(String id) throws ClassNotFoundException, SQLException;
	boolean restpwd(String id) throws ClassNotFoundException, SQLException;
}
