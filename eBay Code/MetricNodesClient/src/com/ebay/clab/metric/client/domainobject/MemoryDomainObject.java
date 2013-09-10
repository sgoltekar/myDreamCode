/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Domain object class for storing Memory data
 *************************************************************************************/
package com.ebay.clab.metric.client.domainobject;

/**
 * Class Name: MemoryDomainObject
 * This is a Domain object class for storing memory data
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
public class MemoryDomainObject {
	private String usedMemory = null;
	private String freeMemory = null;

	/**
	 * @return usedMemory
	 */
	public String getUsedMemory() {
		return usedMemory;
	}

	/**
	 * @param usedMemory
	 */
	public void setUsedMemory(String usedMemory) {
		this.usedMemory = usedMemory;
	}

	/**
	 * @return freeMemory
	 */
	public String getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @param freeMemory
	 */
	public void setFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
	}

}
