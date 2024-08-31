package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import java.lang.Math;
@Disabled
public class Vector{ //for handling values associated with vectors.
     static boolean POLAR = true, CARTESIAN = false;
     static int FRONT = 0, LEFT = 1, RIGHT = 2;
     double angle = 0, magnitude = 0, i = 0, j = 0;
    public void reset(boolean resetPolar){
        //converting from one type to the other.
        // True resets angle or polar, false resets i+j or cartesian
        if (resetPolar){
            magnitude = Math.sqrt( (i*i+j*j));

            if (i<0){//calculate angle based on arctan however if condition used to specify special conditions like i ==0;
                angle = Math.atan(j/i)+Math.PI;
            }else if(i>0){
                angle = Math.atan(j/i);
            }else{
                if(j >= 0){
                    angle = Math.PI/2;
                }else{
                    angle = Math.PI*3/2;
                }
            }
        }else{
            i = magnitude*Math.cos(angle);
            j = magnitude*Math.sin(angle);
        }


    }
    public double calculateWheel(int wheelNum){
        double calulationAngle = ((wheelNum * Math.PI* 2/3)-angle);
        return Math.cos(calulationAngle);
    }
}