package factory.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import factory.entity.Car;

public interface CarDao {
	public List<Car> queryAllCar();
	
	
	public Car queryCarByDriverId(@Param("driverId") int driverId);
	
	public Car queryCarById(@Param("carId") int carId);
	
	public Car queryCarByLicense(@Param("license") String license);
	
	public List<Car> queryCarByStatus(@Param("status") int status);
	
	public void deleteCar(@Param("carId") int carId);
	
	public void addCar(Car car);
	
	public void editCar(Car car);
	
	public List<Car> fuzzyQueryCar(@Param("condition") String condition);
	
	/*public List<Car> fuzzyQueryCarWithNoDriver(@Param("condition") String condition);*/
	
	public void updateSenserIdSet(@Param("carId") int carId,@Param("sensorIdSet") String sensorIdSet);
	public String querySensorIdSetByCarId(@Param("carId") int carId);

	public int queryNullCarNum();


	public List<Car> queryAllCarOrderByStatus();


	public List<Car> queryMapCar(String queryStr);


	public List<Car> queryWorkerMapCar(@Param("userId") int userId);

	public void editWorkerCarStatus(@Param("userId") int userId,@Param("status") int status);
	
	public Car queryCarByRecordId(@Param("recordId") int recordId);
	
	public void editWorkerCarStatusAndSiteId(@Param("carId") int carId,@Param("status") int status,@Param("siteId") int siteId);
	
	public List<Car> queryCarWhichNotAssignDriver();
	
	public void updateDriverId(@Param("carId") int carId,@Param("driverId") int driverId);
	
	public void setDriverIdToNUll(@Param("driverId") int driverId);
	
	public List<Car> queryTreatmentCarUnassign();
	
	public List<Car> queryCarrierUnassign();
	
	public List<Car> queryCarBySiteId(@Param("siteId") int siteId);
}

