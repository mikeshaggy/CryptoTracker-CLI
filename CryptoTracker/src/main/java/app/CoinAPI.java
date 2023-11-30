package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;

public class CoinAPI {
    private static final String API_KEY = "463013E2-04A2-47F2-800B-6E05DFD22033";

    public static String getCoinData(String coinName) {
        StringBuilder sb = new StringBuilder();

        try {
            String apiUrl = "https://rest.coinapi.io/v1/exchangerate/%s/USD".formatted(coinName);
            URL url = URI.create(apiUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-app.CoinAPI-Key", API_KEY);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;

            while ((input = br.readLine()) != null) {
                sb.append(input);
            }

            br.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Caught MalformedURLException");
        } catch (IOException e) {
            System.out.println("Caught IOException");
        }

        return sb.toString();
    }

    public static double getCoinExchangeRate(String coinName) {
        String coinData = getCoinData(coinName);

        double exchangeRate = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(coinData);
            exchangeRate = round(jsonNode.get("rate").asDouble());
        } catch (JsonProcessingException e) {
            System.out.println("Caught JsonProcessingException");
        }

        return exchangeRate;
    }

    private static double round(double value) {

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
