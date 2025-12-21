import java.util.*;

/* Task-8: Pizza
 * Tryggvi, the owner of Mahjong Pizza, and Ómar, the owner of Trapizza,
 * are nemeses. They always run into each other downtown and their
 * conversations always end up in heated arguments, more often than not
 * related to their pizzas. Tryggvi says that trapezoid shaped pizzas
 * are immoral and society should not allow them to be sold in the
 * country. Ómar says that round pizzas are a thing of the past and the
 * future is all about having more creative shaped objects.
 *
 * Their last argument ended up with a comparison of whose pizza was
 * bigger, since the prize of the pizzas are the same. They cannot
 * figure out which one has the bigger pizza, they are no mathematicians
 * after all, so they are asking for your help. Tryggvi tells you the
 * diameter of the pizzas he sells and Ómar tells you both sides and
 * height of the pizzas he sells.
 *
 * The input consists of four lines. The first line consists of a
 * single integer 0 < d <= 100, the diameter of Mahjong’s pizza. The
 * second line consists of a single integer 0 < a <= 100, the length
 * of one of the parallel sides of Trapizza’s pizza. The third line
 * consists of a single integer 0 < b <= 100, the length of the other
 * parallel side of Trapizza’s pizza. The fourth line consists of a
 * single integer 0 < h <= 100, the height of Trapizza’s pizza.
 *
 * If Trapizza has the bigger pizza, return the String "Trapizza!".
 * If Mahjong has the bigger pizza, return the String "Mahjong!". If
 * the pizzas are the same size, output "They are the same". Include
 * a meaningful toString of the Pizza as well to indicate the result
 * of the comparison.
 *
 * In addition, we also need to compare the size of different orders
 * of pizza. In this case, the input will specify multiple pizzas as
 * above. The final line of output should specify whether Trapizza
 * or Mahjong has a largest total order size. All input is provided as
 * a String.
 */

import java.util.ArrayList;
import java.util.List;

// Abstract base class
abstract class Pizza {
    public abstract double getArea();
    public abstract String toString();
}

// Mahjong pizza (circular)
class MahjongPizza extends Pizza {
    private int diameter;

    public MahjongPizza(int diameter) {
        if (diameter <= 0 || diameter > 100) {
            throw new IllegalArgumentException("Invalid diameter");
        }
        this.diameter = diameter;
    }

    @Override
    public double getArea() {
        double radius = diameter / 2.0;
        return Math.PI * radius * radius;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public String toString() {
        return "MahjongPizza (diameter=" + diameter + ", area=" + getArea() + ")";
    }
}

// Trapizza pizza (trapezoid)
class Trapizza extends Pizza {
    private int sideA;
    private int sideB;
    private int height;

    public Trapizza(int a, int b, int h) {
        if (a <= 0 || b <= 0 || h <= 0 || a > 100 || b > 100 || h > 100) {
            throw new IllegalArgumentException("Invalid trapezoid dimensions");
        }
        this.sideA = a;
        this.sideB = b;
        this.height = h;
    }

    @Override
    public double getArea() {
        return ((sideA + sideB) / 2.0) * height;
    }

    public int getSideA() {
        return sideA;
    }

    public int getSideB() {
        return sideB;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Trapizza (a=" + sideA + ", b=" + sideB + ", h=" + height + ", area=" + getArea() + ")";
    }
}

// Represents a single order comparison
class PizzaOrder {
    private MahjongPizza mahjongPizza;
    private Trapizza trapizza;
    private String result;

    public PizzaOrder(MahjongPizza m, Trapizza t) {
        this.mahjongPizza = m;
        this.trapizza = t;
        this.result = compare();
    }

    private String compare() {
        double mArea = mahjongPizza.getArea();
        double tArea = trapizza.getArea();

        if (Math.abs(mArea - tArea) < 1e-6) {
            return "They are the same";
        } else if (mArea > tArea) {
            return "Mahjong!";
        } else {
            return "Trapizza!";
        }
    }

    public MahjongPizza getMahjongPizza() {
        return mahjongPizza;
    }

    public Trapizza getTrapizza() {
        return trapizza;
    }

    public String getResult() {
        return result;
    }
    // added to support test
    public double getMahjongPizzaArea() {
        return mahjongPizza.getArea();
    }
    // added to support test
    public double getTrapizzaArea() {
        return trapizza.getArea();
    }

    @Override
    public String toString() {
        return mahjongPizza.toString() + " vs " + trapizza.toString() + " => " + result;
    }
}

// Manages multiple orders
class OrderManager {
    private List<PizzaOrder> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(MahjongPizza m, Trapizza t) {
        orders.add(new PizzaOrder(m, t));
    }

    // added to support test
    public List<PizzaOrder> getOrders() {
        return orders;
    }

    public void printResults() {
        double totalMahjong = 0;
        double totalTrapizza = 0;

        for (PizzaOrder order : orders) {
            System.out.println(order.toString());
            totalMahjong += order.getMahjongPizza().getArea();
            totalTrapizza += order.getTrapizza().getArea();
        }

        if (Math.abs(totalMahjong - totalTrapizza) < 1e-6) {
            System.out.println("Overall: They are the same");
        } else if (totalMahjong > totalTrapizza) {
            System.out.println("Overall: Mahjong!");
        } else {
            System.out.println("Overall: Trapizza!");
        }
    }
}

// Main class only to demonstrate usage
// it does not need testing
class PizzaMain {
    public static void main(String[] args) {
        String input = "2\n10\n8\n5\n10\n4\n6\n8\n12";
        String[] lines = input.split("\n");
        int n = Integer.parseInt(lines[0]);

        if (lines.length != 1 + (n * 4)) {
            throw new IllegalArgumentException("Input format error: expected " + (1 + n * 4) + " lines but got " + lines.length);
        }

        OrderManager manager = new OrderManager();
        int index = 1;
        for (int i = 0; i < n; i++) {
            int d = Integer.parseInt(lines[index++]);
            int a = Integer.parseInt(lines[index++]);
            int b = Integer.parseInt(lines[index++]);
            int h = Integer.parseInt(lines[index++]);

            MahjongPizza m = new MahjongPizza(d);
            Trapizza t = new Trapizza(a, b, h);
            manager.addOrder(m, t);
        }

        manager.printResults();
    }
}

