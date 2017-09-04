package domain;

import java.util.ArrayList;

public class Vehicle {
	private String depatureDate;
	private String uniqueId;
	private TransportationType type;
	private ArrayList<Cargo> cargoesCarried;
	private double upperLimit;
	
	public Vehicle(){
		cargoesCarried = new ArrayList<Cargo>();
	}
	
	public Vehicle(String uniqueId,TransportationType type,double upperLimit){
		cargoesCarried = new ArrayList<Cargo>();
		setUniqueId(uniqueId);
		setType(type);
		setUpperLimit(upperLimit);
	}
	
	public double getWeightOfCargoes(){
		double total=0.0d;
		if(cargoesCarried.size()>0)
			for(Cargo c:cargoesCarried)
				total+=c.getWeight();
		return total;
	}
	
	//Getter and Setter for unique id
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	//Getter and Setter for TransportationType
	public TransportationType getType() {
		return type;
	}
	public void setType(TransportationType type) {
		this.type = type;
	}
	
	public void addCargo(Cargo cargo){
		cargoesCarried.add(cargo);
	}
	
	//Getter and Setter for TransportationType
	public double getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	//setter getter for date
	public String getDepatureDate() {
		return depatureDate;
	}
	public void setDepatureDate(String depatureDate) {
		this.depatureDate = depatureDate;
	}
}
