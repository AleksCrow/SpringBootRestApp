package com.voronkov.testrestapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.voronkov.testrestapp.util.View;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;

/**User entity
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Proxy(lazy = false)
public class User {

    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.DisplayId.class)
    protected Integer id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @Size(max = 128)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 128)
    @Email
    private String email;

    @Column(name = "age", nullable = false)
    @Min(value = 1)
    private int age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    @Column(name = "created", nullable = false)
    @ApiModelProperty(hidden = true)
    @NotNull
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    @Column(name = "password")
    private String password = "123";

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
