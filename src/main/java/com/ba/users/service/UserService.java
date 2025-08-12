/**
 * 
 */
package com.ba.users.service;

import com.ba.users.model.UserResponse;

/**
 * @author Preeti Pandey
 *
 */
public interface UserService {

	UserResponse getUser(String userId);

	UserResponse getUserProfile(String userId);

	UserResponse getUserSummary(String userId);

	UserResponse getUserContact(String userId);

}
