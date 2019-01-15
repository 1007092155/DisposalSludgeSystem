package factory.util;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import factory.entity.Car;
import factory.entity.Record;
import factory.entity.Site;
import factory.entity.Sludge;
import factory.service.CarService;
import factory.service.RecordService;
import factory.service.SludgeService;

public class AssignCarForReocrdThread implements Runnable{
	
	private RecordService recordService;
	
	private CarService carService;
	
	private SludgeService sludgeService;
	
	private int recordId;
	
	private Site site;
	
	private static Log log=LogFactory.getLog(AssignCarForReocrdThread.class);
	
	public  AssignCarForReocrdThread() {
		// Ĭ�Ϲ�����
	}
	public AssignCarForReocrdThread(RecordService recordService,CarService carService,SludgeService sludgeService,int recordId,Site site) {
		this.recordService=recordService;
		this.carService=carService;
		this.sludgeService=sludgeService;
		this.recordId=recordId;
		this.site=site;
	}

	@Override
	public void run() {
		//�ȷ��䴦��
		assinTreatmentCar();
		//�ٷ������䳵
		assignCarrier();
		
	}
	
	public  void assinTreatmentCar() { //��������¼�ͬʱ��������˾����ô�죿
		while(true) {
			List<Car> unAssinTreatmentCar=carService.queryTreatmentCarUnassign();
			if(unAssinTreatmentCar.size()!=0) { //������ڿ��еĴ���
				//ѡ������Ĵ���
				Car disPacherTreatmentCar=selectMinDistanceCar(unAssinTreatmentCar);
				//�����䳵id�浽record��¼��
				recordService.updateCarId(recordId, disPacherTreatmentCar.getId());
				//�޸ĳ���״̬ 3��ʾ�ѷ��仹δ����
				carService.editWorkerCarStatusAndSiteId(disPacherTreatmentCar.getId(), 3, site.getId());
				log.info("Ϊ"+recordId+"������ ���䴦��:"+disPacherTreatmentCar.getLicense());
				break;
			}
			log.info("��Ϊ���д���,3������·���");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	public void assignCarrier(){
		while(true) {
			List<Car> unAssignCarrier=carService.queryCarrierUnassign();
			if(unAssignCarrier.size()!=0) { //������ڿ��е����䳵
				Car disPacherCarrier=selectMinDistanceCar(unAssignCarrier);
				log.info("Ϊ"+recordId+"������ �������䳵:"+disPacherCarrier.getLicense());
				Sludge sludge=new Sludge();
				sludge.setRecordId(recordId);
				sludge.setStatus(6);
				sludge.setTranscarId(disPacherCarrier.getId());
				sludgeService.addSludge(sludge);
				carService.editWorkerCarStatusAndSiteId(disPacherCarrier.getId(), 3, site.getId());
				log.info(sludge.getId());
				break;
			}
			log.info("��Ϊ�������䳵,3������·���");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param cars
	 * ���ݾ���ѡ������ĳ���
	 * @return
	 */
	public Car selectMinDistanceCar(List<Car> cars) {
		double minDistance = Double.MAX_VALUE;
		Car car = new Car();
		for(int i = 0; i < cars.size();i++){
			double dis = GpsUtil.getDistance(Double.valueOf(site.getLongitude()),Double.valueOf(site.getLatitude()),cars.get(i).getLongitude(),cars.get(i).getLatitude());
			System.out.println("id: "+ cars.get(i).getId() + "  "+ dis);
			if(dis < minDistance){
				minDistance = dis;
				car = cars.get(i);
			}
		}
		return car;
	}

}
