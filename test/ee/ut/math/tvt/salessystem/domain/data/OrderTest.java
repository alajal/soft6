package ee.ut.math.tvt.salessystem.domain.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderTest {
    SoldItem soldItem1;
    StockItem stockItem1;
    List<SoldItem> orderedItems;
    Order order;

    @Before
    public void setUp() {
        stockItem1 = new StockItem(3L, "Peanut butter", "Sweet peanut butter", 2, 5);
        soldItem1 = new SoldItem(stockItem1, 1);
    }

    @Test
    public void testAddSoldItem() {
        orderedItems = new ArrayList<>();
        orderedItems.add(soldItem1);
        order = new Order(orderedItems, new Date());

    }

    @Test
    public void testGetSumWithNoItems() {

    }

    @Test
    public void testGetSumWithOneItem() {


    }

    @Test
    public void testGetSumWithMultipleItems() {

    }
}
