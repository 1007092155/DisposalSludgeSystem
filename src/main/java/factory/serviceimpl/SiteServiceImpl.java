package factory.serviceimpl;

import factory.dao.SiteDao;
import factory.entity.Site;
import factory.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import factory.dao.CarDao;
import factory.dao.RecordDao;
import factory.dao.SensorDao;
import factory.dao.SiteDao;
import factory.dao.UserDao;
import factory.entity.Sensor;
import factory.entity.Site;
import factory.entity.User;
import factory.service.SiteService;
@Service
public class SiteServiceImpl implements SiteService{
	@Autowired
	private SiteDao siteDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CarDao carDao;
	@Autowired
	private SensorDao sensorDao;
	@Autowired
	private RecordDao recordDao;
	/**
	 * 查询所有工厂
	 */
	@Override
	public List<Site> queryAllSite() {
		// TODO Auto-generated method stub
		return siteDao.queryAllSite();
	}

	public List<Site> queryAllSites() {
		// TODO Auto-generated method stub
		return siteDao.queryAllSites();
	}

	@Override
	public void deleteSite(int siteId) {
		// TODO Auto-generated method stub
		siteDao.deleteSite(siteId);
	}

	@Override
	public Site querySiteById(int id) {
		// TODO Auto-generated method stub
		return siteDao.querySiteById(id);
	}

	@Override
	public List<Site> fuzzyQuerySite(String queryStr) {
		// TODO Auto-generated method stub
		return siteDao.fuzzyQuerySite(queryStr);
	}

	@Override
	public int addSite(Map<String, String> siteInfo) {
		// TODO Auto-generated method stub
		Site site=new Site();
		site.setSerialNumber(siteInfo.get("serialNumber"));
		site.setSiteName(siteInfo.get("siteName"));
		site.setAddress(siteInfo.get("address"));
		site.setTelephone(siteInfo.get("telephone"));
		site.setLongitude(siteInfo.get("longitude"));
		site.setLatitude(siteInfo.get("latitude"));
		site.setDepth(Float.valueOf(siteInfo.get("depth")));
		siteDao.addSite(site);
		return site.getId();
	}

	@Override
	public String queryManagerTel(int managerId) {
		// TODO Auto-generated method stub
		User user=userDao.queryUserByUserId(managerId);
		if(user==null) 
			return null;
		else
			return user.getTelephone();
	}

	@Override
	public int querySiteIDByManageId(int manageId) {
		return siteDao.querySiteIDByManageId(manageId);
	}

	@Override
	public Site querySiteManagerById(int siteId) {
		// TODO Auto-generated method stub
		return siteDao.querySiteManagerById(siteId);
	}

	@Override
	public void editSite(Map<String, String> siteInfo) {
		// TODO Auto-generated method stub
		Site site=new Site();
		site.setId(Integer.valueOf(siteInfo.get("id")));
		site.setSerialNumber(siteInfo.get("serialNumber"));
		site.setSiteName(siteInfo.get("siteName"));
		site.setAddress(siteInfo.get("address"));
		site.setTelephone(siteInfo.get("telephone"));
		site.setLongitude(siteInfo.get("longitude"));
		site.setLatitude(siteInfo.get("latitude"));
		site.setDepth(Float.valueOf(siteInfo.get("depth")));
		
		int managerId = Integer.valueOf(siteInfo.get("managerId"));
		if( managerId != 0)
		site.setManageId(managerId);
		
		siteDao.editSite(site);
	}

	@Override
	public int querySiteSerialNumberAndName(String serialNumber,String siteName) {
		// TODO Auto-generated method stub
		return siteDao.querySiteSerialNumberAndName(serialNumber,siteName);
	}

	@Override
	public List<Sensor> queryUltrasonicValueBySite(String sensorIdSet) {
		// TODO Auto-generated method stub
		List<Sensor> sensors = new ArrayList<Sensor>();
		sensorIdSet = "(" + sensorIdSet + ")";
		sensors.addAll(sensorDao.querySensorBySensorIdSet(sensorIdSet));
		return sensors;
	}

	@Override
	public int queryRedSiteNum() {
		// TODO Auto-generated method stub
		return siteDao.queryRedSiteNum();
	}

	@Override
	public int queryNullCarNum() {
		// TODO Auto-generated method stub
		return carDao.queryNullCarNum();
	}

	@Override
	public List<Site> queryMapSite(String queryStr) {
		// TODO Auto-generated method stub
		return siteDao.queryMapSite(queryStr);
	}

	@Override
	public List<Site> querySiteServedByOneDriver(int driverId) {
		List<Site> sites=new ArrayList<Site>();
		sites.addAll(siteDao.querySiteServedByOneDriver(driverId));
		return sites;
	}

	@Override
	public List<User> queryAllManagerSiteNull() {
		// TODO Auto-generated method stub
		return userDao.queryAllManagerSiteNull();
	}

	@Override
	public List<User> queryAllManagerBySiteId(String siteId) {
		// TODO Auto-generated method stub
		return userDao.queryAllManagerBySiteId(siteId);
	}

	@Override
	public int countRecordOfCarNullBySiteId(int siteId) {
		// TODO Auto-generated method stub
		return recordDao.countRecordOfCarNullBySiteId(siteId);
	}

	@Override
	public Site querySiteMapBySiteId(int siteId) {
		// TODO Auto-generated method stub
		return siteDao.querySiteMapBySiteId(siteId);
	}
	
	

}
