
package presentation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.Cargo;
import domain.Company;
import domain.ReceiverCustomer;
import domain.SenderCustomer;
import domain.TransportationType;
import domain.Vehicle;
import fileAccess.DataAccessLayer;

public class Presentation {
	private JFrame  frame = null;
	private JPanel cardPanel = null;
	private JPanel firstPanel = null;
	private JPanel senderPanel = null;
	private JPanel checkerPanel = null;
	private JPanel recieverPanel = null;
	private JPanel writeAndShowPanel = null;
	private JPanel lastPanel = null;
	private Cargo newCargo = null;
	private CardLayout cardLayout = null;
	double weight=0.0d;
	JComboBox<String> lPayerComboBox;
	Vehicle emptyVehicle;
	String currentDate="";
	String calculatedPrice = "";
	String senderID="";
	String recieverID="";
	
	DefaultTableModel model ;
	JTable table ;
	
	public Presentation(){
		
		frame = new JFrame("Cargo Company");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(true);
		firstPanel = createPanel(Color.black, "Calculate Price");
		senderPanel = createPanel(Color.black, "Sender Information");
		recieverPanel = createPanel(Color.black, "Reciever Information");
		lastPanel = createPanel(Color.black, "Complete");
		checkerPanel = createPanel(Color.black, "Check Sender Id Existence");
		writeAndShowPanel = createPanel(Color.black, "Save or Add");
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		model =  new DefaultTableModel();
		model.addColumn("Cargo Id");
	    model.addColumn("Weight");
	    model.addColumn("Price");
	    model.addColumn("Date");
	    model.addColumn("Sender Id");
	    model.addColumn("Receiver Id");
	    model.addColumn("Payer Id");
	    table  =new JTable(model);
		
	}
	
	private JPanel createPanel(Color c,String title){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(c),title ));
		return panel;
	}
	
	private void setMaxSize(Component c){
		c.setMaximumSize(new Dimension(500,25));
	}
	private void firstPanel(Company company){
		JTextField textBoxForWeight = new JTextField(10); 
		JTextField textBoxForCurrentDate = new JTextField(10);
		
		//Dimension is fixed
		setMaxSize(textBoxForCurrentDate);
		setMaxSize(textBoxForWeight);
		
		JButton buttonAgree = new JButton();
		buttonAgree.setVisible(false);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("AIR");
		comboBox.addItem("RAIL");
		comboBox.addItem("ROAD");
		//Dimension is fixed
		setMaxSize(comboBox);
		
		JLabel labelForWeight = new JLabel("Please Enter Weight of Cargo: ");
		JLabel labelForDate = new JLabel("Please Enter Current Date: ");
		
		JLabel labelForAccepted = new JLabel("You Should Accept to Price");
		labelForAccepted.setForeground(Color.red);
		labelForAccepted.setVisible(false);
		JLabel labelInvalidWeight = new JLabel("Invalid Weight Please Try Again!");
		JLabel labelInvalidDate = new JLabel("Date format should be dd/mm/yyyy");
		labelInvalidWeight.setForeground(Color.red);
		labelInvalidWeight.setVisible(false);
		labelInvalidDate.setForeground(Color.red);
		labelInvalidDate.setVisible(false);
		
		JLabel labelAviable = new JLabel("There is no Vehicle that is aviable for you!");
		labelAviable.setForeground(Color.red);
		labelAviable.setVisible(false);
		
		JLabel labelForPrice = new JLabel();
		JCheckBox checkBox = new JCheckBox();
		checkBox.setVisible(false);
		
		JButton buttonCalculate = new JButton("Calculate");
		buttonCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					weight = Double.parseDouble(textBoxForWeight.getText());
					currentDate =textBoxForCurrentDate.getText();
					TransportationType type = TransportationType.valueOf(comboBox.getSelectedItem().toString());
					String regex = "([0-9]{2})/([0-9]{2}/([0-9]){4})";
					if((textBoxForCurrentDate.getText().matches(regex))){
						emptyVehicle=company.availableVehicle(type, weight, currentDate);
						if(emptyVehicle!=null){
							labelInvalidWeight.setVisible(false);
							labelInvalidDate.setVisible(false);
							labelAviable.setVisible(false);
							labelForPrice.setVisible(true);
							calculatedPrice =String.valueOf(company.calculatePrice(type, weight));
							labelForPrice.setText(calculatedPrice);
							checkBox.setText("The price is good for me");
							checkBox.setVisible(true);
							buttonAgree.setVisible(true);
							
						}else{
							labelAviable.setVisible(true);
							labelInvalidDate.setVisible(false);
						}
					}else if(textBoxForCurrentDate.getText()==""){
						labelInvalidWeight.setVisible(false);
						labelAviable.setVisible(false);
						labelInvalidDate.setVisible(true);
					}else{
						labelInvalidWeight.setVisible(false);
						labelInvalidDate.setVisible(true);
					}
				}catch(NumberFormatException ex){
					labelInvalidWeight.setVisible(true);  			
					//if textbox for weight is null
				}
			}
		});
		
		
		buttonAgree.setText("I AGREE");
		buttonAgree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				if(checkBox.isSelected()){
					newCargo = new Cargo(weight,currentDate);
					newCargo.setOrderDate(textBoxForCurrentDate.getText());
					newCargo.setPrice(Double.parseDouble(calculatedPrice));
					newCargo.setWeight(Integer.parseInt(textBoxForWeight.getText()));
					textBoxForCurrentDate.setText("");
					textBoxForWeight.setText("");
					checkBox.setVisible(false);
					checkBox.setSelected(false);
					buttonAgree.setVisible(false);
					labelInvalidWeight.setVisible(false);
					labelAviable.setVisible(false);
					labelInvalidDate.setVisible(false);
					labelForPrice.setVisible(false);
					labelForAccepted.setVisible(false);
					cardLayout.next(cardPanel);
				}
				else{
					labelForAccepted.setVisible(true);
				}
			}
		} );
		
		firstPanel.add(labelForWeight);
		firstPanel.add(textBoxForWeight);
		firstPanel.add(labelInvalidWeight);
		firstPanel.add(labelForDate);
		firstPanel.add(textBoxForCurrentDate);
		firstPanel.add(labelInvalidDate);
		firstPanel.add(comboBox);
		firstPanel.add(labelAviable);
		firstPanel.add(buttonCalculate);
		firstPanel.add(labelForPrice);
		firstPanel.add(checkBox);
		firstPanel.add(labelForAccepted);
		firstPanel.add(buttonAgree);
	}
	
	private void initalLabel(JLabel label){
		label.setForeground(Color.red);
		label.setVisible(false);
	}
	
	private void checkPanel(Company company){
		JLabel sNationalIdLabel = new JLabel("Enter sender national ID: ");
		JTextField sNationalTextField = new JTextField(15);
		
		JLabel sLabelInvalidId = new JLabel("Invalid National ID!");
		initalLabel(sLabelInvalidId);
		
		//Dimension is fixed
		setMaxSize(sNationalTextField);
		
		JButton sCheckButton = new JButton("Check And Next");
		sCheckButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sNationalTextField.getText().matches("\\d+")){
					SenderCustomer senderCostumer;
					senderID = sNationalTextField.getText();
					senderCostumer = company.getSenderCustomer(senderID);
					if(senderCostumer!=null){
						newCargo.setSender(senderCostumer);
						sLabelInvalidId.setVisible(false);
						sNationalTextField.setText("");
						cardLayout.next(cardPanel);
						cardLayout.next(cardPanel);
					}else{
						sNationalTextField.setText("");
						cardLayout.next(cardPanel);
					}
				}else{
					sLabelInvalidId.setVisible(true);
				}
				
				// TODO Auto-generated method stub
				
			}
		});
		
		checkerPanel.add(sNationalIdLabel);
		checkerPanel.add(sNationalTextField);
		checkerPanel.add(sNationalIdLabel);
		checkerPanel.add(sLabelInvalidId);
		checkerPanel.add(sCheckButton);
	}
	
	private void senderPanel(Company company){
		
		JLabel sLabelName = new JLabel("Enter sender name: ");
		JTextField sTextFieldName = new JTextField(15);
		
		JLabel sLabelInvalidName = new JLabel("Invalid name!!");
		initalLabel(sLabelInvalidName);
		
		JLabel sLabelNull = new JLabel("Please fill all fields!!");
		initalLabel(sLabelNull);
		
		
		JLabel sLabelPhone = new JLabel("Enter sender phone number");
		JTextField sTextFieldPhone = new JTextField(15);
		
		JLabel sLabelAddress = new JLabel("Enter sender address");
		JTextField sTextFieldAddress = new JTextField(15);
		
		//Dimension is fixed
		setMaxSize(sTextFieldAddress);
		setMaxSize(sTextFieldPhone);
		setMaxSize(sTextFieldName);
		
		JButton sSaveButton = new JButton("NEXT");
		sSaveButton.addActionListener(new ActionListener() {
			SenderCustomer newSender=null;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!sTextFieldName.getText().equals("") &&
						!sTextFieldAddress.getText().equals("") && !sTextFieldPhone.getText().equals("")){
					String senderName = sTextFieldName.getText();
					String senderAdress = sTextFieldAddress.getText();
					String senderPhone = sTextFieldPhone.getText();
					try{
						newSender = new SenderCustomer(senderID,
								senderName,senderPhone,senderAdress);
						
						newCargo.setSender(newSender);
						sTextFieldName.setText("");
						sTextFieldAddress.setText("");
						sTextFieldPhone.setText("");
						sLabelInvalidName.setVisible(false);
						sLabelNull.setVisible(false);
						cardLayout.next(cardPanel);
					}catch(NumberFormatException e){
						sLabelNull.setVisible(true);
					}
				}else{
					sLabelNull.setVisible(true);
				}
			}
		});
		
		senderPanel.add(sLabelName);
		senderPanel.add(sTextFieldName);
		senderPanel.add(sLabelInvalidName);
		senderPanel.add(sLabelPhone);
		senderPanel.add(sTextFieldPhone);
		senderPanel.add(sLabelAddress);
		senderPanel.add(sTextFieldAddress);
		senderPanel.add(sLabelNull);
		senderPanel.add(sSaveButton);
	}
	
	private void recieverPanel(){
		
		JLabel rNationalIdLabel = new JLabel("Enter reciever national ID: ");
		JTextField rNationalTextField = new JTextField(15);
		
		JLabel rLabelInvalidId = new JLabel("Invalid National ID!");
		initalLabel(rLabelInvalidId);
		
		JLabel rLabelName = new JLabel("Enter reciever name: ");
		JTextField rTextFieldName = new JTextField(15);
		
		JLabel rLabelInvalidName = new JLabel("Invalid name!!");
		initalLabel(rLabelInvalidName);
		
		JLabel rLabelPhone = new JLabel("Enter reciever phone number");
		JTextField rTextFieldPhone = new JTextField(15);
		
		JLabel rLabelAddress = new JLabel("Enter reciever address");
		JTextField rTextFieldAddress = new JTextField(15);
		
		JLabel rLabelNull = new JLabel("Please fill all fields!!");
		initalLabel(rLabelNull);
		
		//Dimension size is fixed
		setMaxSize(rTextFieldAddress);
		setMaxSize(rNationalTextField);
		setMaxSize(rTextFieldPhone);
		setMaxSize(rTextFieldName);
		
		JButton rSaveButton = new JButton("NEXT");
		rSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!rTextFieldName.getText().equals("") && !rNationalTextField.getText().equals("") &&
						!rTextFieldAddress.getText().equals("") && !rTextFieldPhone.getText().equals("")){
					recieverID = rNationalTextField.getText();
					String recieverName =rTextFieldName.getText();
					String recieverAdress =rTextFieldAddress.getText();
					String recieverPhone =rTextFieldPhone.getText();
					try{
						ReceiverCustomer newReciever = new ReceiverCustomer(recieverID,recieverName,recieverPhone,
								recieverAdress);
						
						newCargo.setReciever(newReciever);
						
						lPayerComboBox.removeAllItems();
						lPayerComboBox.addItem(newCargo.getSender().getNationalId());
						lPayerComboBox.addItem(newCargo.getReciever().getNationalId());
						
						rTextFieldAddress.setText("");
						rTextFieldName.setText("");
						rTextFieldPhone.setText("");
						rNationalTextField.setText("");
						rLabelInvalidId.setVisible(false);
						rLabelInvalidName.setVisible(false);
						rLabelNull.setVisible(false);
						cardLayout.next(cardPanel);
					}catch(NumberFormatException ex){
						rLabelNull.setVisible(true);
					}
				}else
					rLabelNull.setVisible(true);
			}
		});
		
		recieverPanel.add(rNationalIdLabel);
		recieverPanel.add(rNationalTextField);
		recieverPanel.add(rLabelInvalidId);
		recieverPanel.add(rLabelName);
		recieverPanel.add(rTextFieldName);
		recieverPanel.add(rLabelInvalidName);
		recieverPanel.add(rLabelPhone);
		recieverPanel.add(rTextFieldPhone);
		recieverPanel.add(rLabelAddress);
		recieverPanel.add(rTextFieldAddress);
		recieverPanel.add(rLabelNull);
		recieverPanel.add(rSaveButton);
	}
	
	private void lastPanel(Company company){
		JLabel lLabelChoice = new JLabel("Please Select payer Customer according to Id's");
		
		lPayerComboBox = new JComboBox<String>();
		
		setMaxSize(lPayerComboBox);
		JButton lAcceptButton = new JButton("Complete");
		lAcceptButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lPayerComboBox.getSelectedItem().equals(newCargo.getReciever().getNationalId()))
					newCargo.setPayer(newCargo.getReciever());
				else
					newCargo.setPayer(newCargo.getSender());
				
				company.addCargo(newCargo);
				// Cargo is completed
				
				newCargo.getReciever().setCargo(newCargo);
				newCargo.getSender().addCargo(newCargo);   
				company.addSenderCustomer(newCargo.getSender());
				
				emptyVehicle.addCargo(newCargo);
				
				model.addRow(new Object[] { newCargo.getUniqueId(), newCargo.getWeight(),
			    		newCargo.getPrice(),newCargo.getOrderDate(),newCargo.getSender().getNationalId(),
			    		newCargo.getReciever().getNationalId(),newCargo.getPayer().getNationalId()});
				
				cardLayout.next(cardPanel);
				
			}
		});
		
		
		lastPanel.add(lLabelChoice);
		lastPanel.add(lPayerComboBox);
		lastPanel.add(lAcceptButton);
		
	}
	
	private void writeAndShowPanel(Company company,DataAccessLayer dataAccess){
		
		JButton lBackAgain = new JButton("Add New Cargo");
		JButton lSaveAndQuit = new JButton("Save to file and Quit");
		
		JPanel tablePanel = createPanel(Color.black, "Cargoes Information");
		tablePanel.add(new JScrollPane(table));
		
		writeAndShowPanel.add(tablePanel);
		writeAndShowPanel.add(lBackAgain);
		writeAndShowPanel.add(lSaveAndQuit);
		
		
		lBackAgain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardLayout.next(cardPanel);
			}
		});
		
		
		lSaveAndQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dataAccess.saveInFile(company);
				System.exit(-1);
			}
		});
	}
	
	public void run(Company c){
		DataAccessLayer d  = new DataAccessLayer();
		d.fillListFromFile(c);
		firstPanel(c);
		senderPanel(c);
		checkPanel(c);
		recieverPanel();
		lastPanel(c);
		writeAndShowPanel(c,d);
		firstPanel.setSize(200, 200);
		cardPanel.add(firstPanel);
		cardPanel.add(checkerPanel);
		cardPanel.add(senderPanel);
		cardPanel.add(recieverPanel);
		cardPanel.add(lastPanel);
		cardPanel.add(writeAndShowPanel);
		frame.add(cardPanel);
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
			int choice = JOptionPane.showConfirmDialog(frame, "If you close, Cargo informations will not be saved\n"
					+ "Do you want to close?","Close",
					JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE);
			if(choice == JOptionPane.YES_OPTION)
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}	
		});
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
