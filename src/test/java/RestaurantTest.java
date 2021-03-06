import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void initializeRestaurantWithMenu() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        //Arrange
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        //Mock Local time to return time between opening and closing time.
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:15:00"));

        //Act
        Boolean isRestaurantOpen;
        isRestaurantOpen = mockRestaurant.isRestaurantOpen();

        //Assert
        assertTrue(mockRestaurant.isRestaurantOpen(), "Restaurant Open");
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //Arrange
        Restaurant mockRestaurant = Mockito.mock(Restaurant.class);
        //Mock Local time to return time between opening and closing time.
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:15:00"));

        //Act
        Boolean isRestaurantOpen;
        isRestaurantOpen = mockRestaurant.isRestaurantOpen();

        //Assert
        assertFalse(mockRestaurant.isRestaurantOpen(), "Restaurant Closed");

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //New Feature by TDD
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Get total cost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void get_total_order_value_should_return_total_value_of_the_order_of_selected_menu_items() {
        String[] OrderedMenuItems = {"Sweet corn soup", "Vegetable lasagne"};
        int totalOrderValue = restaurant.getTotalOrderValue(OrderedMenuItems);
        assertEquals(119 + 269, totalOrderValue);
    }

    @Test
    public void get_total_order_value_should_return_zero_when_no_menu_item_is_selected() {
        String[] OrderedMenuItems = {};
        int totalOrderValue = restaurant.getTotalOrderValue(OrderedMenuItems);
        assertEquals(0, totalOrderValue);
    }

//<<<<<<<<<<<<<<<<<<<<<<<Get total cost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}