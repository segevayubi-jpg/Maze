import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {

    private boolean[][] maze;
    private RenderConfig renderConfig;
    private int cellSize;

    private List<Point> solutionPath;
    private int visiblePathCells;

    public MazePanel(boolean[][] maze,
                     RenderConfig renderConfig,
                     int cellSize) {

        this.maze = maze;
        this.renderConfig = renderConfig;
        this.cellSize = cellSize;

        int width = maze[0].length * cellSize;
        int height = maze.length * cellSize;

        setPreferredSize(
                new Dimension(width, height)
        );
    }
    public void setSolutionPath(List<Point> solutionPath) {
    
        this.solutionPath = solutionPath;
        this.visiblePathCells = 0;
    
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Color wallColor =
                Color.decode(
                        renderConfig.getWallCellColor()
                );

        Color gridColor =
                Color.decode(
                        renderConfig.getGridColor()
                );

        for (int y = 0; y < maze.length; y++) {

            for (int x = 0; x < maze[y].length; x++) {

                if (maze[y][x]) {

                    // true = passage
                    g.setColor(Color.WHITE);

                } else {

                    // false = wall
                    g.setColor(wallColor);
                }

                g.fillRect(
                        x * cellSize,
                        y * cellSize,
                        cellSize,
                        cellSize
                );

                if (renderConfig.isDrawGrid()) {

                    g.setColor(gridColor);

                    g.drawRect(
                            x * cellSize,
                            y * cellSize,
                            cellSize,
                            cellSize
                    );
                }
            }
        }
    }
}
