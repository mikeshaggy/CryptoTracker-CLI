import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CryptoTrackerApp {
    public static void main(String[] args) {
        List<CryptoTransaction> transactions = generateRandomList();

        CryptoWallet cryptoWallet = new CryptoWallet(transactions);

        cryptoWallet.listTransactions();

        cryptoWallet.summarizeCurrencies();

        System.out.println(cryptoWallet.getCoinsQuantity());
    }
    private static List<CryptoTransaction> generateRandomList() {
        Random random = new Random();
        String[] names = {"btc", "eth", "bnb", "xrp", "doge"};
        List<CryptoTransaction> transactions = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            String name = names[random.nextInt(0, 4)];
            double price = random.nextDouble(50);
            double qty = random.nextDouble(50);
            transactions.add(new CryptoTransaction(name, price, qty));
        }

        return transactions;
    }
}