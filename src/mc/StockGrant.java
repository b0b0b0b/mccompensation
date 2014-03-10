package mc;

class StockGrant {
  final double totalShares;
  double vestedShares = 0;
  final int cliff, length;
  int age = 0;

  public StockGrant(double totalShares, int cliff,int length) {
    this.totalShares = totalShares;
    this.cliff = cliff;
    this.length = length;
  }

  public void advanceQuarter() {
    age++;
    if (age < cliff) {
      vestedShares = 0;
    } else if (age == cliff) {
      vestedShares = cliff * totalShares / length;
    } else if (age < length) {
      vestedShares = totalShares / length;
    } else {
      vestedShares = 0;
    }
  }
}