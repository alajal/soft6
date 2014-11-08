package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

    private static final Logger log = Logger.getLogger(SalesSystemModel.class);
    private StockTableModel warehouseTableModel;
    private PurchaseInfoTableModel currentPurchaseTableModel;
    private final SalesDomainController domainController;
    private final HistoryTabModel historyTabModel;
    private OrderedGoodsHistoryModel orderedGoodsHistoryModel;

    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        historyTabModel = new HistoryTabModel();
        orderedGoodsHistoryModel = new OrderedGoodsHistoryModel();

        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());
        historyTabModel.populateWithData(domainController.loadOrderHistoryData());

    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }

    public HistoryTabModel getHistoryTabModel(){
        return historyTabModel;
    }

    public OrderedGoodsHistoryModel getOrderedGoodsHistoryModel() {
        return orderedGoodsHistoryModel;
    }

}
