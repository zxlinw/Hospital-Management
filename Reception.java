import java.util.*;
import java.io.*;
/**
 * This is a Reception class
 * @author Andre Ke
 * @author Ryan Su
 * @author Wesley Goh
 * @author Zilin Weng
 * @version Java 17.0.1
 */
class Reception extends Staff implements Sortable{
  private static final long serialVersionUID = 6267332186483611803L;
  String id = "";
  
  /**
  * Explanation - creates instance of Reception
  * @param name - a String representing the name
  * @param age - a number representing the age
  * @param gender - a String representing the gender
  * @param dateOfBirth - a String reprsenting the date of birth
  * @param phoneNumber - a String representing the phone number
  * @param emailAddress - a String that represents the email address
  * @param id - a String representing the login ID
  * @param password - a String representing the login password
  */
  public Reception(String name, int age, String gender, String dateOfBirth, String phoneNumber, String emailAddress, String id, String password){
    super(name, age, gender, dateOfBirth, phoneNumber, emailAddress);
    super.changeUserUsername(id);
    super.changeUserPassword(password);
  }

  /**
  * Explanation - partitions a patient ArrayList and sorts one side
  * @param patients - an ArrayList of patient objects
  * @param low - the lower restriction of the ArrayList of the that is being partitioned
  * @param high - the upper restriction of the part of the ArrayList that is being partitioned
  * @return a number is used in the sortPatientName method
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
  * Explanation - sorts an ArrayList of patients by name using an optimized QuickSort algorithm
  * @param patients - an ArrayList of patients
  * @param low - the lower limit of the ArrayList, typically set initially to 0: first index
  * @param high - the upper limit of the ArrayList, typically set to the size of the ArrayList-1
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
  * Explanation - partitions an appointment ArrayList and sorts one side
  * @param patients - an ArrayList of appointment objects
  * @param low - the lower restriction of the ArrayList of the that is being partitioned
  * @param high - the upper restriction of the part of the ArrayList that is being partitioned
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
  * Explanation - determines if requested appointment is available
  * @param request - an Appointment object that represents the requested appointment
  * @return a boolean that represents the availability
  */
  public static boolean checkAvailability(Appointment request){
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
    addAppointmentToSchedule(request, schedule);
    try{
      Main.writeAppointments(schedule.getAllAppointments());
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return true;
  }

  /**
  * Explanation - adds an appointment to the schedule
  * @param appointment - an Appointment object that represents the appointment
  * @param schedule - a Schedule object that the appointment is being added to
  */
  public static void addAppointmentToSchedule(Appointment appointment, Schedule schedule){
    schedule.addAppointment(appointment);
  }

  /**
  * Explanation - searches for a doctor
  * @param name - a String that represents the name of the doctor being searched for
  * @param doctors - an ArrayList that represents all the doctors
  * @return a Doctor object that represents the doctor being searched for
  */
  public Doctor searchDoctor(String name, ArrayList<Doctor> doctors){
    ArrayList<String> doctorNames = new ArrayList<String>();
    int index = -1;
    for(int i = 0; i < doctors.size()-1; i++){
      doctorNames.add(doctors.get(i).getName());
    }
    if(doctorNames.contains(name)){
      index = doctorNames.indexOf(name);
      return doctors.get(index);
    }
    return null;
  }

  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  public static <T> ArrayList<T> readFromFile(String fileName, Class<T> clazz) throws IOException, ClassNotFoundException {
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