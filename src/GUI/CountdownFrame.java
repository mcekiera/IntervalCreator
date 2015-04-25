package GUI;

import Interval.Countdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CountdownFrame extends JFrame{
    private Timer timer;
    private JLabel intervalTime;
    private JLabel setTime;
    private JLabel message;
    private Countdown ofInterval;
    private Countdown ofSet;

    public CountdownFrame(Countdown ofInterval, Countdown ofSet){
        this.ofInterval = ofInterval;
        this.ofSet = ofSet;
        display();
    }
    public void display(){
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                dispose();
                setUndecorated(false);
                revalidate();
                setVisible(true);
            }
        });
        add(initText());
        startTimer();
        setVisible(true);
    }
    private JPanel initText(){
        JPanel panel = new JPanel( new GridLayout( 2, 1 ) );
        JPanel innerPanel = new JPanel(new GridLayout(2,1));
        innerPanel.setBackground(Color.WHITE);
        panel.setBackground( Color.WHITE );

        intervalTime = new JLabel();
        intervalTime.setFont(new Font("Arial Black", Font.BOLD, 300));
        intervalTime.setHorizontalAlignment(SwingConstants.CENTER);

        setTime = new JLabel();
        setTime.setFont(new Font("Arial Black", Font.BOLD, 50));
        setTime.setHorizontalAlignment(SwingConstants.CENTER);

        message = new JLabel();
        message.setFont( new Font( "Arial Black", Font.BOLD, 100 ) );
        message.setHorizontalAlignment( SwingConstants.CENTER );

        panel.add(intervalTime);
        innerPanel.add(message);
        if(ofSet !=null) innerPanel.add(setTime);
        panel.add( innerPanel );

        return panel;
    }
    private void refreshDisplay(){
        intervalTime.setText(ofInterval.toReadableString());
        setTime.setText(ofSet.toReadableString());
        message.setText( ofInterval.getMessage() );
        if(ofSet !=null) ofSet.toReadableString();
    }
    private void startTimer(){
        refreshDisplay();
        intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);
        timer = new Timer( 1000, new TimerListener() );
        timer.setRepeats( true );
        timer.start();
    }
    public boolean isBusy(){
        return timer.isRunning();
    }
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            ofInterval.decrement();
            ofSet.decrement();
            intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);
            if( ofInterval.isFinished() ){
                timer.stop();
            }
            if( ofSet.isFinished()){
                setTime.setText("00:00");
                intervalTime.setText("00:00");
                message.setText( ofSet.getMessage() );
            }else{
                refreshDisplay();
            }
        }
    }
}
//todo pierszwy interwał nie wchodzi, ramka szaleje
