package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;

import javax.sound.sampled.Port;

import lejos.hardware.Sound;

import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class OdometryCorrection implements Runnable {
  private static final long CORRECTION_PERIOD = 10;
  static SensorModes lightsensor = colorSensor;
  static SampleProvider myColorSample = lightsensor.getMode("Red");
  static float[] sampleColor = new float[lightsensor.sampleSize()];
  private double tmpX;
  private double tmpY;
  private double deltaT;
  private int countY=0;
  private int countX=0;
  private double prevdata = 1 ;
  private static final double colorlimit = 0.6;
 
  
  public void run() {
    long correctionStart, correctionEnd;
    float reflectIntensity;
    
    /**
     * In the following section we will describe how does the correction work: 
     * Actually, as soon as the robots crosses a black line, the robot beeps. Also, it enters a block of each statement in which every statement is for a specific side of the square in which the robot is running. 
     * 
     * To finally track how many tiles the robot actually passed, we created a counter x and a counter y that keeps track of the number of tiles already visited.
     * The correction will be made on the values of the counter x and the counter y, by multiplying the size of a tile (30.4cm) by the number of the counter x or the counter y. 
     */
    
        
    while (true) {
      correctionStart = System.currentTimeMillis();
      double[] currentPosition = odometer.getXYT();
      tmpX = currentPosition[0];
      tmpY = currentPosition[1];
      deltaT = currentPosition[2];
      myColorSample.fetchSample(sampleColor, 0); 
      

      // TODO Trigger correction (When do I have information to correct?)
      
      // TODO Calculate new (accurate) robot position

      // TODO Update odometer with new calculated (and more accurate) values, eg:
      //odometer.setXYT(0.3, 19.23, 5.0);
      
      //measure intensity of reflected red light
      
      reflectIntensity = sampleColor[0];//current sensor data
      double filter = Math.abs(prevdata - reflectIntensity);//filter for noise removal
      prevdata = reflectIntensity;
      
      
      if(reflectIntensity<0.29 &&  filter<colorlimit){ // if it detects a black line.

         
          if(deltaT < 103 || deltaT >= 73){
            
            odometer.setX(countX * TILE_SIZE);
            countX++; 
         }
          else if( deltaT < 13 || deltaT > 348){
             
             odometer.setY(countY * TILE_SIZE);
             countY++;
          }
          
           else if(deltaT < 283 && deltaT >= 253){
            
            odometer.setX(countX * TILE_SIZE);
            countX--;
         }
          
        
          else if(deltaT <193 && deltaT >= 169){ 
             
             odometer.setY(countY * TILE_SIZE);
             countY--;
          }
           //Beeps when the robot crosses a black line.
          Sound.beep(); 
          
        }



      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
  }
  }
}