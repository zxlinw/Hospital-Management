import java.util.*;

/**
* This is a Sortable interface
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
interface Sortable{

  /**
  * Explanation - partitions a patient ArrayList and sorts one side
  * @param patients - an ArrayList of patient objects
  * @param low - the lower restriction of the ArrayList of the that is being partitioned
  * @param high - the upper restriction of the part of the ArrayList that is being partitioned
  * @return a number is used in the sortPatientName method
  */
  int sortPatientNamePartition(ArrayList<Patient> patients, int low, int high);

  /**
  * Explanation - sorts an ArrayList of patients by name using an optimized QuickSort algorithm
  * @param patients - an ArrayList of patients
  * @param low - the lower limit of the ArrayList, typically set initially to 0: first index
  * @param high - the upper limit of the ArrayList, typically set to the size of the ArrayList-1
  */
  void sortPatientName(ArrayList<Patient> patients, int low, int high);

  /**
  * Explanation - partitions an appointment ArrayList and sorts one side
  * @param patients - an ArrayList of appointment objects
  * @param low - the lower restriction of the ArrayList of the that is being partitioned
  * @param high - the upper restriction of the part of the ArrayList that is being partitioned
  * @return a number that is used in the sortAppointTime method
  */
  int sortAppointTimePartition(ArrayList<Appointment> appoints, int low, int high);
  
  /**
   * Explanation - sorts the appointment times
   * @param appoints - an ArrayList that represents the appointments
   * @param low - a number that represents the lower restriction of the ArrayList
   * @param high - a number that represents the upper restriction of the ArrayList
   */
  void sortAppointTime(ArrayList<Appointment> appoints, int low, int high);
}