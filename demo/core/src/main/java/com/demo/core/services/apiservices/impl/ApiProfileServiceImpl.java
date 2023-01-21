package com.demo.core.services.apiservices.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.bo.ResponseStatusMsg;
import com.demo.core.services.apiservices.ApiProfileService;
import com.demo.core.services.apiservices.RESTServiceFramework;

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

	@Override
	public List<ProfileResponse> getAllMemberProfile(RequestModel requestModel,String isDemo) {
		String endPointUrl = (config.getApiEndPointUrl()) + config.getApiGetProfilePath();
		final String response = restService.makeGetWSCall(endPointUrl, null, getRequestHeaders(isDemo), requestModel);
		final Gson gson = new Gson();
		if (StringUtils.isNotBlank(response)) {
			final List<ProfileResponse> resObj = gson.fromJson(response, List.class);
			return resObj;
		}
		return null;
	}

	@Override
	public ProfileResponse addMemberProfile(RequestModel requestModel, String requestBody ,String IsDemo){
		String endPointUrl = (config.getApiEndPointUrl()) + config.getApiGetProfilePath();
		final String response = restService.makePostWSCall(endPointUrl, requestBody, null, getRequestHeaders(IsDemo), requestModel);
		final Gson gson = new Gson();
		if (StringUtils.isNotBlank(response)) {
			final ProfileResponse resObj = gson.fromJson(response,ProfileResponse.class);
			return resObj;
		}
		return null;
	}

	@Override
	public ResponseStatusMsg deleteMemberProfile(RequestModel requestModel, String profileId,String IsDemo) {
		String endPointUrl = formEndPointUrl(profileId);
		final String response = restService.makeDeleteWSCall(endPointUrl, getRequestHeaders(IsDemo) , requestModel);
		final Gson gson = new Gson();
		if (StringUtils.isNotBlank(response)) {
			final ResponseStatusMsg resObj = gson.fromJson(response, ResponseStatusMsg.class);
			return resObj;
		}
		return null;
	}

	@Override
	public ProfileResponse editMemberProfile(RequestModel requestModel,String requestBody,String profileId,String IsDemo) {
		String endPointUrl = formEndPointUrl(profileId);
		final String response = restService.makePatchWSCall(endPointUrl, requestBody, null, getRequestHeaders(IsDemo), requestModel);
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
