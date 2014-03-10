package mc;

import java.util.Random;

class WeightedDraw implements Drawer {
  private final Drawer[] options;
  private final double[] weights;
  WeightedDraw(Drawer[] options, double[] weights) {
    this.options = options;
    this.weights = weights;
  }

  @Override
  public double draw(Random r) {
    double total = 0;
    for (double d : weights) {
      total += d;
    }

    double bonusdraw = r.nextDouble() * total;
    double ax = 0;
    for (int i=0; i<options.length; i++) {
      if (bonusdraw >= ax && bonusdraw < ax + weights[i]) return options[i].draw(r);
      ax += weights[i];
    }
    throw new IllegalArgumentException(bonusdraw+" " + ax);
  }
}