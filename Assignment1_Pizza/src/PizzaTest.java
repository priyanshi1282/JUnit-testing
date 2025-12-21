import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class PizzaTest {
//    ***********************************************************************************
//    Mahjong Pizza Test Cases
//    ***********************************************************************************

//    Test correctness of the Mahjong Pizza area(circle) formula.
    @Test
    public void testMahjongAreaFormula() {
        MahjongPizza pizza = new MahjongPizza(10);
        double expected = Math.PI * 25; // radius = 5
        assertEquals(expected, pizza.getArea(), 1e-6, "Area should match πr²");
    }

//    Test ability to work with various integer with accurate result.
    @Test
    public void testMahjongAreaIncreasesWithDiameter() {
        MahjongPizza small = new MahjongPizza(5);
        MahjongPizza big = new MahjongPizza(100);
        assertTrue(small.getArea() < big.getArea(), "Larger diameter => larger area");
    }

//    Check if program is able to identify the invalid diameter input.
    @Test
    public void testMahjongInvalidDiameter() {
        assertThrows(IllegalArgumentException.class, () -> new MahjongPizza(0));
        assertThrows(IllegalArgumentException.class, () -> new MahjongPizza(-34));
        assertThrows(IllegalArgumentException.class, () -> new MahjongPizza(136));
    }

//    Test if toString() is displaying area and diameter of the MahjongPizza's object.
    @Test
    public void testMahjongToStringFormat() {
        MahjongPizza pizza = new MahjongPizza(68);
        String s = pizza.toString();
        assertAll(
                () -> assertTrue(s.contains("diameter=")),
                () -> assertTrue(s.contains("area="))
        );
    }

//    Test the correctness with high precision value for the formula.
    @Test
    public void testMahjongAreaPrecision() {
        MahjongPizza pizza = new MahjongPizza(9);
        double expected = Math.PI * Math.pow(4.5, 2);
        assertEquals(expected, pizza.getArea(), 1e-9, "Precision should be maintained");
    }


//    ***********************************************************************************
//    Trapizza Pizza Test Cases
//    ***********************************************************************************

//    Test correctness of the Trapizza Pizza area(trapezium) formula.
    @Test
    public void testTrapizzaAreaFormula() {
        Trapizza t = new Trapizza(10, 6, 8);
        double expected = ((10 + 6) / 2.0) * 8;
        assertEquals(expected, t.getArea(), 1e-6);
    }

//    Check if program is able to identify the invalid side and height input.
    @Test
    public void testTrapizzaInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new Trapizza(0, 5, 45));
        assertThrows(IllegalArgumentException.class, () -> new Trapizza(5, -4, 5));
        assertThrows(IllegalArgumentException.class, () -> new Trapizza(20, 5, 139));
        assertThrows(IllegalArgumentException.class, () -> new Trapizza(150, 10, 0));
    }

//    Test ability to work with various integer with accurate result.
    @Test
    public void testTrapizzaAreaIncreasesWithSides() {
        Trapizza small = new Trapizza(4, 6, 4);
        Trapizza large = new Trapizza(8, 10, 10);
        assertTrue(small.getArea() < large.getArea());
    }

//    Test if toString() is displaying area and all dimensions of the Trapizza's object.
    @Test
    public void testTrapizzaToStringIncludesDimensions() {
        Trapizza t = new Trapizza(4, 6, 8);
        String out = t.toString();
        assertAll(
                () -> assertTrue(out.contains("a=")),
                () -> assertTrue(out.contains("b=")),
                () -> assertTrue(out.contains("h=")),
                () -> assertTrue(out.contains("area="))
        );
    }

//    This test shows the numerical correctness of the code even if the sides are equal, the formula used by AI holds still.
//    The equal sides of the figure shows that the shape is either rectangle or parallelogram and irrespective of it, code gives correct output.
    @Test
    public void testTrapizzaSymmetricSides() {
        Trapizza t = new Trapizza(10, 10, 5);
        assertEquals(10 * 5, t.getArea(), 1e-6, "Equal sides => rectangular area");
    }


//    ***********************************************************************************
//    Pizza Order Test Cases
//    ***********************************************************************************

//    Testing the correctness of the comparison logic for both pizza's area. Test for Mahjong win.
    @Test
    public void testPizzaOrderMahjongBigger() {
        MahjongPizza m = new MahjongPizza(20);
        Trapizza t = new Trapizza(5, 5, 5);
        assertEquals("Mahjong!", new PizzaOrder(m, t).getResult());
    }

//    Testing the correctness of the comparison logic for both pizza's area. Test for Trapizza win.
    @Test
    public void testPizzaOrderTrapizzaBigger() {
        MahjongPizza m = new MahjongPizza(10);
        Trapizza t = new Trapizza(20, 18, 10);
        assertEquals("Trapizza!", new PizzaOrder(m, t).getResult());
    }

//    Testing for nearly same area for both pizza.
//    Here, area of circle = 5674.50173 and area of trapezia = 5674.5, and the difference is ≈ 0.00173.
//    Yet the test case will fail because the AI has implemented very precise difference.
//    To make the area same we need to pass floating integers, but method's signature takes only integers.
//    Hence, this test will fail always for any integer combination in the range (0,100].
    @Test
    public void testPizzaOrderSameArea() {
        MahjongPizza m = new MahjongPizza(85);
        Trapizza t = new Trapizza(17, 100,97);
        assertEquals("They are the same", new PizzaOrder(m, t).getResult());
    }

//    Testing the toString() method to make sure it prints the result.
//    But in AI code there is no keyword like 'result', so based on the string "=>', test case is made.
    @Test
    public void testPizzaOrderToStringReadable() {
        MahjongPizza m = new MahjongPizza(8);
        Trapizza t = new Trapizza(6, 8, 5);
        String s = new PizzaOrder(m, t).toString();
        assertTrue(s.contains("=>"), "Comparison result should be clear");
    }

//    Testing the correctness of both formula, but with the PizzaOrder object to check the implementation is working good.
    @Test
    public void testPizzaOrderAreasNonNegative() {
        MahjongPizza m = new MahjongPizza(5);
        Trapizza t = new Trapizza(3, 4, 5);
        PizzaOrder o = new PizzaOrder(m, t);
        assertTrue(o.getMahjongPizzaArea() > 0 && o.getTrapizzaArea() > 0);
    }

//    ***********************************************************************************
//    Order Manager Test Cases
//    ***********************************************************************************

//    Testing if the class does the proper work of storing the orders correctly.
//    This is achieved by checking the size of the orders and matching it with no. of orders added.
    @Test
    public void testOrderManagerStoresOrders() {
        OrderManager manager = new OrderManager();
        manager.addOrder(new MahjongPizza(18), new Trapizza(6, 6, 5));
        manager.addOrder(new MahjongPizza(10), new Trapizza(20, 15, 55));
        assertEquals(2, manager.getOrders().size());
    }

//    Testing the result by comparing the total areas of both competitors.
//    Here, add area of Mahjong and Trapizza respectively and then compare which one is greater.
//    In this test case, MMahjong should bw the winner.
    @Test
    public void testOrderManagerTotalComparison() {
        OrderManager manager = new OrderManager();
        manager.addOrder(new MahjongPizza(15), new Trapizza(5, 5, 5));
        manager.addOrder(new MahjongPizza(20), new Trapizza(10, 10, 10));
        double totalM = manager.getOrders().stream().mapToDouble(o -> o.getMahjongPizza().getArea()).sum();
        double totalT = manager.getOrders().stream().mapToDouble(o -> o.getTrapizza().getArea()).sum();
        assertTrue(totalM > totalT, "Total Mahjong area should be greater");
    }

//    Test to check correctness of the function.
//    If side(s) are small, area should be small and vise-versa.
    @Test
    public void testBoundaryValues() {
        MahjongPizza mMin = new MahjongPizza(1);
        MahjongPizza mMax = new MahjongPizza(100);
        Trapizza tMin = new Trapizza(1, 1, 1);
        Trapizza tMax = new Trapizza(100, 100, 100);
        assertTrue(mMin.getArea() < mMax.getArea());
        assertTrue(tMin.getArea() < tMax.getArea());
    }

//    ***********************************************************************************
//    Some Additional Test Cases
//    ***********************************************************************************

//    Test if program can handle large numerical values accurately.
//    Edge cases are 1) d=100 and 2)a=100, b=100, h=100.
//    The largest possible area is 10000, which is obtained from second case mentioned above.
    @Test
    public void testPrecisionForLargeValues() {
        Trapizza t = new Trapizza(100, 100, 100);
        double area = t.getArea();
        assertTrue(area > 9999, "Area should handle large double calculations");
    }

//    Test if AI has handled the invalid input by throwing the detailed error message.
    @Test
    public void testIllegalArgumentMessageContainsKeyword() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Trapizza(-2, -3, -4));
        assertTrue(ex.getMessage().toLowerCase().contains("invalid"));
    }

//    Testing the implementation of oops concept.
//    If properties of one object is changed, it does not affect the other object(s).
    @Test
    public void testOrderManagerIndependence() {
        OrderManager m1 = new OrderManager();
        OrderManager m2 = new OrderManager();
        m1.addOrder(new MahjongPizza(10), new Trapizza(10, 10, 10));
        assertEquals(0, m2.getOrders().size(), "Managers should not share state");
    }

//    Testing the accuracy of the circle area calculation.
//    Idea is to test with identical integers, program should yield same area without any roundoff.
    @Test
    public void testFloatingPointRoundingConsistency() {
        MahjongPizza m1 = new MahjongPizza(9);
        MahjongPizza m2 = new MahjongPizza(9);
        assertEquals(m1.getArea(), m2.getArea(), 1e-9, "Identical pizzas should yield same area");
    }

//    The program does not deal with floating numbers, so it will fail.
//    the actual value should be "10" by the formula, but program will round-off the float value and  gives "8".
//    Hence, the precision fails for fractions.
    @Test
    public void testTrapizzaWithFloatingValuesShouldFail() {
            Trapizza t = new Trapizza(2, 6, (int) 2.5);
            double area = t.getArea();
            assertEquals(((2 + 6) / 2.0) * 2.5, area, 1e-6,
                    "Precision lost because fractional part was truncated");
    }
}
