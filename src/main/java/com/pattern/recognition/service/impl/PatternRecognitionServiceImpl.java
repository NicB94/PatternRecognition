package com.pattern.recognition.service.impl;

import com.pattern.recognition.data.Point;
import com.pattern.recognition.service.PatternRecognitionService;
import com.pattern.recognition.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class PatternRecognitionServiceImpl implements PatternRecognitionService {

    private static List<Point> plane;

    static{
        plane = new ArrayList<>();
    }

    /**
     * If the coordinates provided are valid, then adds a point to the plan
     * @param point (x, y) point's coordinates
     */
    public void addPoint(Point point){

        ValidationUtils.validatePoint(point, plane);
        plane.add(point);
    }

    /**
     * Returns the list of all the points present into the plane
     * @return the plane's coordinates
     */
    public List<Point> getPoints(){

        return plane;
    }

    /**
     * Search all the lines contained into the plan with length equals or greater then n
     * @param n the minimum line's length
     * @return all the coordinates representing lines with length >= n
     */
    public List<List<Point>> getLinesWithLengthEqualsOrGreaterThanN(Integer n){

        ValidationUtils.validateLineLength(n);
        List<List<Point>> pointList = new ArrayList<>();
        Collections.sort(plane);

        for(int i = n; i <= plane.size(); i++){
            List<List<Point>> linesEqualsToN = getLinesWithLengthEqualsToN(i);
            pointList.addAll(linesEqualsToN);
        }

        return pointList;
    }

    /**
     * Search all the lines contained into the plan with length equals to n
     * @param n the minimum line's length
     * @return all the coordinates representing lines with length == n
     */
    private List<List<Point>> getLinesWithLengthEqualsToN(Integer n){

        if(n == 2)
            return getLinesBetweenTwoPoints();
        else
            return getLinesBetweenMoreThanTwoPoints(n);
    }

    /**
     * Search all the lines contained into the plan with length greater than 2
     * @return all the coordinates representing lines with length > 2
     */
    private List<List<Point>> getLinesBetweenMoreThanTwoPoints(Integer n){
        List<List<Point>> linesWithLengthGreaterThanN = new ArrayList<>();

        List<Point> verticalPoints = new ArrayList<>();
        List<Point> horizontalPoints = new ArrayList<>();
        List<Point> diagonalPointsAhead = new ArrayList<>();
        List<Point> diagonalPointsBack = new ArrayList<>();

        for(int i = 0; i < plane.size(); i++){
            Point currentPoint = plane.get(i);

            horizontalPoints.add(currentPoint);
            verticalPoints.add(currentPoint);
            diagonalPointsAhead.add(currentPoint);
            diagonalPointsBack.add(currentPoint);

            for(int j = i +1; j < plane.size(); j++){
                Point nextPoint = plane.get(j);

                addLineIfEnoughPointsAreFound(linesWithLengthGreaterThanN, horizontalPoints, nextPoint, n, currentPoint::collinearOnX);
                addLineIfEnoughPointsAreFound(linesWithLengthGreaterThanN, verticalPoints, nextPoint, n, currentPoint::collinearOnY);
                addLineIfEnoughPointsAreFoundOnDiagonal(linesWithLengthGreaterThanN, diagonalPointsAhead, nextPoint, n, currentPoint::collinearOnDiagonal);
                addLineIfEnoughPointsAreFoundOnDiagonalBack(linesWithLengthGreaterThanN, diagonalPointsBack, nextPoint, n, currentPoint::collinearOnDiagonalBack);
            }
            verticalPoints.clear();
            horizontalPoints.clear();
            diagonalPointsAhead.clear();
            diagonalPointsBack.clear();
        }
        return linesWithLengthGreaterThanN;
    }

    /**
     * Search all the lines contained into the plan with length equals to 2
     * @return all the coordinates representing lines with length == 2
     */
    private List<List<Point>> getLinesBetweenTwoPoints(){

        List<List<Point>> linesWithTwoPoints = new ArrayList<>();
        for(int i = 0; i < plane.size(); i++){
            for(int j = i+1; j < plane.size(); j++){
                List<Point> currentLine = new ArrayList<>();
                currentLine.add(plane.get(i));
                currentLine.add(plane.get(j));
                linesWithTwoPoints.add(currentLine);
            }
        }
        return linesWithTwoPoints;
    }

    /**
     * Add next point to pointsRelatedToDirection list if the result of
     * the function applied is true
     * @param linesWithLengthGreaterThanN set of point representing the lines equals or greater than n
     * @param pointsRelatedToDirection collinear points related to current point
     * @param nextPoint next point of the list to be compared with the current one
     * @param lineLength line's length desired
     * @param func function used to check if next point is collinear with the current one
     */
    private void addLineIfEnoughPointsAreFound(List<List<Point>> linesWithLengthGreaterThanN, List<Point> pointsRelatedToDirection, Point nextPoint,Integer  lineLength, Function<Point, Boolean> func){
        if(func.apply(nextPoint)){
            pointsRelatedToDirection.add(nextPoint);
            if(pointsRelatedToDirection.size() == lineLength){
                linesWithLengthGreaterThanN.add(List.copyOf(pointsRelatedToDirection));
                pointsRelatedToDirection.clear();
            }
        }
    }

    private void addLineIfEnoughPointsAreFoundOnDiagonal(List<List<Point>> linesWithLengthGreaterThanN, List<Point> pointsRelatedToDirection, Point nextPoint,Integer  lineLength, Function<Point, Boolean> func){
        if(func.apply(nextPoint)){
            if(checkIfItsCollinearWithAllThePoints(pointsRelatedToDirection, nextPoint)) {
                pointsRelatedToDirection.add(nextPoint);
                if(pointsRelatedToDirection.size() == lineLength){
                    linesWithLengthGreaterThanN.add(List.copyOf(pointsRelatedToDirection));
                    pointsRelatedToDirection.clear();
                }
            }
        }
    }

    private void addLineIfEnoughPointsAreFoundOnDiagonalBack(List<List<Point>> linesWithLengthGreaterThanN, List<Point> pointsRelatedToDirection, Point nextPoint,Integer  lineLength, Function<Point, Boolean> func){
        if(func.apply(nextPoint)){
            if(checkIfItsCollinearWithAllThePointsBack(pointsRelatedToDirection, nextPoint)) {
                pointsRelatedToDirection.add(nextPoint);
                if(pointsRelatedToDirection.size() == lineLength){
                    linesWithLengthGreaterThanN.add(List.copyOf(pointsRelatedToDirection));
                    pointsRelatedToDirection.clear();
                }
            }
        }
    }

    /**
     * Check if current point is collinear on the diagonal with all the
     * points contained into the list
     * @param pointsRelatedToDirection
     * @param currentPoint
     * @return true if it's  collinear
     *         false otherwise
     */
    private Boolean checkIfItsCollinearWithAllThePoints(List<Point> pointsRelatedToDirection, Point currentPoint){
        for(Point p : pointsRelatedToDirection){
            if(!currentPoint.collinearOnDiagonal(p))
                return false;
        }
        return true;
    }

    /**
     * Check if current point is collinear on the diagonal with all the
     * points contained into the list
     * @param pointsRelatedToDirection
     * @param currentPoint
     * @return true if it's  collinear
     *         false otherwise
     */
    private Boolean checkIfItsCollinearWithAllThePointsBack(List<Point> pointsRelatedToDirection, Point currentPoint){
        for(Point p : pointsRelatedToDirection){
            if(!currentPoint.collinearOnDiagonalBack(p))
                return false;
        }
        return true;
    }


    /**
     * Deletes all points contained into the plan
     */
    public void delete(){
        plane.clear();
    }
}
