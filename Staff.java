import java.util.*;
/**
* This is a Staff abstract class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
abstract class Staff extends User{
  
  ArrayList<Patient>patients = new ArrayList<Patient>();

  /**
  * Explanation - constructs an instance of the Staff class
  * @param name - a String that represents the staff name
  * @param age - a number that represents the staff age
  * @param gender - a String that represents the staff gender
  * @param dateOfBirth - a String that represents the staff date of birth
  * @param phoneNumber - a String that represents the staff phone number
  * @param emailAddress - a String that represents the staff email address
  */
  public Staff(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress){
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
  }

  /**
  * Explanation - gets current patients
  * @param curDateAndTime - a String that represents the current date and time
  * @param schedule - a Schedule object that represents the schedule
  * @return an ArrayList that contains all the current patients
  */
  public ArrayList<Patient> getCurrentPatients(String curDateAndTime, Schedule schedule){
    ArrayList<Patient> unseen = new ArrayList<Patient>();
    ArrayList<Appointment> allAppoint = schedule.getAllAppointments();
    for (int j = 0; j < allAppoint.size(); j++){
      if (allAppoint.get(j).getDateAndTime().compareTo(curDateAndTime) > 0){
        unseen.add(searchPatient(allAppoint.get(j).getPatientName(), patients));
        break;
      }
    }
    return unseen;
  }

  /**
  * Explanation - searches if a patient's name is already in the database
  * @param name - a String that represents the patient's name
  * @param patients - an ArrayList that represents all the patients
  * @return a Patient that represents the patient being searched for
  */
  public Patient searchPatient(String name, ArrayList<Patient> patients){
    ArrayList<String> patientNames = new ArrayList<String>();
    int patientIndex = -1;
    for(int i = 0; i < patients.size() - 1; i++){
      patientNames.add(patients.get(i).getName());
    }
    if (patientNames.contains(name)){
      patientIndex = patientNames.indexOf(name);
      return patients.get(patientIndex);
    }
    return null;
  }
  
  /**
  * Explanation - a void method to change the user's username
  * @param newUsername - a new username as a String
  */
  public void changeUserUsername(String newUsername){
    super.setUsername(newUsername);
  }

  /**
  * Explanation - a void method used to change the user's password
  * @param newPassword - a String that will replace the old password
  */
  public void changeUserPassword(String newPassword){
    super.setPassword(newPassword);
  }
}