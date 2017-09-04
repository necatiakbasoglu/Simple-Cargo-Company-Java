
package domain;

import java.util.ArrayList;

public class SenderCustomer extends Customer {
	private ArrayList<Cargo> cargoes;
	
	public SenderCustomer(String nationalId,String name,String phoneNumber,String address){
		super( nationalId,name,phoneNumber,address);
		cargoes = new ArrayList<Cargo>();
	}
	
	public void addCargo(Cargo cargo){
		cargoes.add(cargo);
	}
	

}
