package com.demo.core.services;

import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.exceptions.DemoException;

/**
 * Interface for Member Profile LookUp
 * 
 * @author sv2
 *
 */
public interface ApiProfileService {

	public ProfileResponse getMemberProfile(RequestModel requestModel, String profileId, String IsDemo);

}

