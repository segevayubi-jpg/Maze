import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    private int mazeWidth = 30;
    private int mazeHeight = 30;

    private JLabel wallColorLabel;
    private JLabel pathColorLabel;
    private JLabel drawGridLabel;
    private JLabel gridColorLabel;
    private JLabel animationDelayLabel;

    private JButton refreshButton;
    private JButton getMazeButton;

    private ApiClient apiClient;

    public MainFrame() {

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);

        apiClient = new ApiClient();

        createComponents();
        createLayout();
        createListeners();

        loadConfig();
    }


    private void createComponents() {

        widthField = new JTextField("30", 10);
        heightField = new JTextField("30", 10);

        wallColorLabel = new JLabel("Not loaded yet");
        pathColorLabel = new JLabel("Not loaded yet");
        drawGridLabel = new JLabel("Not loaded yet");
        gridColorLabel = new JLabel("Not loaded yet");
        animationDelayLabel = new JLabel("Not loaded yet");

        refreshButton = new JButton("Refresh Config");
        getMazeButton = new JButton("GET MAZE");
    }


    private void createListeners() {

        refreshButton.addActionListener(e -> {

            System.out.println("Refresh button clicked");

            loadConfig();
        });
    }


    private void loadConfig() {

        RenderConfig config = apiClient.getRenderConfig();

        if (config == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load configuration"
            );

            return;
        }

        wallColorLabel.setText(
                config.getWallCellColor()
        );

        pathColorLabel.setText(
                config.getPathColor()
        );

        drawGridLabel.setText(
                String.valueOf(config.isDrawGrid())
        );

        gridColorLabel.setText(
                config.getGridColor()
        );

        animationDelayLabel.setText(
                config.getAnimationDelayMs() + " ms"
        );
    }


    private void createLayout() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(
                new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Maze Generator");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(
                Box.createVerticalStrut(20)
        );

        mainPanel.add(title);

        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        JPanel configPanel =
                new JPanel(
                        new GridLayout(5, 2, 10, 10)
                );

        configPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Render Configuration"
                )
        );


        configPanel.add(
                new JLabel("Wall Color:")
        );

        configPanel.add(
                wallColorLabel
        );


        configPanel.add(
                new JLabel("Path Color:")
        );

        configPanel.add(
                pathColorLabel
        );


        configPanel.add(
                new JLabel("Draw Grid:")
        );

        configPanel.add(
                drawGridLabel
        );


        configPanel.add(
                new JLabel("Grid Color:")
        );

        configPanel.add(
                gridColorLabel
        );


        configPanel.add(
                new JLabel("Animation Delay:")
        );

        configPanel.add(
                animationDelayLabel
        );


        mainPanel.add(configPanel);

        mainPanel.add(
                Box.createVerticalStrut(15)
        );


        refreshButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(refreshButton);


        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        JPanel sizePanel = new JPanel();

        sizePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Maze Size"
                )
        );


        sizePanel.add(
                new JLabel("Width:")
        );

        sizePanel.add(
                widthField
        );


        sizePanel.add(
                new JLabel("Height:")
        );

        sizePanel.add(
                heightField
        );


        mainPanel.add(sizePanel);


        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        getMazeButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mainPanel.add(getMazeButton);


        mainPanel.add(
                Box.createVerticalStrut(20)
        );


        add(mainPanel);
    }
}
