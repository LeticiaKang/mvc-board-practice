package com.fastcampus.boardpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//RequestMapping으로 url설정이 가능하다
@RequestMapping("/articles")
@Controller
public class ArticleController {
    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

}
