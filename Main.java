import java.util.*;
import java.io.*;

/**
* This is the Main class.
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Main {

  /**
  * Explanation - a static function that writes the an appointment serialized file
  * @param appointments - an ArrayList that represents all the appointments
  */
  Reception wesley = new Reception("wesley", 17, "male", "2005/12/07", "89123123", "email@email.com", "wesley", "password");
  
  public static void writeAppointments(ArrayList<Appointment> appointments) throws IOException {
    try (FileOutputStream os = new FileOutputStream("appointments.ser");
         ObjectOutputStream oos = new ObjectOutputStream(os)) {
      for (int i = 0; i < appointments.size(); i++) {
        oos.writeObject(appointments.get(i));
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
  * Explanation - a static function that writes reports into a serialized file
  * @param reports - an ArrayList that represents all reports
  */
  public static void writeReports(ArrayList<Report> reports) throws IOException {
    try (FileOutputStream os = new FileOutputStream("reports.ser");
         ObjectOutputStream oos = new ObjectOutputStream(os)) {
      for (int i = 0; i < reports.size(); i++) {
        oos.writeObject(reports.get(i));
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
  * Explanation - a static function that writes patients to serialized file
  * @param patients - an ArrayList that holds all patients
  */
  public static void writePatients(ArrayList<Patient> patients) throws IOException {
    try (FileOutputStream fos = new FileOutputStream("patients.ser");
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      for (int i = 0; i < patients.size(); i++) {
        oos.writeObject(patients.get(i));
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
  * Explanation - a static function that writes doctors to serialized file
  * @param doctors - an ArrayList that holds all doctors
  */
  public static void writeDoctors(ArrayList<Doctor> doctors) throws IOException {
    try (FileOutputStream fos = new FileOutputStream("doctors.ser");
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      for (int i = 0; i < doctors.size(); i++) {
        oos.writeObject(doctors.get(i));
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }


  /**
  * Explanation - a void function that runs the code
  * @throws IOException if files are not found
  * @throws ClassNotFoundException if the class being converted is not found
  */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Login in = new Login();
    Doctor temp = new Doctor("andre", 17, "male", "2005/12/08", "6475555555", "email@email.com", 1, "andre", "password");
    ArrayList<Doctor> tempA = new ArrayList<Doctor>();
    tempA.add(temp);
    writeDoctors(tempA);
    try{
      in.displayLogin();
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
}