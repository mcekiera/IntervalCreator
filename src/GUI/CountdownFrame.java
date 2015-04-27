package GUI;

import Interval.Countdown;
import Interval.Set;
import Interval.Signal;
import Interval.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class creates a JFrame, and proceed a counting on a data, derived from particular Set object (for whole
 * set countdown) and Countdown object (for partial countdown).
 */
public class CountdownFrame{
    private JFrame frame;
    private Timer timer;
    private JLabel intervalTime;
    private JLabel setTime;
    private JLabel message;
    private Countdown ofInterval;
    private Countdown ofSet;
    private Set set;
    private Signal signal;
    private static int setIndex;

    /**
     * Constructor use two Countdown objects, providing data to countdown display. JFrame frame have a Window Listener
     * which allows to stop countdown in a case of window closing.
     * @param set, provide information for partial countdowns,
     * @param ofSet is created only as a information holder, it provide information and allows to display countdown for
     *              whole Set duration.
     */
    public CountdownFrame(Set set, Countdown ofSet){
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(timer.isRunning()) timer.stop();
                frame.dispose();
            }
        });
        this.set = set;
        this.ofSet = ofSet;
        setIndex = 0;
        signal = new Signal();
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
        timer = new Timer(1000, new TimerListener());
        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Method which allows to display partial countdown next to countdown of whole Set. It changes a reference
     * of ofInterval in response to ending of previous partial countdown. To prevent ArrayOutOfBounds Exception
     * it increase value of setIndex only if it will not exceed schedule.size.
     */
    public void getPartialCountdown(){
        String[] part = set.getSchedule().get(setIndex);
        ofInterval = new Countdown(part[0], part[1]);
        setIndex = (setIndex < set.getSchedule().size()) ? ++setIndex : setIndex;
    }


    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){

            ofInterval.decrement();
            ofSet.decrement();
            intervalTime.setForeground(ofInterval.isCloseToEnd() ? Color.RED : Color.BLACK);

            if(ofSet.isZero()) signal.getSound(Sounds.FINISH).run();
            if(ofInterval.isZero()) signal.getSound(Sounds.TRANSITION).run();

            if( ofSet.isFinished()){
                timer.stop();
                message.setText( ofSet.getMessage() );
                frame.setVisible(false);
                frame.dispose();
            }else if( ofInterval.isFinished()){
                getPartialCountdown();
            }
            if(frame.isVisible()) refreshDisplay();

        }
    }
}


