/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Domain object class for storing node info data
 *************************************************************************************/
package com.ebay.clab.metric.client.domainobject;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ebay.clab.metric.client.constants.MetricConstants;

/**
 * Class Name: MetricNodeDomainObject
 * This is a Domain object class for storing node info data
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
@XmlRootElement
public class MetricNodeDomainObject {
	private String requestId = null;
	private CpuDomainObject cpuDomainOject = null;
	private MemoryDomainObject memoryDomainOject = null;
	
	public MetricNodeDomainObject(){
	}
	
	/**
	 * @return requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return cpuDomainOject
	 */
	public CpuDomainObject getCpuDomainOject() {
		return cpuDomainOject;
	}
	/**
	 * @param cpuDomainOject
	 */
	public void setCpuDomainOject(CpuDomainObject cpuDomainOject) {
		this.cpuDomainOject = cpuDomainOject;
	}
	/**
	 * @return  memoryDomainOject
	 */
	public MemoryDomainObject getMemoryDomainOject() {
		return memoryDomainOject;
	}
	/**
	 * @param memoryDomainOject
	 */
	public void setMemoryDomainOject(MemoryDomainObject memoryDomainOject) {
		this.memoryDomainOject = memoryDomainOject;
	}

	/**
	 * @return String
	 */
	public String toString(){
		JSONObject nodeObj		=	new JSONObject();
		JSONObject cpuObj		=	new JSONObject();
		JSONObject memoryObj	=	new JSONObject();
		
		try {
			cpuObj.put(MetricConstants.CORE1,this.cpuDomainOject.getCoreSize1());
			cpuObj.put(MetricConstants.CORE2,this.cpuDomainOject.getCoreSize2());
			
			memoryObj.put(MetricConstants.USED,this.memoryDomainOject.getUsedMemory());
			memoryObj.put(MetricConstants.FREE,this.memoryDomainOject.getFreeMemory());
			
			nodeObj.put(MetricConstants.CPU, cpuObj);
			nodeObj.put(MetricConstants.MEMORY, memoryObj);
			
			return nodeObj.toString();
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
