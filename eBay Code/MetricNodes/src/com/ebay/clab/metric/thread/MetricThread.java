/************************************************************************************
 * Title:		MatricNode
 * Author:		Sandeep.Goltekar
 * Date:		09/08/2013
 * Version:		1.0
 * Description:	This is a Domain object class for storing Memory data
 *************************************************************************************/

package com.ebay.clab.metric.thread;

import com.ebay.clab.metric.dao.interf.MetricsDaoInterface;
import com.ebay.clab.metric.factory.MetricFactory;

public class MetricThread implements Runnable {
	private String input = null;
	private String timeStamp = null;

	public MetricThread(String input, String timeStamp) {
		this.input = input;
		this.timeStamp = timeStamp;
	}

	@Override
	public void run() {
		MetricsDaoInterface mDaoObj = MetricFactory.getMetricDAO();
		mDaoObj.StoreMetric(input, timeStamp);
	}
}
