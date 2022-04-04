package com.splask.user;


import java.util.List;

import com.splask.team.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.sun.org.apache.xerces.internal.util.URI;

import net.minidev.json.JSONObject;

@RestController 
public class UserController {
	
    @Autowired
	UserDB userRepository;

//  get user by ID
	@GetMapping("/user/{id}")
	User getUsername(@PathVariable Integer id) {
		return userRepository.findById(id).orElseThrow(RuntimeException::new);
	}

//  get all the users
	@RequestMapping("user")
	List<User> getAllUsers() {
		return userRepository.findAll();
	}

// Registers user to the database and checks if there is an
// existing user registered with the same username
	@PostMapping("/register")
	public JSONObject registerUser(@RequestBody User newUser) {
		JSONObject responseBody = new JSONObject();
		List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.username.equals(newUser.username)) {
                responseBody.put("status", 400);
                responseBody.put("message", "User Already Exists!");
                return responseBody;
            }
        }

//      Checks length, upper, lower case, numeric value and special character of the input password
        User user = new User();
        if (user.isAllPresent(user.password) != true){ //Checks if the password meets the safety criteria
            responseBody.put("status", 400);
            responseBody.put("message", "Please enter a valid password of at least 4 characters containing uppercase, lowercase\n" +
                    "\t// special character & numeric value");
        }

        userRepository.save(newUser);
        responseBody.put("status", 200);
        responseBody.put("message", "Account successfully created!");
        
        return responseBody;
    }

    @PostMapping("/login")
    public JSONObject loginUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();

//      Updates user logged in status
        for (User other : users) {
            if (other.equals(user)) {
                user.setLoggedIn(true);
                responseBody.put("status", 200);
                responseBody.put("message", "Login Successful");
                return responseBody;
            }
        }

        responseBody.put("status", 400);
        responseBody.put("message", "Login Failed");
        return responseBody;
    }

//  Log out call
    @PostMapping("/logout")
    public JSONObject logoutUser(@RequestBody User user) {
        JSONObject responseBody = new JSONObject();
        List<User> users = userRepository.findAll();

        for (User other : users) {
            if (other.username.equals(user.username)) {
                user.setLoggedIn(false);
                responseBody.put("status", 200);
                responseBody.put("message", "User Successfully logged out");
                return responseBody;
            }
        }
        responseBody.put("status", 400);
        responseBody.put("message", "Failure to logout");
        return responseBody;
    }

//	 Delete user by id
	@DeleteMapping("/user/{id}")
	JSONObject deleteUser(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
		userRepository.deleteById(id);
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully deleted user");
		return responseBody;
	}

//	Delete ALL users
    @DeleteMapping("/user/all")
    public JSONObject deleteUsers() {
        JSONObject responseBody = new JSONObject();
    	userRepository.deleteAll();
        responseBody.put("status", 200);
        responseBody.put("message", "Successfully deleted all users");
        return responseBody;
    }


}
