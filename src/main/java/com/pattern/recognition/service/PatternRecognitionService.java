package com.pattern.recognition.service;

import com.pattern.recognition.data.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PatternRecognitionService {

    void addPoint(Point point);

    List<Point> getPoints();

    List<List<Point>> getLinesWithLengthEqualsOrGreaterThanN(Integer n);

    void delete();
}
