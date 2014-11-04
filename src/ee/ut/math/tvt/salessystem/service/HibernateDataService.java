package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


public class HibernateDataService {
	private Session session = HibernateUtil.currentSession();
	
	// paring "from entity_name" tagastabki koik read, list() teeb selle veel listiks
	public List<StockItem> getStockItems() {
		@SuppressWarnings("unchecked")
		List<StockItem> result = session.createQuery("from STOCKITEM").list();
		return result;
	}
	
	public List<SoldItem> getSoldItems() {
		@SuppressWarnings("unchecked")
		List<SoldItem> result = session.createQuery("from SOLDITEM").list();
		return result;
	}

}
