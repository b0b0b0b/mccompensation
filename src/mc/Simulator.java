package mc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

class Simulator {
  private final Comp comp;
  private final double signing;
  private final Drawer cashBonuses, stockForecaster, extraGrants, raise;

  Simulator(Comp comp) {
    this.comp = comp;
    cashBonuses = comp.cashBonuses();
    stockForecaster = comp.stockForecast();
    extraGrants = comp.extraStock();
    raise = comp.raise();
    signing = comp.signing();
  }

  class State {
    double base = comp.base();
    double total = 0;
    int q = 0;
    double sharePrice = comp.price();
    double annualTotal = 0;
    double income = 0;
    private final Random r;
    private final StockGrants stock = new StockGrants();
    State(Random r) {
      this.r = r;
    }
    void tick() throws Exception {
      if (q % 4 == 0) {
        annualTotal = 0;
      }
      q++;
      stock.advanceQuarter();
      sharePrice *= (1 + stockForecaster.draw(r));
      double stockValue = sharePrice * stock.shares();
      income = base/4;
      income += stockValue;
      if (q % 4 == 0) {
        double bonusValue = base * cashBonuses.draw(r);
        income += bonusValue;
        base *= (1 + raise.draw(r));
        stock.addGrant(comp.extraStockGrant(sharePrice, r, extraGrants));
      } else  if (q == 1) {
        income += signing;
      }
      annualTotal += income;
      addIncomeToTotal(income);
    }
    private void addIncomeToTotal(double income) {
      if (Double.isNaN(income) || Double.isInfinite(income))
        throw new IllegalArgumentException();

      total += income;
    }
  }

  void run(Random r, final BufferedWriter valueWriter, final BufferedWriter stockWriter, BufferedWriter totalWriter, final BufferedWriter annualWriter) throws IOException, Exception {
    State state = new State(r);

    state.stock.addGrant(comp.initialGrant());

    for (int quarter = 0; quarter < 16; quarter++) {
      state.tick();

      int y = quarter/4;
      String yq = "y"+y+" q"+(1+(quarter%4));
      Main.emit(valueWriter, comp, state.income, yq);
      Main.emit(stockWriter, comp, state.sharePrice, yq);

      if ((quarter+1) % 4 == 0) {
        Main.emit(annualWriter, comp, state.annualTotal, "y"+ y);
      }
    }

    Main.emit(totalWriter, comp, state.total, "total");
  }
}