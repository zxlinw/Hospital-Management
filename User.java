import java.util.*;
import java.io.Serializable;
/**
* This is a User abstract class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
abstract class User extends Person{
  private static final long serialVersionUID = -4132515889029963589L;
  private String username;
  private String password;

  /**
  * Explanation - constructs an instance of the User class
  * @param name - a String that represents the user's name
  * @param age - a number that represents the user age
  * @param gender - a String that represents the user gender
  * @param dateOfBirth - a String that represents the user date of birth
  * @param phoneNumber - a String that represents the user phone number
  * @param emailAddress - a String that represents the user email address
  */
  public User(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress){
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
  }

  /**
  * Explanation - gets name
  * @return a String stored in name
  */
  public String getName(){
    return super.name;
  }

  /**
  * Explanation - gets username
  * @return a String stored in username
  */
  public String getUsername(){
    return username;
  }

  /**
  * Explanation - gets password
  * @return a String stored in password
  */
  public String getPassword(){
    return password;
  }

  /**
  * Explanation - sets username
  * @param newUsername as a String
  */
  public void setUsername(String newUsername){
    this.username = newUsername;
  }

  /**
  * Explanation - sets password
  * @param newPassword as a String
  */
  public void setPassword(String newPassword){
    this.password = newPassword;
  }
}