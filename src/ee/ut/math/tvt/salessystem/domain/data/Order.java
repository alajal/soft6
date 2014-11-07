package ee.ut.math.tvt.salessystem.domain.data;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SALE")
public class Order implements DisplayableItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy="order")
    private List<SoldItem> orderedItems;
    
    @Column(name="SALE_DATE")
    private Date dateAndTimeOfTheOrder;
    
    public Order() {
    }

    public Order(List<SoldItem> orderedItems, Date dateAndTimeOfTheOrder) {
        this.orderedItems = orderedItems;
        this.dateAndTimeOfTheOrder = dateAndTimeOfTheOrder;
        for(SoldItem soldItem:orderedItems) {
            soldItem.setOrder(this);
        }
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
