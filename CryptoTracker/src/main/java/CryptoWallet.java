import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CryptoWallet {
    private List<CryptoTransaction> transactions;
    private Map<String, Double> coinsQuantity = new HashMap<>();

    public CryptoWallet(List<CryptoTransaction> transactions) {
        this.transactions = transactions;
    }

    public void listTransactions() {
        for (CryptoTransaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void summarizeCurrencies() {
        for (CryptoTransaction transaction : transactions) {
            String coinName = transaction.getCryptoName();
            double coinQuantity = transaction.getQuantity();
            if (coinsQuantity.containsKey(coinName)) {
                coinsQuantity.put(coinName, coinsQuantity.get(coinName) + coinQuantity);
            } else {
                coinsQuantity.put(coinName, coinQuantity);
            }
        }
    }

    public Map<String, Double> getCoinsQuantity() {
        return coinsQuantity;
    }
}
