package ee.ut.math.tvt.soft6;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class IntroUI extends JFrame {

    private static final Logger log = Logger.getLogger(IntroUI.class);
    JPanel panel;
    JLabel teamName;
    JLabel teamLeader;
    JLabel teamLeaderEmail;
    JLabel teamMembers;
    JLabel logo;

    public IntroUI() throws IOException {
        createComponents();
        addComponentsToPanel();
        fillFields();
    }

    private void createComponents() {
        //setting up container & making layout manager
        this.panel = new JPanel(new GridLayout(6, 1));

        this.teamName = new JLabel(" Team name: soft6");
        this.teamLeader = new JLabel("Team leader: Anu");
        this.teamLeaderEmail = new JLabel("Team leader email: anulajal@gmail.com");
        this.teamMembers = new JLabel("Team members: Lembit Valgma, Erki Lomp, Andrey Vavilov, Anu Lajal");
        this.logo = new JLabel(createImageIcon("GeekHead.jpg"));
    }

    private void addComponentsToPanel() {
        panel.add(teamName);
        panel.add(teamLeader);
        panel.add(teamLeaderEmail);
        panel.add(teamMembers);
        panel.add(logo);
        this.add(panel);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        ClassLoader classLoader = IntroUI.class.getClassLoader();//oskab faile leida, otsib classpathist; klassi classloaderilt
        java.net.URL imgURL = classLoader.getResource(path); //kysitakse kus on "path" fail, saan asukoha

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void fillFields() throws IOException {

        Properties properties = new Properties();
        loadProperties(properties);
        properties.getProperty("Name");


    }

    private void loadProperties(Properties properties) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        InputStream resourceStream = cl.getResourceAsStream("application.properties"); //andsin nime, mida otsin
        if (resourceStream == null) {
            throw new AssertionError("Check that etc is in classpath");
        }
        try {
            properties.load(resourceStream);
        } finally {
            resourceStream.close();
        }
    }
}



