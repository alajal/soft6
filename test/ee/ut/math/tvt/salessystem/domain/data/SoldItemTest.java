package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	StockItem newItem1;
	
	SoldItem newItem;
    
    
    @Test
    public void testGetSum() {
    	newItem1 = new StockItem((long) 1, "Ice-cream", "jats", 4, 10);
        SoldItem item = new SoldItem(newItem1, 10);
    	// System.out.println(item.getSum());

        assertEquals(item.getSum(), 40.0, 0.0001);
    }
    @Test
    public void testSumZero() {
    	newItem1 = new StockItem((long) 1, "Ice-cream", "jats", 4, 10);
        SoldItem item = new SoldItem(newItem1, 0);
    	// System.out.println(item.getSum());

        assertEquals(item.getSum(), 0.0, 0.0001);
    }
}
