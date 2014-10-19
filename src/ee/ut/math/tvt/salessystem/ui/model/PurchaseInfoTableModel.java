package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
    public PurchaseInfoTableModel() {
        super(new String[]{"Id", "Name", "Price", "Quantity"});
    }

    @Override
    protected Object getColumnValue(SoldItem item, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
                return item.getName();
            case 2:
                return item.getPrice();
            case 3:
                return item.getQuantity();
        }
        throw new IllegalArgumentException("Column index out of range");
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < headers.length; i++)
            buffer.append(headers[i]).append("\t");
        buffer.append("\n");

        for (final SoldItem item : rows) {
            buffer.append(item.getId()).append("\t");
            buffer.append(item.getName()).append("\t");
            buffer.append(item.getPrice()).append("\t");
            buffer.append(item.getQuantity()).append("\t");
            buffer.append(item.getSum()).append("\t");
            buffer.append("\n");
        }

        return buffer.toString();
    }


    public void addStockItem(final SoldItem item) {
        /**
         * XXX In case such stockItem already exists increase the quantity of the
         * existing stock.
         */

        rows.add(item);
        log.debug("Added " + item.getName() + " quantity of " + item.getQuantity());
        fireTableDataChanged();
    }
}
