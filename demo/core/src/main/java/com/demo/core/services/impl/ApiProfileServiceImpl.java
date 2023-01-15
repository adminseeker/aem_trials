package com.demo.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.services.ApiProfileService;
import com.demo.core.services.RESTServiceFramework;
import com.demo.core.services.impl.ApiRestConfigImpl;

@Component(immediate = true, service = ApiProfileService.class)
public class ApiProfileServiceImpl implements ApiProfileService {

	/** The rest service. */
	@Reference
	RESTServiceFramework restService;
	@Reference
	private ApiRestConfigImpl config;

	@Override
	public ProfileResponse getMemberProfile(RequestModel requestModel, String profileId,String isDemo) {
		String endPointUrl = formEndPointUrl(profileId);
		final String response = restService.makeGetWSCall(endPointUrl, null, getRequestHeaders(isDemo), requestModel);
		final Gson gson = new Gson();
		if (StringUtils.isNotBlank(response)) {
			final ProfileResponse resObj = gson.fromJson(response, ProfileResponse.class);
			return resObj;
		}
		return null;
	}

	/**
	 * Gets the reuest headers.
	 *
	 * @param accesToken    the acces token
	 * @param updateProfile the update profile
	 * @param isCsr 
	 * @return the reuest headers
	 */
	private Map<String, String> getRequestHeaders(String isDemo) {
		final Map<String, String> headers = new HashMap<>();
		headers.put("IsDemo",isDemo);
		return headers;
	}

	/**
	 * Form end point url.
	 *
	 * @param uid         the uid
	 * @param destAccNum  the dest acc num
	 * @param logActivity the log activity
	 * @param isCsr
	 * @return the string
	 */
	private String formEndPointUrl(String profileId) {
		String endpoint = null;
		endpoint = (config.getApiEndPointUrl()) + config.getApiGetProfilePath() + "/" + profileId;
		return endpoint;
	}

}
