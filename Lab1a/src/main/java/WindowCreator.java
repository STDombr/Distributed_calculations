import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowCreator {

    JFrame frameMain;

    JSlider slider;

    JPanel panelMain;
    JPanel panelSlider;
    JPanel panelMode;
    JPanel panelPriorityUp;
    JPanel panelPriorityDown;
    JPanel panelLabel;

    JButton buttonStart;
    JButton buttonStop;
    JButton buttonPriorityUp1;
    JButton buttonPriorityUp2;
    JButton buttonPriorityDown1;
    JButton buttonPriorityDown2;

    JLabel labelPriority1;
    JLabel labelPriority2;

    ThreadManager thread1;
    ThreadManager thread2;

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
        buttonStart = new JButton("Start");
        buttonStart.setPreferredSize(new Dimension(200, 30));
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread1.setStart();
                thread2.setStart();

                buttonStop.setEnabled(true);
                buttonStart.setEnabled(false);

                updateLabel();
            }
        });

        buttonStop = new JButton("Stop");
        buttonStop.setPreferredSize(new Dimension(200, 30));
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread1.setStop();
                thread2.setStop();

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);

                updateLabel();
            }
        });

        buttonPriorityUp1 = new JButton("Up Thread 1 priority");
        buttonPriorityUp1.setPreferredSize(new Dimension(200, 30));
        buttonPriorityUp1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread1.upPriority();

                updateLabel();
            }
        });

        buttonPriorityUp2 = new JButton("Up Thread 2 priority");
        buttonPriorityUp2.setPreferredSize(new Dimension(200, 30));
        buttonPriorityUp2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread2.upPriority();

                updateLabel();
            }
        });

        buttonPriorityDown1 = new JButton("Down Thread 1 priority");
        buttonPriorityDown1.setPreferredSize(new Dimension(200, 30));
        buttonPriorityDown1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread1.downPriority();

                updateLabel();
            }
        });

        buttonPriorityDown2 = new JButton("Down Thread 2 priority");
        buttonPriorityDown2.setPreferredSize(new Dimension(200, 30));
        buttonPriorityDown2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread2.downPriority();

                updateLabel();
            }
        });

        labelPriority1 = new JLabel();
        labelPriority2 = new JLabel();
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

        panelMode = new JPanel();
        panelMode.add(buttonStart);
        panelMode.add(buttonStop);

        panelPriorityUp = new JPanel();
        panelPriorityUp.add(buttonPriorityUp1);
        panelPriorityUp.add(buttonPriorityUp2);

        panelPriorityDown = new JPanel();
        panelPriorityDown.add(buttonPriorityDown1);
        panelPriorityDown.add(buttonPriorityDown2);

        panelLabel = new JPanel();
        panelLabel.setPreferredSize(new Dimension(450, 30));
        labelPriority1 = new JLabel("", SwingConstants.CENTER);
        labelPriority1.setPreferredSize(new Dimension(200, 30));
        panelLabel.add(labelPriority1);
        labelPriority2 = new JLabel("", SwingConstants.CENTER);
        labelPriority2.setPreferredSize(new Dimension(200, 30));
        panelLabel.add(labelPriority2);

        panelMain = new JPanel();
        panelMain.add(panelSlider);
        panelMain.add(panelMode);
        panelMain.add(panelPriorityUp);
        panelMain.add(panelPriorityDown);
        panelMain.add(panelLabel);

        frameMain.add(panelMain);
    }

    private void updateLabel() {
        if (thread1.isMode())
            labelPriority1.setText("Priority: " + thread1.getThread().getPriority());
        else
            labelPriority1.setText("");
        if (thread2.isMode())
            labelPriority2.setText("Priority: " + thread2.getThread().getPriority());
        else
            labelPriority2.setText("");
    }
}
