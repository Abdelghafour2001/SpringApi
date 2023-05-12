package com.example.controllers;

import com.example.service.AnimeService;
import com.example.model.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/OngoingSeries"})
public class Controller {
  @Autowired
  AnimeService service;
  @GetMapping
  public List<Anime>list(){
    return service.list();
  }
}
