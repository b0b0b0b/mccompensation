package mc;

import java.util.Random;

class UniformRange implements Drawer {
  private final double min, max;

  public UniformRange(double min, double max) {
    this.min = min;
    this.max = max;
    if (max < min) throw new IllegalArgumentException(max+" " + min);
  }

  @Override
  public double draw(Random r) {
    if (max == min) return max;
    double val = min + (max - min) * r.nextDouble();
    return val;
  }
}