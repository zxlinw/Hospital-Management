import java.util.*;
import java.io.*;

/**
* This is a Report class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Report implements Serializable, ReadFromFileable{
  private static final long serialVersionUID = -9027040502307744183L;
  String patientID;
  String doctor;
  String testsDone;
  String patientStatus;
  String timeAdmitted;
  String medicationPrescribed;
  String results;
  String height;
  String weight;
  String bloodtype;

  /**
  * Explanation - constructs an instance of Report
  * @param patientID - a String representing the patient's ID
  * @param doctor - a String representing the doctor
  * @param timeAdmitted - a String representing the time the patient was admitted
  * @param patientStatus - a String representing the patient's status
  * @param medicationPrescribed - a String representing the medicine prescribed to patient
  * @param testsDone - a String representing the tests done for patient
  * @param results - a String representing results of patient
  * @param bloodType - a String representing blood type of patient
  * @param height - a String representing height of patient
  * @param weight - a String representing weight of patient
  */
  public Report(String patientID, String doctor, String timeAdmitted, String patientStatus, String medicationPrescribed, String testsDone, String results, String bloodType, String height, String weight){
    this.patientID = patientID;
    this.doctor = doctor;
    this.testsDone = testsDone;
    this.patientStatus = patientStatus;
    this.timeAdmitted = timeAdmitted;
    this.medicationPrescribed = medicationPrescribed;
    this.results = results;
    this.bloodtype = bloodType;
    this.height = height;
    this.weight = weight;
  }

  /**
  * Explanation - gets patient ID
  * @return a String stored in patientID
  */
  public String getPatientID(){
    return this.patientID;
  }

  /**
  * Explanation - gets doctor
  * @return a String stored in doctor
  */
  public String getDoctor(){
    return this.doctor;
  }

  /**
  * Explanation gets tests done
  * @return a String stored in testsDone
  */
  public String getTestsDone() {
    return this.testsDone;
  }

  /**
  * Explanation - gets patient status
  * @return a String stored in patientStatus
  */
  public String getPatientStatus(){
    return this.patientStatus;
  }

  /**
  * Explanation - gets medication prescribed
  * @return a String stored in medicationPrescribed
  */
  public String getMedicationPrescribed(){
    return this.medicationPrescribed;
  }

  /**
  * Explanation - gets time admitted
  * @return a String stored in timeAdmitted
  */
  public String getTimeAdmitted(){
    return this.timeAdmitted;
  }

  /**
  * Explanation - gets results
  * @return a String stored in results
  */
  public String getResults(){
    return this.results;
  }

  
  /**
  * Explanation - prints report
  * @return a String that contains report details
  */
  @Override
  public String toString(){
    return "Patient: " + this.patientID
        + "\nDoctor: " + this.doctor
        + "\nTests Done: " + this.testsDone
        + "\nPatient Status: "+ this.patientStatus
        + "\nTime Admitted: " + this.timeAdmitted
        + "\nMedication(s) Prescribed: " + this.medicationPrescribed
        + "\nResults: " + this.results;
  }

  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if the file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  @Override
  public <T> ArrayList<T> readFromFile(String fileName, Class<T> clazz) throws IOException, ClassNotFoundException {
    ArrayList<T> objects = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream(fileName);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
      while (true) {
      objects.add(clazz.cast(ois.readObject()));
      }
    } 
    catch (EOFException e){
      return objects;
    }
    catch (Exception e){
      e.printStackTrace();
    }
    finally{
      return objects;
    }
  }
}