package ee.ut.math.tvt.salessystem;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;

import java.util.ArrayList;
import java.util.List;

public class FakeDataService extends HibernateDataService {

    public FakeDataService() {
        super(null);
    }

    @Override
    public void addOrder(Order order) {
    }

    @Override
    public void addSoldItem(SoldItem soldItem) {
    }

    @Override
    public List<StockItem> getStockItems() {
        StockItem s = new StockItem(3L, "Kapsasalat", "salat", 2, 6);
        List<StockItem> stockItemList = new ArrayList<>();
        //stockItemList.add(s);
        return stockItemList;
    }

    @Override
    public List<Order> getOrders() {
        List<SoldItem> soldItems1 = new ArrayList<>();
        //List<SoldItem> soldItems1 = new ArrayList<>();

        StockItem s = new StockItem(3L, "Kapsasalat", "salat", 5, 6);
        StockItem d = new StockItem((long) 1, "Ice-cream", "vanilla ice-cream", 4, 10);
        SoldItem soldItem1 = new SoldItem(s,1);
        SoldItem soldItem2 = new SoldItem(d,2);
        SoldItem soldItem3 = new SoldItem(s,2);
        SoldItem soldItem4 = new SoldItem(d,1);
        soldItems1.add(soldItem1);
        //Order order1 = new Order(orderedItems, new Date());
        List<Order> orders = new ArrayList<>();

        return  orders;
    }

    @Override
    public void updateStockItemQuantity(StockItem stockItem, int itemQuantity) {

    }
}
