package com.dcpro.demo.controller.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.dcpro.demo.controller.dao.CsDaoInterface;
import com.dcpro.demo.controller.dao.impl.CsDaoImpl;
import com.dcpro.demo.controller.po.Customer;
import com.dcpro.demo.controller.service.CsServiceInterface;
import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.MD5Util;
import com.dcpro.demo.controller.util.UUIDTool;

public class CsServiceImpl implements CsServiceInterface{

	@Override
	public Customer checkLogin(String username, String password) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String md5pwd = MD5Util.makePassword(password);
		Customer cs = new Customer();
		cs.setAccount(username);
		cs.setPassword(md5pwd);
	    
		CsDaoInterface csDao = new CsDaoImpl();
		return csDao.checkLogin(cs);
	}

	@Override
	public List<Customer> getAllCS() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		CsDaoInterface daoImpl=new CsDaoImpl();
		List<Customer> csList=daoImpl.getAllCS();
		
		return csList;
	}



	@Override
	public Customer getCSById(String id) throws ClassNotFoundException, SQLException {
		CsDaoInterface dao=new CsDaoImpl();
		return dao.getCustomerById(id);
	}

	@Override
	public String updateCustomer(Customer newcustomer, Customer oldcustomer) throws Exception {
		CsDaoInterface dao=new CsDaoImpl();
		if(!newcustomer.getName().equals(oldcustomer.getName())) {
			int count = dao.getCountBySameName(newcustomer.getName());

			if (count >= 1) {
				return Constant.CS_NAME_REPEAT;
			}
		}
		if(!newcustomer.getAccount().equals(oldcustomer.getAccount())) {
			int count = dao.getCountBySameAccount(newcustomer.getAccount());

			if (count >= 1) {
				return Constant.CS__CARNUM_REPEAT;
			}
		}if(!newcustomer.getMobile().equals(oldcustomer.getMobile())) {
			int count = dao.getCountBySameMobile(newcustomer.getMobile());

			if (count >= 1) {
				return Constant.CS__MOBILE_REPEAT;
			}
		}
		boolean flag = false;
		flag=dao.UpdateCustomer(newcustomer);
		if (flag) {
			
			return Constant.UPDCSMSG_SUCCESS;
			
		} else {
			return Constant.UPDCSMSG_FAUSE;
		}
	}

	@Override
	public boolean delCustomer(String id) throws ClassNotFoundException, SQLException {
	    CsDaoInterface daoInterface=new CsDaoImpl();
		return daoInterface.delCustomer(id);

	}

	@Override
	public boolean restpwd(String id) throws ClassNotFoundException, SQLException {
		 CsDaoInterface daoInterface=new CsDaoImpl();
			return daoInterface.restpwd(id);
	}

	@Override
	public boolean addCs(String account, String name, String mobile, String createBy) throws Exception {
		// TODO Auto-generated method stub
		CsDaoInterface dao=new CsDaoImpl();
		Customer customer=new Customer();
		
		customer.setAccount(account);
		customer.setName(name);
		customer.setMobile(mobile);
		customer.setId(UUIDTool.getUUID());
		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		customer.setCreateBy(createBy);
		customer.setIsAdmin("b");
		customer.setPassword("999999");
		customer.setState("1");
		return dao.addCs(customer);
	}

	

}
