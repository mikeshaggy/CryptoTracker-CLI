// TODO printing current portfolio content with its value

public class App {
    private final UserInterface ui;
    private Portfolio selectedPortfolio;

    public App() {
        ui = new UserInterface();
    }

    private void mainMenu() {
        boolean exit = false;
        ui.printMainMenu();
        do {
            String userChoice = ui.getUserInput();
            switch (userChoice) {
                case "1" -> {
                    ui.printPortfoliosList();
                    mainMenu();
                }
                case "2" -> {
                    this.selectedPortfolio = ui.portfolioSelecting();
                    portfolioMenu();
                }
                case "3" ->  {
                    ui.creatingNewPortfolio();
                    mainMenu();
                }
                case "4" -> {
                    ui.deletingPortfolio();
                    selectedPortfolio = null;
                    mainMenu();
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
        System.out.println("Currently selected portfolio -> " + selectedPortfolio.getName());
        ui.printPortfolioMenu();
        String userChoice = ui.getUserInput();
        switch (userChoice) {
            case "1" -> {
                Trade tradeToAdd = ui.getTradeFromUser();
                if (tradeToAdd != null) {
                    selectedPortfolio.addTrade(tradeToAdd);
                }
                portfolioMenu();
            }
            case "2" -> System.out.println("Deleting trade not implemented yet");
            case "3" -> selectedPortfolio.printTradeList();
            case "4" -> {
                System.out.println("unimplemented");
                portfolioMenu();
            }
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
                ui.printLineSeparator();
                portfolioMenu();
            }
        }
    }

    public void launch() {
        ui.printTitle();
        mainMenu();
    }
}