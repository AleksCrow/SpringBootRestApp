package com.voronkov.testrestapp.exception;

/**Exception which throw, when email not valid
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public class EmailValidException extends RuntimeException {

    public EmailValidException() {
        super("Email is not valid");
    }
}
