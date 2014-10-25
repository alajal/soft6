package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab labelled "History" in the menu).
 */
public class HistoryTabMaker {

    // TODO - implement!

    public HistoryTabMaker() {

    }

    public Component createHistoryTab() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Date of the order", "Time of the order", "Total order price"};

        displayAcceptedOrders();

        Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
                { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };

        JTable table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setSize(300, 350);
        panel.setVisible(true);

        return panel;
    }

    private void displayAcceptedOrders() {
    }

}