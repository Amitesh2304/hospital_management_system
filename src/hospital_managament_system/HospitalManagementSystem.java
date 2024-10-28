package hospital_managament_system;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "Amitesh";
	private static final String password = "amitesh";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);

		try {
			Connection con = DriverManager.getConnection(url,username,password);
			Patients patients = new Patients(con, sc);
			Doctors doctors = new Doctors(con);
			while(true) {
				System.out.println("============ Hospital Management System ============");
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");

				System.out.println("Enter Your Choice!!!");
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
					//Add Patients
					patients.addPatient();
					System.out.println();
					break;
				case 2: 
					//view Patients
					patients.viewPatients();
					System.out.println();
					break;
				case 3: 
					// View Doctors
					doctors.viewDoctors();
					System.out.println();
					break;
				case 4: 
					//book appointment
					bookAppointement(patients,doctors,con,sc);
					System.out.println();
					break;
				case 5:
					System.out.println("Thank for using Hospital Management System.");
					return;
					
				default:
					System.out.println("Invalid Input!!! Enter a valid choices.");
					System.out.println();
					break;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void bookAppointement(Patients patient,Doctors doctor,Connection con,Scanner sc) {
		System.out.println("Enter Patient's id: ");
		int patientId = sc.nextInt();
		System.out.println("Enter Doctor's id: ");
		int doctorId = sc.nextInt();
		System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
		String appointementdate =sc.next();

		if(patient.getPatientById(patientId) ) {
			if( doctor.getDoctorById(doctorId)) {
				if(checkDoctorAvailablity(doctorId,appointementdate,con)) {
					String appointmentQuery = "INSERT INTO appointment(patient_id,doctors_id,appointment_date) VALUES(?,?,?)";
					try {
						PreparedStatement pre = con.prepareStatement(appointmentQuery);
						pre.setInt(1, patientId);
						pre.setInt(2,doctorId);
						pre.setString(3, appointementdate);
						int rowsAffected = pre.executeUpdate();
						if(rowsAffected > 0) {
							System.out.println("Appointment Booked Successfully!!!");
						}
						else {
							System.err.println("Failed to book Appointment!!!!");
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Doctor is not available on this date.");
				}
					
			}
			else {
				System.out.println("Doctor Does not exist with this id.");
			}
		}
			else{
				System.out.println("Patient Does not exist with this id.");
			}
		

	}
	
	public static boolean checkDoctorAvailablity(int doctorId,String appointmentDate, Connection con) {
		String query = "SELECT COUNT(*) FROM appointment WHERE doctors_id = ? AND appointment_date = ?";
		try {
			PreparedStatement pre = con.prepareStatement(query);
			pre.setInt(1,doctorId);
			pre.setString(2, appointmentDate);
			ResultSet resultset = pre.executeQuery();
			if(resultset.next()) {
				int count = resultset.getInt(1);
				if(count == 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

}
