package ru.varep.ariadna.client.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AriadnaLegal {

    public static final String url = "http://ariadnalegal.varep.ru";

    public boolean check() {
        boolean res = false;
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    public static List<String> getServers() throws IOException {
        List<String> servers = new ArrayList<>();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet req = new HttpGet(url);
            org.apache.http.HttpResponse response = client.execute(req);

            if (response.getStatusLine().getStatusCode() == 200) {
                String html = EntityUtils.toString(response.getEntity());
                Pattern pattern = Pattern.compile("<h2.*?>(.*?)</h2>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(html);

                while (matcher.find()) {
                    servers.add(matcher.group(1).trim());
                }
            } else {
                System.err.println("Error getting legal information");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return servers;
    }
}




