package ee.ut.math.tvt.salesystem.ui.model;


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
        stockItem2 = new StockItem(4L, "Orange cake", "Crisp orange cake", 2, 5);
        soldItem1 = new SoldItem(stockItem1, 1);
        soldItem2 = new SoldItem(stockItem2, 1);
        orderedItems = new ArrayList<>();
        orderedItems.add(soldItem2);
        orderedItems.add(soldItem1);
        order = new Order(orderedItems, new Date());

    }

    @Test
    //testib kas modeli muutusel antakse sellest teada "vaatlejatele" (nt. JTable'ile)
    public void testFireTableDataChangedMethodOnModel() {
/*        historyTabModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                //System.out.println(e + "tere");

            }
        });
        historyTabModel.addData(order); //pannakse käima fireTableDataChanged*/




    }
}
