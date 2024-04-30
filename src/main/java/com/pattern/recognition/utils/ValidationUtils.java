package com.pattern.recognition.utils;

import com.pattern.recognition.data.Point;
import com.pattern.recognition.exception.InvalidParametersException;

import java.util.List;

public class ValidationUtils {

    public static void validatePoint(Point point, List<Point> plane){

        if(point == null || point.getX() == null || point.getY() == null)
            throw new InvalidParametersException("Point coordinates cannot be null");

        if(point.getX() < 0 || point.getY() < 0)
            throw new InvalidParametersException("Point coordinates cannot be negative");

        if(plane.stream().anyMatch(point::comparePoints))
            throw new InvalidParametersException("The point is already present into the plan");
    }

    public static void validateLineLength(Integer n){
        if(n == null || n <= 1)
            throw new InvalidParametersException("Length's value provided is not valid");
    }
}
