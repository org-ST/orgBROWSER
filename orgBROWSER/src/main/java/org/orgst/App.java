package org.orgst;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class App 
{
    public static void open(String url) {
        try {
            URI uri = new URI(url); // your website here
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            } else {
                System.out.println("Desktop not supported, can't open browser.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter URL: ");
        String url = sc.nextLine();
        try {


            URL urlObj = new URL(url);
            String host = urlObj.getHost();
            InetAddress addr = InetAddress.getByName(host);
            System.out.println("IP Address: " + addr.getHostAddress());
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            StringBuilder sb = new StringBuilder();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                br.close();
            }
            File file = new File(host+".html");
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            conn.disconnect();
            open("file://" + file.getAbsolutePath());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
