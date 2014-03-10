package mc;

import java.util.Random;

// sample compensation package
class Sample1 implements Comp {
  @Override public Drawer extraStock() {
    return new WeightedChoiceBuilder()
    .choice( 1 , Main.range(0, 0 )) // no stock
    .choice( 1 , Main.range(+.05, +.10 )) // 5-10% extra
    .build();
  }
  @Override public StockGrant extraStockGrant(double currentSharePrice, Random r, Drawer d) {
    // 1 year cliff, 4 years overall
    return new StockGrant(1000 * d.draw(r), 4, 16);
  }
  @Override public Drawer cashBonuses() {
    return new WeightedChoiceBuilder()
    .choice( 1, Main.range(0, 0 )) // no bonus
    .choice( 1, Main.range(+.05, +.10 )) // 5-10%
    .build();
  }
  @Override public double base() { return 65000; }
  @Override public double signing() { return 2500; }
  @Override public double price() { return 10; }
  @Override public Drawer stockForecast() {
    return StockPatterns.RIDICULOUS_SUCCESS;
  }
  @Override public Drawer raise() {
    return new WeightedChoiceBuilder()
    .choice(4, Main.range(.00, .02)) // 0-2% raise
    .choice(2, Main.range(.02, .03)) // 2-3% raise
    .choice(1, Main.range(.03, .05)) // 3-5% raise
    .build();
  }
  @Override public StockGrant initialGrant() {
    return new StockGrant(1000, 4, 16);
  }
}