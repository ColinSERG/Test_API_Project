package src.api;

import static spark.Spark.*;

import src.cars.*;
import src.exceptions.ResponseError;
import src.util.*;

public class Main {

	public static void main(String[] args) {

		CarsDTO carsDto = new CarsDTO();
		JsonUtil jsonUtil = new JsonUtil();
		ParseInt parseInt = new ParseInt();
		//Used to show static analysis error
		int unusedVariable;

		get("/", (req, res) -> {
			res.status(200);
			return "Endpoint active.";
			},jsonUtil.json());
		
		get("/cars", (req, res) -> carsDto.getAllCars(), jsonUtil.json());

		get("/car/:reg", (req, res) -> {
			String reg = req.params(":reg");
			CarDTO car = carsDto.getCarByReg(reg);
			if (car != null) {
				res.status(200);
				return car;
			}
			res.status(400);
			return new ResponseError("No car with registration '%s' found", reg);
		}, jsonUtil.json());

		post("/car", (req, res) -> {
			String body = req.body();
			String make = jsonUtil.parseJsonString(body, "make");
			String model = jsonUtil.parseJsonString(body, "model");
			String year = jsonUtil.parseJsonString(body, "year");
			String reg = jsonUtil.parseJsonString(body, "reg");
			if (make == null || model == null || year == null || reg == null) {
				res.status(400);
				return new ResponseError("Parameters reg, make, model and year must be supplied : '%s'", body);
			}
			try {
				int yearInt = parseInt.parseInt(year);
				carsDto.createCar(reg, make, model, yearInt);
				res.status(200);
			} catch (Exception e) {
				res.status(400);
				return new ResponseError("Parameter reg must be a number.", reg);
			}
			return "Car has been added.";
		}, jsonUtil.json());
	}

}
