package com.dcpro.demo.controller.util;
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


  
public class DataTablePageUtil<T> {  
  
    private int draw; 
    private int start = 0; 
    private int length = 10; 
    private List<T> data= new ArrayList<T>();  
    private int recordsTotal; 
    private int recordsFiltered; 
  
    private Map<String, Object> condition = new HashMap<String, Object>();
    private String search;
    
  
    public DataTablePageUtil() {  
  
    }  
  
    public DataTablePageUtil(HttpServletRequest request) {
    	if(request.getParameter("search[value]")!=null&&!"".equals(request.getParameter("search[value]"))){
    		this.setSearch(request.getParameter("search[value]"));
    	}
    	this.setDraw(Integer.parseInt(request.getParameter("draw")));
    	this.setStart(Integer.parseInt(request.getParameter("start")));
    	this.setLength(Integer.parseInt(request.getParameter("length")));
    }  
  
    public int getDraw() {  
        return draw;  
    }  
  
    public void setDraw(int draw) {  
        this.draw = draw;  
    }  
  
    public int getStart() {  
        return start;  
    }  
  
    public void setStart(int start) {  
        this.start = start;  
    }  
  
    public int getLength() {  
        return length;  
    }  
  
    public void setLength(int length) {  
        this.length = length;  
    }  
  
    public List<T> getData() {  
        return data;  
    }  
  
    public void setData(List<T> data) {  
        this.data = data;  
    }  
  
    public Map<String, Object> getCondition() {  
        return condition;  
    }  
  
    public void setCondition(Map<String, Object> condition) {  
        this.condition = condition;  
    }  
  
    public int getRecordsTotal() {  
        return recordsTotal;  
    }  
  
    public void setRecordsTotal(int recordsTotal) {  
        this.recordsTotal = recordsTotal;  
    }  
  
    public int getRecordsFiltered() {  
        return recordsFiltered;  
    }  
  
    public void setRecordsFiltered(int recordsFiltered) {  
        this.recordsFiltered = recordsFiltered;  
    }

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}  
}  
