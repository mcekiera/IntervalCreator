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
    private JLabel count;
    private JLabel countSet;
    private JLabel message;
    private Countdown countdown;
    private Countdown setCountdown;

    public CountdownFrame(Countdown countdown, Countdown timeRest){
        this.countdown = countdown;
        setCountdown = timeRest;
        build();
    }
    public void build(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        count = new JLabel();
        count.setFont( new Font( "Arial Black", Font.BOLD, 300 ) );
        count.setHorizontalAlignment( SwingConstants.CENTER );
        count.setBackground( Color.RED );
        countSet = new JLabel();
        countSet.setFont( new Font( "Arial Black", Font.BOLD, 50 ) );
        countSet.setHorizontalAlignment(SwingConstants.CENTER);
        message = new JLabel();
        message.setFont( new Font( "Arial Black", Font.BOLD, 100 ) );
        JPanel panel = new JPanel( new GridLayout( 2, 1 ) );
        JPanel innerPanel = new JPanel(new GridLayout(2,1));
        panel.add( count );
        innerPanel.add(message);
        if(setCountdown!=null) innerPanel.add( countSet );
        panel.add( innerPanel );
        panel.setBackground( Color.WHITE );
        message.setHorizontalAlignment( SwingConstants.CENTER );
        return panel;
    }
    private void refreshDisplay(){
        count.setText( countdown.toReadableString());
        countSet.setText(setCountdown.toReadableString());
        message.setText( countdown.getMessage() );
        if(setCountdown!=null) setCountdown.toReadableString();
    }
    private void startTimer(){
        refreshDisplay();
        count.setForeground( countdown.isCloseToEnd() ? Color.RED : Color.BLACK );
        timer = new Timer( 1000, new TimerListener() );
        timer.setRepeats( true );
        timer.start();
    }
    public boolean isBusy(){
        return timer.isRunning();
    }
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            countdown.decrement();
            setCountdown.decrement();
            count.setForeground( countdown.isCloseToEnd() ? Color.RED : Color.BLACK );
            if( countdown.isFinished() ){
                timer.stop();
            }
            if( setCountdown.isFinished()){
                countSet.setText("00:00");
                count.setText("00:00");
                message.setText( setCountdown.getMessage() );
            }
            else
            {
                refreshDisplay();
            }
        }
    }


}
