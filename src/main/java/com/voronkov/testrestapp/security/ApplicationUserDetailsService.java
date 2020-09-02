package com.voronkov.testrestapp.security;

import com.voronkov.testrestapp.exception.UserNotFoundException;
import com.voronkov.testrestapp.model.User;
import com.voronkov.testrestapp.repository.UserRepository;
import com.voronkov.testrestapp.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**Interface for retrieve user-related data
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return JwtUserFactory.create(user);
    }
}
