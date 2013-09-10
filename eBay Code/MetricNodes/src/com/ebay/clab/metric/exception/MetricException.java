/************************************************************************************
* Title:		MetricException 
* Author:		Sandeep.Goltekar
* Date:			06/03/2013
* Version:		1.0
* Description:	This is a Main Mars Custom Exception class.
*************************************************************************************/
package com.ebay.clab.metric.exception;

/**
 * Class Name: 		MetricException
 * Description: 	This is a Main metric Custom Exception class.
 * @version :	 	1.0
 * @author : 		Sandeep.Goltekar
 */
public class MetricException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public MetricException(Exception e) {
		super(e);
	}
	
	public MetricException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public MetricException(Exception e, String errorMessage) {
		super(e);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
