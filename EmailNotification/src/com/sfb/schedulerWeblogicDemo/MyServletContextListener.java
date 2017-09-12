package com.sfb.schedulerWeblogicDemo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class MyServletContextListener implements ServletContextListener {
	
	// contextInitialized
	public void contextInitialized(ServletContextEvent event) {
		/*
		 * This method is called when the servlet context is initialized(when
		 * the Web Application is deployed). You can initialize servlet context
		 * related data here.
		 */
		//System.out.println("ServletContextListener started");
		MyTimerTask task1 = new MyTimerTask ("E-mail Notification Task ");

         long ONCE_PER_DAY = 1000*60*60*24; 
         //long ONE_HOUR = 1000*60*60*1;
         //long TWELVE_HOUR = 1000*60*60*12;

        Timer timer = new Timer();
        timer.schedule(task1, new Date(), ONCE_PER_DAY);   
        //end
 
	}

	// contextDestroyed
	public void contextDestroyed(ServletContextEvent event) {
		/*
		 * This method is invoked when the Servlet Context (the Web Application)
		 * is undeployed or WebLogic Server shuts down.
		 */
		//System.out.println("ServletContextListener destroyed");
	}
}
