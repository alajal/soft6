package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;

/**
 * Implementation of the SalesDomainController interface.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

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
        List<StockItem> stockItemArrayList = new ArrayList<StockItem>();

        StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
        StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
        StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sauseges", 15.0, 12);
        StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 3.0, 1);

        stockItemArrayList.add(chips);
        stockItemArrayList.add(chupaChups);
        stockItemArrayList.add(frankfurters);
        stockItemArrayList.add(beer);

        return stockItemArrayList;
    }
}
