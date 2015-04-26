package GUI;

import Interval.Countdown;
import Interval.Set;

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
    private Set set;
    private static int setIndex;

    /**
     * Constructor use two Countdown objects, providing data to countdown display.
     * @param set, gdfgdfg
     * @param ofSet is created only as a information holder, it provide information and allows to display countdown for
     *              whole Set duration.
     */
    public CountdownFrame(Set set, Countdown ofSet){
        frame = new JFrame();
        this.set = set;
        this.ofSet = ofSet;
        setIndex = 0;
        getPartialCountdown();
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
     * Refresh content of a display. If
     */
    private void refreshDisplay(){
        intervalTime.setText(ofInterval.toReadableString());
        setTime.setText(ofSet.toReadableString());
        message.setText( ofInterval.getMessage() );

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
    public void getPartialCountdown(){
        Set clone = new Set();
        clone.setSchedule(set.copySchedule());
        clone.addToSchedule("00:00",clone.getSchedule().get(Integer.parseInt(clone.getSize())-1)[1]);
        String[] part = clone.getSchedule().get(setIndex);

        ofInterval = new Countdown(part[0], part[1]);
        setIndex = (setIndex< clone.getSchedule().size()-1) ? ++setIndex : setIndex;
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

            if( ofInterval.isFinished()){
                getPartialCountdown();
            }
            if( ofSet.isFinished()){
                timer.stop();
                message.setText( ofSet.getMessage() );
                frame.setVisible(false);
                frame.dispose();
            }else{
                refreshDisplay();
            }
        }
    }
}

//todo display działa do dupy po zmianie

