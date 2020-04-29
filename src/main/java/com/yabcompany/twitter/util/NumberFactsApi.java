package com.yabcompany.twitter.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for API request
 * Here I using API from RapidAPI (rapidapi.com)
 */
@Data
@NoArgsConstructor
@Component
@Slf4j
public class NumberFactsApi {

    @Value("${api.numbers.url}")
    private String URL;

    @Value("${api.numbers.host}")
    private String HOST;

    @Value("${api.numbers.key}")
    private String KEY;


    public String getRandomFact() {
        try {
            JSONObject jsonObject = getJsonObject();
            String text = (String) jsonObject.get("text");
            Integer number = (Integer) jsonObject.get("number");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(text).append(" - ").append(number);
            return stringBuilder.toString();

        } catch (UnirestException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public JSONObject getJsonObject() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(URL)
                .header("x-rapidapi-host", HOST)
                .header("x-rapidapi-key", KEY)
                .asJson();
        return response.getBody().getObject();
    }

}
