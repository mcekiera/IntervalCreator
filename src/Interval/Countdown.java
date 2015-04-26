package Interval;

/**
 *  Countdown class is responsible for a countdown a single time value, introduced by user. It contains
 *  information about minutes and seconds, which would be counted, and a message,
 *  which would be displayed during countdown.
 */
public class Countdown{
    private String message;
    private int minutes;
    private int seconds;

    /**
     * Both constructors parameters are derived from Set.schedule ArrayList, which contains user input.
     * @param timeToCount String in format "mm:ss", base time for countdown. Proper format is determined by
     *                    restrictions for user input.
     * @param displayedMessage String displaying during countdown.
     */
    public Countdown(String timeToCount, String displayedMessage){
        message = displayedMessage;
        splitStringIntoInt(timeToCount);
    }

    /**
     * Is responsible for extracting a time value for class fields: minutes and seconds
     * from a given String. Proper String format is determined by restrictions in user input.
     * @param str String in format "mm:ss".
     */
    private void splitStringIntoInt(String str){
        String[] minAndSec = str.split(":");
        minutes = Integer.parseInt(minAndSec[0]);
        seconds = Integer.parseInt(minAndSec[1]);
    }

    /**
     * Used to export formatted, ready to display, time data form class.
     * @return String in format "##:##", contains minutes and seconds.
     */
    public String toReadableString(){
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * It is a message field getter method.
     * @return message to display.
     */
    public String getMessage(){
        return message;
    }

    /**
     * Serves to provide information about incoming ending of the countdown.
     * @return true if 5 seconds left to end of countdown.
     */
    public boolean isCloseToEnd(){
        return minutes < 1 && seconds <= 6;
    }

    /**
     * Provide information about an ending of countdown.
     * @return true if time value is 00:00.
     */
    public boolean isFinished(){
        return minutes < 1 && seconds <1;
    }

    /**
     * Decrement values of minutes and seconds fields, as a integral part of countdown.
     * @return this, only for test purposes.
     */
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