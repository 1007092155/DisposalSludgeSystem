package factory.service;

import factory.entity.Sensor;
import factory.entity.Site;
import factory.entity.User;

import java.util.List;
import java.util.Map;

public interface SiteService {
	
	public List<Site> queryAllSite();

	public List<Site> queryAllSites();
	
	public void deleteSite(int siteId);
	
	public Site querySiteById(int id);
	
	public List<Site> fuzzyQuerySite(String queryStr);

	public int addSite(Map<String, String> siteInfo);

	public String queryManagerTel(int managerId);
	
	public int querySiteIDByManageId(int manageId);

	public Site querySiteManagerById(int siteId);

	public void editSite(Map<String, String> siteInfo);

	public int querySiteSerialNumberAndName(String serialNumber, String siteName);

	public List<Sensor> queryUltrasonicValueBySite(String sensorIdSet);

	public int queryRedSiteNum();

	public int queryNullCarNum();

	public List<Site> queryMapSite(String queryStr);
	
	public List<Site> querySiteServedByOneDriver(int driverId);

	public List<User> queryAllManagerSiteNull();

	public List<User> queryAllManagerBySiteId(String siteId);

	public int countRecordOfCarNullBySiteId(int siteId);
	
	public Site querySiteMapBySiteId(int siteId);
}
