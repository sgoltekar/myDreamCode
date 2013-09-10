/**
 * 
 */
package com.ebay.clab.metric.factory;

import com.ebay.clab.metric.dao.impl.MetricsDAO;
import com.ebay.clab.metric.domainobject.CpuDomainObject;
import com.ebay.clab.metric.domainobject.MemoryDomainObject;
import com.ebay.clab.metric.domainobject.MetricNodeDomainObject;

/**
 * @author U262781
 *
 */
public class MetricFactory {
	
	public static MetricsDAO getMetricDAO(){
		return new MetricsDAO();
	}

	public static MetricNodeDomainObject getNodeObj(){
		return new MetricNodeDomainObject();
	}
	public static CpuDomainObject getCpuObj(){
		return new CpuDomainObject();
	}
	public static MemoryDomainObject getMemObj(){
		return new MemoryDomainObject();
	}
}
