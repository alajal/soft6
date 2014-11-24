package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {

    /**
     * Commit business transaction - purchase of goods.
     */
    public void submitCurrentPurchase(List<SoldItem> goodsChosenToBuy)
            throws VerificationFailedException;

    /**
     * Rollback business transaction - purchase of goods.
     */
    public void cancelCurrentPurchase() throws VerificationFailedException;

    // business processes

    /**
     * Initiate new business transaction - purchase of the goods.
     */
    public void startNewPurchase() throws VerificationFailedException;

    /**
     * Load the current state of the warehouse.
     *
     * @return List of ${link ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
     */
    public List<StockItem> loadWarehouseState();
    
    public List<Order> loadOrderHistoryData();
    
    /**
     * Closes current database session
     */
    public void endSession();

    public void createPayment( List<SoldItem> soldItems, SalesSystemModel model) throws VerificationFailedException;

}
