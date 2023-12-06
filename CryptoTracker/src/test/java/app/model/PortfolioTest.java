package app.model;

import app.CoinAPI;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
class PortfolioTest {
    private CoinAPI coinAPIMock = Mockito.mock(CoinAPI.class);
    private final Portfolio portfolio = new Portfolio("test", coinAPIMock);

    @Test
    void shouldAddTrade() {
        // GIVEN
        String coinName = "BTC";
        double coinPrice = 50000;
        double coinQuantity = 0.0025;
        Trade trade = new Trade(coinName, coinPrice, coinQuantity);
        // WHEN
        portfolio.addTrade(trade);
        // THEN
        assertThat(portfolio.getTrades().size()).isEqualTo(1);
        assertThat(portfolio.getTrades()).contains(trade);
        assertThat(portfolio.getContent().get(coinName)).isEqualTo(coinQuantity);
    }

    @Test
    void shouldGetValue() {
        // GIVEN
        String coinName1 = "BTC";
        double coinPrice1 = 50000.0;
        double coinQuantity1 = 0.0025;
        Trade trade1 = new Trade(coinName1, coinPrice1, coinQuantity1);
        portfolio.addTrade(trade1);
        String coinName2 = "ETH";
        double coinPrice2 = 3000.0;
        double coinQuantity2 = 0.05;
        Trade trade2 = new Trade(coinName2, coinPrice2, coinQuantity2);
        portfolio.addTrade(trade2);
        // WHEN
        Mockito.when(portfolio.getCoinAPI().getCoinExchangeRate("BTC")).thenReturn(45000.0);
        Mockito.when(portfolio.getCoinAPI().getCoinExchangeRate("ETH")).thenReturn(2500.0);
        double portfolioValue = portfolio.getValue();
        // THEN
        assertThat(portfolioValue).isEqualTo(45000.0 * 0.0025 + 2500.0 * 0.05);
    }

    @Test
    void shouldGetTradeById() {
        // GIVEN
        String coinName = "BTC";
        double coinPrice = 50000;
        double coinQuantity = 0.0025;
        Trade trade = new Trade(coinName, coinPrice, coinQuantity);
        portfolio.addTrade(trade);
        // WHEN
        Trade retrievedTrade = portfolio.getTradeById(1);
        // THEN
        assertThat(retrievedTrade).isEqualTo(trade);
    }
}