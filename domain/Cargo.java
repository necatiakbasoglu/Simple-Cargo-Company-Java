
package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cargo {
	private String uniqueId;
	private double weight;
	private double price;
	private String orderDate;
	
	private SenderCustomer sender;
	private ReceiverCustomer receiver;
	private Customer payer;
	
	public Cargo(double weight,String orderDate){
		setUniqueId();
		setWeight(weight);
		setOrderDate(orderDate);
	}

	//setter getter for id
	public String getUniqueId() {
		return uniqueId;
	}

	private void setUniqueId() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("ddMMyyyy-hhmmss");
		LocalDateTime now = LocalDateTime.now();
		this.uniqueId = formater.format(now);
	}
	
	//setter getter for weight
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	//setter getter for price
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//setter getter for order date
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	//setter getter for sender Customer
	public SenderCustomer getSender() {
		return sender;
	}

	public void setSender(SenderCustomer sender) {
		this.sender = sender;
	}

	//setter getter for receiver customer
	public ReceiverCustomer getReciever() {
		return receiver;
	}

	public void setReciever(ReceiverCustomer reciever) {
		this.receiver = reciever;
	}
	
	//setter getter for payer customer
	public Customer getPayer() {
		return payer;
	}

	public void setPayer(Customer payer) {
		this.payer = payer;
	}
	
}
