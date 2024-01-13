import java.io.Serializable;
/**
* This is a Person class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class Person implements Serializable{
  String name;
  int age;
  String gender;
  String dateOfBirth;
  String phoneNumber;
  String emailAddress;

  /**
  * Explanation - creates an instance of Person
  * @param name - a String representing the person's name
  * @param age - a number representing the person's age
  * @param gender - a String representing the person's gender
  * @param dateOfBirth - a String representing the person's date of birth
  * @param phoneNumber - a String representing the person's phone number
  * @param emailAddress - a String representing the person's email address
	*/
  public Person(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
  }

  /**
  * Explanation - gets name
  * @return a String stored in name
  */
  public String getName(){
    return this.name;
  }

  /**
  * Explanation - gets age
  * @return a number stored in age
  */
  public int getAge(){
    return this.age;
  }

  /**
  * Explanation - gets gender
  * @return a String stored in gender
  */
  public String getGender(){
    return this.gender;
  }

  /**
  * Explanation - gets date of birth
  * @return a String stored in dateOfBirth
  */
  public String getDateOfBirth(){
    return this.dateOfBirth;
  }

  /**
  * Explanation - gets phone number
  * @return a String stored in phoneNumber
  */
  public String getPhoneNumber(){
    return this.phoneNumber;
  }

  /**
  * Explanation - gets email address
  * @return a String stored in emailAddress
  */
  public String getEmailAddress(){
    return this.emailAddress;
  }

}