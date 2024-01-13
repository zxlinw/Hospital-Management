import java.util.*;

/**
* This is a Schedule class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng 
* @version Java 17.0.1
*/
class Schedule{
  ArrayList<Appointment> schedule;

  /**
  * Explanation - creates an instance of Schedule
  */
  public Schedule(){
    schedule = new ArrayList<Appointment>();
  }

  /**
  * Explanation - gets appointments
  * @param doctorName - a String that represents the name of the doctor
  * @return an ArrayList that represents the appointments
  */
  public ArrayList<Appointment> getDoctorAppointments(String doctorName){
    ArrayList<Appointment> docAppoint = new ArrayList<Appointment>();
    for (int i = 0; i < schedule.size(); i++){
      if (schedule.get(i).getDoctorName().equals(doctorName)){
        docAppoint.add(schedule.get(i));
      }
    }
    return docAppoint;
  }

  /**
  * Explanation - gets appointments
  * @param patientName - a String that represent the patient's name
  * @return an ArrayList that represents the appointments
  */
  public ArrayList<Appointment> getPatientAppointments(String patientName){
    ArrayList<Appointment> patiAppoint = new ArrayList<Appointment>();
    for (int i = 0; i < schedule.size(); i++){
      if (schedule.get(i).getPatientName().equals(patientName)){
        patiAppoint.add(schedule.get(i));
      }
    }
    return patiAppoint;
  }

  /**
  * Explanation - gets all appointments
  * @return an ArrayList that represents all the appointments on the schedule
  */
  public ArrayList<Appointment> getAllAppointments(){
    return schedule;
  }

  /**
  * Explanation - adds an appointment to schedule
  * @param a - an Appointment object representing the appointment being added
  */
  public void addAppointment(Appointment a){
    schedule.add(a);
  }

  /**
  * Explanation - sets appointment
  * @param appointments - an ArrayList of appointments
  */
  public void setAppointment(ArrayList<Appointment> appointments){
    schedule = appointments;
  }

  /**
  * Explanation - removes an appointment from schedule
  * @param appointments - an ArrayList holding all appointments
  * @param a - an Appointment object being removed
  */
  public void removeAppointment(ArrayList<Appointment>appointments, Appointment a){
   appointments.remove(a);
  }
}