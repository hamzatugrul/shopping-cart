package trendyol.shopping.delivery;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeliveryCostCalculatorTest {

    private DeliveryCostCalculator sutDeliveryCostCalculator;

    @Test
    public void givenNumbersAreZeroWhenCalculateForThenReturnFixedCost() {
        // Arrange
        sutDeliveryCostCalculator = new DeliveryCostCalculator(2, 5);
        final int numberOfDeliveries = 0;
        final int numberOfProducts = 0;

        // Act
        final double actualResult = sutDeliveryCostCalculator.calculateFor(numberOfDeliveries, numberOfProducts);

        // Assert
        assertEquals(DeliveryCostCalculator.FIXED_COST, actualResult, 0);
    }

    @Test
    public void givenNumbersWhenCalculateForThenReturnShouldReturnCorrectResult() {
        // Arrange
        sutDeliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        final int numberOfDeliveries = 20;
        final int numberOfProducts = 10;

        // Act
        final double actualResult = sutDeliveryCostCalculator.calculateFor(numberOfDeliveries, numberOfProducts);

        // Assert
        assertEquals(30 + DeliveryCostCalculator.FIXED_COST, actualResult, 0);
    }
}
