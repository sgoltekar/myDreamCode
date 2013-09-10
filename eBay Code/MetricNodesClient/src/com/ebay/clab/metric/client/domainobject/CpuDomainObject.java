/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Domain object class for storing cpu data
 *************************************************************************************/
package com.ebay.clab.metric.client.domainobject;

/**
 * Class Name: CpuDomainObject
 * This is a Domain object class for storing cpu data
 * @version : 1.0
 * @author : Sandeep.Goltekar
 */
public class CpuDomainObject {
	private String coreSize1 = null;
	private String coreSize2 = null;

	/**
	 * @return String
	 */
	public String getCoreSize1() {
		return coreSize1;
	}
	/**
	 * @param coreSize1
	 */
	public void setCoreSize1(String coreSize1) {
		this.coreSize1 = coreSize1;
	}
	/**
	 * @return coreSize2
	 */
	public String getCoreSize2() {
		return coreSize2;
	}
	/**
	 * @param coreSize2
	 */
	public void setCoreSize2(String coreSize2) {
		this.coreSize2 = coreSize2;
	}
}
