package com.example;

import com.example.model.Anime;
import com.example.service.AnimeServiceImpl;
import com.example.service.MovieServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {

        //getRecentRelease("DUB","1");
        //getOngoingAnime("1");
        //search("mashle","1");
        getNewSeason("1");
    }
    public static void search(String keyword, String page)throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/search.html?keyword="+keyword+"&page="+page).get();
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
    public static void getRecentRelease(String type,String page) throws IOException {
        Document document = Jsoup.connect("https://ajax.gogo-load.com/ajax/page-recent-release.html?type="+type+"&page="+page).get();
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.last_episodes.loaddub > ul > li");
        for (Element link : imports ) {
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
            System.out.println(episodeId);
        }
    }
    public static void getNewSeason(String page) throws IOException{
        Document document = Jsoup.connect("https://gogoanime.gr/new-season.html?page="+page).get();
        //a with href
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
    public static void getOngoingAnime(String page) throws IOException {

        Document document = Jsoup.connect("https://gogoanime.gr/ongoing-anime.html?page="+page).get();
        //a with href
        Anime anime=new Anime();
        ArrayList<Anime> listAnime= new ArrayList<Anime>();
        Elements imports = document.select("div.main_body div.last_episodes ul.items li");
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
}
