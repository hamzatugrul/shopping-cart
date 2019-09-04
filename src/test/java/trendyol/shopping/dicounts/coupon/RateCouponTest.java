package trendyol.shopping.dicounts.coupon;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RateCouponTest {

    private final double rate = 10, minimumAmount = 200;
    private RateCoupon sutRateCoupon;

    @Before
    public void setUp() {
        // Coupon with 200 TL min purchase amount for a 10% discount
        sutRateCoupon = new RateCoupon(rate, minimumAmount);
    }

    @Test
    public void givenTotalCartAmountIsLessThenMinimumAmountWhenCouponIsApplicableThenShouldReturnFalse() {
        // Arrange
        final double totalCartAmount = 199;

        // Act
        final boolean actualResult = sutRateCoupon.isApplicable(totalCartAmount);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenTotalCartAmountIsEqualToMinimumAmountWhenCouponIsApplicableThenShouldReturnTrue() {
        // Arrange
        final double totalCartAmount = 200;

        // Act
        final boolean actualResult = sutRateCoupon.isApplicable(totalCartAmount);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenTotalCartAmountIsGreaterThanMinimumAmountWhenCouponIsApplicableThenShouldReturnTrue() {
        // Arrange
        final double totalCartAmount = 201;

        // Act
        final boolean actualResult = sutRateCoupon.isApplicable(totalCartAmount);

        // Assert
        assertTrue(actualResult);
    }


    @Test
    public void givenTotalAmountOfCartWhenGetDiscountThenShouldReturnDiscountForGivenRate() {
        // Act
        final double actualResult = sutRateCoupon.getDiscount(500);

        // Assert
        // Discount for 10% rate
        assertEquals(50, actualResult, 0);
    }
}
