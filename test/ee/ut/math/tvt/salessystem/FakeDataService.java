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
        return new ArrayList<>();
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>();
    }

    @Override
    public void updateStockItemQuantity(StockItem stockItem, int itemQuantity) {

    }
}
