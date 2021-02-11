import javax.swing.*;

public class ThreadManager implements Runnable {
    private int finalPosition;
    private JSlider slider;
    private boolean mode;
    private Thread thread;

    /**
     * Constructor for the thread manager
     * @param finalPosition
     * @param slider
     */
    public ThreadManager(int finalPosition, JSlider slider) {
        this.finalPosition = finalPosition;
        this.slider = slider;

        setStop();
    }

    @Override
    public void run() {
        while (mode) {
            synchronized (this) {
                int value = slider.getValue();
                if (value != finalPosition) {
                    if (value > finalPosition) {
                        slider.setValue(value - 1);
                    } else {
                        slider.setValue(value + 1);
                    }
                }
            }
        }
    }

    /**
     * Function to start this thread
     */
    public void setStart() {
        this.mode = true;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Function to stop this thread
     */
    public void setStop() {
        this.mode = false;
    }

    /**
     * Function to increase priority
     */
    public void upPriority(){
        if (thread != null){
            if (thread.getPriority() < Thread.MAX_PRIORITY)
                thread.setPriority(thread.getPriority() + 1);
        }
    }

    /**
     * Function to decrease priority
     */
    public void downPriority(){
        if (thread != null){
            if (thread.getPriority() > Thread.MIN_PRIORITY)
                thread.setPriority(thread.getPriority() - 1);
        }
    }

    public int getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(int finalPosition) {
        this.finalPosition = finalPosition;
    }

    public JSlider getSlider() {
        return slider;
    }

    public void setSlider(JSlider slider) {
        this.slider = slider;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
