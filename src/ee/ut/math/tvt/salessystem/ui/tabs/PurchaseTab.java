package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PaymentFrame;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

    private static final Logger log = Logger.getLogger(PurchaseTab.class);
    private final SalesDomainController domainController;
    private JButton newPurchase;
    private JButton confirmPurchase;
    private JButton cancelPurchase;
    @SuppressWarnings("rawtypes")
	private PurchaseItemPanel purchasePane;
    private SalesSystemModel model;


    public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
        this.domainController = controller;
        this.model = model;
    }


    @SuppressWarnings("rawtypes")
	public Component createPurchaseTab() {
        JPanel panel = new JPanel();

        // Layout
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new GridBagLayout());

        // Add the purchase menu
        panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

        // Add the main purchase-panel
        purchasePane = new PurchaseItemPanel(model);
        panel.add(purchasePane, getConstraintsForPurchasePanel());

        return panel;
    }

    // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
    private Component getPurchaseMenuPane() {
        JPanel panel = new JPanel();

        // Initialize layout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = getConstraintsForMenuButtons();

        // Initialize the buttons
        newPurchase = createNewPurchaseButton();
        confirmPurchase = createConfirmButton();
        cancelPurchase = createCancelButton();

        // Add the buttons to the panel, using GridBagConstraints we defined
        // above
        panel.add(newPurchase, gc);
        panel.add(confirmPurchase, gc);
        panel.add(cancelPurchase, gc);

        return panel;
    }

    // Creates the button "New purchase"
    private JButton createNewPurchaseButton() {
        JButton purchaseButton = new JButton("New purchase");
        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newPurchaseButtonClicked();
            }
        });

        return purchaseButton;
    }

    // Creates the "Confirm" button
    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitPurchaseButtonClicked();
            }
        });
        confirmButton.setEnabled(false);

        return confirmButton;
    }

    // Creates the "Cancel" button
    private JButton createCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelPurchaseButtonClicked();
            }
        });
        cancelButton.setEnabled(false);

        return cancelButton;
    }

	/*
     * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

    protected void newPurchaseButtonClicked() {
        log.info("New sale process started");
        try {
            domainController.startNewPurchase();
            startNewSale();
        } catch (VerificationFailedException e1) {
            log.error(e1.getMessage());
        }
    }


    protected void cancelPurchaseButtonClicked() {
        log.info("Sale cancelled");
        try {
            domainController.cancelCurrentPurchase();
            endSale();
            model.getCurrentPurchaseTableModel().clear();
        } catch (VerificationFailedException e1) {
            log.error(e1.getMessage());
        }
    }


    protected void submitPurchaseButtonClicked() {
        log.info("Confirm button clicked");
        List<SoldItem> soldItems = model.getCurrentPurchaseTableModel().getTableRows();
        if (soldItems.size() != 0) {
            try {
                log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
                PaymentFrame paymentPanel = new PaymentFrame(soldItems, this.domainController, this.model, this, getTotalOrderAmount(soldItems));
                paymentPanel.setVisible(true);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            // catch (VerificationFailedException e1) { log.error(e1.getMessage()); }
        } else {
            JOptionPane.showMessageDialog(null, "There are no items chosen!");
        }
    }

    private double getTotalOrderAmount(List<SoldItem> soldItems) {
        double totalAmount = 0.0;
        for (SoldItem item : soldItems) {
            totalAmount += item.getSum();
        }
        return totalAmount;
    }

	/*
     * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

    // switch UI to the state that allows to proceed with the purchase
    private void startNewSale() {
        purchasePane.reset();
        purchasePane.populateComboBox();

        purchasePane.setEnabled(true);
        confirmPurchase.setEnabled(true);
        cancelPurchase.setEnabled(true);
        newPurchase.setEnabled(false);

    }

    // switch UI to the state that allows to initiate new purchase
    public void endSale() {
        purchasePane.reset();

        cancelPurchase.setEnabled(false);
        confirmPurchase.setEnabled(false);
        newPurchase.setEnabled(true);
        purchasePane.setEnabled(false);
    }

	/*
     * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

    private GridBagConstraints getConstraintsForPurchaseMenu() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0d;
        gc.weighty = 0d;

        return gc;
    }

    private GridBagConstraints getConstraintsForPurchasePanel() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0d;
        gc.weighty = 1.0;

        return gc;
    }

    // The constraints that control the layout of the buttons in the purchase
    // menu
    private GridBagConstraints getConstraintsForMenuButtons() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridwidth = GridBagConstraints.RELATIVE;

        return gc;
    }

}
