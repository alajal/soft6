package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import ee.ut.math.tvt.salessystem.ui.model.OrderedGoodsHistoryModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab labelled "History" in the menu).
 */
public class HistoryTabMaker {
    private SalesSystemModel model;

    public HistoryTabMaker(SalesSystemModel warehouseModel) {
        this.model = warehouseModel;
    }

    public Component createHistoryTab() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        HistoryTabModel historyTabModel = model.getHistoryTabModel();   //historyTabModel==andmed
        JTable table = new JTable(historyTabModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = e.getFirstIndex();
                createOrderDetailedTab(model.getHistoryTabModel().getOrder(row));
                System.out.println("tere");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setSize(300, 350);
        panel.setVisible(true);

        return panel;
    }

    public void createOrderDetailedTab(Order order) {
        JFrame frame = new JFrame("Order details");
        frame.setLayout(new GridLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        OrderedGoodsHistoryModel orderedGoodsHistoryModel = model.getOrderedGoodsHistoryModel();
        JTable itemTable = new JTable(orderedGoodsHistoryModel);
        for (SoldItem item : order.getOrderedItems()){
            orderedGoodsHistoryModel.addItemtoTable(item);
        }

        JScrollPane scrollPane = new JScrollPane(itemTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 350);
        frame.setVisible(true);

        frame.add(panel);

    }
}