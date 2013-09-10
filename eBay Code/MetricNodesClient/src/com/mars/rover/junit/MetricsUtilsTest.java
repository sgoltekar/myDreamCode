/************************************************************************************
* Title:		MarsRover
* Author:		Sandeep.Goltekar
* Date:			06/03/2013
* Version:		1.0
* Description:	This is a JUNIT class. Will perform JUNIT test on methods
*************************************************************************************/

package com.mars.rover.junit;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import com.ebay.clab.metric.client.exception.MetricException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Class Name: 		Metric
 * Description: 	This is a JUNIT class. Will perform JUNIT test on methods.
 * @version :	 	1.0
 * @author : 		Sandeep.Goltekar
 */
public class MetricsUtilsTest {

	@Test
	public void testCalculateCordinate() throws MetricException {
		String mockInput = "\"{\"cpu\":{\"core1\":\"aa\",\"core2\":\"vvv\"},\"mem\":{\"used\":\"qqqqq\",\"free\":\"aaaa\"}}";
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		ClientResponse restResponse = service.path("metric").accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, mockInput);

		// Return code should be 200 == created resource
		assertEquals("Result", "200", ""+restResponse.getStatus());
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/MetricNodes").build();
	}
	

}
