/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon; you
     * should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180.0 * (sides - 2) / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular
     * polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see
     * java.lang.Math). HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int) Math.round(-360 / (angle - 180));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
     * draw.
     * 
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location,
     * calculate the heading towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in
     * the direction of the target point (targetX,targetY), given that the turtle is
     * already at the point (currentX,currentY) and is facing at angle
     * currentHeading. The angle must be expressed in degrees, where 0 <= angle <
     * 360.
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY, int targetX,
            int targetY) {
        int diffX = targetX - currentX;
        int diffY = targetY - currentY;
        double finalDegree = Math.toDegrees(Math.atan2(diffX, diffY));
        if (finalDegree < 0) {
            finalDegree += 360;
        }
        if (currentHeading <= finalDegree) {
            return finalDegree - currentHeading;
        } else {
            return 360 - (currentHeading - finalDegree);
        }
        // throw new RuntimeException("implement me!");
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get
     * from each point to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0
     * degrees). For each subsequent point, assumes that the turtle is still facing
     * in the direction it was facing when it moved to the previous point. You
     * should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of
     *         points) == 0, otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> headDegree = new ArrayList<Double>();
        if (xCoords.size() != yCoords.size()) {
            throw new IllegalArgumentException();
        }
        double targetHeading = 0.0;
        double diffHeading = 0.0;
        int currentX, currentY, targetX, targetY;
        for (int i = 0; i < xCoords.size() - 1; i++) {
            currentX = xCoords.get(i);
            currentY = yCoords.get(i);
            targetX = xCoords.get(i + 1);
            targetY = yCoords.get(i + 1);
            diffHeading = calculateHeadingToPoint(targetHeading, currentX, currentY, targetX, targetY);
            targetHeading = (targetHeading + diffHeading) % 360;
            headDegree.add(diffHeading);
        }
        return headDegree;
        // throw new RuntimeException("implement me!");
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a
     * turtle. For this function, draw something interesting; the complexity can be
     * as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        // TODO
        throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        // System.out.println(Math.toDegrees(Math.atan2(0,-1)));
        // System.out.println(calculateHeadingToPoint(675, 0, 1, -1, 1));
        drawSquare(turtle, 40);
        // drawRegularPolygon(turtle, 7, 40);
        // calculateHeadingToPoint(30, 0, 1, 0, 0);
        // draw the window
        turtle.draw();

        /*
         * List<Integer> xCoords = new ArrayList<Integer>(); xCoords.add(0);
         * xCoords.add(0); xCoords.add(1); xCoords.add(1); List<Integer> yCoords = new
         * ArrayList<Integer>(); yCoords.add(1); yCoords.add(0); yCoords.add(0);
         * yCoords.add(1); List<Double> headDegree = calculateHeadings(xCoords,
         * yCoords);
         * 
         * System.out.println(headDegree);
         */
        // System.out.println(0.0 <= 0);
        // System.out.println(calculateHeadingToPoint(270, 1, 0, 1, 1));
        // System.out.println(calculateHeadingToPoint(270, 1, 0, 1, 1));
    }

}
