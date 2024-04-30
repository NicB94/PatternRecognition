package com.pattern.recognition.data;

import java.util.Comparator;

public class Point implements Comparable<Point>{

    Integer x;
    Integer y;

    public Point(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * @param point of the plan
     * @return true if the two points have the same coordinates
     *         false otherwise
     */
    public boolean comparePoints(Point point){

        return point.getX().equals(x) && point.getY().equals(y);
    }

    /**
     * Verify if the argument is collinear on X with the current point
     * @param point next point
     * @return true if point is on the diagonal of the current point
     *         false otherwise
     */
    public boolean collinearOnX(Point point){
        return this.getY().equals(point.getY());
    }

    /**
     * Verify if the argument is collinear on Y with the current point
     * @param point next point
     * @return true if point is on the diagonal of the current point
     *         false otherwise
     */
    public boolean collinearOnY(Point point){
        return this.getX().equals(point.getX());
    }

    /**
     * Verify if the argument is collinear on the diagonal with the current point
     * @param point next point
     * @return true if point is on the diagonal of the current point
     *         false otherwise
     */
    public boolean collinearOnDiagonal(Point point){
        int differenceBetweenY = point.getY() - this.getY();
        int differenceBetweenX = point.getX() - this.getX();

        if(differenceBetweenX == 0|| differenceBetweenY == 0)
            return false;

        return differenceBetweenY/differenceBetweenX == 1;
    }

    public boolean collinearOnDiagonalBack(Point point){
        int differenceBetweenY = point.getY() - this.getY();
        int differenceBetweenX = point.getX() - this.getX();

        if(differenceBetweenX == 0|| differenceBetweenY == 0)
            return false;

        return differenceBetweenY/differenceBetweenX == - 1;
    }

    /**
     * Used to sort the plan by slope
     * @return the Comparator used to sort the elements by slope
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    private class BySlope implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            if (Double.compare(slopeTo(a), slopeTo(b)) == 0) {
                return 0;
            }
            else if (Double.compare(slopeTo(a), slopeTo(b)) > 0) {
                return 1;
            }
            else
                return -1;
        }
    }

    /**
     * Calculate the slop between the current point and the next note
     * @param nextPoint of the plan
     */
    public double slopeTo(Point nextPoint) {
        if (y == nextPoint.getY() && x != nextPoint.getX()) {
            return 0;
        }
        else if (y != nextPoint.getY() && x == nextPoint.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        else if (y == nextPoint.getY() && x == nextPoint.getX()) {
            return Double.NEGATIVE_INFINITY;
        }
        else {
            return (double) (nextPoint.getY() - y) / (nextPoint.getX() - x);
        }
    }

    /**
     * @param  nextPoint the other point
     * @return - 0 if this point is equal to nextPoint
     *         - a negative integer if this point is less than nextPoint
     *         - a positive integer if this point is greater than nextPoint
     */
    public int compareTo(Point nextPoint) {
        if (y < nextPoint.getY() || (y == nextPoint.getY() && x < nextPoint.getX())) {
            return -1;
        }
        else if (y == nextPoint.getY() && x == nextPoint.getY()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
