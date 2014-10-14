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
    private static final String MODE = "console";


    public static void main(String[] args) throws IOException {
    	final SalesDomainController domainController = new SalesDomainControllerImpl();

    	if (args.length == 1 && args[0].equals(MODE)) {
    		log.debug("Mode: " + MODE);

    		ConsoleUI cui = new ConsoleUI(domainController);
    		cui.run();
    	} else {

    		IntroUI introUI = new IntroUI();
    		introUI.setVisible(true);
    		introUI.setAlwaysOnTop(true);

    		final SalesSystemUI ui = new SalesSystemUI(domainController);
    		ui.setVisible(true);

    		introUI.setAlwaysOnTop(false);
    		try {
    			Thread.sleep(3000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		introUI.setVisible(false);
    	}
    	
    	
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
        int height = 350;
        intro.setSize(width, height);

        //location of screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        intro.setLocation((screen.width - width) / 2, (screen.height - height) / 2);

        intro.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        intro.setVisible(true);
        log.info("Great success - information window opened.");
    }
}
