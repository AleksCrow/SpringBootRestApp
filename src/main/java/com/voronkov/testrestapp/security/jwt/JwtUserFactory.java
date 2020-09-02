package com.voronkov.testrestapp.security.jwt;

import com.voronkov.testrestapp.model.User;

/**Factory for create JwtUser
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }
}
