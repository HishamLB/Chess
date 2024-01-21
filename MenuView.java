import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JButton startButton;
    private JButton loadButton;

    // Author: Nick

    public MenuView() {
        //Frame and Panel initialisation for the main menu
        setTitle("Kwazam Chess Menu");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());

        //Start Button
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(200, 50));

        //Load Button
        loadButton = new JButton("Load Game");
        loadButton.setPreferredSize(new Dimension(200, 50));

        //Add buttons to the panel
        menuPanel.add(startButton);
        menuPanel.add(loadButton);

        //Add the panel to the frame
        this.add(menuPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    // Author: Nick
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    // Author: Nick
    public void addLoadButtonListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }
}