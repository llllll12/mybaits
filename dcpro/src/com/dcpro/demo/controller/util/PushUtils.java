package com.dcpro.demo.controller.util;


import org.json.JSONObject;

import com.sun.istack.internal.logging.Logger;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

public class PushUtils {
     private Logger log=Logger.getLogger(PushUtils.class);
     
     public String pushAndroidByToken(String token,String content) {
    	 
    	// ����������Ӧ��ʱ���ɵ�accessId ��secretKey ������XingApp���󡣽���ʹ��������������Ϣ����
    	 XingeApp xinge = new XingeApp(Constant.accessId_android, Constant.secretKey_android);
    	 // ����һ����Ҫ���͵���Ϣ
    	Message msg=makeMessage(content);
        // ʹ��xingeApp�������Ϣ���͵���Ӧtoken���豸����ȥ��������ͳɹ���json�����������Ϊ0��ʧ��Ϊ-1
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
        //������Ϣ����
        message.setTitle("");
        //������Ϣ����
        message.setContent(content);
        //������Ϣ����Ϊ��͸��Ϣ
        message.setType(Message.TYPE_MESSAGE);
        //������Ϣ����ʱ��Ϊ24Сʱ
        message.setExpireTime(86400);
        return message;
   }
	 
 }

