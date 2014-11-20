package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class StockTableModelTest {

    StockItem newItem;
    StockTableModel warehouseTableModel;

    @Before
    public void setUp() {
        newItem = new StockItem((long) 1, "Ice-cream", "vanilla ice-cream", 4, 10);
        warehouseTableModel = new StockTableModel();
        warehouseTableModel.addItem(newItem);
    }

    @Test(expected = StockTableModel.UnsuitableItem.class)
    //test only name but with different id
    public void testValidateNameUniqueness() {
        StockItem otherItem = new StockItem((long) 2, "Ice-cream", "chocolate ice-cream", 4, 10);
        warehouseTableModel.addItem(otherItem);
    }

    @Test(expected = VerificationFailedException.class)
    public void testHasEnoughInStock() throws VerificationFailedException {
        //Loon tootelao, lisan sinna olemasoleva toote, proovin osta seda toodet väga suure kogusega
        SoldItem soldItem = new SoldItem(newItem, 80);

        //proovin lisada toiduainet korvi
        PurchaseInfoTableModel purchaseInfoTableModel = new PurchaseInfoTableModel();
        purchaseInfoTableModel.addItem(soldItem); //viskab exceptioni, kui osta rohkem tooteid kui laos on
    }

    @Test
    public void testGetItemByIdWhenItemExists() {
        StockItem item = warehouseTableModel.getItemById((long) 1);
        assertEquals(newItem.getName(), item.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetItemByIdWhenThrowsException() {
        //küsin sellist toodet, mida ei eksisteeri
        warehouseTableModel.getItemById((long) 76);
    }

    @Test
    public void testAddItemWithSameIdAndSameName() {
        StockItem otherItem = new StockItem((long) 1, "Ice-cream", "chocolate ice-cream", 4, 10);
        warehouseTableModel.addItem(otherItem);
        StockItem itemInStock = warehouseTableModel.getItemById(1L);
        int itemQuantity = itemInStock.getQuantity();
        int expectedQuantity = 20;
        assertEquals(expectedQuantity, itemQuantity);
    }
}
