package com.yabcompany.twitter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
/*
The dynamic-update attribute tells Hibernate whether to include unmodified properties in the SQL UPDATE statement.
*/
@DynamicUpdate
public class User {

    /**
     * Id field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name field
     */
    @NotNull
    @Length(max = 255)
    @Column(nullable = false)
    private String name;

    /**
     * Surname field
     */
    @NotNull
    @Length(max = 255)
    @Column(nullable = false)
    private String surname;

    /**
     * Username field
     */
    @NotNull
    @Length(min = 4)
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Hashed password field
     */
    @NotNull
    @Length(min = 8)
    @Column(nullable = false)
    private String hashPassword;


    private String bio;

    /**
     * Store string value
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;


    /**
     * Creation date
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * Liked tweets
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tweets")
    private Tweet liked_tweets;

    /**
     * User own tweets
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, targetEntity = Tweet.class)
    private Set<Tweet> tweets = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "users_follows",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_following_id")}
    )
    private Set<User> following = new HashSet<User>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                username.equals(user.username) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, username, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }
}
