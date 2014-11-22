package ee.ut.math.tvt.salessystem.ui.model;


import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;

import org.junit.Before;
import org.junit.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HistoryTabModelTest {

    HistoryTabModel historyTabModel;
    SoldItem soldItem1;
    SoldItem soldItem2;
    StockItem stockItem1;
    StockItem stockItem2;
    Order order;
    List<SoldItem> orderedItems;


    @Before
    public void setUp() {
        historyTabModel = new HistoryTabModel();
        stockItem1 = new StockItem(3L, "Peanut butter", "Sweet peanut butter", 2, 5);
        stockItem2 = new StockItem(4L, "Orange cake", "Crisp orange cake", 3, 5);
        soldItem1 = new SoldItem(stockItem1, 1);
        soldItem2 = new SoldItem(stockItem2, 2);
        orderedItems = new ArrayList<>();
    }
    
	@Test
	public void testAddSoldItem() {
	// kas lisatud SoldItem on nyyd tabelis?
		
	}
	
	@Test
	public void testGetSumWithNoItems() {
	// lisame tyhja orderi, kas summa on 0
		order = new Order(orderedItems, new Date());
		historyTabModel.addData(order);
		double orderSum = historyTabModel.getOrderedSum(order);
		assertEquals(orderSum, 0.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithOneItem() {
	// lisame orderi, milles yks item, kas summa klapib
		orderedItems.add(soldItem1);
		order = new Order(orderedItems, new Date());
		historyTabModel.addData(order);
		double orderSum = historyTabModel.getOrderedSum(order);
		assertEquals(orderSum, 2.0, 0.0001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
	// lisame orderi mitme itemiga, kas summa klapib
		orderedItems.add(soldItem1);
		orderedItems.add(soldItem2);
		order = new Order(orderedItems, new Date());
		historyTabModel.addData(order);
		double orderSum = historyTabModel.getOrderedSum(order);
		assertEquals(orderSum, 8.0, 0.0001);
	}

    @Test
    //testib kas modeli muutusel antakse sellest teada "vaatlejatele" (nt. JTable'ile)
    public void testWhenTableChangedThenListenerIsNotified() {
        final AtomicBoolean listenerWasNotified = new AtomicBoolean();
        orderedItems.add(soldItem2);
        orderedItems.add(soldItem1);
        order = new Order(orderedItems, new Date());
        historyTabModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                listenerWasNotified.set(true);
            }
        });
        historyTabModel.addData(order); //pannakse käima fireTableDataChanged
        assertTrue(listenerWasNotified.get());
    }
}
