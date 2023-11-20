import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade implements Serializable {
    private final String coinName;
    private final LocalDateTime purchaseDate;
    private final double price;
    private final double quantity;

    public Trade(String coinName, double price, double quantity) {
        this.coinName = coinName;
        this.price = price;
        this.purchaseDate = LocalDateTime.now();
        this.quantity = quantity;
    }

    public String getCoinName() {
        return coinName;
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "name: %s | quantity: %.9f | value: %.2f$ | price for 1 %s at %s -> %.2f$"
        .formatted(coinName, quantity, calculateValue(), coinName, purchaseDate.format(dateTimeFormatter), price);
    }
}