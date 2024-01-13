import java.util.*;
import java.io.*;
/**
* This is a Patient class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Patient extends User implements ReadFromFileable{
  private static final long serialVersionUID = 1L;
  String height = "";
  String weight = "";
  String bloodType = "";
  String id = "";
  String allergies = "";
  String medications = "";
  boolean insured;
  
  /**
  * Explanation - creates an instance of Patient
  * @param name - a String that represents the patient's name
  * @param age - a number that represents the patient's age
  * @param gender - a String that represents the patient's gender
  * @param dateOfBirth - a String that represents the date of birth
  * @param phoneNumber - a String that represents the patient's phone number
  * @param emailAddress - a String representing the patient's email
  * @param insured - a boolean representing whether or not the patient is insured
  * @param id - a String representing the patient's login ID
  */
  public Patient(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress, boolean insured, String id){
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
    this.insured = insured;
    this.id = id;
  }

  /**
  * Explanation - creates an instance of Patient with password
  * @param name - a String that represents the patient's name
  * @param age - a number that represents the patient's age
  * @param gender - a String that represents the patient's gender
  * @param dateOfBirth - a String that represents the date of birth
  * @param phoneNumber - a String that represents the patient's phone number
  * @param emailAddress - a String representing the patient's email
  * @param insured - a boolean representing whether or not the patient is insured
  * @param id - a String representing the patient's login ID
  * @param pass - a String representing the patient's login password
  */
  public Patient(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress, boolean insured, String id, String pass){
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
    this.insured = insured;
    this.id = id;
    super.setUsername(id);
    super.setPassword(pass);
  }

  /**
  * Explanation - gets ID
  * @return a String that represents the ID
  */
  
  public String getPatientID(){
    return this.id;
  }
  
  /**
  * Explanation - gets height
  * @return a String stored in height
  */
  public String getHeight(){
    return this.height;
  }

  /**
  * Explanation - gets weight
  * @return a String stored in weight
  */
  public String getWeight(){
    return this.weight;
  }

  /**
  * Explanation - gets blood type
  * @return a String stored in bloodType
  */
  public String getBloodType(){
    return this.bloodType;
  }

  /**
  * Explanation - gets allergies
  * @return a String stored in allergies
  */
  public String getAllergies(){
    return this.allergies;
  }

  /**
  * Explanation - gets medication
  * @return a String stored in medications
  */
  public String getMedication(){
    return this.medications;
  }

  /**
  * Explanation - gets insured
  * @return a boolean stored in insured
  */
  public boolean getInsured(){
    return this.insured;
  }

  /**
  * Explanation - sets height
  * @param h - a String that will replace height
  */
  public void setHeight(String h){
    this.height = h;
  }

  /**
  * Explanation - sets weight
  * @param w - a String that will replace weight
  */
  public void setWeight(String w){
    this.weight = w;
  }

  /**
  * Explanation - sets bloodtype, only used when doctor performs testing
  * @param bt - a String that replaces bloodtype
  */
  public void setBloodType(String bt){
    this.bloodType = bt;
  }

  /**
  * Explanation - adds a new allergy to patients allergies if it is not already listed
  * @param newAllergy - a String representing the new allergy
  */
  public void addAllergy(String newAllergy){
    this.allergies += ", " + newAllergy;
  }

  /**
  * Explanation - adds a new medication to patient's medications if it is not already listed
  * @param newMedication - a String representing the new medication to be added
  */
  public void addMedication(String newMedication){
    this.medications += ", "+newMedication;
  }
  
  /**
  * Explanation - removes a medication from the medication 
  * @param offMedication - a String representing the name of the medication we want to remove
  */
  public void removeMedication(String offMedication){
    String[] medications = this.medications.split(", ");
    String[] temp = new String[medications.length - 1];
    int j = 0;
    for (int i = 0; i < medications.length; i++){
      if (!medications[i].equals(offMedication)){
        temp[j] = medications[i];
        j++;
      }
    }
    this.medications = String.join(", ", medications);
  }

  /**
  * Explanation - allows patient to request an appointment
  * @param appointment - an Appointment object that represents the appointment being requested
  * @return a boolean that represents if the appointment requested is available
  */
  public boolean requestAppointment(Appointment appointment){
    return Reception.checkAvailability(appointment);
  }

  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if file name is not found
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
    return objects;
  }
}