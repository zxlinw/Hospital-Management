import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
/**
* This is a ReceptionText class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
class ReceptionText implements ReadFromFileable, Sortable{

  /**
  * Explanation - prints the reception menu with various different functions for reception
  * @param name - a String that represents the name
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  public void menu(String name) throws IOException, ClassNotFoundException{
    ArrayList<Patient> patients = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Appointment> appointments = new ArrayList<>();
    try{
      patients = readFromFile("patients.ser", Patient.class);
      doctors = readFromFile("doctors.ser", Doctor.class);
      appointments = readFromFile("appointments.ser", Appointment.class);
    }
    catch(EOFException e){
    }
    catch(Exception e){
      e.printStackTrace();
    }

    while(true) {
      clearScreen();
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Hello, " + name);
      System.out.println("Enter 1 if you want to add an appointment" +
                       "\nEnter 2 if you want to change an appointment" +
                       "\nEnter 3 if you want to cancel an appointment" +
                       "\nEnter 4 to add a patient" +
                       "\nEnter 5 to add a doctor" +
                       "\nEnter 6 to search for a patient" +
                       "\nEnter 7 to search for a doctor" +
                       "\nEnter 8 to look at all appointments" +
                       "\nEnter 9 to exit program\n");
      System.out.print("Your input: ");
      //add prompts for the rest of the methods here
      //also add a way for the receptionist to add doctors or patients 
      String userInput = "";
      userInput = sc.nextLine();
      if(userInput.equals("1")) {
        //adding appointment
        System.out.println("Enter date and time (yyyy/mm/dd/tt:tt): ");
        boolean validTime = false;
        String dateAndTime = "";
        while (!validTime){
          dateAndTime = sc.nextLine();
          validTime = isValidFormat(dateAndTime);
          if (!validTime) {
            System.out.print("Please enter the date and time in format yyyy/mm/dd/tt:tt: ");
          }
        }
        String patientName = patientIsValid(patients);
        String doctorName = doctorIsValid(doctors);
        Appointment newAppointment = new Appointment(dateAndTime, patientName, doctorName);
        if (checkAvailability(newAppointment)) {
          ArrayList<Appointment> appoints = new ArrayList<Appointment>();
          try {
            appoints = readFromFile("appointments.ser", Appointment.class);
          } catch (Exception e) {
            System.out.println(e);
          }
          appoints.add(newAppointment);
          try {
            Main.writeAppointments(appoints);
          } catch (Exception e) {
            System.out.println(e);
          }
        }
        else{
          System.out.println("Sorry! the time slot you inputted is currently unavailable, please choose another time");
        }
      }
      else if(userInput.equals("2")) {
        //changing appointment
        boolean exists = false;
        int index = -1;
        //If the old appointment date and time is valid according to user input
        String oldDateAndTime = "";
        boolean validTime = false;
        while (!validTime) {
          System.out.print("Please enter a valid date and time in the format yyyy/mm/dd/tt:tt: ");
          oldDateAndTime = sc.nextLine();
          validTime = isValidFormat(oldDateAndTime);
          if (!validTime) {
            System.out.println("Invalid date and time format! ");
          }
        }
        String patientName = patientIsValid(patients);
        String doctorName = doctorIsValid(doctors);

        //Checks if new date and time is valid and not the same as before
        String newDateAndTime = "";
        boolean validNewTime = false;
        while (!validNewTime) {
          System.out.print("Please enter a new date and time in the format yyyy/mm/dd: ");
          newDateAndTime = sc.nextLine();
          validNewTime = isValidFormat(oldDateAndTime);
          if (!validNewTime || oldDateAndTime.equals(newDateAndTime)) {
            System.out.print("Invalid date format! ");
          }
        }
        Appointment change = new Appointment(oldDateAndTime, patientName, doctorName);
        Appointment newAppoint = new Appointment(newDateAndTime, patientName, doctorName);
        for(int i = 0; i < appointments.size(); i++) {
          if(appointments.get(i).equals(change)) {
            exists = true;
            index = i;
          }
        }
        if(exists) {
          appointments.set(index, newAppoint);
        }
        else {
          System.out.println("The old appointment: \n" + change + "\ndoes not exist");
        }
        try {
          Main.writeAppointments(appointments);
        }
        catch(Exception e) {
         System.out.println(e);
        }
      }
      else if(userInput.equals("3")) {
        //removing appointment
        boolean exists = false;
        int index = -1;
        String dateAndTime = "";
        boolean validTime = false;
        while (!validTime) {
          System.out.print("Please enter a valid date and time in format yyyy/mm/dd/tt:tt: ");
          dateAndTime = sc.nextLine();
          validTime = isValidFormat(dateAndTime);
          if (!validTime) {
            System.out.println("Invalid birthdate format! ");
          }
        }
        String patientName = patientIsValid(patients);
        String doctorName = doctorIsValid(doctors);
  
        Appointment remove = new Appointment(dateAndTime, patientName, doctorName);
        for(int i = 0; i < appointments.size(); i++){
          if(appointments.get(i).equals(remove)){
            exists = true;
            index = i;
          }
        }
        if(exists) {
          appointments.remove(index);
          try {
            Main.writeAppointments(appointments);
          }
          catch(Exception e) {
            System.out.println(e);
          }
        }
        else {
          System.out.println("The appointment:\n" + remove + "\ndoes not exist");
        }
      }
      else if(userInput.equals("4")) {
        //adding patient
        System.out.println("Enter patient's name");
        String patientName = sc.nextLine();
        System.out.println("Enter patient's age");
        int age = inputInt();
        System.out.println("Enter patient's gender");
        String gender = sc.nextLine();
        String dateOfBirth = "";
        boolean validTime = false;
        while (!validTime) {
          System.out.print("Please enter a valid birthdate in format yyyy/mm/dd: ");
          dateOfBirth = sc.nextLine();
          validTime = isValidBirthFormat(dateOfBirth);
          if (!validTime) {
            System.out.print("Invalid birthdate format! ");
          }
        }
        System.out.println("Enter patient's phone number");
        String phoneNumber = sc.nextLine();
        System.out.println("Enter patient's email address");
        String emailAddress = sc.nextLine();
        System.out.println("Is patient insured?");
        boolean insured = inputInsured();
        String id = Integer.toString((int) Math.floor(Math.random() * 1000000));
        boolean idUnused = checkValidID(id);
        while (!idUnused) {
          id = Integer.toString((int) Math.floor(Math.random() * 1000000));
        }
        System.out.println("created patient with id: " + id);
        System.out.print("enter a password for the patient: ");
        String password = sc.nextLine();
        Patient tempPatient = new Patient(patientName, age, gender, dateOfBirth, phoneNumber, emailAddress, insured, id, password);
        Login.directory.put(id, tempPatient);
        patients.add(tempPatient);
        try {
          Login.writeLogins(Login.directory);
          Main.writePatients(patients);
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println("The new patient, " + patientName + ", can now sign in with id: " + id + " and password: " + password);
      }
      else if(userInput.equals("5")) {
        System.out.print("Please enter the doctor's name: ");
        String newDoctorName = sc.nextLine();

        System.out.print("Please enter the doctor's age: ");
        int age = inputInt();

        System.out.print("Please enter the doctor's gender: ");
        String gender = sc.nextLine();

        System.out.print("Please enter the doctor's birthdate in format yyyy/mm/dd: ");
        String dateOfBirth = "";
        boolean validTime = false;
        while (!validTime) {
          dateOfBirth = sc.nextLine();
          validTime = isValidBirthFormat(dateOfBirth);
          if (!validTime) {
            System.out.println("Invalid birthdate format!");
          }
        }

        System.out.print("Please enter the doctor's phone number: ");
        String phoneNumber = sc.nextLine();

        System.out.print("Please enter the doctor's email address: ");
        String emailAddress = sc.nextLine();

        System.out.print("Please enter the doctor's years of experience: ");
        int yearsOfExperience = inputInt();

        System.out.print("Please enter the doctor's ID: ");
        String id = sc.nextLine();

        System.out.print("Please enter the doctor's password: ");
        String password = sc.nextLine();

        Doctor newDoctor = new Doctor(newDoctorName, age, gender, dateOfBirth, phoneNumber, emailAddress, yearsOfExperience, id, password);
        doctors.add(newDoctor);
        Login.directory.put(id, newDoctor);
        try{
          Main.writeDoctors(doctors);
          Login.writeLogins(Login.directory);
        }
        catch(Exception e){
          e.printStackTrace();
        }
        System.out.println("The new doctor, " + newDoctorName + ", can now sign in with id: " + id + " and password: " + password);
      }
      else if(userInput.equals("6")) {
        //search for patient
        System.out.println("Enter the name of the patient: ");
        String patientName = sc.nextLine();
        int index = -1;
        for(int i = 0; i < patients.size(); i++) {
          if(patients.get(i).getName().equals(patientName)) {
            index = i;
            break;
          }
        }
        if(index == -1) {
          System.out.println("This patient does not exist");
        }
        else {
          System.out.println("Name: " + patients.get(index).getName());
          System.out.println("Age: " + patients.get(index).getAge());
          System.out.println("Gender: " + patients.get(index).getGender());
          System.out.println("Date of Birth: " + patients.get(index).getDateOfBirth());
          System.out.println("Phone number: " + patients.get(index).getPhoneNumber());
          System.out.println("Email Address: " + patients.get(index).getEmailAddress());
          System.out.println("Insurance: " + patients.get(index).getInsured());
          System.out.println("ID: " + patients.get(index).getPatientID());
        } 
      }
      else if(userInput.equals("7")) {
        //search for doctor
        System.out.println("Enter the name of the doctor: ");
        String doctorName = sc.nextLine();
        int index = -1;
        for(int i = 0; i < doctors.size(); i++) {
          if(doctors.get(i).getName().equals(doctorName));
          index = i;
        }
        if(index == -1) {
          System.out.println("This doctor does not exist");
        }
        else {
          System.out.println("Name: " + doctors.get(index).getName());
          System.out.println("Age: " + doctors.get(index).getAge());
          System.out.println("Gender: " + doctors.get(index).getGender());
          System.out.println("Date of Birth: " + doctors.get(index).getDateOfBirth());
          System.out.println("Phone number: " + doctors.get(index).getPhoneNumber());
          System.out.println("Email Address: " + doctors.get(index).getEmailAddress());
          System.out.println("Years of Experience: " + doctors.get(index).getYearsOfExperience());
        } 
      }
      else if (userInput.equals("8")) {
        boolean notBreak = true;
        String userEightInput = "";
        //true when times and false when names
        boolean timesOrNames = true;
        //true when descending and false when ascending
        boolean descOrAsc = true;

        while(notBreak) {
          clearScreen();
          if (timesOrNames && descOrAsc){
            System.out.println("Currently sorting by times in descending order\n");
          }
          else if (timesOrNames && !descOrAsc){
            System.out.println("Currently sorting by times in ascending order\n");
          }
          else if (!timesOrNames && descOrAsc){
            System.out.println("Currently sorting by names in descending order\n");
          }
          else if (!timesOrNames && !descOrAsc){
            System.out.println("Currently sorting by names in ascending order\n");
          }
          if (timesOrNames){
            sortAppointName(appointments, 0, appointments.size() - 1);
          }
          else{
            sortAppointTime(appointments, 0, appointments.size() - 1);
          }
          if (descOrAsc) {
            for (int i = 0; i < appointments.size(); i++) {
              System.out.println(appointments.get(i));
            }
          }
          else{
            for (int i = appointments.size() - 1; i >= 0; i--) {
              System.out.println(appointments.get(i));
            }
          }
          System.out.println("\nEnter 1 to sort appointments time"
                           + "\nEnter 2 to sort by patient names"
                           + "\nEnter 3 to sort by descending"
                           + "\nEnter 4 to sort by ascending"
                           + "\nEnter 5 to exit");
          System.out.print("\nYour input: ");
          userEightInput = sc.nextLine();
          if (userEightInput.equals("1")){
            timesOrNames = true;
          }
          else if (userEightInput.equals("2")){
            timesOrNames = false;
          }
          else if(userEightInput.equals("3")){
            descOrAsc = true;
          }
          else if(userEightInput.equals("4")){
            descOrAsc = false;
          }
          else if(userEightInput.equals("5")){
            notBreak = false;
          }
          else{
            System.out.println("Please enter a valid input!");
          }
        }
      }
      else if (userInput.equals("9")) {
        break;
      }
      else{
        System.out.println("Please enter a valid input!");
      }
    }
  }

  /**
  * Explanation - receives input of patient's age and checks if input is valid
  * @return a number that represents the patient's age
  */
  public int inputInt(){
    Scanner sc = new Scanner(System.in);
    int age = 0;
    while(true) {
      try {
        age = sc.nextInt();
      }
      catch(InputMismatchException e) {
        System.out.println("Please enter an integer!");
        age = inputInt();
      }
      return age;
    }
  }

  /**
  * Explanation - receives input from user checks if input is valid
  * @return a boolean that represents whether or not the patient is insured
  */
  public boolean inputInsured() {
    Scanner sc = new Scanner(System.in);
    boolean insured = false;
    while(true){
      try{
        insured = sc.nextBoolean();
      }
      catch(InputMismatchException e){
        System.out.println("Please enter true or false");
        insured = inputInsured();
      }
      return insured;
    }
  }

  /**
  * Explanation - checks if input is in a valid format
  * @param input - a String that represents the input being checked
  * @return a boolean that represents whether or not the input is valid
  */
  public static boolean isValidFormat(String input) {
    String pattern = "^\\d{4}/\\d{2}/\\d{2}/\\d{2}:\\d{2}$";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(input);
    if (!m.matches()) {
      return false;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm");
    try {
      LocalDateTime.parse(input, formatter);
    }
    catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  /**
  * Explanation - checks if date input is in valid format
  * @param input - a String that represents the date input being checked
  * @return a boolean that represents whether or not the date is in the right format
  */
  public static boolean isValidBirthFormat(String input) {
    String pattern = "^\\d{4}/\\d{2}/\\d{2}$";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(input);
    if (!m.matches()) {
      return false;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    try {
      LocalDate.parse(input, formatter);
    }
    catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  /**
  * Explanation - checks if a patient is in the file
  * @param patients - an ArrayList that represents all the patients on file
  * @return - a String that represents the patient's name
  */
  public String patientIsValid(ArrayList<Patient> patients) {
    Scanner sc = new Scanner(System.in);
    String patientName = "";
    boolean invalidPatient = true;
    while (invalidPatient) {
      System.out.print("Please enter the patient's name: ");
      patientName = sc.nextLine();
      invalidPatient = true;
      for (int i = 0; i < patients.size(); i++) {
        if (patientName.equals(patients.get(i).getName())) {
          invalidPatient = false;
          break;
        }
      }
      if (invalidPatient) {
        System.out.println("The inputted patient could not be found, please check that you spelled it correctly or add the patient to file first!");
      }
    }
    return patientName;
  }

  /**
  * Explanation - checks if a doctor is in the file
  * @param doctors - an ArrayList that holds all doctors
  * @return a String that represents the doctor's name
  */
  public String doctorIsValid(ArrayList<Doctor> doctors) {
    Scanner sc = new Scanner(System.in);
    String doctorName = "";
    boolean invalidDoctor = true;
    while (invalidDoctor) {
      System.out.print("Please enter the doctor's name: ");
      doctorName = sc.nextLine();
      invalidDoctor = true;
      for (int i = 0; i < doctors.size(); i++) {
        if (doctorName.equals(doctors.get(i).getName())) {
          invalidDoctor = false;
          break;
        }
      }
      if (invalidDoctor) {
        System.out.println("The inputted doctor could not be found, please check that you spelled it correctly or add the doctor to file first!");
      }
    }
    return doctorName;
  }


  /**
  * Explanation - partitions a patient ArrayList and sorts one side
  * @param patients - an ArrayList of patient objects
  * @param low - he lower restriction of the ArrayList of the that is being partitioned
  * @param high - the upper restriction of the part of the ArrayList that is being partitioned
  * @return a number that is used in the sortPatientName method
  */
  @Override
  public int sortPatientNamePartition(ArrayList<Patient> patients, int low, int high){
    Patient pivot = patients.get(high);
    int i = (low - 1);
    for (int j = low; j < high; j++){
      if ((patients.get(j).getName().compareTo(pivot.getName())) < 0){
        i++;
        Collections.swap(patients, i, j);
      }
    }
    Collections.swap(patients, i + 1, high);
    return (i + 1);
  }

  /**
  * Explanation - Sorts an ArrayList of patients by name using an optimized QuickSort algorithm
  * @param patients - An ArrayList of patients
  * @param low - The lower limit of the ArrayList, typically set initially to 0: first index
  * @param high - The upper limit of the ArrayList, typically set to the size of the ArrayList-1
  */
  @Override
  public void sortPatientName(ArrayList<Patient> patients, int low, int high){
    while (low < high){
      int pi = sortPatientNamePartition(patients, low, high);
      sortPatientName(patients, low, pi - 1);
      if (pi - low < high - pi){
        sortPatientName(patients, low, pi - 1);
        low = pi + 1;
      }
      else{
        sortPatientName(patients, pi + 1, high);
        high = pi - 1;
      }
    }
  }

  /**
  * Explanation - Partitions an appointment ArrayList and sorts one side
  * @param appoints - An ArrayList of appointment objects
  * @param low - The lower restriction of the ArrayList of the that is being partitioned
  * @param high - The upper restriction of the part of the ArrayList that is being partitioned
  * @return a number that is used in the sortAppointTime method
  */
  @Override
  public int sortAppointTimePartition(ArrayList<Appointment> appoints, int low, int high){
    Appointment pivot = appoints.get(high);
    int i = (low - 1);
    for (int j = low; j < high; j++){
      if ((appoints.get(j).getDateAndTime().compareTo(pivot.getDateAndTime())) < 0){
        i++;
        Collections.swap(appoints, i, j);
      }
    }
    Collections.swap(appoints, i + 1, high);
    return (i + 1);
  }

  /**
  * Explanation - sorts the appointment times
  * @param appoints - an ArrayList that represents the appointments
  * @param low - a number that represents the lower restriction of the ArrayList
  * @param high - a number that represents the upper restriction of the ArrayList
  */
  @Override
  public void sortAppointTime(ArrayList<Appointment> appoints, int low, int high){
    while (low < high){
      int pi = sortAppointTimePartition(appoints, low, high);
      if (pi - low < high - pi){
        sortAppointTimePartition(appoints, low, pi - 1);
        low = pi + 1;
      }
      else{
        sortAppointTimePartition(appoints, pi + 1, high);
        high = pi - 1;
      }
    }
  }

  /**
  * Explanation - Partitions an appointment ArrayList and sorts one side
  * @param appoints - An ArrayList of appointment objects
  * @param low - The lower restriction of the ArrayList of the that is being partitioned
  * @param high - The upper restriction of the part of the ArrayList that is being partitioned
  * @return a number that is used in the sortAppointName method
  */
  public int sortAppointNamePartition(ArrayList<Appointment> appoints, int low, int high){
    Appointment pivot = appoints.get(high);
    int i = (low - 1);
    for (int j = low; j < high; j++){
      if ((appoints.get(j).getPatientName().compareTo(pivot.getPatientName())) < 0){
        i++;
        Collections.swap(appoints, i, j);
      }
    }
    Collections.swap(appoints, i + 1, high);
    return (i + 1);
  }

  /**
  * Explanation - sorts the appointment names
  * @param appoints - an ArrayList that represents the appointments
  * @param low - a number that represents the lower restriction of the ArrayList
  * @param high - a number that represents the upper restriction of the ArrayList
  */
  public void sortAppointName(ArrayList<Appointment> appoints, int low, int high){
    while (low < high){
      int pi = sortAppointNamePartition(appoints, low, high);
      if (pi - low < high - pi){
        sortAppointNamePartition(appoints, low, pi - 1);
        low = pi + 1;
      }
      else{
        sortAppointNamePartition(appoints, pi + 1, high);
        high = pi - 1;
      }
    }
  }

  /**
   * Explanation - determines if requested appointment is available
   * @param request - an Appointment object that represents the requested appointment
   * @return a boolean that represents the availability
   */
  public boolean checkAvailability(Appointment request){
    Schedule schedule = new Schedule();
    try{
      schedule.setAppointment(readFromFile("appointments.ser", Appointment.class));
    }
    catch(EOFException e){
    }
    catch(Exception e){
      e.printStackTrace();
    }
    ArrayList<Appointment> docSched = schedule.getAllAppointments();
    ArrayList<Appointment> onlyDates = new ArrayList<Appointment>();
    if(!docSched.isEmpty()){
      for (int i = 0; i < docSched.size(); i++){
        //Checks if the appointments in the doctors schedule yyyy/mm/dd is the same as requests
        if (docSched.get(i).getDateAndTime().substring(0, 9).equals(request.getDateAndTime().substring(0, 9))){
          onlyDates.add(docSched.get(i));
        }
      }
      String startTime = request.getDateAndTime().substring(11);
      String endTime = request.getAppointmentEndTime();
      //Check if appointments overlap
      for (int i = 0; i < onlyDates.size(); i++){
        if (onlyDates.get(i).getDateAndTime().substring(11).compareTo(startTime) < 0 && !(onlyDates.get(i).getAppointmentEndTime().compareTo(startTime) < 0)){
          return false;
        }
        else if(!(onlyDates.get(i).getDateAndTime().substring(11).compareTo(endTime) > 0)){
          return false;
        }
      }
    }
    Reception.addAppointmentToSchedule(request, schedule);
    try{
      Main.writeAppointments(schedule.getAllAppointments());
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return true;
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
    catch (EOFException e) {
      return objects;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      return objects;
    }
  }

  /*
  * Explanation - clears the screen
  */
  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /*
  * Explanation - checks if the reception ID is valid
  * @param id - a String representing the ID
  */
  public boolean checkValidID(String id) {
    ArrayList<Patient> patients = new ArrayList<Patient>();
    try {
      patients = readFromFile("patients.ser", Patient.class);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    for (int i = 0; i < patients.size(); i++) {
      if (patients.get(i).getPatientID().equals(id)){
        return false;
      }
    }
    return true;
  }
}