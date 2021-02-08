import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowCreator {

    Logger log = Logger.getLogger(WindowCreator.class.getName());

    private JFrame frameMain;

    private JSlider slider;

    private JPanel panelMain;
    private JPanel panelSlider;
    private JPanel panelMode1;
    private JPanel panelMode2;

    private JButton buttonStart1;
    private JButton buttonStart2;
    private JButton buttonStop1;
    private JButton buttonStop2;

    private ThreadManager thread1;
    private ThreadManager thread2;

    private AtomicInteger semaphore;

    /**
     * Constructor for the main window
     *
     * @param title
     * @param firstPosition
     * @param secondPosition
     */
    public WindowCreator(String title, int firstPosition, int secondPosition) {
        addFrame(title);
        addButtons();
        addSlider();
        addContent();

        thread1 = new ThreadManager(firstPosition, this.slider);
        thread2 = new ThreadManager(secondPosition, this.slider);

        semaphore = new AtomicInteger(0);

        frameMain.invalidate();
        frameMain.validate();
        frameMain.repaint();
    }

    /**
     * Create frame with some properties
     *
     * @param title
     */
    private void addFrame(String title) {
        frameMain = new JFrame(title);

        frameMain.setSize(600, 400);
        frameMain.setLocationRelativeTo(null);
        frameMain.setVisible(true);
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Create all buttons and actions
     */
    private void addButtons() {
        buttonStart1 = new JButton("Start1");
        buttonStart1.setPreferredSize(new Dimension(200, 30));
        buttonStart1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (semaphore.get() > 0){
                    log.log(Level.INFO, "Slider is busy by another thread");
                } else{
                    semaphore.compareAndSet(0, 1);

                    thread1.setStart();
                    thread1.setMinPriority();

                    buttonStop1.setEnabled(true);
                    buttonStart1.setEnabled(false);
                }
            }
        });

        buttonStart2 = new JButton("Start2");
        buttonStart2.setPreferredSize(new Dimension(200, 30));
        buttonStart2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (semaphore.get() > 0){
                    log.log(Level.INFO, "Slider is busy by another thread");
                } else{
                    semaphore.compareAndSet(0, 1);

                    thread2.setStart();
                    thread2.setMaxPriority();

                    buttonStop2.setEnabled(true);
                    buttonStart2.setEnabled(false);
                }
            }
        });

        buttonStop1 = new JButton("Stop1");
        buttonStop1.setPreferredSize(new Dimension(200, 30));
        buttonStop1.setEnabled(false);
        buttonStop1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                semaphore.compareAndSet(1, 0);

                thread1.setStop();

                buttonStop1.setEnabled(false);
                buttonStart1.setEnabled(true);
            }
        });

        buttonStop2 = new JButton("Stop2");
        buttonStop2.setPreferredSize(new Dimension(200, 30));
        buttonStop2.setEnabled(false);
        buttonStop2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                semaphore.compareAndSet(1, 0);

                thread2.setStop();

                buttonStop2.setEnabled(false);
                buttonStart2.setEnabled(true);
            }
        });
    }

    /**
     * Create slider with some properties
     */
    private void addSlider() {
        slider = new JSlider();
        slider.setPreferredSize(new Dimension(440, 150));

        slider.setMinimum(0);
        slider.setMaximum(100);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
    }

    /**
     * Add all contents to frame
     */
    private void addContent() {
        panelSlider = new JPanel();
        panelSlider.setPreferredSize(new Dimension(450, 150));
        panelSlider.add(slider);

        panelMode1 = new JPanel();
        panelMode1.add(buttonStart1);
        panelMode1.add(buttonStart2);

        panelMode2 = new JPanel();
        panelMode2.add(buttonStop1);
        panelMode2.add(buttonStop2);


        panelMain = new JPanel();
        panelMain.add(panelSlider);
        panelMain.add(panelMode1);
        panelMain.add(panelMode2);

        frameMain.add(panelMain);
    }
}
