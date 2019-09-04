package trendyol.shopping;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CategoryTest {

    private final static String LEVEL_1 = "Level_1";
    private final static String LEVEL_2 = "Level_2";
    private final static String LEVEL_3 = "Level_3";


    private Category categoryLevel_1;
    private Category categoryLevel_2;
    private Category categoryLevel_3_SUT;

    @Before
    public void setUp() {
        //create Categories Level_1 >> Level_2 >> Level_3
        categoryLevel_1 = new Category(LEVEL_1);
        categoryLevel_2 = new Category(LEVEL_2, categoryLevel_1);
        categoryLevel_3_SUT = new Category(LEVEL_3, categoryLevel_2);
    }

    @Test
    public void givenCategoryIsNullWhenIsUpperOfCurrentCategoryThenReturnFalse() {
        // Arrange
        final Category category = null;

        // Act
        boolean actualResult = categoryLevel_3_SUT.isUpperOfCurrentCategory(category);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenCategoryIsSameCategoryWhenIsUpperOfCurrentCategoryThenReturnTrue() {
        // Act
        boolean actualResult = categoryLevel_3_SUT.isUpperOfCurrentCategory(categoryLevel_3_SUT);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenUCategoryIsUpperWhenIsUpperOfCurrentCategoryThenReturnTrue() {
        // Act
        boolean actualResult = categoryLevel_3_SUT.isUpperOfCurrentCategory(categoryLevel_1);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenCategoryIsLowerWhenIsUpperOfCurrentCategoryThenReturnFalse() {
        // Act
        boolean actualResult = categoryLevel_1.isUpperOfCurrentCategory(categoryLevel_3_SUT);

        // Assert
        assertFalse(actualResult);
    }


    @Test
    public void givenCategoryIsNotInSameCategoryLineWhenIsUpperOfCurrentCategoryThenReturnFalse() {
        //Arrange
        final Category category = new Category("DifferentCategoryLine");

        // Act
        boolean actualResult = categoryLevel_3_SUT.isUpperOfCurrentCategory(category);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenSubLevelOfCategoryWhenCategoryToStringThenReturnCategorizedLevel() {
        // Arrange
        final String expectedResult = new StringBuilder(LEVEL_1)
                .append(Category.SUBCATEGORY_ARROW).append(LEVEL_2)
                .append(Category.SUBCATEGORY_ARROW).append(LEVEL_3).toString();

        // Act
        final String actualResult = categoryLevel_3_SUT.toString();

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenMainLevelOfCategoryWhenCategoryToStringThenReturnOnlyBaseLevel() {
        // Act
        final String actualResult = categoryLevel_1.toString();

        // Assert
        assertEquals(LEVEL_1, actualResult);
    }
}
