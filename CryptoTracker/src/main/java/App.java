import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

// selecting and deleting portfolios from a list, not by name
// printing current portfolio content with its value

public class App {
    private Portfolio selectedPortfolio;
    private final int LINE_WIDTH = 60;
    static Scanner sc = new Scanner(System.in);

    private void setSelectedPortfolio(Portfolio selectedPortfolio) {
        this.selectedPortfolio = selectedPortfolio;
    }

    private void printLineSeparator() {
        System.out.println("-".repeat(LINE_WIDTH));
    }

    private void printTitle() {
        String title = "Crypto Tracker App";
        int titlePadding = (LINE_WIDTH - title.length()) / 2;
        System.out.println(" ".repeat(titlePadding) + title);
        printLineSeparator();
    }

    private void printMainMenu() {
        System.out.println("1. Select portfolio");
        System.out.println("2. Create new portfolio");
        System.out.println("3. Delete portfolio");
        System.out.println("4. Show portfolios");
        System.out.println("5. Exit");
    }

    private String getUserInput() {
        String userInput;
        System.out.print(">>> ");
        userInput = sc.nextLine();

        return userInput;
    }

    private void mainMenu() {
        printMainMenu();
        String userChoice = getUserInput();
        switch (userChoice) {
            case "1" -> {
                System.out.print("Enter portfolio name: ");
                String portfolioName = sc.nextLine();
                setSelectedPortfolio(loadPortfolio(portfolioName));
            }
            case "2" -> {
                System.out.print("Enter portfolio name: ");
                String portfolioName = sc.nextLine();
                setSelectedPortfolio(createNewPortfolio(portfolioName));
            }
            case "3" -> deletePortfolio();
            case "4" -> printListOfPortfolios();
            case "5" -> {
                System.out.println("Closing app...");
                System.exit(0);
            }
            default -> System.out.println("Error");
        }
        portfolioMenu();
    }

    private Portfolio loadPortfolio(String portfolioName) {
        Portfolio portfolio = null;
        try {
            portfolio = PortfolioSerializer.deserializePortfolio(portfolioName);
            System.out.printf("Successfully loaded portfolio \"%s\"%n", portfolioName);
            setSelectedPortfolio(portfolio);
            printLineSeparator();
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to load portfolio \"%s\". Please try again%n", portfolioName);
            printLineSeparator();
            mainMenu();
        }

        return portfolio;
    }

    private boolean doesPortfolioExist(String portfolioName) {
        File directory = new File("src/data");
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(portfolioName)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean shouldPortfolioGetOverwritten(String portfolioName) {
        System.out.printf("Portfolio \"%s\" already exists. Do you want to overwrite it? [y/n]%n", portfolioName);
        String userChoice = getUserInput();
        boolean result;
        switch (userChoice) {
            case "y" -> result = true;
            case "n" -> result = false;
            default -> {
                System.out.println("Invalid option");
                result = false;
            }
        }

        return result;
    }

    private Portfolio createNewPortfolio(String portfolioName) {
        if (doesPortfolioExist(portfolioName)) {
            if (!shouldPortfolioGetOverwritten(portfolioName)) {
                mainMenu();
            }
        }
        Portfolio portfolio = new Portfolio(portfolioName);
        try {
            PortfolioSerializer.serializePortfolio(portfolio, portfolioName);
            System.out.printf("Successfully created new portfolio \"%s\"%n", portfolioName);
            System.out.println("-".repeat(LINE_WIDTH));
            setSelectedPortfolio(portfolio);
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to create new portfolio \"%s\"%n", portfolioName);
            mainMenu();
        }

        return portfolio;
    }

    private void deletePortfolio() {
        System.out.println("Enter name of portfolio to delete");
        String portfolioName = getUserInput();
        Path filePath = Paths.get("src/data").resolve(portfolioName);
        try {
            Files.delete(filePath);
            System.out.println("Portfolio deleted successfully");
            selectedPortfolio = null;
            mainMenu();
        } catch (NoSuchFileException e) {
            System.out.printf("Portfolio %s not found", portfolioName);
            portfolioMenu();
        } catch (IOException e) {
            System.out.println("Something went wrong");
            portfolioMenu();
        }
    }

    private void printListOfPortfolios() {
        File directory = new File("src/data");
        File[] files = directory.listFiles();

        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            System.out.printf("%d. %s%n", i+1, files[i].getName());
        }

        printLineSeparator();
        mainMenu();
    }

    private void printManageOptions() {
        System.out.println("1. Add new trade");
        System.out.println("2. Show all trades");
        System.out.println("3. Show portfolio content");
        System.out.println("4. Delete portfolio");
        System.out.println("5. Main menu");
        System.out.println("6. Exit");
    }

    private void portfolioMenu() {
        System.out.println("Currently selected portfolio - " + selectedPortfolio.getName());
        printManageOptions();
        String userChoice = getUserInput();
        switch (userChoice) {
            case "1" -> getTradeFromUser();
            case "2" -> selectedPortfolio.printTradeList();
            case "3" -> System.out.println("unimplemented");
            case "4" -> deletePortfolio();
            case "5" -> {
                selectedPortfolio = null;
                mainMenu();
            }
            case "6" -> {
                System.out.println("Closing app...");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid option. Please try again");
                printLineSeparator();
                portfolioMenu();
            }
        }
        portfolioMenu();
    }

    private void getTradeFromUser() {
        System.out.println("Enter coin name");
        String coinName = getUserInput().toUpperCase();
        try {
            double price = CoinAPI.getCoinExchangeRate(coinName);
            System.out.println("Enter amount of coin you bought");
            double coinQty = Double.parseDouble(getUserInput());
            Trade tradeToAdd = new Trade(coinName, price, coinQty);
            System.out.printf("""
                    Trade you're about to add:
                    Coin name: %s
                    Coin quantity: %.9f
                    1. Accept
                    2. Cancel
                    """, tradeToAdd.getCryptoName(), tradeToAdd.getQuantity());
            String userConfirmation = getUserInput();
            if (userConfirmation.equals("1")) {
                selectedPortfolio.addTrade(tradeToAdd);
                PortfolioSerializer.serializePortfolio(selectedPortfolio, selectedPortfolio.getName());
                System.out.println("Trade added successfully");
            } else {
                portfolioMenu();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again");
            getTradeFromUser();
        }
    }

    public void launch() {
        printTitle();
        mainMenu();
    }
}