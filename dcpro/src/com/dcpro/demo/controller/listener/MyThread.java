package com.dcpro.demo.controller.listener;

import com.dcpro.demo.controller.util.PushUtils;

public class MyThread extends Thread {
	private PushUtils push;

	public MyThread(PushUtils push) {
		this.push = push;
	}

	public void run() {
		while (!this.isInterrupted()) {
			try {
				Thread.sleep(5000);
				Opera opera=new Opera();
				/*try {
					opera.Timeopera();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				/*System.out.println("ÿ��5��ִ��һ��");*/
				/*Opera opera=new Opera();
				opera.operatWaitePai();*/
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("�̳߳��ִ���" + ex);
			}

		}
}
}
