/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a DAO class for metric nodes data
 *************************************************************************************/
package com.ebay.clab.metric.dao.impl;

//Imports
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

import com.ebay.clab.metric.dao.interf.MetricsDaoInterface;
import com.ebay.clab.metric.domainobject.CpuDomainObject;
import com.ebay.clab.metric.domainobject.MemoryDomainObject;
import com.ebay.clab.metric.domainobject.MetricNodeDomainObject;
import com.ebay.clab.metric.exception.MetricDAOException;
import com.ebay.clab.metric.factory.MetricFactory;

/**
 * Class Name: This is a DAO class for metric nodes data.
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
public class MetricsDAO extends SQLEngine implements MetricsDaoInterface {
	private static final String ClassName = "MetricsDAO";

	static Logger log = Logger.getLogger(ClassName);
	private static String insertQuery 	= 	"INSERT INTO NODE_INFO (CORE1_CPU,CORE2_CPU,FREE_MEMORY,USED_MEMORY, REQUEST_ID) VALUES (?,?,?,?,?)";
	private static String readQuery 	= 	"SELECT * FROM NODE_INFO WHERE REQUEST_ID= ? ";
	private static String readCoreQuery 	= 	"SELECT * FROM NODE_INFO WHERE CORE1_CPU= ? ";


	/**
	 * Description: This method will perform read operation on NODE INFO table..
	 * @param requestID
	 * @return String
	 */
	@Override
	public String readData(String requestID) {
		final String METHOD_NAME = "readData";
		log.info("Start executing method - " + METHOD_NAME);
		JSONObject nodeObj		=	null;
		try {
			
			JSONObject cpuObj		=	null;
			JSONObject memoryObj	=	null;
			
			PreparedStatement preparedStmt = getPrepareStatement(readQuery);
			preparedStmt.setString(1, requestID);
			ResultSet resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				nodeObj		=	new JSONObject();
				cpuObj		=	new JSONObject();
				memoryObj	=	new JSONObject();
				
				cpuObj.put("core1",resultSet.getString("CORE1_CPU"));
				cpuObj.put("core2",resultSet.getString("CORE2_CPU"));
				memoryObj.put("used",resultSet.getString("USED_MEMORY"));
				memoryObj.put("free",resultSet.getString("FREE_MEMORY"));
				
				nodeObj.put("cpu", cpuObj);
				nodeObj.put("mem", memoryObj);
				
			}

		} catch (Exception e) {
			log.error("DB Error occured: "+e);
			//releaseConnection();
		} finally {
			releaseConnection();
		}
		return nodeObj!=null ? nodeObj.toString() : "";
	}

	
	/**
	 * Description: This method will perform write operation on NODE INFO table..
	 * @param String input, String timeStamp
	 * @return none
	 */
	@Override
	public void StoreMetric(String input, String timeStamp) {
		final String METHOD_NAME = "StoreMetric";
		log.info("Start executing method - " + METHOD_NAME);
		JSONObject jObj;
		try {
			log.info("DAO : input: "+input);
			
			jObj = new JSONObject(input);

			JSONObject cpuObj = (JSONObject) jObj.get("cpu");
			JSONObject memObj = (JSONObject) jObj.get("mem");

			PreparedStatement preparedStmt = getPrepareStatement(insertQuery);
			preparedStmt.setString(1, cpuObj.getString("core1").toString());
			preparedStmt.setString(2, cpuObj.getString("core2").toString());
			preparedStmt.setString(3, memObj.getString("free").toString());
			preparedStmt.setString(4, memObj.getString("used").toString());
			preparedStmt.setString(5, timeStamp);

			preparedStmt.execute();
			
		} catch (Exception e) {
			log.error("Error: "+e);
		} finally {
			releaseConnection();
		}
	}

	/**
	 * Description: This method will perform read operation on NODE INFO table..
	 * @param requestID
	 * @return String
	 */
	@Override
	public List<MetricNodeDomainObject> readCoreData(String coreId) throws MetricDAOException, SQLException{
		final String METHOD_NAME = "readData";
		log.info("Start executing method - " + METHOD_NAME);
		MetricNodeDomainObject nodeObj	=	null;
		CpuDomainObject cpuObj		=	null;
		MemoryDomainObject memObj	=	null;
		List<MetricNodeDomainObject> objList = new ArrayList<MetricNodeDomainObject>();
		try {
					
			PreparedStatement preparedStmt = getPrepareStatement(readCoreQuery);
			preparedStmt.setString(1, coreId);
			ResultSet resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				nodeObj	=	MetricFactory.getNodeObj();
				cpuObj  =  	MetricFactory.getCpuObj();
				memObj  =  	MetricFactory.getMemObj();

				
				cpuObj.setCoreSize1(resultSet.getString("CORE1_CPU"));
				cpuObj.setCoreSize2(resultSet.getString("CORE2_CPU"));
				memObj.setUsedMemory(resultSet.getString("USED_MEMORY"));
				memObj.setFreeMemory(resultSet.getString("FREE_MEMORY"));
				
				nodeObj.setCpuDomainOject(cpuObj);
				nodeObj.setMemoryDomainOject(memObj);
				objList.add(nodeObj);
			}

		} catch (Exception e) {
			log.error("DB Error occured: "+e);
			//releaseConnection();
		} finally {
			releaseConnection();
		}
		return objList;
	}

	
}
