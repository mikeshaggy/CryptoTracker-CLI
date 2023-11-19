// TODO printing current portfolio content with its value

public class App {
    private final UserInterface ui;
    private final PortfolioManager portfolioManager;

    public App() {
        ui = new UserInterface();
        portfolioManager = new PortfolioManager();
    }

    private void mainMenu() {
        ui.printMainMenu();
        String userChoice = ui.getUserInput();
        switch (userChoice) {
            case "1" -> {
                ui.printPortfoliosList();
                mainMenu();
            }
            case "2" -> {
                Portfolio selectedPortfolio = ui.portfolioSelecting();
                portfolioManager.setSelectedPortfolio(selectedPortfolio);
                portfolioMenu();
            }
            case "3" ->  {
                ui.creatingNewPortfolio();
                mainMenu();
            }
            case "4" -> {
                ui.deletingPortfolio();
                portfolioManager.setSelectedPortfolio(null);
                mainMenu();
            }
            case "5" -> {
                System.out.println("Closing app...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again");
        }
    }

    private void portfolioMenu() {
        Portfolio selectedPortfolio = portfolioManager.getSelectedPortfolio();
        System.out.println("Currently selected portfolio -> " + portfolioManager.getSelectedPortfolio().getName());
        ui.printPortfolioMenu();
        String userChoice = ui.getUserInput();
        switch (userChoice) {
            case "1" -> {
                System.out.println("Adding new trade not implemented yet");
                portfolioMenu();
            }
            case "2" -> selectedPortfolio.printTradeList();
            case "3" -> {
                System.out.println("unimplemented");
                portfolioMenu();
            }
            case "4" -> {
                portfolioManager.setSelectedPortfolio(null);
                mainMenu();
            }
            case "5" -> {
                System.out.println("Closing app...");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid option. Please try again");
                ui.printLineSeparator();
                portfolioMenu();
            }
        }
    }

//    private void getTradeFromUser() {
//        System.out.println("Enter coin name");
//        String coinName = getUserInput().toUpperCase();
//        try {
//            double price = CoinAPI.getCoinExchangeRate(coinName);
//            System.out.println("Enter amount of coin you bought");
//            double coinQty = Double.parseDouble(getUserInput());
//            Trade tradeToAdd = new Trade(coinName, price, coinQty);
//            System.out.printf("""
//                    Trade you're about to add:
//                    Coin name: %s
//                    Coin quantity: %.9f
//                    1. Accept
//                    2. Cancel
//                    """, tradeToAdd.getCryptoName(), tradeToAdd.getQuantity());
//            String userConfirmation = getUserInput();
//            if (userConfirmation.equals("1")) {
//                selectedPortfolio.addTrade(tradeToAdd);
//                PortfolioSerializer.serializePortfolio(selectedPortfolio, selectedPortfolio.getName());
//                System.out.println("Trade added successfully");
//            } else {
//                portfolioMenu();
//            }
//        } catch (Exception e) {
//            System.out.println("Something went wrong. Please try again");
//            getTradeFromUser();
//        }
//    }

    public void launch() {
        ui.printTitle();
        mainMenu();
    }
}