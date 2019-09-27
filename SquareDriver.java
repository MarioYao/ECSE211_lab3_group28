package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to drive the robot on the demo floor.
 */
public class SquareDriver {
	
  public static double[] preCoor;
  public static double[] currVector;
  private static double distance;

/**
   * Drives the robot in a square of size 3x3 Tiles. It is to be run in parallel
   * with the odometer and odometer correction classes to allow testing their functionality.
   */
  public static void turnTo(double theta) {
	  Math.atan(x/y)
  }
  public static void travalTo(double x, double y) {
      if(preCoor[0]==0.0 && preCoor[1]==0.0) {
    	  preCoor=defaultCoordinate;
    	  currVector[0]=x-preCoor[0];
          currVector[1]=y-preCoor[1];
          distance=Math.sqrt(currVector[0]*currVector[0]+currVector[1]*currVector[1]);
          
        // spawn a new Thread to avoid this method blocking
        (new Thread() {
          public void run() {
            // reset the motors
            leftMotor.stop();
            rightMotor.stop();
            leftMotor.setAcceleration(ACCELERATION);
            rightMotor.setAcceleration(ACCELERATION);
            // Sleep for 2 seconds
            Main.sleepFor(TIMEOUT_PERIOD);


              // drive forward three tiles
              leftMotor.setSpeed(FORWARD_SPEED);
              rightMotor.setSpeed(FORWARD_SPEED);
              
             leftMotor.rotate(convertDistance(currVector[0] * distance), true);
             rightMotor.rotate(convertDistance(currVector[1] * distance), false);

              // turn 90 degrees clockwise
             leftMotor.setSpeed(ROTATE_SPEED);
             rightMotor.setSpeed(ROTATE_SPEED);

              leftMotor.rotate(convertAngle(90.0), true);
              rightMotor.rotate(-convertAngle(90.0), false);

          }
        }).start();
      }
      else {  currVector[0]=x-preCoor[0];
      currVector[1]=y-preCoor[1];
      distance=Math.sqrt(currVector[0]*currVector[0]+currVector[1]*currVector[1]);
    // spawn a new Thread to avoid this method blocking
    (new Thread() {
      public void run() {
        // reset the motors
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.setAcceleration(ACCELERATION);
        rightMotor.setAcceleration(ACCELERATION);
        // Sleep for 2 seconds
        Main.sleepFor(TIMEOUT_PERIOD);


          // drive forward three tiles
          leftMotor.setSpeed(FORWARD_SPEED);
          rightMotor.setSpeed(FORWARD_SPEED);
          
         leftMotor.rotate(convertDistance(currVector[0] * distance), true);
         rightMotor.rotate(convertDistance(currVector[1] * distance), false);

          // turn 90 degrees clockwise
         leftMotor.setSpeed(ROTATE_SPEED);
         rightMotor.setSpeed(ROTATE_SPEED);

          leftMotor.rotate(convertAngle(90.0), true);
          rightMotor.rotate(-convertAngle(90.0), false);

      }
    }).start();
  }
    preCoor[0]=x;
    preCoor[0]=y;
  }

  /**
   * Converts input distance to the total rotation of each wheel needed to cover that distance.
   * 
   * @param distance
   * @return the wheel rotations necessary to cover the distance
   */
  public static int convertDistance(double distance) {
    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
  }

  /**
   * Converts input angle to the total rotation of each wheel needed to rotate the robot by that
   * angle.
   * 
   * @param angle
   * @return the wheel rotations necessary to rotate the robot by the angle
   */
  public static int convertAngle(double angle) {
    return convertDistance(Math.PI * TRACK * angle / 360.0);
  }
}
