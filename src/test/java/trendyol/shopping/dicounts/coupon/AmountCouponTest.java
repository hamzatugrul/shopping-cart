package trendyol.shopping.dicounts.coupon;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AmountCouponTest {

    private final double discount = 50, minimumAmount = 200;
    private AmountCoupon sutAmountCoupon;

    @Before
    public void setUp() {
        // Coupon with 200 TL min purchase amount for a 50 TL discount
        sutAmountCoupon = new AmountCoupon(discount, minimumAmount);
    }

    @Test
    public void givenTotalCartAmountIsLessThenMinimumAmountWhenCouponIsApplicableThenShouldReturnFalse() {
        // Arrange
        final double totalCartAmount = 199;

        // Act
        final boolean actualResult = sutAmountCoupon.isApplicable(totalCartAmount);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenTotalCartAmountIsEqualToMinimumAmountWhenCouponIsApplicableThenShouldReturnTrue() {
        // Arrange
        final double totalCartAmount = 200;

        // Act
        final boolean actualResult = sutAmountCoupon.isApplicable(totalCartAmount);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenTotalCartAmountIsGreaterThanMinimumAmountWhenCouponIsApplicableThenShouldReturnTrue() {
        // Arrange
        final double totalCartAmount = 201;

        // Act
        final boolean actualResult = sutAmountCoupon.isApplicable(totalCartAmount);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenTotalAmountOfCartIsLessThanDiscountWhenGetDiscountThenShouldReturnZero() {
        // Act
        AmountCoupon amountCoupon = new AmountCoupon(50, 10);
        final double actualResult = amountCoupon.getDiscount(20);

        // Assert
        // Discount for 50 TL discount
        assertEquals(0, actualResult, 0);
    }

    @Test
    public void givenTotalAmountOfCartWhenGetDiscountThenShouldReturnDiscount() {
        // Act
        final double actualResult = sutAmountCoupon.getDiscount(500);

        // Assert
        // Discount for 50 TL discount
        assertEquals(50, actualResult, 0);
    }
}
