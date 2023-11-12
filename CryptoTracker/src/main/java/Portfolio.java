import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements Serializable {
    private String name;
    private List<Trade> trades = new ArrayList<>();
    private Map<String, Double> coinsQuantity = new HashMap<>();
    private double investedAmount;

    public Portfolio(String name) {
        this.name = name;
    }

    public void addTransaction(Trade trade) {
        trades.add(trade);
        investedAmount += trade.getPrice();
        String coinName = trade.getCryptoName();
        double coinQuantity = trade.getQuantity();
        if (coinsQuantity.containsKey(coinName)) {
            coinsQuantity.put(coinName, coinsQuantity.get(coinName) + coinQuantity);
        } else {
            coinsQuantity.put(coinName, coinQuantity);
        }
    }

    public void listTransactions() {
        if (!trades.isEmpty()) {
            for (Trade trade : trades) {
                System.out.println(trade);
            }
            return;
        }
        System.out.println("Transactions list is empty");
    }

    public Map<String, Double> getCoinsQuantity() {
        return coinsQuantity;
    }

    public String getName() {
        return name;
    }
}
