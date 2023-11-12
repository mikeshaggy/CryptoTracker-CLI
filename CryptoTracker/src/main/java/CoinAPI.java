import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CoinAPI {
    private static final String API_KEY = "463013E2-04A2-47F2-800B-6E05DFD22033";

    private static String getCoinData(String coinName) {
        StringBuffer sb = new StringBuffer();

        try {
            URL url = new URL("https://rest.coinapi.io/v1/exchangerate/%s/USD".formatted(coinName));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-CoinAPI-Key", API_KEY);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;

            while ((input = br.readLine()) != null) {
                sb.append(input);
            }

            br.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Caught MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Caught IOException");
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static double getCoinExchangeRate(String coinName) {
        String coinData = getCoinData(coinName);

        double exchangeRate = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(coinData);
            exchangeRate = round(jsonNode.get("rate").asDouble(), 2);
        } catch (JsonProcessingException e) {
            System.out.println("Caught JsonProcessingException");
            e.printStackTrace();
        }

        return exchangeRate;
    }

    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
