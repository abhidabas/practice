/*making connection to the data source jndi*/
package com.sfb.schedulerWeblogicDemo;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {
	public static Connection getConnection() throws NamingException, SQLException, IOException{
		//System.out.println("inside ConnectionFactory getConnection()");
		Context ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup("jdbc/contracts");
		return dataSource.getConnection();
	}
}
