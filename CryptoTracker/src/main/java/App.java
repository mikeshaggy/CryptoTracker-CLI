import java.io.File;
import java.util.Scanner;

public class App {
    private Portfolio selectedPortfolio;
    private final int LINE_WIDTH = 50;
    static Scanner sc = new Scanner(System.in);

    private void setSelectedPortfolio(Portfolio selectedPortfolio) {
        this.selectedPortfolio = selectedPortfolio;
    }

    private void printLineSeparator() {
        System.out.println("-".repeat(LINE_WIDTH));
    }

    public void printTitle() {
        String title = "Crypto Tracker App";
        int titlePadding = (LINE_WIDTH - title.length()) / 2;
        System.out.println(" ".repeat(titlePadding) + title);
        printLineSeparator();
    }

    public void printMainMenu() {
        System.out.println("1. Select portfolio");
        System.out.println("2. Create new portfolio");
        System.out.println("3. Exit");
    }

    public String getUserInput() {
        String userInput;
        System.out.print(">>> ");
        userInput = sc.nextLine();

        return userInput;
    }

    public void selectPortfolio() {
        printMainMenu();
        String userChoice = getUserInput();
        switch (userChoice) {
            case "1" -> {
                System.out.print("Enter portfolio name: ");
                String portfolioName = sc.nextLine();
                Portfolio selectedPortfolio = loadPortfolio(portfolioName);
                setSelectedPortfolio(selectedPortfolio);
            }
            case "2" -> {
                System.out.print("Enter portfolio name: ");
                String portfolioName = sc.nextLine();
                Portfolio selectedPortfolio = createNewPortfolio(portfolioName);
                setSelectedPortfolio(selectedPortfolio);
            }
            case "3" -> {
                System.out.println("Closing app...");
                System.exit(0);
            }

            default -> System.out.println("Error");
        }
    }

    public Portfolio loadPortfolio(String portfolioName) {
        Portfolio portfolio = null;
        try {
            portfolio = PortfolioSerializer.deserializePortfolio(portfolioName);
            System.out.printf("Successfully loaded portfolio \"%s\"%n", portfolioName);
            printLineSeparator();
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to load portfolio \"%s\"%n", portfolioName);
            selectPortfolio();
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

    public Portfolio createNewPortfolio(String portfolioName) {
        if (doesPortfolioExist(portfolioName)) {
            if (!shouldPortfolioGetOverwritten(portfolioName)) {
                selectPortfolio();
            }
        }
        Portfolio portfolio = new Portfolio(portfolioName);
        try {
            PortfolioSerializer.serializePortfolio(portfolio, portfolioName);
            System.out.printf("Successfully created new portfolio \"%s\"%n", portfolioName);
            System.out.println("-".repeat(LINE_WIDTH));
        } catch (Exception e) {
            System.out.printf("ERROR: Failed to create new portfolio \"%s\"%n", portfolioName);
            selectPortfolio();
        }

        return portfolio;
    }

    public void printManageOptions() {
        System.out.println("1. Add new trade");
        System.out.println("2. Show all trades");
        System.out.println("3. Show portfolio content");
        System.out.println("4. Delete portfolio");
        System.out.println("5. Main menu");
    }

    public void managePortfolio() {
        System.out.println("Currently selected portfolio - " + selectedPortfolio.getName());
        printManageOptions();
        String userChoice = getUserInput();
    }

    public void launch() {
        printTitle();
        selectPortfolio();
        managePortfolio();
    }
}