package repro;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:3003/example/test");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        try (InputStream in = con.getInputStream();) {
            byte[] buffer = new byte[64];
            for (int i = 0; i < 2; ++i) {
                in.read(buffer);
                System.out.println(new String(buffer));
            }
        }
    }

}
