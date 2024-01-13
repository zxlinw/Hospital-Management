import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This is a Login class
 * @author Andre Ke
 * @author Ryan Su
 * @author Wesley Goh
 * @author Zilin Weng
 * @version Java 17.0.1
 */
class Login implements Serializable{
  //create a hashmap to hold users as the value to a string key
  static HashMap<String, User> directory = new HashMap<String, User>();
  
  /**
  * Explanation - this method displays the login page
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  public void displayLogin() throws IOException, ClassNotFoundException{
    //get the HashMap stored in the file and popular the directory with the entries

    try{
      HashMap<String, User> temp = readLogins();
      if (temp != null){
        directory = temp;
      }
      else{
        directory = new HashMap<String, User>();
      }
    }
    catch(Exception e){
      System.out.println("No users detected");
    }
    //created a doctor and receptionist so that you can login
    Doctor andre = new Doctor("andre", 17, "male", "2005/12/08", "123123123", "123@mail.com", 1, "andre", "password");
    Reception wesley = new Reception("wesley", 17, "male", "2005/00/00", "1231231234", "1234@gmail.com", "wesley", "password");
    directory.put("andre", andre);
    directory.put("wesley", wesley);
    writeLogins(directory);
    // Create a new JFrame for the login page
    JFrame frame = new JFrame("Login Page");
    frame.setSize(300, 150);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());

    // Create a label and a field for the username
    JLabel usernameLabel = new JLabel("Id:");
    frame.add(usernameLabel);
    JTextField usernameField = new JTextField(20);
    frame.add(usernameField);

    // Create a label and field for the password
    JLabel passwordLabel = new JLabel("Password:");
    frame.add(passwordLabel);
    JPasswordField passwordField = new JPasswordField(20);
    frame.add(passwordField);

    // Create a login button
    JButton loginButton = new JButton("Login");
    frame.add(loginButton);
    //listen to clicks on login button and then verify username and passwords align
    loginButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        //get username
        String idInput = usernameField.getText();
        //get password
        char[] passInput = passwordField.getPassword();
        String password = new String(passInput);
        //check if username is in the hashmap
        User temp = checkCredentials(idInput, password);
        if(temp != null){
          Interface in = new Interface();
          //determine what type of user the person is
          if(temp instanceof Doctor){
            in.doctorUi((Doctor)temp);
            frame.dispose();
          }
          else if(temp instanceof Patient){
            in.patientUi((Patient)temp);
            frame.dispose();
          }
          else {
            try {
              in.receptionUi((Reception)temp);
            }
            catch (IOException ex) {
              ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
              ex.printStackTrace();
            }
            frame.dispose();
          }
        }
        else{
          JOptionPane.showMessageDialog(null, "Incorrect Id");
        }
        //   String match = "";
        //   try{
        // match = search(userInput, password);
        //   }
        //   catch(Exception err){
        // System.out.println(err);
        //   }
        //   Interface f2 = new Interface();
        //   if(match.equals("Doctor")){
        //   f2.doctorUi(userInput);
        //   frame.dispose();
        //   }
        //   else if(match.equals("Reception")){
        //   f2.receptionUi(userInput);
        //   frame.dispose();
        //   }
        //   else if(match.equals("Patient")){
        //   f2.patientUi(userInput);
        //   frame.dispose();
        //   }
        //   else {
        // JOptionPane.showMessageDialog(null, "Access denied");
        //   }
      }
    });
    // Display the login page
    frame.setVisible(true);
  }

  /**
  * Explanation - checks the ID and password input
  * @param IdInput - a String that represents the ID inputted
  * @param pass - a String that represents the password inputted
  * @return a User object that represents the type of user
  */
  public static User checkCredentials(String IdInput, String pass){
    if (directory.containsKey(IdInput)){
      User user = directory.get(IdInput);
      if (user.getPassword().equals(pass)){
        return user;
      }
    }
    return null;
  }

  /**
  * Explanation - writes logins to serialized file
  * @param logins - a HashMap<String, User> that represents the logins
  * @throws IOException if the file is not found
  */
  public static void writeLogins(HashMap<String, User> logins) throws IOException {
    try(FileOutputStream fos = new FileOutputStream("logins.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
      oos.writeObject(logins);
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  /**
  * Explanation - reads logins from serialized file
  * @return a HashMap<String, User> that holds the logins
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  public static HashMap<String, User> readLogins() throws IOException, ClassNotFoundException {
    HashMap<String, User> logins = null;
    try(FileInputStream fis = new FileInputStream("logins.ser");
        ObjectInputStream ois = new ObjectInputStream(fis)){
      logins = (HashMap<String, User>) ois.readObject();
    }
    catch(EOFException e){
      return null;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    finally{
      return logins;
    }
  }
}