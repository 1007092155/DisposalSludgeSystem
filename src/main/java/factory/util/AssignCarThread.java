package factory.util;

import java.util.List;
import java.util.concurrent.Callable;

import factory.dao.CarDao;
import factory.entity.Car;

//���ȳ������߳�
public class AssignCarThread implements Callable<List<Car>>{

	private CarDao carDao;
	private int carType; //0��ʾ������1��ʾ���䳵
	
	public AssignCarThread() {
		
	}
	
	public AssignCarThread(CarDao carDao,int carType) {
		this.carDao=carDao;
		this.carType=carType;
	}
	
	@Override
	public List<Car> call() throws Exception {
		System.out.println("���ó��������㷨");
		if(carType==0) {
			while(true) { //��ѯ�������ദ��
				List<Car> treatmentCarUnassign=carDao.queryTreatmentCarUnassign();
				if(treatmentCarUnassign.size()!=0) {
					System.out.println("�г�");
					return treatmentCarUnassign;
				}
				System.out.println("û��,5���ٴε���");
				Thread.sleep(5000); //5�����һ��
			}
		}
		else if (carType==1) {  //��ѯ�����������䳵
			while(true) {
				List<Car> transportCarUnassign=carDao.queryCarrierUnassign();
				if(transportCarUnassign.size()!=0) {
					System.out.println("�г�");
					return transportCarUnassign;
				}
				System.out.println("û��,5����ٴε���");
				Thread.sleep(5000); //5�����һ��
			}
		}
		return null;
	}

	
	public CarDao getCarDao() {
		return carDao;
	}
	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}
	public int getCarType() {
		return carType;
	}
	public void setCarType(int carType) {
		this.carType = carType;
	}


}
