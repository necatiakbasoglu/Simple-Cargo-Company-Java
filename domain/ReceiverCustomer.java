
package domain;

public class ReceiverCustomer extends Customer{
	private Cargo cargo;
	
	public ReceiverCustomer(String nationalId,String name,String phoneNumber,String address){
		super( nationalId,name,phoneNumber,address);
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}
