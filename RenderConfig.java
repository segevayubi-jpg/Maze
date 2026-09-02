public class RenderConfig {

    private String wallCellColor;
    private String pathColor;
    private boolean drawGrid;
    private String gridColor;
    private int animationDelayMs;

    public RenderConfig(
            String wallCellColor,
            String pathColor,
            boolean drawGrid,
            String gridColor,
            int animationDelayMs) {

        this.wallCellColor = wallCellColor;
        this.pathColor = pathColor;
        this.drawGrid = drawGrid;
        this.gridColor = gridColor;
        this.animationDelayMs = animationDelayMs;
    }

    public String getWallCellColor() {
        return wallCellColor;
    }

    public String getPathColor() {
        return pathColor;
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    public String getGridColor() {
        return gridColor;
    }

    public int getAnimationDelayMs() {
        return animationDelayMs;
    }
}
