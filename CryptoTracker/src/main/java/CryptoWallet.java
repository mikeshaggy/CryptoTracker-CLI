import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CryptoWallet implements Serializable {
    private List<CryptoTransaction> transactions = new ArrayList<>();
    private Map<String, Double> coinsQuantity = new HashMap<>();

    public CryptoWallet() {}

    public CryptoWallet(List<CryptoTransaction> transactions) {
        this.transactions = transactions;
    }

    public void listTransactions() {
        if (!transactions.isEmpty()) {
            for (CryptoTransaction transaction : transactions) {
                System.out.println(transaction);
            }
            return;
        }
        System.out.println("Transactions list is empty");
    }

    private void summarizeCurrencies() {
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
