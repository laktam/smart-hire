package org.mql.cloud.smart_hire.service;

import org.mql.cloud.smart_hire.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleGeminiService {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    @Value("${google.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    
    public String matchApplicationToPost(String application, Post post) {
    	String prompt = "Don't responde with more than 30 word, Match this job with this title : "+ post.getPostName() + " and this desciption : " + post.getPostDescription() + " with this application information : " + application + ". (start the response with a percentage in this form 'X% Match.' then a very brief summary of why, and make a strict maching)";

    	String requestBody = "{"
            + "\"contents\": ["
            + "  {"
            + "    \"parts\": ["
            + "      { \"text\": \"" + prompt + "\" }"
            + "    ]"
            + "  }"
            + "]"
            + "}";

        // Construct the full URL with the API key as a query parameter
        String url = GEMINI_API_URL + "?key=" + apiKey;

        // Set up the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the request body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
            );

            String responseBody = response.getBody();
            JsonNode responseJson = new ObjectMapper().readTree(responseBody);
            String content = responseJson.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
            System.out.println("gemini content : " + content);
            
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to fetch content from Gemini API.";
        }
    }

    public String intelligentFormat(String prompt) {
        // Prepare the request payload
    	prompt = "format this text (and use html as a response) (don't add any extra text in the response) : " + prompt;

    	String requestBody = "{"
            + "\"contents\": ["
            + "  {"
            + "    \"parts\": ["
            + "      { \"text\": \"" + prompt + "\" }"
            + "    ]"
            + "  }"
            + "]"
            + "}";

        // Construct the full URL with the API key as a query parameter
        String url = GEMINI_API_URL + "?key=" + apiKey;

        // Set up the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the request body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Send the request to the Gemini API
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
            );

            // Parse the response and extract the content
            String responseBody = response.getBody();
            // Assuming the response body contains the candidates in a JSON format
            JsonNode responseJson = new ObjectMapper().readTree(responseBody);
            String content = responseJson.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
            System.out.println("gemini content : " + content.subSequence(8, content.length() - 4));
            
            return content.subSequence(8, content.length() - 4) + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to fetch content from Gemini API.";
        }
    }
}
