package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(StockTableModel.class);

    public StockTableModel() {
        super(new String[]{"Id", "Name", "Price", "Quantity"});
    }

    @Override
    protected Object getColumnValue(StockItem item, int columnIndex) {
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

    /**
     * Add new stock item to table. If there already is a stock item with
     * same id, then existing item's quantity will be increased.
     */
    public void addItem(final StockItem stockItem) {
        try {
            checkNameUniquess(stockItem);   //l2heb edasi, kui nimed on erinevad ning id-d on samad
            StockItem item = getItemById(stockItem.getId());
            item.setQuantity(item.getQuantity() + stockItem.getQuantity());
            log.debug("Found existing item " + stockItem.getName() + " increased quantity by " + stockItem.getQuantity());
        } catch (NoSuchElementException e) {
            rows.add(stockItem);
            log.debug("Added " + stockItem.getName() + " quantity of " + stockItem.getQuantity());
        }
        fireTableDataChanged();

    }

    private void checkNameUniquess(StockItem stockItem) {
        String name = stockItem.getName();
        List<StockItem> stockItems = this.getTableRows();
        for (StockItem it : stockItems) {
            if ((it.getName().equals(name)) && !it.getId().equals(stockItem.getId())) {
                throw new UnsuitableItem("Cannot add item with the name that already exists in warehouse.");
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        for (String header : headers) {
            buffer.append(header).append("\t");
        }

        buffer.append("\n");

        for (final StockItem stockItem : rows) {
            buffer.append(stockItem.getId()).append("\t");
            buffer.append(stockItem.getName()).append("\t");
            buffer.append(stockItem.getPrice()).append("\t");
            buffer.append(stockItem.getQuantity()).append("\t");
            buffer.append("\n");
        }

        return buffer.toString();
    }

    @SuppressWarnings("serial")
    public static class UnsuitableItem extends RuntimeException {

        public UnsuitableItem() {
            super();
        }

        public UnsuitableItem(String string) {
            super(string);
        }
    }
}
