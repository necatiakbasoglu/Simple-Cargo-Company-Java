
package domain;

public class Customer {
	private String nationalId;
	private String name;
	private String phoneNumber;
	private String address;
	
	public Customer(String nationalId,String name,String phoneNumber,String address){
		setNationalId(nationalId);
		setName(name);
		setAddress(address);
		setPhoneNumber(phoneNumber);
	}
	
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
