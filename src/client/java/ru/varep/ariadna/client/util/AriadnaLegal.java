package ru.varep.ariadna.client.util;

import org.apache.http.HttpResponse;
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

    public static final String url = "https://ariadnalegal.varep.ru";

    public static List<String> getLegal() throws IOException {
       List<String> offline = getOffline();
       List<String> online = getServers();
       List<String> legal = new ArrayList<>(offline);
       legal.addAll(online);
       return legal;
    }


    public static List<String> getOffline() {
        List<String> blacklist = new ArrayList<>();
        blacklist.add("subserver.ru");
        blacklist.add("de.subserver.ru");
        blacklist.add("us.subserver.ru");

        return blacklist;
    }

    public static List<String> getServers() throws IOException {
        List<String> servers = new ArrayList<>();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet req = new HttpGet(url);
            HttpResponse response = client.execute(req);

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Sync blacklist. Status:" + response.getStatusLine().getStatusCode());
                String html = EntityUtils.toString(response.getEntity());
                Pattern pattern = Pattern.compile("<h1.*?>(.*?)</h1>", Pattern.DOTALL);
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






