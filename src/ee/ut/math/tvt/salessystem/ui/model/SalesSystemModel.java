package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTabMaker;
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

    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        historyTabModel = new HistoryTabModel();

        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());

    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }

    public HistoryTabModel getHistoryTabModel(){
        return historyTabModel;
    };

}
