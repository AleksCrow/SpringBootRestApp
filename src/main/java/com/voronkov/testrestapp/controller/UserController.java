package com.voronkov.testrestapp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.TextNode;
import com.voronkov.testrestapp.exception.EmailValidException;
import com.voronkov.testrestapp.exception.ExistingEmailException;
import com.voronkov.testrestapp.exception.UserNotFoundException;
import com.voronkov.testrestapp.model.User;
import com.voronkov.testrestapp.repository.UserRepository;
import com.voronkov.testrestapp.util.View;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**Controller for main users endpoints
 * @author A.Voronkov
 * @since 27.08.2020
 * @version 1.0
 */
@RestController
public class UserController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/**Pattern of checking a string to email
	 */
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
					"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Autowired
	public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**Get all profiles
	 * @return json of all users
	 */
	@GetMapping("profiles")
	public List<User> getAllProfiles() {
		return userRepository.findAll();
	}

	/**Get profile by id
	 * @param id user id
	 * @return json user id which we are looking for
	 * @exception UserNotFoundException if user with this id does not exist
	 */
	@GetMapping("profiles/{id}")
	public User getById(@PathVariable("id") int id) {
		return userRepository.findById(id)
				.orElseThrow(UserNotFoundException::new);
	}

	/**Get last user which registered
	 * @return json user
	 */
	@GetMapping("profiles/last")
	public User getLast() {
		return userRepository.findTopByOrderByIdDesc();
	}

	/**Get profile by email
	 * @param email user email
	 * @return json user
	 * @exception EmailValidException if email not valid
	 * @exception UserNotFoundException
	 */
	@PostMapping("profiles/get")
	public ResponseEntity<User> getByEmail(@RequestBody String email) {

		String userEmail = new TextNode(email).asText();
		System.out.println(userEmail);
		isValidEmail(userEmail);

		return Optional
				.ofNullable(userRepository.findByEmailIgnoreCase(userEmail))
				.map(user -> ResponseEntity.ok().body(user))
				.orElseThrow(UserNotFoundException::new);
	}

	/**Create user
	 * @param user name, email, age
	 * @return id of created user
	 * @exception EmailValidException
	 * @exception ExistingEmailException if email already exist
	 */
	@JsonView({View.DisplayId.class})
	@PostMapping(value = "profiles/set")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<User> create(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		isValidEmail(user.getEmail());
		if (userRepository.findByEmailIgnoreCase(user.getEmail()) != null) {
			throw new ExistingEmailException(user.getEmail());
		}

		userRepository.save(user);
		return ResponseEntity.ok().body(user);
	}

	/**Method for checking string on validation by email pattern
	 * @param email
	 * @exception EmailValidException
	 */
	protected static void isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		if (!pattern.matcher(email).matches()) {
			throw new EmailValidException();
		}
	}

}
