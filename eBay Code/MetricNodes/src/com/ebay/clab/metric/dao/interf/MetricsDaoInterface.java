/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a DAO interface for metric nodes data
 *************************************************************************************/
package com.ebay.clab.metric.dao.interf;

import java.sql.SQLException;
import java.util.List;

import com.ebay.clab.metric.domainobject.MetricNodeDomainObject;
import com.ebay.clab.metric.exception.MetricDAOException;

public interface MetricsDaoInterface {

	/**
	 * Description: This method will perform write operation on NODE INFO table..
	 * @param String input, String timeStamp
	 * @return none
	 */
	public void StoreMetric(String input, String timeStamp);
	
	/**
	 * Description: This method will perform read operation on NODE INFO table..
	 * @param String input, String timeStamp
	 * @return String
	 */
	public String readData(String requestID);

	List<MetricNodeDomainObject> readCoreData(String coreId) throws MetricDAOException, SQLException;
}
