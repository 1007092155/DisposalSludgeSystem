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
	 * @description:��site���в�ѯ����վ��
	 */
	@RequestMapping("/jumpToSite")
	public ModelAndView querySite(ModelAndView mv){
		log.info("��ѯվ��");
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
		log.info("��ѯվ��");
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
		log.info("ɾ��id="+siteId+"��վ���¼");
		Site delSite=siteService.querySiteById(siteId);
		if(delSite.getSensorIdSet()!=null){
		sensorService.setSiteIdSetNull(delSite.getSensorIdSet());}
		siteService.deleteSite(siteId);
	}
	
	@RequestMapping("fuzzyQuerySite")
	@ResponseBody
	public List<Site> fuzzyQuerySite(@RequestParam("queryStr") String queryStr){
		log.info("ģ����ѯ="+queryStr);
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
		log.info("����վ��");
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
	public Map<String, String> queryManagerTel(@RequestParam("manager") String manager) {
		log.info("��ѯmanagerTel");
		Map<String, String> result = new HashMap<String, String>();
		try {
			log.info(manager);
			String managerTel = siteService.queryManagerTel(manager);
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
		log.info("����siteId="+siteId+"��վ��");
		return siteService.querySiteManagerById(siteId);
	}
	
	@RequestMapping("editSite")
	@ResponseBody
	public Map<String, String> editSite(@RequestBody Map<String, String> siteInfo) {
		log.info("�༭վ��");
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
		log.info("��ѯ�Ƿ��б��Ϊ"+serialNumber+"��վ����Ϊ"+siteName+"��վ��");
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
		log.info("��ѯվ��������"+sensorIdSet);
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
		log.info("��ѯ���г�����������վ������");
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
		log.info("��ͼ��ѯվ��");
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
	 * @description:����վ���ѯ������Ա
	 */
	@RequestMapping("/queryAllManagerBySite")
	@ResponseBody
	public List<User> queryAllManagerBySite(@RequestParam("siteId") String siteId) {
		log.info("����վ���ѯ������Ա��siteId="+siteId);
		if(siteId==null||siteId=="")
			return siteService.queryAllManagerSiteNull();
		else
			return siteService.queryAllManagerBySiteId(siteId);
	}
	/**
	 * @description:��ѯվ���Ƿ��ѷ���
	 */
	@RequestMapping("/countRecordOfCarNullBySiteId")
	@ResponseBody
	public int countRecordOfCarNullBySiteId(@RequestParam("siteId") int siteId) {
		log.info("��ѯվ���Ƿ��ѷ��䣬siteId="+siteId);
		try{
			return siteService.countRecordOfCarNullBySiteId(siteId);
		}catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			return 0;
		}
	}
}