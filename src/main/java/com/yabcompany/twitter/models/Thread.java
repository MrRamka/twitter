package com.yabcompany.twitter.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_tweet_id", referencedColumnName = "id")
    private Tweet mainTweet;


    @OneToMany(mappedBy = "repliedTweet", cascade = CascadeType.ALL, targetEntity = Tweet.class)
    private Set<Tweet> repliedTweets = new HashSet<>();

}
