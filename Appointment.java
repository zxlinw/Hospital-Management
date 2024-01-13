import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
* This is an Appointment class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Appointment implements Serializable, ReadFromFileable{
  private static final long serialVersionUID = 2L;
  String dateAndTime;
  String patientName;
  String doctorName;
  String endTime;
  
  /**
  * Explanation - constructs an instance of Appointment
  * @param dateAndTime - a String that represents the date and time of appointment
  * @param patientName - a String that represents the name of the patient
  * @param doctorName - a String that represents the name of the doctor
  * @param inputEndTime - a String that represents the end time of the appointment
  */
  public Appointment(String inputDateAndTime, String patientName, String doctorName, String inputEndTime){
    this.dateAndTime = inputDateAndTime;
    this.patientName = patientName;
    this.doctorName = doctorName;
    this.endTime = inputEndTime;
  }

  /**
  * Explanation - constructs an instance of Appointment without end time
  * @param inputDateAndTime - a String that represents the date and time of appointment
  * @param patientName - a Patient object that represents the patient
  * @param doctorName - a Doctor object that represents the doctor
  */
  public Appointment(String inputDateAndTime, String patientName, String doctorName){
    //Defaults end time to 30 minutes added to the initial time using time library
    this(inputDateAndTime, patientName, doctorName, DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.parse(inputDateAndTime.substring(11), DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(30)));
  }

  /**
  * Explanation - gets patient name
  * @return a String that is the patient's name
  */
  public String getPatientName(){
    return patientName;
  }

  /**
  * Explanation - gets doctor name
  * @return a String that is the doctor's name
  */
  public String getDoctorName(){
    return doctorName;
  }

  /**
  * Explanation - gets the date and time of appointment
  * @return a String that represents the date and time of the appointment
  */
  public String getDateAndTime(){
    return this.dateAndTime;
  }

  /**
  * Explanation - gets the end time of the appointment
  * @return a String that represents the ending time of the appointment
  */
  public String getAppointmentEndTime(){
    return this.endTime;
  }

  /**
  * Explanation - sets the doctor's name
  * @param doctorName - a String that will replace the doctor's current name
  */
  public void setDoctorName(String doctorName){
    this.doctorName = doctorName;
  }

  /**
  * Explanation - sets the date and time of appointment
  * @param dateAndTime - a String that will replace the old date and time
  */
  public void setDateAndTime(String dateAndTime){
    this.dateAndTime = dateAndTime;
  }

  /**
  * Explanation - sets appointment length
  * @param newEndTime - a String that will replace the old end time
  */
  public void setEndTime(String newEndTime){
    this.endTime = newEndTime;
  }

  /**
  * Explanation - displays the date, time, and doctor of appointment
  * @return a String that displays details of appointment
  */
  @Override
  public String toString(){
    return this.patientName + " has an appointment with Doctor " + this.doctorName + " on " + this.dateAndTime;
  }

  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if the file name is not found
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

  /**
  * Explanation - checks if an object is an instance of Appointment
  * @param obj - an Object that represets the Object being compared
  * @return a boolean that represents if the the object is an instance of Appointment
  */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Appointment)){
      return false;
    }
    Appointment other = (Appointment) obj;
    return dateAndTime.equals(other.dateAndTime) && doctorName.equals(other.doctorName) && patientName.equals(other.patientName) && endTime.equals(other.endTime);
  }
}