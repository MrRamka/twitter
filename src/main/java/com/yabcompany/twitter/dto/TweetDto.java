package com.yabcompany.twitter.dto;

import com.yabcompany.twitter.models.Tweet;
import com.yabcompany.twitter.models.User;
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
@Data
@Builder
public class TweetDto {
    private Long id;

    private UserDto author;

    @Length(min = 4)
    private String tweetText;

    private LocalDateTime dateCreated;

    /**
     * Creating Data Transfer Object from Tweet
     *
     * @param tweet
     * @return TweetDto
     */
    public static TweetDto from(Tweet tweet) {
        return TweetDto.builder()
                .id(tweet.getId())
                .author(UserDto.from(tweet.getAuthor()))
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
    public static List<TweetDto> from(List<Tweet> tweets) {
        return tweets.stream()
                .map(TweetDto::from)
                .collect(Collectors.toList());
    }
}
