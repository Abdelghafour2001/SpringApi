package com.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class MovieServiceImpl implements MovieService {
    @Override
    public ResponseEntity<Object> getMovies(String page, RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/movies?page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getTvshows(String page, RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/tv-shows?page=" + page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getStreaming(String mediaId, String episodeId, String server, RestTemplate restTemplate) {
        try {
            //if(page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/streaming?mediaId=" + mediaId+"&episodeId="+episodeId+"&server="+server;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getFiltered(String type, String quality, String released, String genre, String country, String page, RestTemplate restTemplate) {
        try {
            //String apiUrl = "http://localhost:3030/api/filter?type=" + type+"&quality="+quality+"&released="+released+"&genre="+genre+"&country="+country+"&page="+page;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filter");
            if (type != null) {
                builder.queryParam("type", type);
            }
            if (quality != null) {
                builder.queryParam("quality", quality);
            }
            if (released != null) {
                builder.queryParam("released", released);
            }
            if (genre != null) {
                builder.queryParam("genre", genre);
            }
            if (country != null) {
                builder.queryParam("country", country);
            }

            if (page != null) {
                builder.queryParam("page", page);
            }
            // Get the final API URL
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getEpisodeServers(String mediaId, String episodeId, RestTemplate restTemplate) {
        try {
            String apiUrl = "http://localhost:3030/api/episode-servers?mediaId=" + mediaId+"&episodeId="+episodeId;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getMovieInfo(String mediaId, RestTemplate restTemplate) {
        try {
            String apiUrl = "http://localhost:3030/api/info?mediaId=" + mediaId;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getTopMoviesImdb(String type, String page, RestTemplate restTemplate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filter");
            if (type != null) {
                builder.queryParam("type", type);
            }
            if (page != null) {
                builder.queryParam("page", page);
            }
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getAllgenres(RestTemplate restTemplate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/all-genres");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getAllCountries(RestTemplate restTemplate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/all-countries");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getFilters(RestTemplate restTemplate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:3030/api/filters-list");
            String apiUrl = builder.toUriString();
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getMovieByCountry(String country, String page, RestTemplate restTemplate) {
        try {
            if (page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/country/" + country+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> getMovieByGenre(String genre, String page, RestTemplate restTemplate) {
        try {
            if (page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/genre/" + genre+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }

    @Override
    public ResponseEntity<Object> search(String query, String page, RestTemplate restTemplate) {
        try {
            if (page==null){page="1";}
            String apiUrl = "http://localhost:3030/api/search/" + query+"?page="+page;
            Object response = restTemplate.getForObject(apiUrl, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }    }
}
