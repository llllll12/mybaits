package com.dcpro.demo.controller.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.dcpro.demo.controller.dao.CsDaoInterface;
import com.dcpro.demo.controller.po.Customer;

import com.dcpro.demo.controller.util.Constant;
import com.dcpro.demo.controller.util.DBUtil;
import com.dcpro.demo.controller.util.MD5Util;

public class CsDaoImpl implements CsDaoInterface{

	@Override
	public Customer checkLogin(Customer cs) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Customer customer = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT id,ACCOUNT,`PASSWORD`,`NAME`, IS_ADMIN , MOBILE  from tbl_cs WHERE ACCOUNT=? AND PASSWORD=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, cs.getAccount());
		pstmt.setString(2, cs.getPassword());
		rs = pstmt.executeQuery();
		if(rs.next()) {
			customer = new Customer();
			customer.setId(rs.getString("id"));
			customer.setAccount(rs.getString("account"));
			customer.setPassword(rs.getString("password"));
			customer.setName(rs.getString("name"));
			customer.setMobile(rs.getString("mobile"));
			customer.setIsAdmin(rs.getString("is_admin"));
		}
		DBUtil.close(rs, pstmt, con);
		return customer;
	}

	@Override
	public List<Customer> getAllCS() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Customer> list=new ArrayList<Customer>();
		Customer customer=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID,`ACCOUNT`,`PASSWORD`,`NAME`,MOBILE,STATE,CREATE_TIME, UPDATE_TIME from tbl_cs WHERE STATE!=0 order by UPDATE_TIME desc";
		pstmt = con.prepareStatement(sql);
	
		rs = pstmt.executeQuery();
		while(rs.next()) {
			customer=new Customer();
			customer.setId(rs.getString("id"));
			customer.setAccount(rs.getString("account"));
			customer.setPassword(rs.getString("password"));
			customer.setName(rs.getString("name"));
			customer.setMobile(rs.getString("mobile"));
			customer.setState(rs.getString("state"));
			customer.setCreateTime(rs.getDate("create_time"));
			customer.setUpdateTime(rs.getDate("update_time"));
			list.add(customer);
		}
		
		DBUtil.close(rs, pstmt, con);
		return list;
	}

	@Override
	public boolean addCs(Customer customer) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		CsDaoInterface dao=new CsDaoImpl();
		if(dao.getCountBySameAccount(customer.getAccount())==1 || dao.getCountBySameMobile(customer.getMobile())==1) {
			
			 return false;
		}else {
	
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "INSERT into tbl_cs(ID,`ACCOUNT`,`PASSWORD`,`NAME`,MOBILE,STATE,CREATE_TIME,UPDATE_TIME,IS_ADMIN,CREATE_BY) VALUES (?,?,?,?,?,?,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
	    pstmt.setString(1, customer.getId());
	    pstmt.setString(2, customer.getAccount());
	    pstmt.setString(3, customer.getPassword());
	    pstmt.setString(4, customer.getName());
	    pstmt.setString(5, customer.getMobile());
	    pstmt.setString(6, customer.getState());
		pstmt.setTimestamp(7,new Timestamp (customer.getCreateTime().getTime()));
		pstmt.setTimestamp(8,new Timestamp(customer.getUpdateTime().getTime()));
		pstmt.setString(9, customer.getIsAdmin());
		pstmt .setString(10,customer.getCreateBy());
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
		}
	}

	@Override
	public Customer getCustomerById(String id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Customer customer=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = DBUtil.getConnection();
		String sql = "SELECT ID,`NAME`,MOBILE,ACCOUNT from tbl_cs WHERE ID=? ";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			customer = new Customer();
			customer.setId(rs.getString("id"));
			customer.setName(rs.getString("name"));
			customer.setMobile(rs.getString("mobile"));
			customer.setAccount(rs.getString("account"));

		}
		DBUtil.close(rs, pstmt, con);
		return customer;
	}

	@Override
	public int getCountBySameName(String name) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_cs WHERE `NAME`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, name);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public int getCountBySameAccount(String number) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_cs WHERE `ACCOUNT`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, number);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public int getCountBySameMobile(String mobile) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count=-1;
		con = DBUtil.getConnection();
		String sql="SELECT COUNT(*) FROM tbl_cs WHERE `MOBILE`=?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, mobile);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	@Override
	public boolean UpdateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_cs SET ACCOUNT=?,`NAME`=?,MOBILE=?,UPDATE_TIME=?,UPDATE_BY=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1, customer.getAccount());
		pstmt .setString(2, customer.getName());
		pstmt .setString(3, customer.getMobile());
		//pstmt.setString(4, customer.getUpdateBy());
		pstmt .setTimestamp(4,new Timestamp(customer.getUpdateTime().getTime()));
		pstmt .setString(5,customer.getUpdateBy());
		pstmt.setString(6, customer.getId());
		
		
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

	@Override
	public boolean delCustomer(String id) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String sql = "UPDATE tbl_cs SET STATE=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1,Constant.DELETE_STATE );
		pstmt .setString(2,id );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

	@Override
	public boolean restpwd(String id) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBUtil.getConnection();
		String md5pwd = MD5Util.makePassword(Constant.DEFAULT_PASSWORD);
		String sql = "UPDATE tbl_cs SET PASSWORD=? WHERE ID=?";
		pstmt = con.prepareStatement(sql);
		pstmt .setString(1,md5pwd);
		pstmt .setString(2,id );
		boolean flag=false;
		int count=pstmt.executeUpdate();
		if(count>0) {
			 flag = true;
		}
		DBUtil.close(pstmt, con);
		return flag;
	}

}
