package ee.ut.math.tvt.soft6;

import java.awt.*;
import java.io.IOException;

import org.apache.log4j.Logger;

import javax.swing.*;

public class Intro {

    private static final Logger log = Logger.getLogger(Intro.class);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    log.error("IO problem.", e);
                    throw new RuntimeException(e);
                } catch (Throwable t) {
                    log.error("Something went wrong.", t);
                    throw t;
                }
            }
        });
    }

    private static void createAndShowGUI() throws IOException {
        IntroUI intro = new IntroUI();
        intro.setTitle("Information");

        //size of frame
        int width = 500;
        int height = 500;
        intro.setSize(width, height);

        //location of screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        intro.setLocation((screen.width - width) / 2, (screen.height - height) / 2);

        intro.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        intro.setVisible(true);
        log.info("Great success - information window opened.");
    }
}
