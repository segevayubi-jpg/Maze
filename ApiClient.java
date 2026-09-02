import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final String CONFIG_URL =
            "https://shaitest-production-3066.up.railway.app/fm1/get-render-config";

    public String getRenderConfig() {

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

            return response.body();

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
            return null;
        }
    }
}
