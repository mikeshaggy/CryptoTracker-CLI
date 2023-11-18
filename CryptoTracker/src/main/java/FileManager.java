import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String DATA_PATH = "CryptoTracker/src/data";
    public File[] getPortfolios() {
        File directory = new File(DATA_PATH);

        return directory.listFiles();
    }

    public void serializePortfolio(Portfolio portfolioToSerialize) throws Exception {
        String portfolioName = portfolioToSerialize.getName();

        FileOutputStream file = new FileOutputStream(String.format("%s/%s", DATA_PATH, portfolioName));
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(portfolioToSerialize);

        out.close();
        file.close();
    }

    public Portfolio deserializePortfolio(String portfolioName) throws Exception {
        FileInputStream file = new FileInputStream(String.format("%s/%s", DATA_PATH, portfolioName));
        ObjectInputStream in = new ObjectInputStream(file);

        return (Portfolio) in.readObject();
    }

    public boolean portfolioExists(String portfolioName) {
        File[] portfolios = getPortfolios();

        for (File portfolio : portfolios) {
            if (portfolio.getName().equals(portfolioName)) {
                return true;
            }
        }

        return false;
    }

    public void deletePortfolio(String portfolioName) throws Exception {
        Path filePath = Paths.get(DATA_PATH).resolve(portfolioName);
        Files.delete(filePath);
    }
}
