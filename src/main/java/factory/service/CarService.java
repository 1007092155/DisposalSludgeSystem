package factory.service;

import java.util.List;
import java.util.Map;

import org.ietf.jgss.Oid;

import factory.entity.Car;
import factory.entity.User;

public interface CarService {
    public List<Car> queryAllCar();
	
	public Car queryCarByDriverId(int driverId);
	
	public Car queryCarByLicense(String license);
	
	public List<Car> queryCarByStatus(int status);
	
	public void deleteCar(int carId);
	
	public List<User> queryNoCarAssignedDriver();
	
	public int addCar(Car car);
	
	public void editCar(Car car);
	
	public List<Car> fuzzyQueryCar(String condition);

	public List<Car> queryAllCarOrderByStatus();

	public List<Car> queryMapCar(String queryStr);

	public List<Car> queryWorkerMapCar(int userId);

	public void editWorkerCarStatus(int userId, int status);
	
	public List<Car> queryCarWhichNotAssignDriver();

	public List<Car> queryTreatmentCarUnassign();
	
	public List<Car> queryCarrierUnassign();
	
	public Car assignCarrier(int siteId,double siteLongitude,double siteLatitude);
	
	public List<Car> queryCarBySiteId(int siteId);

}
