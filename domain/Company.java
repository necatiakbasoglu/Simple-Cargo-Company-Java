
package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import presentation.Presentation;


public class Company {
	public ArrayList<Cargo> cargoes;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<SenderCustomer> senderCustomers;
	
	public Company(){
		vehicles = new ArrayList<Vehicle>();
		cargoes = new ArrayList<Cargo>();
		senderCustomers = new ArrayList<SenderCustomer>();
	}
	
	public Vehicle availableVehicle(TransportationType type, double weight ,String currentDate){
		for(Vehicle v: vehicles)
			if(v.getType().equals(type))
				if((isDateAvailable(currentDate,v))&&isWeightAvailable(weight, v))
					return v;
		return null;
	}
	/////
	private Date convertDate(String date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date newDate=null;
		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	private boolean isDateAvailable(String currentDate,Vehicle vehicle){
		boolean flag = false;
		Date date  = convertDate(currentDate);
		Date vehicleDate  = convertDate(vehicle.getDepatureDate());
		if(date.compareTo(vehicleDate)<=0)
			flag=true;
		return flag;
	}
	//available
	private boolean isWeightAvailable(double weight,Vehicle vehicle){
		boolean flag = false;
		if(weight+vehicle.getWeightOfCargoes()<=vehicle.getUpperLimit())
			flag=true;
		return flag;
	}
	////
	public SenderCustomer getSenderCustomer(String nationalId){
		for(SenderCustomer s: senderCustomers)
			if(s.getNationalId().equals(nationalId))
				return s;			
		return null;
	}
	
	public double calculatePrice(TransportationType type, double weigth){
		double price=0.0;
		switch(type){
		case AIR:
			price = weigth * 5.0;
			break;
		case ROAD:
			price = weigth * 3.5;
			break;
		case RAIL:
			price = weigth * 2.0;
			break;
		}
		return price;
	}
	
	public void addCargo(Cargo cargo){
		cargoes.add(cargo);
	}
	
	public void addVehicle(Vehicle vehicle){
		vehicles.add(vehicle);
	}
	
	public void addSenderCustomer(SenderCustomer senderCustomer){
		senderCustomers.add(senderCustomer);
	}
	
	public void start(){
		Presentation present = new Presentation();
		present.run(this);
	}
}
