package factory.dao;

import factory.entity.Site;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SiteDao {

	public List<Site> queryAllSite();

	public List<Site> queryAllSites();
	
	public Site querySiteBySiteName(@Param("siteName") String siteName);
	
	public void deleteSite(@Param("siteId") int siteId);
	
	public Site querySiteById(@Param("id") int id);

	public List<Site> fuzzyQuerySite(@Param("queryStr") String queryStr);
	
	public int querySiteIDByManageId(@Param("manageId") int manageId);

	public void addSite(Site site);

	public Site querySiteManagerById(int siteId);

	public void editSite(Site site);

	public int querySiteSerialNumberAndName(@Param("serialNumber") String serialNumber, @Param("siteName") String siteName);
	
	public void updateSetIdSet(@Param("siteId") int siteId,@Param("sensorIdSet") String sensorIdSet);

	public String querySensorIdSetBySiteId(@Param("siteId") int siteId);
	public double queryUltrasonicValueBySite(@Param("sensorIdSet") String sensorIdSet);

	public int queryRedSiteNum();

	public List<Site> queryMapSite(@Param("queryStr") String queryStr);
	
	public List<Site> querySiteServedByOneDriver(@Param("driverId") int driverId);
	
	public Site querySiteMapBySiteId(@Param("siteId") int siteId);
}
