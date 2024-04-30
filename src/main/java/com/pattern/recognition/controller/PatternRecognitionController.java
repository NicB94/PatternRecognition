package com.pattern.recognition.controller;

import com.pattern.recognition.data.Point;
import com.pattern.recognition.service.PatternRecognitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class PatternRecognitionController {

    private final PatternRecognitionService patternRecognitionService;
    private PatternRecognitionController(PatternRecognitionService patternRecognitionService){
        this.patternRecognitionService = patternRecognitionService;
    }

    @PostMapping("/point")
    @ResponseStatus(HttpStatus.CREATED)
    private void addPoint(@RequestBody Point point){

        patternRecognitionService.addPoint(point);
    }

    @GetMapping("/space")
    private List<Point> getPoints(){

        return patternRecognitionService.getPoints();
    }

    @GetMapping("/lines/{n}")
    private List<List<Point>> getLinesWithLengthEqualsOrGreaterThanN(@PathVariable Integer n){

        return patternRecognitionService.getLinesWithLengthEqualsOrGreaterThanN(n);
    }

    @DeleteMapping("/space")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(){

        patternRecognitionService.delete();
    }
}
