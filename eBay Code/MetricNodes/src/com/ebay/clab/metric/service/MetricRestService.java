/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Rest Service class for node info
 *************************************************************************************/
package com.ebay.clab.metric.service;

//import
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.ebay.clab.metric.dao.impl.MetricsDAO;
import com.ebay.clab.metric.dao.interf.MetricsDaoInterface;
import com.ebay.clab.metric.domainobject.MetricNodeDomainObject;
import com.ebay.clab.metric.exception.MetricDAOException;
import com.ebay.clab.metric.factory.MetricFactory;
import com.ebay.clab.metric.thread.MetricThread;

/**
 * Class Name: MetricRestService
 * This is a Rest Service class for node info
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
@Path("/metric")
public class MetricRestService {
	private static final String ClassName = "MetricRestService";

	static Logger log = Logger.getLogger(ClassName);

	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * Description: This method will provide service to store data.
	 * @param input
	 * @return Response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response putTodo(String input) {
		final String METHOD_NAME = "doPost()";
		log.info("Start executing method - " + METHOD_NAME);
		/*
		 * Step 1: Initialize objects
		 */
		MetricsDaoInterface mDaoObj = MetricFactory.getMetricDAO();
		Date date = new Date();
		
		/*
		 * Step 2: Create unique time stamp key
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy-hhmmssSSS");
		String timeStamp = sdf.format(date);
		
		/*
		 * Step 3: Pass the call to thread
		 */
		try {
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
			Runnable node = new MetricThread(input, timeStamp);
			executor.execute(node);
		
			/*
			 * Step 4: This will make the executor accept no new threads and finish all existing threads in the queue
			 */
		     executor.shutdown();
		     //Wait until all threads are finish
		     //executor.awaitTermination(0, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error occured :"+e);
		}
		
		/*
		 * Step 4: Make a DAO call to store data
		 */
		mDaoObj.StoreMetric(input, timeStamp);
		
		
		return Response.status(200).entity(timeStamp).build();
	}

	/**
	 * Description: This method will provide service to retrive data on request id.
	 * @param input
	 * @return String
	 */
	@Path("{requestId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getMetricNode(@PathParam("requestId") String requestId) {
		String str = null;
		try{
			/*
			 * Step 1: Read the data from DB.
			 */
			MetricsDaoInterface mDaoObj 	= 	MetricFactory.getMetricDAO();
			str 	= 	mDaoObj.readData(requestId);
		} catch (Exception e) {
			log.error("Error occured :"+e);
		}
		return str;

	}
	/**
	 * Description: This method will provide service to retrieve data..
	 * @param input
	 * @return String
	 */
	@Path("/cpu/{core1}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getNode(@PathParam("core1") String core1) {
		MetricsDaoInterface mDaoObj 	= 	MetricFactory.getMetricDAO();
		List<MetricNodeDomainObject> listObj = new ArrayList<MetricNodeDomainObject>();
		try {
			listObj = mDaoObj.readCoreData(core1);
		} catch (MetricDAOException e) {
			// TODO Auto-generated catch block
			listObj = new ArrayList<MetricNodeDomainObject>();
			e.printStackTrace();
		} catch (SQLException e) {
			listObj = new ArrayList<MetricNodeDomainObject>();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listObj.toString();

	}

}
