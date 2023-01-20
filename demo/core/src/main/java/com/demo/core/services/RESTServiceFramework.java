package com.demo.core.services;

import java.util.Map;

import com.demo.core.bo.RequestModel;

public interface RESTServiceFramework {
	/**
	 * Make get WS call.
	 *
	 * @param endPointUrl   the end point url
	 * @param requestParams the request params
	 * @param headers       the headers
	 * @return the string
	 */
	public String makeGetWSCall(String endPointUrl, Map<String, String> requestParams, Map<String, String> headers, RequestModel requestModel);

	String makePostWSCall(String endPointUrl, String requestBody, Map<String, String> requestParams, Map<String, String> headers, RequestModel requestModel);

	String makePutWSCall(String endPointUrl, String requestBody, Map<String, String> requestParams, Map<String, String> headers, RequestModel requestModel);

	String makePatchWSCall(String endPointUrl, String requestBody, Map<String, String> requestParams, Map<String, String> headers, RequestModel requestModel);

	String makeDeleteWSCall(String endPointUrl, Map<String, String> headers, RequestModel requestModel);

}
