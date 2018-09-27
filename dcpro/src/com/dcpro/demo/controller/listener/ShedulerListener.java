package com.dcpro.demo.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dcpro.demo.controller.util.PushUtils;

@WebListener
public class ShedulerListener implements ServletContextListener{
	private MyThread myThread;
	private PushUtils push;

	public ShedulerListener() {
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		if (myThread != null && myThread.isInterrupted()) {
			myThread.interrupt();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {

		String str = null;
		push = new PushUtils();
		if (str == null && myThread == null) {
			myThread = new MyThread(push);
			myThread.start();
			
		}
	}
}

