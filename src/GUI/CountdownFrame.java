package GUI;

import Interval.Countdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CountdownFrame extends WindowAdapter{
    private JFrame frame;
    private Timer timer;
    private JLabel intervalTime;
    private JLabel setTime;
    private JLabel message;
    private Countdown ofInterval;
    private Countdown ofSet;

    public CountdownFrame(Countdown ofInterval, Countdown ofSet){
        frame = new JFrame();
        this.ofInterval = ofInterval;
        this.ofSet = ofSet;
        display();
    }
    public void display(){
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                frame.dispose();
                frame.setUndecorated(false);
                frame.revalidate();
                frame.setVisible(true);
            }
        });
        frame.add(initText());
        startTimer();
        frame.setVisible(true);
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

    @Override
    public void windowClosing(WindowEvent e){
        timer.stop();
        frame.dispose();
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            ofInterval.decrement();
            ofSet.decrement();
            intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);
            if( ofInterval.isFinished() ){
                timer.stop();
                frame.setVisible(false);
                frame.dispose();
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

