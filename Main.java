import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        ApiClient apiClient = new ApiClient();

        String config = apiClient.getRenderConfig();

        System.out.println(config);

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
