package com.example;

import com.example.model.Anime;
import com.example.model.Episode;
import com.example.model.WatchAnime;
import com.example.service.AnimeServiceImpl;
import com.example.service.MovieServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {

        //getRecentRelease("DUB","1");
        //getOngoingAnime("1");
        //search("mashle","1");
        //getNewSeason("1");
        //getAnimeDetails("mou-ippon");
        //getPopular("1");
        //getSeason("1","summer-2022-anime");
        //getGenres("1","police");
        //getOngoingSeries
        //getRecentlyAdded("1");
        //getEpisode("mou-ippon-episode-4");
        //getCompleted("1");
        //getAZanime("D","1");
    }
   /* public static void getSeason(String page,String season)throws IOException{
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
            System.out.println(imgUrl);
        }
    }
    public static void getGenres(String page,String genre)throws IOException{
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
            System.out.println(animeUrl);
        }
    }
    public static void getCompleted(String page)throws IOException{
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
    }
    public static void getAZanime(String aph,String page)throws IOException{
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
    }

    public static void getAnimeList(String aph,String page)throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/anime-list.html?page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.anime_list_body > ul.listing > li   ");
        for (Element link : imports ) {
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
    public static void getPopular(String page)throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/popular.html?page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.last_episodes > ul > li");
        for (Element link : imports ) {
            String animeId = String.valueOf(link.select("p.name > a").attr("href").split("/")[2]);
            String animeTitle = String.valueOf(link.select("p.name > a").attr("title"));
            String imgUrl = String.valueOf(link.select("div > a > img").attr("src"));
            String status = String.valueOf(link.select("p.released").text().trim());
            anime.setAnimeId(animeId);
            anime.setAnimeTitle(animeTitle);
            anime.setAnimeImg(imgUrl);
            anime.setStatus(status);
            listAnime.add(anime);
            System.out.println(imgUrl);
        }
    }
        public static void getTopAiring(String page)throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/popular.html?page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.last_episodes > ul > li");
        for (Element link : imports ) {
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
    }
     public static void getRecentlyAdded(String page) throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/?page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.added_series_body.final ul.listing li");
        for (Element link : imports ) {
            String animeId = String.valueOf(link.select("a").attr("href"));
            String animeTitle = String.valueOf(link.select("a").text());
            anime.setAnimeId(animeId);
            anime.setAnimeTitle(animeTitle);
            listAnime.add(anime);
            System.out.println(animeTitle);
        }
    }
   public static void getOngoingSeries(String page) throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/?page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("nav.menu_series.cron ul li");
        for (Element link : imports ) {
            String animeId = String.valueOf(link.select("a").attr("href"));
            String animeTitle = String.valueOf(link.select("a").text());
            anime.setAnimeId(animeId);
            anime.setAnimeTitle(animeTitle);
            listAnime.add(anime);
            System.out.println(animeTitle);
        }
    }*/

}
