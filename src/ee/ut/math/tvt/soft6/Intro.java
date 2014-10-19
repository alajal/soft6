package ee.ut.math.tvt.soft6;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class Intro {

    private static final Logger log = Logger.getLogger(Intro.class);
    private static final String CONSOLE = "console";

    public static void main(String[] args) throws IOException {
        final SalesDomainController salesDomainController = new SalesDomainControllerImpl();

        if (args.length == 1 && args[0].equals(CONSOLE)) {
            log.debug("Mode: " + CONSOLE);
            ConsoleUI consoleUI = new ConsoleUI(salesDomainController);
            consoleUI.run();

        } else {
            final SalesSystemUI salesSystemUI = new SalesSystemUI(salesDomainController);
            salesSystemUI.setVisible(true);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        createAndShowIntroGUI();
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
    }

    private static void createAndShowIntroGUI() throws IOException {
        IntroUI intro = new IntroUI();
        intro.setTitle("Information");

        //size of frame
        int width = 500;
        int height = 350;
        intro.setSize(width, height);
        intro.setAlwaysOnTop(false);

        //location of screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        intro.setLocation((screen.width - width) / 2, (screen.height - height) / 2);
        intro.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        intro.setVisible(false);

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //log.info("Great success - information window opened.");
    }
}
