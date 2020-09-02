package com.voronkov.testrestapp.exception;

/**Exception which throw, when email already exist
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public class ExistingEmailException extends RuntimeException {

    public ExistingEmailException(String email) {
        super("Email " + email + " is already existing");
    }
}
