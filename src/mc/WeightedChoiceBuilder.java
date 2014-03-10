package mc;

import java.util.ArrayList;
import java.util.List;

class WeightedChoiceBuilder {
  private final List<Drawer> l = new ArrayList<>();
  private final List<Double> d = new ArrayList<>();
  static WeightedChoiceBuilder blah() {
    return new WeightedChoiceBuilder();
  }
  WeightedChoiceBuilder choice(double weight, Drawer d) {
    this.d.add(weight);
    this.l.add(d);
    return this;
  }
  WeightedDraw build() {
    Drawer options[] = l.toArray(new Drawer[l.size()]);
    double weights []= new double[d.size()];
    for (int i=0; i<options.length; i++) {
      weights[i] = d.get(i);
    }
    return new WeightedDraw(options, weights);
  }
}