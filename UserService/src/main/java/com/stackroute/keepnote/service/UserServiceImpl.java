/*package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;


 * Service classes are used here to implement additional business logic/validation 
 * This class has to be annotated with @Service annotation.
 * @Service - It is a specialization of the component annotation. It doesn't currently 
 * provide any additional behavior over the @Component annotation, but it's a good idea 
 * to use @Service over @Component in service-layer classes because it specifies intent 
 * better. Additionally, tool support and additional behavior might rely on it in the 
 * future.
 * 
@Service
public class UserServiceImpl implements UserService {


 * Autowiring should be implemented for the UserRepository. (Use
 * Constructor-based autowiring) Please note that we should not create any
 * object using the new keyword.



 * This method should be used to save a new user.Call the corresponding method
 * of Respository interface.

	@Autowired
	UserRepository userRepository;

	public User registerUser(User user) throws UserAlreadyExistsException {

		try {
			User userVo = getUserById(user.getUserId());
			if(null != userVo) {
				throw new UserAlreadyExistsException("UserAlreadyExistsException");
			}
			 userRepository.insert(user);
			 return user;
		} catch (Exception e) {
			throw new UserAlreadyExistsException("UserAlreadyExistsException");
		}
	}
	public User registerUser(User user) throws UserAlreadyExistsException {
		User savedUser = null;
        user.setUserAddedDate(new Date());
        try {
			savedUser = userRepository.insert(user);
		} catch (Exception e) {
			throw new UserAlreadyExistsException("User with ID" + user.getUserId() + "already exists");
		}
        if (userRepository.existsById(user.getUserId())) {
            throw new UserAlreadyExistsException("User with ID" + user.getUserId() + "already exists");
        } else {
            user.setUserAddedDate(new Date());
            savedUser = userRepository.insert(user);
            if (savedUser == null) {
                throw new UserAlreadyExistsException("User with ID" + user.getUserId() + "already exists");
            }
        }
        return savedUser;
		user.setUserAddedDate(new Date());
		return Optional.ofNullable(userRepository.insert(user))
				.orElseThrow(() -> new UserAlreadyExistsException("Not created"));
		if (userRepository.existsById(user.getUserId())) {
			throw new UserAlreadyExistsException("");
		} else {
			user.setUserAddedDate(new Date());
			User user2 = userRepository.insert(user);
			if (user2 == null) {
				throw new UserAlreadyExistsException("");
			}
			return user2;
		}
	}


 * This method should be used to update a existing user.Call the corresponding
 * method of Respository interface.


	public User updateUser(String userId, User user) throws UserNotFoundException {

		if(null == userId || null == user) {
			return null;
		}
		try {
			getUserById(userId);
			user.setUserAddedDate(new Date());
			userRepository.save(user);
			User userVo = userRepository.save(user);
			return userVo;
			return user;
		} catch (Exception e) {
			throw new UserNotFoundException("UserNotFoundException");
		}
	}


 * This method should be used to delete an existing user. Call the corresponding
 * method of Respository interface.


	public boolean deleteUser(String userId) throws UserNotFoundException {

		try {
			if(null != userId) {
				User user = getUserById(userId);
				userRepository.delete(user);
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new UserNotFoundException("UserNotFoundException");
		}
	}


 * This method should be used to get a user by userId.Call the corresponding
 * method of Respository interface.


	public User getUserById(String userId) throws UserNotFoundException {

		if(null == userId) {
			return null;
		}
		try {
			return userRepository.findById(userId).orElse(null);
			if(null == user) {
				return null;
			}
			return user.get();
			//	return userRepository.findById(userId).get();
		} catch (Exception e) {
			throw new UserNotFoundException("UserNotFoundException");
		}
	}

}
 */
package com.stackroute.keepnote.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
 * Service classes are used here to implement additional business logic/validation 
 * This class has to be annotated with @Service annotation.
 * @Service - It is a specialization of the component annotation. It doesn't currently 
 * provide any additional behavior over the @Component annotation, but it's a good idea 
 * to use @Service over @Component in service-layer classes because it specifies intent 
 * better. Additionally, tool support and additional behavior might rely on it in the 
 * future.
 * */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private UserRepository userRepository;
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */
	public User registerUser(User user) throws UserAlreadyExistsException {
		if (userRepository.existsById(user.getUserId())) {
			throw new UserAlreadyExistsException("");
		} else {
			LOGGER.info("User is not found now saving process started");
			user.setUserAddedDate(new Date());
			User user2 = userRepository.insert(user);
			if (user2 == null) {
				throw new UserAlreadyExistsException("");
			}
			return user2;
		}

	}/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId, User user) throws UserNotFoundException {
		if (getUserById(userId) != null) {
			userRepository.save(user);
			return user;
		} else {
			throw new UserNotFoundException("");
		}

	}
	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */
	public boolean deleteUser(String userId) throws UserNotFoundException {
		if (userId == null) {
			return false;
		}
		userRepository.delete(getUserById(userId));
		return true;
	}
	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {

		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(""));
	}
}