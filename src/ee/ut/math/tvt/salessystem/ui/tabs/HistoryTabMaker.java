package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.*;
import java.util.List;

import javax.swing.*;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab labelled "History" in the menu).
 */
public class HistoryTabMaker {
    private SalesSystemModel model;
    // TODO - implement!

    public HistoryTabMaker(SalesSystemModel warehouseModel) {
        this.model = warehouseModel;
    }

    public Component createHistoryTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Order> purchases = null;

        HistoryTabModel historyTabModel = model.getHistoryTabModel();   //historyTabModel==andmed
        //JTable does not contain or cache data; it is simply a view of your data.
        JTable table = new JTable(historyTabModel);


        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setSize(300, 350);
        panel.setVisible(true);

        return panel;
    }

}