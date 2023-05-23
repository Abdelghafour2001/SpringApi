package com.example.service;

import com.example.dto.AnimeDTO;
import com.example.model.Anime;
import com.example.model.Episode;
import com.example.model.WatchAnime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AnimeServiceImpl implements AnimeService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<Object> getRecentlyAdded(String page,RestTemplate restTemplate) {
        try{if(page==null){page="1";}
        Document document = Jsoup.connect("https://gogoanime.gr/?page="+page).get();

        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.added_series_body.final ul.listing li");
        for (Element link : imports ) {Anime anime=new Anime();
            String animeId = String.valueOf(link.select("a").attr("href"));
            String animeTitle = String.valueOf(link.select("a").text());
            anime.setAnimeId(animeId);
            anime.setAnimeTitle(animeTitle);
            listAnime.add(anime);
           // System.out.println(animeTitle);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getOngoingSeries(String page,RestTemplate restTemplate) {
        try{if(page==null){page="1";}
        Document document = Jsoup.connect("https://gogoanime.gr/?page="+page).get();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("nav.menu_series.cron ul li");
        for (Element link : imports ) {Anime anime=new Anime();
            String animeId = String.valueOf(link.select("a").attr("href"));
            String animeTitle = String.valueOf(link.select("a").text());
            anime.setAnimeId(animeId);
            anime.setAnimeTitle(animeTitle);
            listAnime.add(anime);
            //System.out.println(animeTitle);
        }return ResponseEntity.status(HttpStatus.OK).body(listAnime);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Error: " + e.getMessage());
    }   }

    @Override
    public ResponseEntity<Object> search(String keyword, String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/search.html?keyword="+keyword+"&page="+page).get();

            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
               // System.out.println(imgUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> watchEpisode(String id,RestTemplate restTemplate) {
        try {
            Document link = Jsoup.connect("https://gogoanime.gr/"+id).get();
            WatchAnime anime =new WatchAnime();
            String movie_id = String.valueOf(link.select("#movie_id").attr("value"));
            String anime_category = String.valueOf(link.select("div.anime-info a").attr("href").replace("/category/", ""));
            String episode_page = String.valueOf(link.select("ul#episode_page").html());
            String alias = String.valueOf(link.select("#alias_anime").attr("value"));
            String episode_link = String.valueOf(link.select("div.play-video > iframe").attr("src"));
            String gogoserver = String.valueOf(link.select("li.vidcdn > a").attr("data-video"));
            String streamsb = String.valueOf(link.select("li.streamsb > a").attr("data-video"));
            String xstreamcdn = String.valueOf(link.select("li.xstreamcdn > a").attr("data-video"));
            String anime_name_with_ep = String.valueOf(link.select("div.title_name h2").text());
            String ep_num = String.valueOf(link.select("div.anime_video_body > input.default_ep").attr("value"));
            String download = String.valueOf(link.select("li.dowloads a").attr("href"));
            String nextEpText = String.valueOf(link.select("div.anime_video_body_episodes_r a").text());
            String nextEpLink = String.valueOf(link.select("div.anime_video_body_episodes_r > a").attr("href"));
            String prevEpText = String.valueOf(link.select("div.anime_video_body_episodes_l a").text());
            String prevEpLink = String.valueOf(link.select("div.anime_video_body_episodes_l > a").attr("href"));
            anime.setAnime_info(anime_category);
            anime.setAlias(alias);
            anime.setVideo(episode_link);
            anime.setEpisode_page(episode_page);
            anime.setEp_num(ep_num);
            anime.setStreamsb(streamsb);
            anime.setXstreamcdn(xstreamcdn);
            anime.setMovie_id(movie_id);
            anime.setNextEpLink(nextEpLink);
            anime.setNextEpText(nextEpText);
            anime.setPrevEpLink(prevEpLink);
            anime.setPrevEpText(prevEpText);
            anime.setAnimeNameWithEP(anime_name_with_ep);
            anime.setGogoserver(gogoserver);
            anime.setEp_download(download);
           // System.out.println(episode_link);

        return ResponseEntity.status(HttpStatus.OK).body(anime);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Error: " + e.getMessage());
    }
    }

    @Override
    public ResponseEntity<?> getGenrePage(String genre, String page,RestTemplate restTemplate) {
        try {            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/genre/"+genre+"?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {        Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                String animeUrl = String.valueOf(link.select("p.name > a").attr("href"));
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setAnimeUrl(animeUrl);
                anime.setStatus(status);
                listAnime.add(anime);
                //System.out.println(animeUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getSeasonPage(String season, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.film/sub-category/"+season+"?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {
                Anime anime=new Anime();
                String animeId = String.valueOf(link.select("div > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("div > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").html().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
               // System.out.println(imgUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getAnimeAZPage(String aph, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/anime-list-"+aph+"?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.anime_list_body > ul.listing > li   ");
            for (Element link : imports ) {        Anime anime=new Anime();
                String animeId = String.valueOf(link.select("a").attr("href").replace("/category/", ""));
                String animeTitle = String.valueOf(link.select("a").html().replace("\"\"",""));
                //String liTitle = String.valueOf(link.attr("title"));
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                listAnime.add(anime);
                //System.out.println(liTitle);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<Object> getTopAiring(String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/popular.html?page="+page).get();

            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
            }

            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getAnimeList(String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/anime-list.html?page="+page).get();

            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.anime_list_body > ul.listing > li   ");
            for (Element link : imports ) {Anime anime=new Anime();
                String animeId = String.valueOf(link.select("a").attr("href").replace("/category/", ""));
                String animeTitle = String.valueOf(link.select("a").html().replace("\"\"",""));
                String liTitle = String.valueOf(link.attr("title"));
                anime.setAnimeImg(extractImageUrl(liTitle));
                anime.setAnimeTitle(extractTitle(liTitle));
                //anime.setGenres(extractGenres(liTitle));
                anime.setReleasedDate(extractReleased(liTitle));
                anime.setStatus(extractStatus(liTitle));
                anime.setSynopsis(extractPlotSummary(liTitle));
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                listAnime.add(anime);
                //System.out.println(liTitle);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }

    }
    private static String extractTitle(String liTitle) {
        org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
        Element titleElement = doc.selectFirst("a.bigChar");
        return titleElement.text();
    }
    public static String[] extractGenres(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element genreElement = doc.selectFirst("p.type:contains(Genre)");
        String genreText = genreElement.text().replace("Genre: ", "");
        String[] genreArray = genreText.split(", ");
        return genreArray;
    }

    public static String extractReleased(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element releasedElement = doc.select("span:contains(Released)").first();
        if (releasedElement != null) {
            String released = releasedElement.parent().text().replace("Released:", "").trim();
            return released;
        }
        return null;
    }

    private static String extractStatus(String liTitle) {
        org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
        Element statusElement = doc.selectFirst("p:contains(Status) span");
        String status = statusElement.text();
        return status;
    }


    public static String extractPlotSummary(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element summary = doc.select("p.sumer").first();
        String plotSummary = summary.text().replace("Plot Summary: ", "");
        return plotSummary;
    }


    private static String extractImageUrl(String liTitle) {
        org.jsoup.nodes.Document doc = Jsoup.parse(liTitle);
        Elements imgElements = doc.select("div.thumnail_tool img");
        if (imgElements.size() > 0) {
            return imgElements.get(0).attr("src");
        } else {
            return null;
        }}
    @Override
    public ResponseEntity<Object> getRecentRelease(String type, String page,RestTemplate restTemplate) {
        try {if(page==null){page="1";}
            Document document = Jsoup.connect("https://ajax.gogo-load.com/ajax/page-recent-release.html?type="+type+"&page="+page).get();

            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes.loaddub > ul > li");
            for (Element link : imports ) {
                Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[1].split("-episode-")[0]);
                String episodeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[1]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String episodeNum = String.valueOf(link.select("p.episode").text().replace("Episode ", "").trim());
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String subOrDub = String.valueOf(link.select("div > a > div").attr("class").replace("type ic-", ""));
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setEpisodeNum(episodeNum);
                anime.setEpisodeId(episodeId);
                anime.setSubOrDub(subOrDub);
                listAnime.add(anime);
                //System.out.println(episodeId);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }  }

    @Override
    public ResponseEntity<Object> getNewSeason(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/new-season.html?page="+page).get();
            //a with href

            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {
                Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
                //System.out.println(imgUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getOngoing(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/ongoing-anime.html?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.main_body div.last_episodes ul.items li");
            for (Element link : imports ) {
                Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
            }

            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getCompleted(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/completed-anime.html?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.main_body div.last_episodes ul.items li");
            for (Element link : imports ) {        Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getPopular(String page,RestTemplate restTemplate) {
        try {
            if(page==null){page="1";}
            Document document = Jsoup.connect("https://gogoanime.gr/popular.html?page="+page).get();
            ArrayList<Anime> listAnime= new ArrayList<Anime>();
            Elements imports = document.select("div.last_episodes > ul > li");
            for (Element link : imports ) {            Anime anime=new Anime();
                String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
                String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
                String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
                String status = String.valueOf(link.select("p.released").text().trim());
                anime.setAnimeId(animeId);
                anime.setAnimeTitle(animeTitle);
                anime.setAnimeImg(imgUrl);
                anime.setStatus(status);
                listAnime.add(anime);
                //System.out.println(imgUrl);
            }
            return ResponseEntity.status(HttpStatus.OK).body(listAnime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getAnime(String id,RestTemplate restTemplate) {
        try {
            Document document = Jsoup.connect("https://gogoanime.gr/category/"+id).get();
            Anime anime=new Anime();
            ArrayList<String> genres = new ArrayList<String>() ;
            ArrayList<Episode> episodes = new ArrayList<Episode>();
            String animeTitle = String.valueOf(document.select("div.anime_info_body_bg > h1").text());
            String episodeNum = String.valueOf(document.select("p.episode").text().replace("Episode ", "").trim());
            String type =String.valueOf(document.select("div.anime_info_body_bg > p:nth-child(4) > a").text());
            String imgUrl = String.valueOf(document.select("div.anime_info_body_bg > img").attr("src"));
            String desc = String.valueOf(document.select("div.anime_info_body_bg > p:nth-child(5)").text().replace("Plot Summary: ", ""));
            String status = String.valueOf(document.select("div.anime_info_body_bg > p:nth-child(8) > a").text());
            String otherNames = String.valueOf(document.select("div.anime_info_body_bg > p:nth-child(9)").text().replace("Other name: ", "").replace(";", ","));
            Elements genress = document.select("div.anime_info_body_bg > p:nth-child(6) > a");
            String releasedDate = String.valueOf(document.select("div.anime_info_body_bg > p:nth-child(7)").text().replace("Released: ", ""));
            for (Element link : genress ) {
                genres.add(link.attr("title").trim());
            }
            String ep_start = String.valueOf(document.select("#episode_page > li").first().select("a").attr("ep_start"));
            String ep_end =  String.valueOf(document.select("#episode_page > li").last().select("a").attr("ep_end"));
            String movie_id = String.valueOf(document.select("#movie_id").attr("value"));
            String alias = String.valueOf(document.select("#alias_anime").attr("value"));
            String episode_info_html =  String.valueOf(document.select("div.anime_info_episodes_next").html());
            String episode_page =  String.valueOf(document.select("ul#episode_page").html());
            Document document2 = Jsoup.connect("https://ajax.gogo-load.com/ajax/load-list-episode?ep_start="+ep_start+"&ep_end="+ep_end+"&id="+movie_id+"&default_ep="+0+"&alias="+alias).get();
            Elements eps = document2.select("#episode_related > li");
            for (Element link : eps ) {
                Episode ep = new Episode();
                String eid =String.valueOf(link.select("a").attr("href").split("/")[1]);
                String epnum =String.valueOf(link.select("div.name").text().replace("EP ", ""));
                ep.setEpisodeId(eid);
                ep.setEpisodeNum(epnum);
                episodes.add(ep);
            }
            anime.setGenres(genres);
            anime.setStatus(status);
            anime.setReleasedDate(releasedDate);
            anime.setSynopsis(desc);
            anime.setAnimeTitle(animeTitle);
            anime.setAnimeImg(imgUrl);
            anime.setEpisodeNum(episodeNum);
            anime.setEpisodesList(episodes);
            anime.setTotalEpisodes(ep_end);
            anime.setType(type);
            anime.setOtherNames(otherNames);

        return ResponseEntity.status(HttpStatus.OK).body(anime);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Error: " + e.getMessage());
    }
    }
}
