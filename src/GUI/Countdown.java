package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Countdown{
    JDialog dialog;
    Timer timer;
    JLabel label;
    int[] timeList;
    String message;
    final Rectangle MAX = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    final Rectangle MIN = new Rectangle(200,200,200,200);


    public Countdown(String time, String message){
        this.message = message;
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setBounds(MAX);
        dialog.setVisible(true);
        dialog.setBackground(Color.WHITE);
        dialog.setAlwaysOnTop(true);

        label = new JLabel();
        label.setFont(new Font("Arial Black", Font.BOLD, 100));
        label.setLocation((dialog.getHeight()/2),(dialog.getWidth()/2));
        dialog.add(label);
        label.addMouseListener(new DialogMouseListener());
        label.setText(time);
        timeList = convertArray(time.split(":"));
        startCountdown();

    }
    public void displayTime(int min, int sec){
        String minute = String.format("%02d",min);
        String second = (String.format("%02d",sec));
        label.setText(minute+":"+second+"\n"+ message);
    }

    public void startCountdown(){
        timer = new Timer(1000,new CountdownListener());
        timer.setRepeats(true);
        timer.start();

    }
    public int[] convertArray(String[] time){
        int[] converted = new int[time.length];
        for(int i = 0; i<time.length; i++){
            converted[i] = Integer.valueOf(time[i]);
        }
        return converted;
    }

    private class CountdownListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(timeList[0]==0 && timeList[1]==1){
                timer.stop();

                dialog.setVisible(false);
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

    private class DialogMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
