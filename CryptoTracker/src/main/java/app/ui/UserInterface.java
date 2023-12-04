package app.ui;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import app.FileManager;
import app.model.*;

public class UserInterface {
    private final int LINE_WIDTH = 60;
    static final Scanner sc = new Scanner(System.in);
    private final FileManager fileManager;

    public UserInterface() {
        this.fileManager = new FileManager();
    }

    public void printLineSeparator() {
        System.out.println("-".repeat(LINE_WIDTH));
    }

    public void printTitle() {
        String title = "Crypto Tracker App";
        int titlePadding = (LINE_WIDTH - title.length()) / 2;
        System.out.println(" ".repeat(titlePadding) + title);
        printLineSeparator();
    }

    public void printMainMenu() {
        System.out.println("1. Show my portfolios");
        System.out.println("2. Manage portfolio");
        System.out.println("3. Create new portfolio");
        System.out.println("4. Delete portfolio");
        System.out.println("5. Exit");
    }

    public String getUserInput() {
        String userInput;
        System.out.print(">>> ");
        userInput = sc.nextLine();

        return userInput;
    }

    public void printPortfoliosList() {
        File[] portfolios = fileManager.getPortfolios();

        if (portfolios != null && portfolios.length > 0) {
            for (int i = 0; i < portfolios.length; i++) {
                System.out.printf("%d. %s%n", i+1, portfolios[i].getName());
            }
        } else {
            System.out.println("There are no portfolios available");
        }

        printLineSeparator();
    }

    public Portfolio portfolioSelecting() {
        Portfolio selectedPortfolio = null;
        System.out.println("Enter portfolio name");
        String portfolioName = getUserInput();
        try {
            selectedPortfolio = fileManager.deserializePortfolio(portfolioName);
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to load portfolio \"%s\"%n", portfolioName);
        }

        return selectedPortfolio;
    }

    public boolean confirmOverwritingPortfolio(String portfolioName) {
        boolean result;

        System.out.printf("Portfolio \"%s\" already exists. Do you want to overwrite it? [y/n]%n", portfolioName);
        char userChoice = getUserInput().toLowerCase().charAt(0);

        switch (userChoice) {
            case 'y' -> result = true;
            case 'n' -> result = false;
            default -> {
                System.out.println("Invalid option");
                result = false;
            }
        }

        return result;
    }

    public void creatingNewPortfolio() {
        System.out.println("Enter portfolio name");
        String portfolioName = getUserInput();
        try {
            if (fileManager.portfolioExists(portfolioName)) {
                if (!confirmOverwritingPortfolio(portfolioName)) return;
            }
            Portfolio portfolio = new Portfolio(portfolioName);
            fileManager.serializePortfolio(portfolio);
            System.out.printf("Successfully created new portfolio \"%s\"%n", portfolioName);
            printLineSeparator();
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to create new portfolio \"%s\"%n", portfolioName);
        }
    }

    public void deletingPortfolio() {
        System.out.println("Enter portfolio name");
        String portfolioName = getUserInput();
        System.out.printf("Are you sure you want to delete \"%s\"?%nOnce deleted, it cannot be recovered!%nEnter [%s] to confirm %n", portfolioName, portfolioName);
        String userConfirmation = getUserInput();
        if (!userConfirmation.equals(portfolioName)) return;
        try {
            fileManager.deletePortfolio(portfolioName);
            System.out.println("Portfolio deleted successfully");
        } catch (NoSuchFileException e) {
            System.out.printf("ERROR: Portfolio \"%s\" not found%n", portfolioName);
        } catch (Exception e) {
            System.out.println("ERROR: Something went wrong");
        }
    }

    public void printPortfolioMenu() {
        System.out.println("1. Add new trade");
        System.out.println("2. Delete trade");
        System.out.println("3. Show all trades");
        System.out.println("4. Show portfolio details");
        System.out.println("5. Main menu");
        System.out.println("6. Exit");
    }

    public Trade getTradeFromUser() {
        Trade userTrade = null;
        System.out.println("Enter coin name");
        String coinName = getUserInput().toUpperCase();
        try {
            System.out.println("Enter amount of coin you bought");
            double coinQuantity = Double.parseDouble(getUserInput());
            System.out.println("Enter current price of the coin you bought");
            double coinPrice = Double.parseDouble(getUserInput());
            System.out.printf("""
                    Trade you're about to add:
                    Coin name: %s
                    Coin quantity: %.9f
                    Coin price: %.2f
                    1. Accept
                    2. Cancel
                    """, coinName, coinQuantity, coinPrice);
            String userConfirmation = getUserInput();
            if (userConfirmation.equals("1")) {
                userTrade = new Trade(coinName, coinPrice, coinQuantity);
                System.out.println("Trade added successfully");
            } else {
                System.out.println("Trade has not been added");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        return userTrade;
    }

    public void deleteTrade(Portfolio portfolio) {
        System.out.println("Enter trade ID");
        try {
            int id = Integer.parseInt(getUserInput());
            Trade tradeToDelete = portfolio.getTradeById(id);
            System.out.printf("""
                    Trade you're about to delete:
                    %s
                    Once deleted, it cannot be recovered!
                    Enter [yes] to confirm
                    """, tradeToDelete.toString());
            String userConfirmation = getUserInput();
            if (userConfirmation.equals("yes")) {
                portfolio.deleteTrade(tradeToDelete);
                System.out.println("Trade has been deleted");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Wrong trade ID");
        }
    }

    public void updatePortfolio(Portfolio portfolio) {
        try {
            fileManager.serializePortfolio(portfolio);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
