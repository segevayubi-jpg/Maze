import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiClient {

    private static final String CONFIG_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-render-config";

    public RenderConfig getRenderConfig() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CONFIG_URL))
                .GET()
                .build();

        try {

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                System.out.println(
                        "Server returned status: " + response.statusCode()
                );

                return null;
            }

            return parseRenderConfig(response.body());

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
            return null;
        }
    }

    private RenderConfig parseRenderConfig(String json) {

        String wallCellColor =
                extractString(json, "wallCellColor");

        String pathColor =
                extractString(json, "pathColor");

        boolean drawGrid =
                extractBoolean(json, "drawGrid");

        String gridColor =
                extractString(json, "gridColor");

        int animationDelayMs =
                extractInt(json, "animationDelayMs");

        return new RenderConfig(
                wallCellColor,
                pathColor,
                drawGrid,
                gridColor,
                animationDelayMs
        );
    }

    private String extractString(String json, String key) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*\"([^\"]*)\""
        );

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new IllegalArgumentException(
                "Missing field: " + key
        );
    }

    private boolean extractBoolean(String json, String key) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*(true|false)"
        );

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Boolean.parseBoolean(matcher.group(1));
        }

        throw new IllegalArgumentException(
                "Missing field: " + key
        );
    }

    private int extractInt(String json, String key) {

        Pattern pattern = Pattern.compile(
                "\"" + key + "\"\\s*:\\s*(\\d+)"
        );

        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        throw new IllegalArgumentException(
                "Missing field: " + key
        );
    }
}
