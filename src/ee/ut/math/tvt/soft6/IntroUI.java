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

        this.teamName = new JLabel();
        this.teamLeader = new JLabel();
        this.teamLeaderEmail = new JLabel();
        this.teamMembers = new JLabel();
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
    private ImageIcon createImageIcon(String path) {
        ClassLoader classLoader = IntroUI.class.getClassLoader();//oskab faile leida, otsib classpathist; klassi classloaderilt
        java.net.URL imgURL = classLoader.getResource(path); //kysitakse kus on "path" fail, saan asukoha

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void fillFields() throws IOException {

        Properties properties = new Properties();
        loadProperties(properties);

        teamName.setText("Team name: " + properties.getProperty("Name"));
        teamLeader.setText("Team leader: " + properties.getProperty("Leader"));
        teamLeaderEmail.setText("Team leader email: " + properties.getProperty("Email"));
        teamMembers.setText("Team lmembers: " + properties.getProperty("Members"));

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



