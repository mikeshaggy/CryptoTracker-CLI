public class App {
    private final UserInterface ui;
    private Portfolio selectedPortfolio;

    public App() {
        ui = new UserInterface();
    }

    private void mainMenu() {
        boolean exit = false;
        do {
            ui.printMainMenu();
            String userChoice = ui.getUserInput();
            switch (userChoice) {
                case "1" -> {
                    ui.printPortfoliosList();
                }
                case "2" -> {
                    selectedPortfolio = ui.portfolioSelecting();
                    if (selectedPortfolio != null) {
                        exit = true;
                        portfolioMenu();
                    }
                }
                case "3" ->  {
                    ui.creatingNewPortfolio();
                }
                case "4" -> {
                    ui.deletingPortfolio();
                    selectedPortfolio = null;
                }
                case "5" -> {
                    System.out.println("Closing app...");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again");
            }
        } while (!exit);
    }

    private void portfolioMenu() {
        boolean exit = false;
        do {
            ui.printLineSeparator();
            System.out.println("Currently selected portfolio -> " + selectedPortfolio.getName());
            ui.printPortfolioMenu();
            String userChoice = ui.getUserInput();
            switch (userChoice) {
                case "1" -> {
                    Trade tradeToAdd = ui.getTradeFromUser();
                    if (tradeToAdd != null) {
                        selectedPortfolio.addTrade(tradeToAdd);
                    }
                    ui.updatePortfolio(selectedPortfolio);
                }
                case "2" -> {
                    ui.deleteTrade(selectedPortfolio);
                }
                case "3" -> selectedPortfolio.printTradeList();
                case "4" -> selectedPortfolio.getDetails();
                case "5" -> {
                    selectedPortfolio = null;
                    exit = true;
                    mainMenu();
                }
                case "6" -> {
                    System.out.println("Closing app...");
                    exit = true;
                }
                default -> {
                    System.out.println("Invalid option. Please try again");
                    ui.printLineSeparator();
                }
            }
        } while (!exit);
    }

    public void launch() {
        ui.printTitle();
        mainMenu();
    }
}