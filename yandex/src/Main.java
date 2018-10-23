import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main {
private static final    String key = "trnsl.1.1.20181012T035544Z.b310f13dfc051302.e26047e5305c05bc8e138b88f292274b61192681";
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String text = in.nextLine();
            if(!text.equals("")) {
                String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key + "&text=" + URLEncoder.encode(text, "UTF-8") + "&lang=en-ru";
                String JSONStr = excutePost(url);
                String result = JSONStr.substring(36, JSONStr.length() - 4);
                System.out.println(result);
            }
        }
    }
    /**
     * получает json по данному url
     * @param targetURL
     * @return
     */
    public static String excutePost(String targetURL)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //подключаемся
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //отправляем request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.flush ();
            wr.close ();

            //получаем Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
