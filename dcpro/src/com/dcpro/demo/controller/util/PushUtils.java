package com.dcpro.demo.controller.util;


import org.json.JSONObject;

import com.sun.istack.internal.logging.Logger;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

public class PushUtils {
     private Logger log=Logger.getLogger(PushUtils.class);
     
     public String pushAndroidByToken(String token,String content) {
    	 
    	// 借助于配置应用时生成的accessId 和secretKey 产生个XingApp对象。将来使用这个对象进行消息推送
    	 XingeApp xinge = new XingeApp(Constant.accessId_android, Constant.secretKey_android);
    	 // 创建一个需要推送的消息
    	Message msg=makeMessage(content);
        // 使用xingeApp对象把信息推送到对应token的设备上面去。如果推送成功，json数据里面代码为0，失败为-1
        JSONObject ret=xinge.pushSingleDevice(token, msg);
     
        int status=ret.getInt("ret_code");
        if(status==0){
            log.info("[PUSH-OK]token:"+token+",mid:"+content);
            return "success";
        }else{
        	System.out.println("[PUSH-ERROR]token:"+token+",mid:"+content+"{"+ret.getString("err_msg")+"}");
            //log.error("[PUSH-ERROR]token:"+token+",mid:"+content+"{"+ret.getString("err_msg")+"}");
            return "error";
        }
}


   public Message makeMessage(String content){
        Message message = new Message();
        //设置信息标题
        message.setTitle("");
        //设置信息内容
        message.setContent(content);
        //设置信息类型为传透信息
        message.setType(Message.TYPE_MESSAGE);
        //设置信息保留时间为24小时
        message.setExpireTime(86400);
        return message;
   }
	 
 }

