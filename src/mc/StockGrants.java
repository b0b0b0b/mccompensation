package mc;

import java.util.ArrayList;
import java.util.List;

class StockGrants {
  private final List<StockGrant> grants = new ArrayList<>();
  void addGrant(StockGrant g) {
    if (g == null) return;
    grants.add(g);
  }
  public void advanceQuarter() {
    for (StockGrant g : grants) {
      g.advanceQuarter();
    }
  }
  public double shares() {
    double d = 0;
    for (StockGrant g : grants) {
      d += g.vestedShares;
    }
    return d;
  }
}