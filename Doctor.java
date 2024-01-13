import java.util.*;
import java.io.*;
/**
* This is a Doctor class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Doctor extends Staff implements ReadFromFileable{
  private static final long serialVersionUID = 3L;
  int yearsOfExperience;

  /**
  * Explanation - constructs an instance of Doctor
  * @param name - a String that represents the doctor's name
  * @param age - a number that represents the doctor's age
  * @param gender - a String that represents the doctor's gender
  * @param dateOfBirth - a String that represents the doctor's date of birth
  * @param phoneNumber - a String that represents the doctor's phone number
  * @param emailAddress - a String that represents the doctor's email
  * @param yearsOfExperience - a number representing how many years of experience the doctor has
  * @param id - a String that represents the doctor's id
  * @param password - a String that represents the doctor's password
  */
  public Doctor(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress, int yearsOfExperience, String id, String password){ 
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
    super.changeUserUsername(id);
    super.changeUserPassword(password);
    this.yearsOfExperience = yearsOfExperience;
  }

  /**
  * Explanation - gets the years of experience
  * @return a number stored in yearsOfExperience
  */
  public int getYearsOfExperience(){
    return this.yearsOfExperience;
  }

  /**
  * Explanation - Gets the name of the doctor
  * @return the name of the doctor
  */
  public String getName(){
    return super.getName();
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
    finally{
      return objects;
    }
  }
}