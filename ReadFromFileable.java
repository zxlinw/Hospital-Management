import java.util.*;
import java.io.*;
/**
* This is a ReadFromFileable interface
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
interface ReadFromFileable{
  
  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
  <T> ArrayList<T> readFromFile(String fileName, Class<T> clazz) throws IOException, ClassNotFoundException;
}