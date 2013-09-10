/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Common class for creating JDBC connection
 *************************************************************************************/
package com.ebay.clab.metric.dao.impl;

//Import
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class Name: SQLEngine - This is a Common class for creating JDBC connection
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
public class SQLEngine {
	public static final String JDBC_DRIVER 	= 	"com.mysql.jdbc.Driver";
	public static final String DB_URL 		= 	"jdbc:mysql://localhost/mySqlDB";

	public static final String JDBC_USER 	= 	"sqluser";
	public static final String JDBC_PWD 	= 	"sqluserpw";

	public Connection connect = null;
	public Statement statement = null;
	public PreparedStatement preparedStatement = null;
	public ResultSet resultSet = null;


	/**
	 * Description: This method will create connection to DB.
	 * @param none
	 * @return Connection
	 */
	public Connection connect() {
		try {
			return DriverManager.getConnection(DB_URL, JDBC_USER, JDBC_PWD);
		} catch (Exception e) {
			System.out.println("Failed : " + e);
			return null;
		}
	}

	/**
	 * Description: This method will create PreparedStatement to connection.
	 * @param sqlQuery
	 * @return PreparedStatement
	 */
	public PreparedStatement getPrepareStatement(String sqlQuery)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		
		Class.forName(JDBC_DRIVER).newInstance();
		connect = connect();

		PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery);

		return preparedStatement;

	}

	/**
	 * Description: This method will release all DB resouces
	 * @param none
	 * @return none
	 */
	public void releaseConnection() {
		try {

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
