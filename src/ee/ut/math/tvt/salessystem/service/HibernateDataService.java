package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.data.Order;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


public class HibernateDataService {
	private Session session = HibernateUtil.currentSession();
	
	// paring "from entity_name" tagastabki koik read, list() teeb selle veel listiks
	public List<StockItem> getStockItems() {
		@SuppressWarnings("unchecked")
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}
	
	public List<SoldItem> getSoldItems() {
		@SuppressWarnings("unchecked")
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}
	
	public void addStockItem(StockItem stockItem) {
		Transaction trans = session.beginTransaction();
		session.save(stockItem);
		trans.commit();
	}
	
	public void addOrder(Order order) {
		Transaction trans = session.beginTransaction();
		session.save(order);
		trans.commit();
	}
	
	public void addSoldItem(SoldItem soldItem) {
		Transaction trans = session.beginTransaction();
		session.save(soldItem);
		trans.commit();
	}
	
	public void updateStockItemQuantity(StockItem stockItem, int itemQuantity) {
		Transaction trans = session.beginTransaction();
		StockItem stockItemFromTable = (StockItem)session.get(StockItem.class, stockItem.getId());
		stockItemFromTable.setQuantity(itemQuantity);
		session.update(stockItemFromTable);
		
		trans.commit();
	}

}
