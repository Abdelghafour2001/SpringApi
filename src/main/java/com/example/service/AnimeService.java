package com.example.service;

import com.example.model.Anime;

import java.util.List;

public interface AnimeService {
        List<Anime> list();
        Anime listId(int id);
        Anime add(Anime p);
        Anime edit(Anime p);
        Anime delete(int id);

}
