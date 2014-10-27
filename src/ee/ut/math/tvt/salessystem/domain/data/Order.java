package ee.ut.math.tvt.salessystem.domain.data;


import java.util.Date;
import java.util.List;

public class Order implements DisplayableItem {
    private List<SoldItem> orderedItems;
    private Date dateAndTimeOfTheOrder;

    public Order(List<SoldItem> orderedItems, Date dateAndTimeOfTheOrder) {
        this.orderedItems = orderedItems;
        this.dateAndTimeOfTheOrder = dateAndTimeOfTheOrder;
    }


    @Override
    public Long getId() {
        return null;
    }

    public Date getDateAndTimeOfTheOrder() {
        return dateAndTimeOfTheOrder;
    }

    public List<SoldItem> getOrderedItems() {
        return orderedItems;
    }
}
