package com.example.pipeline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.pipeline.entity.Feed;
import com.example.pipeline.service.FeedService;

@Controller
@RequestMapping("/feed")
public class FeedController {
    
    @Autowired
    private FeedService feedService;

    @GetMapping
    public String list(Model model) {
        List<Feed> feeds = feedService.getAllFeeds();
        model.addAttribute("feeds", feeds);
        return "feed_list";
    }
}
