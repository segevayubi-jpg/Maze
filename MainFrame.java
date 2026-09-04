import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class MainFrame extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    private int mazeWidth = 30;
    private int mazeHeight = 30;
    
    private BufferedImage mazeImage;
    private boolean[][] maze;
    
    private JLabel wallColorLabel;
    private JLabel pathColorLabel;
    private JLabel drawGridLabel;
    private JLabel gridColorLabel;
    private JLabel animationDelayLabel;

    private JButton refreshButton;
    private JButton getMazeButton;
    private JButton checkSolutionButton;
    
    private ApiClient apiClient;
    private RenderConfig renderConfig;
    
    private static final int CELL_SIZE = 20;

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
    private void decodeMaze() {
    
        if (mazeImage == null) {
            return;
        }
    
        int imageWidth = mazeImage.getWidth();
        int imageHeight = mazeImage.getHeight();
    
        maze = new boolean[imageHeight][imageWidth];
    
        for (int y = 0; y < imageHeight; y++) {
    
            for (int x = 0; x < imageWidth; x++) {
    
                int rgb = mazeImage.getRGB(x, y);
    
                Color pixelColor = new Color(rgb);
    
                boolean isWhite =
                        pixelColor.getRed() == 255
                        && pixelColor.getGreen() == 255
                        && pixelColor.getBlue() == 255;
    
                maze[y][x] = isWhite;
            }
        }
    }

    private void handleGetMaze() {
    
        readMazeSize();
    
        mazeImage = apiClient.getMazeImage(
                mazeWidth,
                mazeHeight
        );
    
        if (mazeImage == null) {
    
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load maze image"
            );
    
            return;
        }
    
        decodeMaze();
    
        System.out.println("Maze decoded successfully");
    }
    private int validateDimension(JTextField field) {
    
        try {
    
            int value = Integer.parseInt(field.getText());
    
            if (value < 5 || value > 100) {
                return 30;
            }
    
            return value;
    
        } catch (NumberFormatException e) {
    
            return 30;
        }
    }
    private void readMazeSize() {
    
        mazeWidth = validateDimension(widthField);
        mazeHeight = validateDimension(heightField);
    
        widthField.setText(String.valueOf(mazeWidth));
        heightField.setText(String.valueOf(mazeHeight));
    
        System.out.println("Width: " + mazeWidth);
        System.out.println("Height: " + mazeHeight);
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
    
        getMazeButton.addActionListener(e -> {
            handleGetMaze();
        });
    }

    private void loadConfig() {
    
        renderConfig = apiClient.getRenderConfig();
    
        if (renderConfig == null) {
    
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load configuration"
            );
    
            return;
        }
    
        wallColorLabel.setText(
                renderConfig.getWallCellColor()
        );
    
        pathColorLabel.setText(
                renderConfig.getPathColor()
        );
    
        drawGridLabel.setText(
                String.valueOf(renderConfig.isDrawGrid())
        );
    
        gridColorLabel.setText(
                renderConfig.getGridColor()
        );
    
        animationDelayLabel.setText(
                renderConfig.getAnimationDelayMs() + " ms"
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
