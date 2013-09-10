package com.ebay.clab.metric.client.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.ebay.clab.metric.client.constants.MetricConstants;
import com.ebay.clab.metric.client.domainobject.CpuDomainObject;
import com.ebay.clab.metric.client.domainobject.MemoryDomainObject;
import com.ebay.clab.metric.client.domainobject.MetricNodeDomainObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Servlet implementation class NodesServlet
 */
@WebServlet("/NodesServlet")
public class NodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ClassName = "NodesServlet";

	static Logger log = Logger.getLogger(ClassName);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NodesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
		final String METHOD_NAME = "doPost()";
		log.info("Start executing method - " + METHOD_NAME);
		// TODO Auto-generated method stub
		Object responseText = null;
		PrintWriter out = response.getWriter();
		try {
			CpuDomainObject cpuObj = new CpuDomainObject();
			MemoryDomainObject mObj = new MemoryDomainObject();
			MetricNodeDomainObject nodeObj = new MetricNodeDomainObject();
			String requestID	=	 (String) request.getParameter(MetricConstants.REQUESTID); 
			
			String task=(String) request.getParameter("task"); //task=store"
			cpuObj.setCoreSize1((String) request.getParameter(MetricConstants.CORE1));
			cpuObj.setCoreSize2((String) request.getParameter(MetricConstants.CORE2));

			mObj.setFreeMemory((String) request.getParameter(MetricConstants.FREE));
			mObj.setUsedMemory((String) request.getParameter(MetricConstants.USED));

			nodeObj.setCpuDomainOject(cpuObj);
			nodeObj.setMemoryDomainOject(mObj);

			String error = "";

			JSONObject resultSet = new JSONObject();
			try {
				log.info("Node: " + nodeObj.toString() +"-"+ METHOD_NAME);
				if("store".equals(task)){
					if(!StringUtils.isNumeric(cpuObj.getCoreSize1()) || !StringUtils.isNumeric(cpuObj.getCoreSize2()) ){
						error	=	"Please enter numeric CPU size.";
					}else{
						String requestId = this.storeRecords(nodeObj);
						resultSet.put(MetricConstants.REQUESTID, requestId);
					}
				}else if ("read".equals(task)){
					JSONObject jsObj = this.readRecords(requestID);
					if(jsObj!=null){
						resultSet	=	jsObj;
						error	=	"Record found for requestID# "+requestID+".";
					}else{
						error	=	"Record not found.";
					}
					
				}
			}catch (Exception e) {
				error = e.toString();
				System.out.println("ERROR!!! : " + e);
			}

			/*
			 * resultSet.put("out1", marsRoverObj.getOutput1());
			 * resultSet.put("out2", marsRoverObj.getOutput2());
			 */
			resultSet.put("err", error);
			log.info("Json request method# ");

			responseText = resultSet.toString();

			response.setContentType("text/json");
			response.setHeader("Cache-Control", "no-cache");
			out.print(responseText);
		} catch (Exception e) {
			log.error("Json request error occured... " + e, e);
		} finally {
			log.info("End executing method - " + METHOD_NAME);
			out.close();
		}
	}

	private String storeRecords(MetricNodeDomainObject nodeObj) {
		ClientResponse restResponse = null;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		//
		restResponse = service.path("metric").accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, nodeObj.toString());

		// Return code should be 200 == created resource
		int status = restResponse.getStatus();
		String requestId = restResponse.getEntity(String.class);

		if (200 == status) {
			log.info("Client:" + restResponse.getStatus() + " requestId: " + requestId);
			return requestId;
		}

		return "";
	}
	private JSONObject readRecords(String requestId ) {
		
		ClientConfig config 	= 	new DefaultClientConfig();
		Client client 			= 	Client.create(config);
		WebResource service 	= 	client.resource(getBaseURI());
		JSONObject jsonObj	=	null;
		JSONObject jsonObjOutput	=	null;
		//
		String jsonData = service.path("metric/"+requestId).accept(MediaType.APPLICATION_JSON)
				.get(String.class);
	
		try {
			if(StringUtils.isNotBlank(jsonData)){
				jsonObj	=	 new JSONObject(jsonData);
				
				if(jsonObj.length()!=0){
					JSONObject cpuObj = (JSONObject) jsonObj.get(MetricConstants.CPU);
					JSONObject memObj = (JSONObject) jsonObj.get(MetricConstants.MEMORY);
					
					jsonObjOutput	=	 new JSONObject();
					jsonObjOutput.put(MetricConstants.CORE1, cpuObj.getString(MetricConstants.CORE1).toString());
					jsonObjOutput.put(MetricConstants.CORE2, cpuObj.getString(MetricConstants.CORE2).toString());
					jsonObjOutput.put(MetricConstants.FREE, memObj.getString(MetricConstants.FREE).toString());
					jsonObjOutput.put(MetricConstants.USED, memObj.getString(MetricConstants.USED).toString());
					jsonObjOutput.put(MetricConstants.REQUESTID, requestId);
				}	

			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObjOutput;
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/MetricNodes").build();
	}
}
