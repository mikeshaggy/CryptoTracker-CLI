import java.time.LocalDate;

public class CryptoTransaction {
    private String cryptoName;
    private LocalDate purchaseDate;
    private double price;
    private double quantity;

    public CryptoTransaction(String cryptoName, double price, double quantity) {
        this.cryptoName = cryptoName;
        this.price = price;
        this.purchaseDate = LocalDate.now();
        this.quantity = quantity;

    }

    public String getCryptoName() {
        return cryptoName;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }


    @Override
    public String toString() {
        return "date: %s | name: %s | price: %.2f | qty: %.9f".formatted(purchaseDate.toString(), cryptoName, price, quantity);
    }
}
