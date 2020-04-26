package com.yabcompany.twitter.dto;

import com.yabcompany.twitter.models.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateTweetDto {
    @Length(min = 1, max = 140)
    private String tweetText;

    private LocalDateTime dateCreated;

    /**
     * Creating Data Transfer Object from Tweet
     *
     * @param tweet
     * @return CreateTweetDto
     */
    public static CreateTweetDto from(Tweet tweet) {
        return CreateTweetDto.builder()
                .dateCreated(tweet.getCreatedAt())
                .tweetText(tweet.getTweetText())
                .build();
    }

    /**
     * Creating Data Transfer Object from Tweets list
     *
     * @param tweets
     * @return List of TweetDto
     */
    public static List<CreateTweetDto> from(List<Tweet> tweets) {
        return tweets.stream()
                .map(CreateTweetDto::from)
                .collect(Collectors.toList());
    }
}
