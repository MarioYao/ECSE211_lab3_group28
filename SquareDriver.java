package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to drive the robot on the demo floor.
 */
public class SquareDriver {
	
  public static double[] preCoor;
  public static double[] currVector;
  public static double[] preVector;
  private static double distance;
  private static double Theta_of_PreVector;
  private static double Theta_of_CurrVector;

/**
   * Drives the robot in a square of size 3x3 Tiles. It is to be run in parallel
   * with the odometer and odometer correction classes to allow testing their functionality.
   */


  public static void turnTo(double Theta_of_PreVector,double Theta_of_CurrVector) {

	//Celine: please write here, you may need other input to calculate the angle. Here I provide 
	//the angle of previous and current vector relative to x-axis in radians.
	  
  }
  
  
  
  public static void travalTo(double x, double y) {
      if(preCoor==null) {
    	  preCoor=defaultCoordinate;
    	  currVector[0]=x-preCoor[0];
          currVector[1]=y-preCoor[1];
          distance=TILE_SIZE*Math.sqrt((currVector[0]*currVector[0])+(currVector[1]*currVector[1]));
          
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
            

              // drive forward by a given distance
            leftMotor.setSpeed(FORWARD_SPEED);
            rightMotor.setSpeed(FORWARD_SPEED);
            leftMotor.rotate(convertDistance(distance), true);
            rightMotor.rotate(convertDistance(distance), false);

            preVector[0]=1;
            preVector[1]=1;

          }
        }).start();
      }
      else {  currVector[0]=x-preCoor[0];
      currVector[1]=y-preCoor[1];
      distance=Math.sqrt(currVector[0]*currVector[0]+currVector[1]*currVector[1]);
      
      Theta_of_PreVector = Math.atan(preVector[1]/preVector[0]);
      Theta_of_CurrVector = Math.atan(currVector[1]/currVector[0]);
      

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
          
       // turn an appropriate angle
          leftMotor.setSpeed(ROTATE_SPEED);
          rightMotor.setSpeed(ROTATE_SPEED);
          leftMotor.rotate(convertAngle(turnTo(a certain angle)), true);
          rightMotor.rotate(convertAngle(turnTo(a certain angle)), false);

            // drive forward by a given distance
          leftMotor.setSpeed(FORWARD_SPEED);
          rightMotor.setSpeed(FORWARD_SPEED);
          leftMotor.rotate(convertDistance(distance), true);
          rightMotor.rotate(convertDistance(distance), false);

      }
    }).start();
  }
    preCoor[0]=x;
    preCoor[1]=y;
    if(preVector!=null) {
        preVector[0]=x-preCoor[0];
        preVector[1]=y-preCoor[1];
    }
    else {
    	preVector= {0,0};
    }
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
