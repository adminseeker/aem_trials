package com.demo.core.services;

import java.util.List;

import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.bo.ResponseStatusMsg;
import com.demo.core.exceptions.DemoException;

/**
 * Interface for Member Profile LookUp
 * 
 * @author sv2
 *
 */
public interface ApiProfileService {

	public ProfileResponse getMemberProfile(RequestModel requestModel, String profileId, String IsDemo);
	public List<ProfileResponse> getAllMemberProfile(RequestModel requestModel, String IsDemo);
	public ProfileResponse addMemberProfile(RequestModel requestModel, String requestBody ,String IsDemo);
	public ResponseStatusMsg deleteMemberProfile(RequestModel requestModel, String profileId, String IsDemo);

}

