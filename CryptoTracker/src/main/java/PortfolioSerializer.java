import java.io.*;

public class PortfolioSerializer {
    public static void serializePortfolio(Portfolio portfolio, String portfolioName) throws Exception {
        FileOutputStream file = new FileOutputStream(String.format("src/data/%s", portfolioName));
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(portfolio);

        out.close();
        file.close();
    }

    public static Portfolio deserializePortfolio(String portfolioName) throws Exception {
        FileInputStream file = new FileInputStream(String.format("src/data/%s", portfolioName));
        ObjectInputStream in = new ObjectInputStream(file);

        Portfolio portfolio = (Portfolio) in.readObject();

        return portfolio;
    }
}
