package com.poc.movieticketbookingplatform.controller;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeatureController {

    @Autowired
    private FF4j ff4j;

    @GetMapping("/feature-status")
    public String getFeatureStatus() {
        Feature feature = ff4j.getFeature("newFeature");
        return "New Feature is " + (feature.isEnable()? "enabled" : "disabled");
    }
}