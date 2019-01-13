package factory.controller;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import factory.entity.Sensor;
import factory.entity.Site;
import factory.entity.User;
import factory.service.SensorService;
import factory.service.SiteService;


@Controller
@RequestMapping(value="system")
public class SiteController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private SensorService sensorService;
	
	private static Log log=LogFactory.getLog(SiteController.class);
	
	/**
	 * @description:从site表中查询所有站点
	 */
	@RequestMapping("/jumpToSite")
	public ModelAndView querySite(ModelAndView mv){
		log.info("查询站点");
		List<Site> sites=siteService.queryAllSite();
		for(Site site:sites){
			if(site.getSensorIdSet()!=null&&site.getSensorIdSet()!="")
			site.setSensors(sensorService.querySensorTypeByIdSet(site.getSensorIdSet()));
		}
		log.info(sites.toString());
		mv.addObject("siteList",sites);
		mv.setViewName("system/siteManage");
		return mv;
	}
	
	@RequestMapping("{formName}")
	public String deviceForm(@PathVariable String formName){
		return "system/"+formName;
	}
	
	@RequestMapping("queryAllSite")
	@ResponseBody
	public List<Site> queryAllSite() {
		log.info("查询站点");
		List<Site> sites=siteService.queryAllSite();
		for(Site site:sites){
			if(site.getSensorIdSet()!=null&&site.getSensorIdSet()!="")
			site.setSensors(sensorService.querySensorTypeByIdSet(site.getSensorIdSet()));
		}
		log.info(sites.toString());
		return sites;

	}
	
	@RequestMapping("deleteSite")
	@ResponseBody
	public void deleteSite(@RequestParam("siteId") int siteId){
		log.info("删除id="+siteId+"的站点记录");
		Site delSite=siteService.querySiteById(siteId);
		if(delSite.getSensorIdSet()!=null){
		sensorService.setSiteIdSetNull(delSite.getSensorIdSet());}
		siteService.deleteSite(siteId);
	}
	
	@RequestMapping("fuzzyQuerySite")
	@ResponseBody
	public List<Site> fuzzyQuerySite(@RequestParam("queryStr") String queryStr){
		log.info("模糊查询="+queryStr);
		List<Site> sites=siteService.fuzzyQuerySite(queryStr);
		for(Site site:sites){
			if(site.getSensorIdSet()!=null)
			site.setSensors(sensorService.querySensorTypeByIdSet(site.getSensorIdSet()));
		}
		return sites;
	}
	
	@RequestMapping("addSite")
	@ResponseBody
	public Map<String, String> addSite(@RequestBody Map<String, String> siteInfo) {
		log.info("增加站点");
		Map<String, String> result = new HashMap<String, String>();
		try {
			log.info(siteInfo.toString());
			int siteId = siteService.addSite(siteInfo);
			result.put("result", "success");
			result.put("siteId",String.valueOf(siteId));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "failure");
		}
		return result;
	}
	
	@RequestMapping("queryManagerTel")
	@ResponseBody
	public Map<String, String> queryManagerTel(@RequestParam("managerId") int managerId) {
		log.info("查询managerTel");
		Map<String, String> result = new HashMap<String, String>();
		try {
			log.info(managerId);
			String managerTel = siteService.queryManagerTel(managerId);
			if(managerTel==null) result.put("result", "failure");
			else{
				result.put("result", "success");
				result.put("managerTel",managerTel);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "failure");
		}
		return result;
	}
	
	@RequestMapping("querySiteManagerById")
	@ResponseBody
	public Site querySiteManagerById(@RequestParam("siteId") int siteId){
		log.info("查找siteId="+siteId+"的站点");
		return siteService.querySiteManagerById(siteId);
	}
	
	@RequestMapping("editSite")
	@ResponseBody
	public Map<String, String> editSite(@RequestBody Map<String, String> siteInfo) {
		log.info("编辑站点");
		Map<String, String> result = new HashMap<String, String>();
		try {
			log.info(siteInfo.toString());
			siteService.editSite(siteInfo);
			result.put("result", "success");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("result", "failure");
		}
		return result;
	}
	
	@RequestMapping("querySiteSerialNumberAndName")
	@ResponseBody
	public Map<String, String> querySiteSerialNumberAndName(@RequestParam("serialNumber") String serialNumber,@RequestParam("siteName") String siteName) {
		log.info("查询是否有编号为"+serialNumber+"或站点名为"+siteName+"的站点");
		Map<String, String> result = new HashMap<String, String>();
		int n=siteService.querySiteSerialNumberAndName(serialNumber,siteName);
		if(n>0)
			result.put("result", "1");
		else result.put("result", "0");

		return result;
	}
	
	@RequestMapping("queryUltrasonicValueBySite")
	@ResponseBody
	public List<Sensor> queryUltrasonicValueBySite(@RequestParam("sensorIdSet") String sensorIdSet) {
		log.info("查询站点污泥量"+sensorIdSet);
		List<Sensor> sensors = new ArrayList<Sensor>();
		if(sensorIdSet==null||sensorIdSet=="")
		{
			sensors=null;
			return sensors;
		}
		else{
			try{
				sensors=siteService.queryUltrasonicValueBySite(sensorIdSet);
			}catch (Exception e) {
				// TODO: handle exception
				sensors=null;
				return sensors;
			}
		}
		return sensors;
	}
	
	@RequestMapping("queryRedNum")
	@ResponseBody
	public Map<String,Integer> queryRedStatusNum() {
		log.info("查询空闲车辆及待处理站点数量");
		Map<String,Integer> result = new HashMap<String,Integer>();
		int carNum=siteService.queryNullCarNum();
		int siteNum=siteService.queryRedSiteNum();
		result.put("car", carNum);
		result.put("site", siteNum);
		return result;
	}
	
	@RequestMapping("queryMapSite")
	@ResponseBody
	public List<Site> queryMapSite(@RequestParam("queryStr") String queryStr) {
		log.info("地图查询站点");
		List<Site> siteList=new ArrayList<Site>();
		try{
			siteList=siteService.queryMapSite(queryStr);
		}catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			siteList=null;
		}

		return siteList;
	}
	/**
	 * @description:根据站点查询工作人员
	 */
	@RequestMapping("/queryAllManagerBySite")
	@ResponseBody
	public List<User> queryAllManagerBySite(@RequestParam("siteId") String siteId) {
		log.info("根据站点查询工厂人员，siteId="+siteId);
		if(siteId==null||siteId=="")
			return siteService.queryAllManagerSiteNull();
		else
			return siteService.queryAllManagerBySiteId(siteId);
	}
	/**
	 * @description:查询站点是否已分配
	 */
	@RequestMapping("/countRecordOfCarNullBySiteId")
	@ResponseBody
	public int countRecordOfCarNullBySiteId(@RequestParam("siteId") int siteId) {
		log.info("查询站点是否已分配，siteId="+siteId);
		try{
			return siteService.countRecordOfCarNullBySiteId(siteId);
		}catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			return 0;
		}
	}
	/**
	 * @description:根据站点编号查找站点地图信息
	 */
	@RequestMapping("querySiteMapBySiteId")
	@ResponseBody
	public Site querySiteMapBySiteId(@RequestParam("siteId") int siteId) {
		log.info("查询站点"+siteId);
		Site site=siteService.querySiteMapBySiteId(siteId);
		if(site.getSensorIdSet()!=null&&site.getSensorIdSet()!="")
			site.setSensors(sensorService.querySensorTypeByIdSet(site.getSensorIdSet()));
		log.info(site.toString());
		return site;

	}
}
