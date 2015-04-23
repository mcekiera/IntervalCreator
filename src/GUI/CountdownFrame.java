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
    private JLabel message;
    private Countdown countdown;

    public CountdownFrame(Countdown countdown){
        this.countdown = countdown;
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
        count.setFont( new Font( "Arial Black", Font.BOLD, 200 ) );
        count.setHorizontalAlignment( SwingConstants.CENTER );
        count.setBackground( Color.RED );
        message = new JLabel();
        message.setFont( new Font( "Arial Black", Font.BOLD, 100 ) );
        JPanel panel = new JPanel( new GridLayout( 2, 1 ) );
        panel.add( count );
        panel.add( message );
        panel.setBackground( Color.WHITE );
        message.setHorizontalAlignment( SwingConstants.CENTER );
        return panel;
    }
    private void startTimer(){
        count.setText( countdown.toReadableString());
        message.setText( countdown.getMessage() );
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
            count.setForeground( countdown.isCloseToEnd() ? Color.RED : Color.BLACK );
            if( countdown.isFinished() ){
                count.setText( "" );
                message.setText( "END" );
                timer.stop();
            }
            else
            {
                count.setText(countdown.toReadableString() );
            }
        }
    }


}
