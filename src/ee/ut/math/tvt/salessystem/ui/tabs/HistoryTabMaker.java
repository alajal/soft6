package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTabModel;
import ee.ut.math.tvt.salessystem.ui.model.OrderedGoodsHistoryModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        final JTable table = new JTable(historyTabModel);

        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                createOrderDetailedTab(model.getHistoryTabModel().getOrder(row));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });

        /*table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int row = e.getFirstIndex();
                createOrderDetailedTab(model.getHistoryTabModel().getOrder(row));
                System.out.println("tere");
            }
        });*/

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

        orderedGoodsHistoryModel.clear();

        JTable itemTable = new JTable(orderedGoodsHistoryModel);
        for (SoldItem item : order.getOrderedItems()){
            orderedGoodsHistoryModel.addItemtoTable(item);
        }

        JScrollPane scrollPane = new JScrollPane(itemTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
        int width = 500;
        int height = 350;
        frame.setSize(width, height);
        frame.setAlwaysOnTop(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - width) / 2, (screen.height - height) / 2);

        frame.add(panel);
    }
}