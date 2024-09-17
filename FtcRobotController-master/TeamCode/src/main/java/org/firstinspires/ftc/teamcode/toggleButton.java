package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class toggleButton { //simple button toggle object
    boolean wait = false;
    boolean on = false;
    private final boolean result;
    toggleButton(boolean trueJustOnActivation){
        result = trueJustOnActivation;
    }
    public boolean evaluate(boolean parameter){
        if (result) on = false;
        if (parameter && !wait){
            wait = true;
            on = !on;
        }
        else if (!parameter){
            wait = false;
        }
        return on;
    }
}
