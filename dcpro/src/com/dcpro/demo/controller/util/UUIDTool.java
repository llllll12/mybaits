package com.dcpro.demo.controller.util;

import java.util.UUID;

public class UUIDTool {
	public UUIDTool() {  
    }  
    /**  
     * 閼奉亜濮╅悽鐔稿灇32娴ｅ秶娈慤Uid閿涘苯顕惔鏃�鏆熼幑顔肩氨閻ㄥ嫪瀵岄柨鐢禿鏉╂稖顢戦幓鎺戝弳閻€劊锟斤拷  
     * @return  
     */  
    public static String getUUID() {  
        /*UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 閸樼粯甯�"-"缁楋箑褰�  
        String temp = str.substring(0, 8) + str.substring(9, 13)  
                + str.substring(14, 18) + str.substring(19, 23)  
                + str.substring(24);  
        return temp;*/  
          
        return UUID.randomUUID().toString().replace("-", "");   
    }  
  
  
    public static void main(String[] args) {  
//      String[] ss = getUUID(10);  
        for (int i = 0; i < 10; i++) {  
            System.out.println("ss[" + i + "]=====" + getUUID());  
        }  
    }  
}
