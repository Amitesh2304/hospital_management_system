package hospital_managament_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctors {
	private Connection con;
	


	public Doctors(Connection con) {
		super();
		this.con = con;
		
	}

	
	
	public void viewDoctors() {
		String query = "select * from doctors";
		try {
			PreparedStatement pre = con.prepareStatement(query);
			ResultSet resultset = pre.executeQuery();
			System.out.println("Doctors");
			System.out.println("+-----------+------------------------------+-----------------+");
			System.out.println("| Doctor id | Doctor's Name                | Specialization  |");
			System.out.println("+-----------+------------------------------+-----------------+");
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String  specialization = resultset.getString("specialization");
				System.out.printf("| %-9s | %-29s | %-14s |\n",id,name,specialization);
				System.out.println("+-----------+------------------------------+-----------------+");
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) {
		String query = "select * FROM  doctors where id = ?";
		
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
