package Interval;

public class Countdown{
    String message;
    int minutes;
    int seconds;

    public Countdown(String timeToCount, String displayedMessage){
        message = displayedMessage;
        splitStringIntoInt(timeToCount);
    }

    private void splitStringIntoInt(String str){
        String[] minAndSec = str.split(":");
        minutes = Integer.parseInt(minAndSec[0]);
        seconds = Integer.parseInt(minAndSec[1]);
    }

    public String toReadableString(){
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getMessage(){
        return message;
    }

    public boolean isCloseToEnd(){
        return minutes < 1 && seconds <= 5;
    }

    public boolean isFinished(){
        return minutes < 1 && seconds <1;
    }

    public Countdown decrement(){
        if(!(seconds == 0 && minutes == 0)){
            if(seconds > 0){
                seconds--;
            }
            else{
                seconds = 59;
                minutes --;
            }
        }
        return this;
    }
}