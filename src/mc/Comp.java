package mc;

import java.util.Random;

interface Comp {
  Drawer extraStock();
  StockGrant extraStockGrant(double currentSharePrice, Random r, Drawer d);
  Drawer cashBonuses();
  double base();
  double price();
  double signing();
  Drawer stockForecast();
  Drawer raise();
  StockGrant initialGrant();
}