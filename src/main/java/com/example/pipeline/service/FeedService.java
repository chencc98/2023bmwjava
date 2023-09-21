package com.example.pipeline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pipeline.dao.FeedDao;
import com.example.pipeline.entity.Feed;

@Service
public class FeedService {
    @Autowired
    private FeedDao feedDao;
    
    public List<Feed> getAllFeeds() {
        return feedDao.getAllFeeds();
    }
}
