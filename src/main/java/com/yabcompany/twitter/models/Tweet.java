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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tweets")
/*
The dynamic-update attribute tells Hibernate whether to include unmodified properties in the SQL UPDATE statement.
*/
@DynamicUpdate
public class Tweet {

    /**
     * Id field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tweet author
     */

    /*
    The FetchType.EAGER tells Hibernate to get all elements of a relationship
     when selecting the root entity. As I explained earlier, this is the
     default.ftlh for to-one relationships, and you can see it in the
     following code snippets.
     */

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    /**
     * Tweet text field
     */

    @NotNull
    @Length(max = 140)
    @Column(nullable = false)
    private String tweetText;

    /**
     * List users who likes tweet
     */

    /*
    The FetchType.LAZY tells Hibernate to only fetch the related entities
     from the database when you use the relationship. This is a good idea
     in general because there’s no reason to select entities you don’t need
     for your uses case. You can see an example of a lazily fetched relationship
     in the following code snippets.
     */
    @OneToMany(mappedBy = "liked_tweets", fetch = FetchType.LAZY)
    private Set<User> likes = new HashSet<>();


    /**
     * Creation date
     */
    @NotNull
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.EAGER)
    private Thread repliedTweet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return id.equals(tweet.id) &&
                author.equals(tweet.author) &&
                tweetText.equals(tweet.tweetText) &&
                Objects.equals(likes, tweet.likes) &&
                createdAt.equals(tweet.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, tweetText, likes, createdAt);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", author=" + author +
                ", tweetText='" + tweetText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
