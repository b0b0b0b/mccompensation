package mc;

import java.io.*;
import java.util.*;

public class Main {
  public static UniformRange range(double min, double max) {
    return new UniformRange(min, max);
  }
  static double q(double d) {
    if (d<0) {
      return -q(-d);
    } else {
      return Math.pow(1 + d, .25) - 1;
    }
  }
  public static UniformRange range4(double min, double max) {
    return new UniformRange(q(min), q(max));
  }

  public static void main(String[] args) throws Exception {
    String HOME = System.getProperty("user.home");
    int draws = 3000;

    List<Comp> comps = new ArrayList<>();
    for (String s : args) {
      Class<Comp> cls = (Class<Comp>) Class.forName(s);
      comps.add(cls.newInstance());
    }

    Random r = new Random();

    try (BufferedWriter stockWriter = new BufferedWriter(new FileWriter(HOME + "monte-carlo-stock.txt"))) {
      try (BufferedWriter valueWriter = new BufferedWriter(new FileWriter(HOME + "monte-carlo.txt"))) {
        try (BufferedWriter totalWriter = new BufferedWriter(new FileWriter(HOME + "monte-carlo-total.txt"))) {
          try (BufferedWriter annualWriter = new BufferedWriter(new FileWriter(HOME + "monte-carlo-annual.txt"))) {
            valueWriter.write("who\twhen\tvalue\n");
            totalWriter.write("who\twhen\tvalue\n");
            stockWriter.write("who\twhen\tvalue\n");
            annualWriter.write("who\twhen\tvalue\n");
            for (Comp comp : comps) {
              Simulator simulation = new Simulator(comp);
              for (int i=0; i<draws; i++) {
                simulation.run(r, valueWriter, stockWriter, totalWriter, annualWriter);
              }
            }
          }
        }
      }
    }
  }
  static void emit(BufferedWriter w, Comp comp, double value, String str) throws IOException, Exception {
    w.write(comp.getClass().getSimpleName() + "\t"+str+"\t");
    emit(w, value);
    w.write("\n");
  }
  static void emit(BufferedWriter w, double value) throws Exception {
    w.write(String.format("%.2f", value));
  }
}
