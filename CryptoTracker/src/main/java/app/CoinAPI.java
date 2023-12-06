package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

public class CoinAPI implements Serializable {
    private final String API_KEY = "302F2A7C-47D7-4049-AB47-BB36C318CF27";

    private String getCoinData(String coinName) throws IOException {
        String apiUrl = "https://rest.coinapi.io/v1/exchangerate/%s/USD".formatted(coinName);
        URL url = URI.create(apiUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("X-CoinAPI-Key", API_KEY);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return sb.toString();
    }

    public double getCoinExchangeRate(String coinName) {
        double exchangeRate = 0;

        try {
            String coinData = getCoinData(coinName);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(coinData);
            exchangeRate = round(jsonNode.get("rate").asDouble());
        } catch (JsonProcessingException e) {
            System.out.println("Caught JsonProcessingException");
        } catch (IOException e) {
            System.out.println("Caught IOException");
        }

        return exchangeRate;
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
