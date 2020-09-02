package com.voronkov.testrestapp.dto;

/**For response user for authorization
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
public class AuthenticationRequestDto {

    private String email;
    private String password;

    public AuthenticationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
