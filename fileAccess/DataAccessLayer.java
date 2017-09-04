
package fileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

import domain.Cargo;
import domain.Company;
import domain.TransportationType;
import domain.Vehicle;

public class DataAccessLayer {
	private Scanner scanner=null;
	private StringTokenizer token = null;
	
	
	public void fillListFromFile(Company company){
		try {
			scanner = new Scanner(new File("vehicles.dat"));
			String id="";
			String date="";
			double weight=0.0d;
			TransportationType type=null;
			Vehicle newVehicle;
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				token = new StringTokenizer(line,", ");
				while(token.hasMoreTokens()){
					id = token.nextToken();
					date = token.nextToken();
					type = TransportationType.valueOf(token.nextToken());
				    weight = Double.parseDouble(token.nextToken());
				}
				newVehicle = new Vehicle(id,type,weight);
				newVehicle.setDepatureDate(date);
				company.addVehicle(newVehicle);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner.close();
	}
	
	
	public void saveInFile(Company company){
		PrintWriter outputStream = null;
		double total=0;
		try{
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDateTime now = LocalDateTime.now();
			outputStream = new PrintWriter(new FileOutputStream(formater.format(now)+"_cargoesInformation.dat"));
			
			for(Cargo cargo : company.cargoes){
				outputStream.println(cargo.getUniqueId()+", "+cargo.getWeight()+", "+
			cargo.getPrice()
				+", "+cargo.getOrderDate()+", "+cargo.getSender().getNationalId()+", "+
						cargo.getReciever().getNationalId()+", "
				+cargo.getPayer().getNationalId());
				total+=cargo.getPrice();
				
			}
			outputStream.print("Total price is: "+total+"$");
			outputStream.close();
		}catch(FileNotFoundException e){
			e.getMessage();
		}

	}
}
