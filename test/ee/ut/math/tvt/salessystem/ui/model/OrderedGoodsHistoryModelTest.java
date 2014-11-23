package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderedGoodsHistoryModelTest {
    SoldItem soldItem1;
    SoldItem soldItem2;
    StockItem stockItem1;
    StockItem stockItem2;
    OrderedGoodsHistoryModel orderedGoodsHistoryModel;


    @Before
    public void setUp() {
        stockItem1 = new StockItem(3L, "Peanut butter", "Sweet peanut butter", 2, 5);
        stockItem2 = new StockItem(4L, "Orange cake", "Crisp orange cake", 3, 5);
        soldItem1 = new SoldItem(stockItem1, 1);
        soldItem2 = new SoldItem(stockItem2, 2);
    }

    @Test
    //testime StockTableModeli clear() meetodit
    public void testClearTable() {
        orderedGoodsHistoryModel = new OrderedGoodsHistoryModel();
        orderedGoodsHistoryModel.addItemtoTable(soldItem1);
        orderedGoodsHistoryModel.addItemtoTable(soldItem2);

        assertEquals(2,orderedGoodsHistoryModel.getTableRows().size());
        orderedGoodsHistoryModel.clear();
        assertEquals(0, orderedGoodsHistoryModel.getTableRows().size());
    }

}
