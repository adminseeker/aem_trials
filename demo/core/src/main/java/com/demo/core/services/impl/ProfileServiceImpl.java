package com.demo.core.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.bo.ResponseStatusMsg;
import com.demo.core.services.ProfileService;
import com.demo.core.services.apiservices.ApiProfileService;
import com.demo.core.utils.CommonsUtil;

/**
 * 
 * 
 */
@Component(immediate = true, service = ProfileService.class)
public class ProfileServiceImpl implements ProfileService {


	/** The login service. */
	@Reference
	private ApiProfileService apiProfileService;

		/*
		 * Making getProfileById call
		 * 
		 */
    
    @Override
	public String getProfileDetails(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {
		
		
		
		ProfileResponse profile = apiProfileService.getMemberProfile(requestModel,requestModel.getProfileId(),requestModel.getIsDemo());
		if(null != profile ) {
			return CommonsUtil.getJsonFromObject(profile);
		} else {
			return CommonsUtil.getJsonFromObject(null);
		}
		
	}

		/*
		 * Making getProfiles call
		 * 
		 */
    @Override
	public List<ProfileResponse>  getProfileList(RequestModel requestModel) {
		
		List<ProfileResponse> profiles = apiProfileService.getAllMemberProfile(requestModel, requestModel.getIsDemo());
		if(null != profiles ) {
			return profiles;
		} else {
			return null;
		}
		
	}

		
		/*
		 * Making addProfile call
		 * 
		 */

    @Override
	public String addProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {		
		String profileResp = null;
		try {

			String body = IOUtils.toString(req.getReader());
			ProfileResponse profile = apiProfileService.addMemberProfile(requestModel,body,requestModel.getIsDemo());
		if(null != profile ) {
			profileResp= CommonsUtil.getJsonFromObject(profile);
		} else {
			profileResp = CommonsUtil.getJsonFromObject(null);
		} 
		
	}
		catch (IOException e) {
			e.printStackTrace();
			profileResp = CommonsUtil.getJsonFromObject(null);

		} 
		return profileResp;
		
	} 

	/*
		 * delete getProfileById call
		 * 
		 */
        @Override
		 public String deleteProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {
		
		
		
			ResponseStatusMsg statusMsg = apiProfileService.deleteMemberProfile(requestModel,requestModel.getProfileId(),requestModel.getIsDemo());
			if(null != statusMsg ) {
				return CommonsUtil.getJsonFromObject(statusMsg);
			} else {
				return CommonsUtil.getJsonFromObject(null);
			}
			
		}

		/*
		 * edit getProfileById call
		 * 
		 */
        @Override
		public String editProfile(SlingHttpServletRequest req, SlingHttpServletResponse resp, RequestModel requestModel) {		
			String profileResp = null;
			try {
	
				String body = IOUtils.toString(req.getReader());
				ProfileResponse profile = apiProfileService.editMemberProfile(requestModel,body,requestModel.getProfileId(),requestModel.getIsDemo());
			if(null != profile ) {
				profileResp= CommonsUtil.getJsonFromObject(profile);
			} else {
				profileResp = CommonsUtil.getJsonFromObject(null);
			} 
			
		}
			catch (IOException e) {
				e.printStackTrace();
				profileResp = CommonsUtil.getJsonFromObject(null);
	
			} 
			return profileResp;
			
		} 
	

}
