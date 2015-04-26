package GUI;

import Interval.Countdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class creates a JFrame, and proceed a counting, on a data derived from particular Countdown object. Class
 * extends WindowAdapter, what allows to implement MouseListener() and MouseClicked() method.
 */
public class CountdownFrame extends WindowAdapter{
    private JFrame frame;
    private Timer timer;
    private JLabel intervalTime;
    private JLabel setTime;
    private JLabel message;
    private Countdown ofInterval;
    private Countdown ofSet;

    /**
     * Constructor use two Countdown objects, providing data to countdown display.
     * @param ofInterval is a Countdown object of single position in Set,
     * @param ofSet is created only as a information holder, it provide information and allows to display countdown for
     *              whole Set duration.
     */
    public CountdownFrame(Countdown ofInterval, Countdown ofSet){
        frame = new JFrame();
        this.ofInterval = ofInterval;
        this.ofSet = ofSet;
        display();
    }

    /**
     * Creates visible, undecorated JFrame and call startTimer() by which starts countdown process. Also menage
     * mouse action, which cause transformation do decorated JFrame, what gives a user possibility to close
     * frame and end timer activity before execution of entire countdown.
     */
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

    /**
     * Is responsible for creation of a text displayed on a JFrame.
     * @return panel with 3 JLabels, for: position time, position message and whole remaining set time.
     */
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

    /**
     * Refresh content of a display.
     */
    private void refreshDisplay(){
        intervalTime.setText(ofInterval.toReadableString());
        setTime.setText(ofSet.toReadableString());
        message.setText( ofInterval.getMessage() );

    }

    /**
     * Initialize work of Timer object, for particular countdown. If countdown is close to end, changes color
     * of displayed text form black to red. Timers work is set on 1000 millisecond.
     */
    private void startTimer(){
        refreshDisplay();
        intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);
        timer = new Timer( 1000, new TimerListener() );
        timer.setRepeats( true );
        timer.start();
    }

    /**
     * Provides information for Set class, by which it is determined, if next countdown in a Set.schedule, should starts.
     * @return true if Timer object is still working.
     */
    public boolean isBusy(){
        return timer.isRunning();
    }

    /**
     * Window event handler. It stops work of Timer with closing of JFrame.
     * @param e event fired by closing of JFrame.
     */
    @Override
    public void windowClosing(WindowEvent e){
        timer.stop();
        frame.dispose();
    }

    /**
     * Inner class, contains method fired by Timer counting. It decrease values provided within constructor, and
     * display it on JFrame.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            ofInterval.decrement();
            ofSet.decrement();
            intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);
            if( ofInterval.isFinished() ){
                timer.stop();
                frame.setVisible(false);

            }
            if( ofSet.isFinished()){
                setTime.setText("00:00");
                intervalTime.setText("00:00");
                message.setText( ofSet.getMessage() );
                frame.dispose();
            }else{
                refreshDisplay();
            }
        }
    }
}

