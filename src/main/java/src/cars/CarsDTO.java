package src.cars;

import java.util.ArrayList;
import java.util.List;

public class CarsDTO {

	List<CarDTO> cars = new ArrayList<CarDTO>();
	
	public CarDTO createCar(String reg, String make, String model, int year) {
		CarDTO car = new CarDTO ();
		car.setReg(reg);
		car.setMake(make);
		car.setModel(model);
		car.setYear(year);
		cars.add(car);
		return car;
	}
	
	public CarDTO getCarByReg(String reg) {
		
		for(CarDTO car : cars){
			if(car.getReg().equals(reg)) {
				return car;
			}
		}
		return null;
	}
	
	public List<CarDTO> getAllCars() {
		return cars;
	}
	
}
