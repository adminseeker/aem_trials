package com.demo.core.utils.apiUtils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.demo.core.bo.RequestModel;
import com.demo.core.exceptions.DemoException;
import com.demo.core.services.apiservices.RESTServiceFramework;
import com.demo.core.services.apiservices.impl.ApiRestConfigImpl;
import com.demo.core.utils.CommonsUtil;

@Component(service = ApiUtilService.class, immediate = true, property = { "process.label= Api Util Service", Constants.SERVICE_DESCRIPTION + "=Demo - API Util Service", Constants.SERVICE_VENDOR + "=Demo",
		Constants.SERVICE_RANKING + ":Integer=1096" })
public class ApiUtilService {
	/**
	 * The rest service.
	 */
	@Reference
	private RESTServiceFramework restService;
	@Reference
	private ApiRestConfigImpl config;

	/**
	 * @param endPointUrl
	 * @param requestParams
	 * @param requestModel
	 * @param responseObject
	 * @return
	 * @throws DemoException
	 */
	public <T> T getAPI(String endPointUrl, Map<String, String> requestParams, RequestModel requestModel, Class<T> responseObject) throws DemoException {

		final Map<String, String> headers = getProfileIdHeaders(requestModel);
		final String response = restService.makeGetWSCall(endPointUrl, requestParams, headers,requestModel);
		if (null != response) {
			return CommonsUtil.getObjectFromJson(response, responseObject);
		}
		return null;
	}

	public <T> T putAPI(String request, String endPointUrl, RequestModel requestModel, Class<T> responseObject) {

		final Map<String, String> headers = getProfileIdHeaders(requestModel);
		if (StringUtils.isNotBlank(request)) {
			final String response = restService.makePutWSCall(endPointUrl, request, null, headers,requestModel);
			if (StringUtils.isNotBlank(response)) {
				return CommonsUtil.getObjectFromJson(response, responseObject);
			}
		}
		return null;
	}

	public <T> T postAPI(String endPointUrl, Map<String, String> requestParams, String requestBody, RequestModel requestModel, Class<T> responseObject) {
		final Map<String, String> headers = getProfileIdHeaders(requestModel);
		final String response = restService.makePostWSCall(endPointUrl, requestBody, requestParams, headers,requestModel);
		if (StringUtils.isNotBlank(response)) {
			return CommonsUtil.getObjectFromJson(response, responseObject);
		}
		return null;
	}

	

	/**
	 * @param requestModel
	 * @return
	 */
	private Map<String, String> getProfileIdHeaders(RequestModel requestModel) {
		final Map<String, String> headers = new HashMap<>();
		headers.put("isDemo",requestModel.getIsDemo());
		return headers;
	}


}
