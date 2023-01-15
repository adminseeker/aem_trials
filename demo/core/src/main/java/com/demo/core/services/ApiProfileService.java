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
	/**
	 * Gets the member profile.
	 *
	 * @param accesToken the acces token
	 * @param profileId  the profile id
	 * @param config     the config
	 * @return the member profile
	 * @throws USBankExecption
	 */
	public ProfileResponse getMemberProfile(RequestModel requestModel, String profileId, String IsDemo);

}
