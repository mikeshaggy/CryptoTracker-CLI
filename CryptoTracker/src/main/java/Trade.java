import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade implements Serializable {
    private String cryptoName;
    private LocalDateTime purchaseDate;
    private double price;
    private double quantity;
    private transient DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Trade(String cryptoName, double price, double quantity) {
        this.cryptoName = cryptoName;
        this.price = price;
        this.purchaseDate = LocalDateTime.now();
        this.quantity = quantity;

    }

    public String getCryptoName() {
        return cryptoName;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    private double calculateValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return "name: %s | quantity: %.9f | value: %.2f$ | price for %s - %.2f$"
        .formatted(cryptoName, quantity, calculateValue(), purchaseDate.format(dateTimeFormatter), price);
    }
}
