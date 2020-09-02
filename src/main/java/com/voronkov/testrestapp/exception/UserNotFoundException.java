package com.voronkov.testrestapp.exception;

/**Exception which throw, when user can't find
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User is not found");
    }
}
