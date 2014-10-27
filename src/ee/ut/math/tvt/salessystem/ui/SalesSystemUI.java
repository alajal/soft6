package ee.ut.math.tvt.salessystem.ui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTabMaker;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(SalesSystemUI.class);
    private final SalesDomainController domainController;
    private SalesSystemModel warehouseModel;

    // Instances of tab classes
    private PurchaseTab purchaseTabMaker;
    private HistoryTabMaker historyTabMaker;
    private StockTab stockTabMaker;


    public SalesSystemUI(SalesDomainController domainController) {
        this.domainController = domainController;
        this.warehouseModel = new SalesSystemModel(domainController);

        createTabs();
        setTitle("Sales system");
        setWindowsLookAndFeel();
        createWidgets();
        setUISizeAndLocation(600, 400);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void setUISizeAndLocation(int width, int height) {
        setSize(width, height);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - width) / 2, (screen.height - height) / 2);
    }

    private void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e1) {
            log.warn(e1.getMessage());
        }
    }

    private void createTabs() {
        historyTabMaker = new HistoryTabMaker(warehouseModel);
        stockTabMaker = new StockTab(warehouseModel);
        purchaseTabMaker = new PurchaseTab(domainController, warehouseModel);
    }

    private void createWidgets() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Point-of-sale", purchaseTabMaker.createPurchaseTab());
        tabbedPane.add("Warehouse", stockTabMaker.createStockTab());
        tabbedPane.add("History", historyTabMaker.createHistoryTab());

        getContentPane().add(tabbedPane);
    }

}


