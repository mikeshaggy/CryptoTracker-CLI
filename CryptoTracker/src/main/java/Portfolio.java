import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements Serializable {
    private String name;
    private List<Trade> trades = new ArrayList<>();
    private Map<String, Double> content = new HashMap<>();
    private double investedAmount;

    public Portfolio(String name) {
        this.name = name;
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
        String coinName = trade.getCoinName();
        double coinQuantity = trade.getQuantity();
        investedAmount += trade.getPrice() * coinQuantity;
        if (content.containsKey(coinName)) {
            content.put(coinName, content.get(coinName) + coinQuantity);
        } else {
            content.put(coinName, coinQuantity);
        }
    }

    public void printTradeList() {
        if (!trades.isEmpty()) {
            for (Trade trade : trades) {
                System.out.println(trade);
            }
            return;
        }
        System.out.println("Trade list is empty");
    }

    private void getContent() {
        System.out.printf("%-5s | %-5s | %-15s%n", "Coin Name", "Held Quantity", "Estimated Value in USD [$]");
        for (Map.Entry<String, Double> entry : content.entrySet()) {
            String coinName = entry.getKey();
            double coinAmount = entry.getValue();
            double coinPrice = CoinAPI.getCoinExchangeRate(coinName);
            double coinValue = coinPrice * coinAmount;
            System.out.printf("%-9s | %-13.9f | %-15.2f%n", coinName, coinAmount, coinValue);
        }
    }

    private double getValue() {
        double value = 0;
        for (Map.Entry<String, Double> entry : content.entrySet()) {
            String coinName = entry.getKey();
            double coinAmount = entry.getValue();
            double coinPrice = CoinAPI.getCoinExchangeRate(coinName);
            value += coinPrice * coinAmount;
        }

        return value;
    }

    private double getProfit() {
        double currentValue = getValue();

        return ((currentValue - investedAmount) / investedAmount) * 100;
    }

    public void getDetails() {
        double profit = getProfit();

        String result = profit >= 0 ? "Gain" : "Loss";

        getContent();
        System.out.println("-".repeat(60));
        System.out.printf("Invested amount: " + investedAmount + "$");
        System.out.printf("Total portfolio value: %.2f$%n", getValue());
        System.out.printf("%s: %.2f%%%n", result, profit);
    }

    public String getName() {
        return name;
    }
}
