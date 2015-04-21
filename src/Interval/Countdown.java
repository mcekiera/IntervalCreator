package Interval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Countdown{
    JDialog dialog;
    JPanel panel;
    Timer timer;
    public JLabel label;
    protected JLabel message;
    int[] timeList;
    final Rectangle MAX = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    final Rectangle MIN = new Rectangle(100,100,400,400);
    Color color;


    public Countdown(){
        dialog = new JDialog();
        dialog.setUndecorated(false);
        dialog.setBounds(MAX);
        dialog.setAlwaysOnTop(true);
        label = new JLabel();
        label.setFont(new Font("Arial Black", Font.BOLD, 100));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(Color.RED);
        panel = new JPanel(new GridLayout(2,1));
        panel.add(label);
        message = new JLabel();
        message.setFont(new Font("Arial Black", Font.BOLD, 100));
        panel.add(message);
        message.setHorizontalAlignment(SwingConstants.CENTER);

        panel.setBackground(Color.WHITE);
        dialog.add(panel);
        dialog.setVisible(true);
    }
    public void displayTime(int min, int sec){
        String minute = String.format("%02d",min);
        String second = (String.format("%02d",sec));
        label.setText(minute+":"+second);
    }

    public void startCountdown(String time, String message){
        timeList = convertTimeToInt(time.split(":"));
        this.message.setText(message);
        label.setText(time);
        timer = new Timer(1000,new CountdownListener());
        timer.setRepeats(true);
        timer.start();

    }
    public int[] convertTimeToInt(String[] time){
        int[] converted = new int[time.length];
        for(int i = 0; i<time.length; i++){
            converted[i] = Integer.valueOf(time[i]);
        }
        return converted;
    }
    public boolean isRunning(){
        return timer.isRunning();
    }

    private class CountdownListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(timeList[0]==0 && timeList[1]==1){
                label.setText("");
                message.setText("END");
                timer.stop();
            }else if(timeList[1]>0){
                timeList[1] -= 1;
                displayTime(timeList[0],timeList[1]);
            }else if(timeList[1]==0){
                timeList[1]=59;
                timeList[0] -= 1;
                displayTime(timeList[0],timeList[1]);
            }
        }
    }
}