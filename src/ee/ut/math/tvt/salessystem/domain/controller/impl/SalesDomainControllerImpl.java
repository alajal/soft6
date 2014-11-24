package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PaymentFrame;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import ee.ut.math.tvt.salessystem.service.HibernateDataService;
import org.apache.log4j.Logger;

import javax.xml.ws.Service;

/**
 * Implementation of the SalesDomainController interface.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
    private static final Logger log = Logger.getLogger(SalesDomainController.class);
	final HibernateDataService service;

    public SalesDomainControllerImpl() {
        this.service = new HibernateDataService();
    }

    public SalesDomainControllerImpl(HibernateDataService dataService) {
        this.service = dataService;
    }

    public void submitCurrentPurchase(List<SoldItem> goodsChosenToBuy) throws VerificationFailedException {
        reduceStock(goodsChosenToBuy);
    }

    private void reduceStock(List<SoldItem> goodsChosenToBuy) throws VerificationFailedException {
        // reduces stockItem quantity by soldItem quantity
        // if sold > stock, then throw error
        for (SoldItem soldItem : goodsChosenToBuy) {
            StockItem stockItem = soldItem.getStockItem();

            int newQuantity = stockItem.getQuantity() - soldItem.getQuantity();

            if (newQuantity >= 0) {
                stockItem.setQuantity(newQuantity);
                service.updateStockItemQuantity(stockItem, newQuantity);
                System.out.println("Stockitem quantity decreased.");
            } else {
                throw new VerificationFailedException("More sold items than in stock!");
            }
        }
    }

    public void cancelCurrentPurchase() throws VerificationFailedException {
        // XXX - Cancel current purchase
    }


    public void startNewPurchase() throws VerificationFailedException {
        // XXX - Start new purchase
    }

    public List<StockItem> loadWarehouseState() {
        // XXX mock implementation
//        List<StockItem> stockItemArrayList = new ArrayList<StockItem>();
//        StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);//
//        stockItemArrayList.add(chips);

        return service.getStockItems();
    }

    public List<Order> loadOrderHistoryData(){
        return service.getOrders();
    }
    
    public void endSession() {
        HibernateUtil.closeSession();
    }

    public void createPayment( List<SoldItem> soldItems, SalesSystemModel model) throws VerificationFailedException {
        model.getDomainController().submitCurrentPurchase(soldItems);   //reduce stock
        log.info("Sale confirmed and payment accepted.");

        Order order = new Order(soldItems, new Date());
        HistoryTabModel historyTabModel = model.getHistoryTabModel();
        historyTabModel.addData(order);

        service.addOrder(order);

        for (SoldItem soldItem : soldItems) {
            service.addSoldItem(soldItem);
        }
        log.info("Order added to database");
        model.getCurrentPurchaseTableModel().clear();
    }
}
