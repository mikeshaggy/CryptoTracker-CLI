import java.io.*;

public class CryptoWalletSerializer {
    public static void serializeWallet(CryptoWallet wallet, String filepath) {
        try {
            FileOutputStream file = new FileOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(wallet);

            out.close();
            file.close();

            System.out.println("Wallet has been serialized");
        } catch (IOException e) {
            System.out.println("Caught IOException");
            e.printStackTrace();
        }
    }

    public static CryptoWallet deserializeWallet(String filepath) {
        CryptoWallet wallet = null;
        try {
            FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream in = new ObjectInputStream(file);

            wallet = (CryptoWallet) in.readObject();
        } catch (IOException e) {
            System.out.println("Caught IOException");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Caught ClassNotFoundException");
            e.printStackTrace();
        }

        return wallet;
    }
}
