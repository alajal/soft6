package ee.ut.math.tvt.salesystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;

public class StockTableModelTest {

    StockItem newItem;
    StockTableModel warehouseTableModel;

    @Before
    public void setUp() {
        newItem = new StockItem((long) 1, "Ice-cream", "vanilla ice-cream", 4, 10);
        warehouseTableModel = new StockTableModel();
        warehouseTableModel.addItem(newItem);
    }

    @Test(expected = StockTab.UnsuitableItem.class)
    public void testValidateNameUniqueness() {

        StockItem otherItem = new StockItem((long) 2, "Ice-cream", "chocolate ice-cream", 4, 10);

        List<StockItem> stockItems = warehouseTableModel.getTableRows();
        //StockTab klassi itemNameController() meetodi taaskasutus; see test peaks seda meetodit kontrollima
        for (StockItem item : stockItems) {
            if ((item.getName().equals(otherItem.getName())) && !item.getId().equals(otherItem.getId())) {
                throw new StockTab.UnsuitableItem("Cannot add item with the name that already exists in warehouse.");
            } else {
                warehouseTableModel.addItem(otherItem);
            }
        }
        //assertEquals(items.get(0).getName(), result);
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
        System.out.println(item.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetItemByIdWhenThrowsException() {
        //küsin sellist toodet, mida ei eksisteeri
        warehouseTableModel.getItemById((long) 23);
    }
}
