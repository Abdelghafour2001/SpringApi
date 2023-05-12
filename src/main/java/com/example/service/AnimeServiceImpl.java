package com.example.service;

import com.example.dto.AnimeDTO;
import com.example.model.Anime;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Anime> list() {
        RestTemplate restTemplate = new RestTemplate();

        // Specify the API URL
        String apiUrl = "http://localhost:3000/getOngoingSeries";

        // Send a GET request to the API and retrieve the response
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

        // Extract the response body
        String responseBody = responseEntity.getBody();

        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Map the JSON data to a list of Person objects
        List<Anime> persons = new ArrayList<>();
        try {
            List<AnimeDTO> dataObjects = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, AnimeDTO.class));
            for (AnimeDTO dataObject : dataObjects) {
                Anime person = new Anime();
                person.setAnimeId(dataObject.getAnimeId());
                mongoTemplate.insert(person);
                persons.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process the list of persons
        for (Anime person : persons) {
            System.out.println(person.getAnimeId());
        }
        return persons;
    }


    @Override
    public Anime listId(int id) {
        return null;
    }
    @Override
    public Anime add(Anime p) {
        return null;
    }
    @Override
    public Anime edit(Anime p) {
        return null;
    }
    @Override
    public Anime delete(int id) {
        return null;
    }
}
