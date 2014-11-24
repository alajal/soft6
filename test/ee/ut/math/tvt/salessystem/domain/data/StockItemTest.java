package ee.ut.math.tvt.salessystem.domain.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockItemTest {

    StockItem stockItem;
    @Before
    public void setUp(){
        stockItem = new StockItem(3L, "Peanut butter", "Sweet peanut butter", 2, 5);
    }
    @Test
    public void testClone(){
        StockItem item = (StockItem) stockItem.clone();
        assertEquals(item.getId().longValue(), 3L);
        assertEquals(item.getName(), "Peanut butter");
        assertEquals(item.getDescription(), "Sweet peanut butter");
        assertEquals(item.getPrice(), 2, 0);
        assertEquals(item.getQuantity(), 5);
    }

    @Test
    public void testGetColumn(){
        assertEquals(stockItem.getColumn(0),3L);
        assertEquals(stockItem.getColumn(1), "Peanut butter");
        assertEquals(stockItem.getColumn(2), (double)2);
        assertEquals(stockItem.getColumn(3), 5);
    }
}
