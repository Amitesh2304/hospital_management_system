package hospital_managament_system;
import java.util.*;

import java.sql.*;
public class Patients {
	private Connection con;
	private Scanner sc;


	public Patients(Connection con, Scanner sc) {
		super();
		this.con = con;
		this.sc = sc;
	}

	public  void addPatient() {
		System.out.print("Enter Patient's name: ");
		String patientName = sc.next();
		
		System.out.print("Enter Patient's age: ");
		int patientAge = sc.nextInt();
		System.out.print("Enter Patient's gender: ");
		String gender = sc.next();
		try {
			String query = "INSERT INTO patients (name,age,gender) 	VALUES(?,?,?)";
			PreparedStatement pre = con.prepareStatement(query);
			pre.setString(1,patientName );
			pre.setInt(2, patientAge);
			pre.setString(3,gender);
			int affectedRows = pre.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient Details added successfully!!!!");

			}
			else {
				System.err.println("Failed!!! to add details.");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}


	}
	
	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement pre = con.prepareStatement(query);
			ResultSet resultset = pre.executeQuery();
			System.out.println("Patients");
			System.out.println("+------------+------------------------------+---------+--------+");
			System.out.println("| Patient id | Patient's Name               | Age     | Gender |");
			System.out.println("+------------+------------------------------+---------+--------+");
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getNString("name");
				int age = resultset.getInt("age");
				String gender = resultset.getString("gender");
				System.out.printf("| %-10s | %-28s | %-7s | %-6s |\n",id,name,age,gender);
				System.out.println("+------------+------------------------------+---------+--------+");
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query = "select * FROM  patients where id = ?";
		
		try {
			PreparedStatement pre = con.prepareStatement(query);
			pre.setInt(1,id);
			ResultSet resultset = pre.executeQuery();
			if(resultset.next()) {
				return true;
			}
			else {
				return false;
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
