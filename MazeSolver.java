import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class MazeSolver {

    public static List<Point> findPath(boolean[][] maze) {

        int height = maze.length;
        int width = maze[0].length;

        // אם נקודת ההתחלה או הסיום הן קיר - אין פתרון
        if (!maze[0][0] || !maze[height - 1][width - 1]) {
            return new ArrayList<>();
        }

        boolean[][] visited = new boolean[height][width];

        Point[][] parent = new Point[height][width];

        Queue<Point> queue = new ArrayDeque<>();

        Point start = new Point(0, 0);

        queue.add(start);
        visited[0][0] = true;

        int[][] directions = {
                {1, 0},   // right
                {-1, 0},  // left
                {0, 1},   // down
                {0, -1}   // up
        };

        while (!queue.isEmpty()) {

            Point current = queue.remove();

            if (current.x == width - 1 &&
                    current.y == height - 1) {

                return buildPath(
                        parent,
                        current
                );
            }

            for (int[] direction : directions) {

                int newX =
                        current.x + direction[0];

                int newY =
                        current.y + direction[1];

                if (newX >= 0 &&
                        newX < width &&
                        newY >= 0 &&
                        newY < height &&
                        maze[newY][newX] &&
                        !visited[newY][newX]) {

                    visited[newY][newX] = true;

                    parent[newY][newX] = current;

                    queue.add(
                            new Point(newX, newY)
                    );
                }
            }
        }

        // התור התרוקן ולא הגענו לסוף
        return new ArrayList<>();
    }


    private static List<Point> buildPath(
            Point[][] parent,
            Point end) {

        List<Point> path = new ArrayList<>();

        Point current = end;

        while (current != null) {

            path.add(current);

            current =
                    parent[current.y][current.x];
        }

        Collections.reverse(path);

        return path;
    }
}
