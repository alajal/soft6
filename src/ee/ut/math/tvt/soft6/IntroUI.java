package ee.ut.math.tvt.soft6;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class IntroUI extends JFrame {

    private JPanel panel;
    private JLabel teamName;
    private JLabel teamLeader;
    private JLabel teamLeaderEmail;
    private JLabel teamMembers;
    private JLabel buildNbr;
    private JLabel logo;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private static final Insets insets = new Insets(0, 0, 0, 0);

    public IntroUI() throws IOException {
        createComponents();
        addComponentsToPanel();
        fillFields();
    }

    private void createComponents() {
        //setting up container & making layout manager
        this.panel = new JPanel(gridBagLayout);
        this.panel.setBackground(Color.white);

        this.teamName = new JLabel();
        this.teamLeader = new JLabel();
        this.teamLeaderEmail = new JLabel();
        this.teamMembers = new JLabel();
        this.buildNbr = new JLabel();
        this.logo = new JLabel(createImageIcon("GeekHead.jpg"));
    }

    private void addComponentsToPanel() {
        addComponent(panel, teamName, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        addComponent(panel, teamLeader, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        addComponent(panel, teamLeaderEmail, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        addComponent(panel, teamMembers, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        addComponent(panel, buildNbr, 0, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
        panel.add(logo);
        this.add(panel);

    }

    private static void addComponent(Container container, Component component, int gridx, int gridy,
                                     int gridwidth, int gridheight, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

    // Returns an ImageIcon, or null if the path was invalid.
    private ImageIcon createImageIcon(String path) {
        ClassLoader classLoader = IntroUI.class.getClassLoader();  // oskab faile leida, otsib classpathist; klassi classloaderilt
        java.net.URL imgURL = classLoader.getResource(path);  // kysitakse kus on "path" fail, saan asukoha

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void fillFields() throws IOException {

        Properties properties = new Properties();
        loadProperties(properties, "application.properties");  // loeme andmed application.properties failist

        teamName.setText("Team name: " + properties.getProperty("Name"));
        teamLeader.setText("Team leader: " + properties.getProperty("Leader"));
        teamLeaderEmail.setText("Team leader email: " + properties.getProperty("Email"));
        teamMembers.setText("Team members: " + properties.getProperty("Members"));

        loadProperties(properties, "version.properties");  // andmed version.properties failist
        buildNbr.setText("Build number: " + properties.getProperty("build.number"));

    }

    private void loadProperties(Properties properties, String filename) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        InputStream resourceStream = cl.getResourceAsStream(filename);  // andsin nime, mida otsin
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



