import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import java.io.*;
/**
* This is an Interface class
* @author Andre Ke
* @author Ryan Su
* @author Wesley Goh
* @author Zilin Weng
* @version Java 17.0.1
*/
public class Interface implements ReadFromFileable{
  
  /**
  * this method opens a window that displays all the patients in the system
  * @param requester is the Doctor object requesting to view all reports
  */
  public void reportUi(Doctor requester){
    // Create a new JFrame for the patient reports
    JFrame frame = new JFrame("View All Patients");
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    // Create a table model
    DefaultTableModel model = new DefaultTableModel();

    // Add columns to the table model
    model.addColumn("Name");
    model.addColumn("Patient Lookup ID");

    // Add data to the table model
    ArrayList<Patient> allPatients = new ArrayList<Patient>();
    try{
      allPatients = readFromFile("patients.ser", Patient.class);
    }
    catch(Exception e){
      System.out.println(e);
    }
    for(int i=0; i < allPatients.size(); i++){
      model.addRow(new Object[]{allPatients.get(i).name, allPatients.get(i).id});
    }
    // Create a table using the table model
    JTable table = new JTable(model);
    table.setDefaultEditor(Object.class, null); //prevent editing the table

    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    //add a return button
    JButton returnBtn = new JButton("Return");
    frame.add(returnBtn, BorderLayout.SOUTH);
    returnBtn.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
      doctorUi(requester);
      frame.dispose();
    }
    });
    // Display the patient reports GUI
    frame.setVisible(true);
  }

  /**
  * this method creates a report viewer that views the report of a specific patient
  * @param id - a String that represents the ID
  * @param requester - a User object 
  * @param role - a String
  */
  public void viewReport(String id, User requester, String role){
    JFrame frame = new JFrame("View Report");
    frame.setSize(1200, 800);
    frame.setLayout(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //need to retrieve information from file 
    ArrayList<Report> toSearch = new ArrayList<Report>();
    ArrayList<Patient> allPatients = new ArrayList<Patient>();
    String recordName = "incomplete";
    String recordId = "incomplete";
    String recordStatus = "incomplete";
    String recordTimeAdmitted = "incomplete";
    String recordMedication = "incomplete";
    String recordTests = "incomplete";
    String recordResults = "incomplete";
    String recordHeight = "incomplete";
    String recordWeight = "incomplete";
    String recordBloodType = "incomplete";
    
    //read file that stores reports 
    try{
      toSearch = readFromFile("reports.ser", Report.class); 
    }
    catch(Exception e){
      System.out.println(e+"reading reports");
    }
    //read patient info from file
    try{
      allPatients = readFromFile("patients.ser", Patient.class);
    }
    catch(Exception e){
      System.out.println(e+"reading patients");
    }

    //search for the record 
    for(int i = 0; i < toSearch.size(); i++){
      Report current = toSearch.get(i);
      if(current.patientID.equals(id)){
        recordId = current.patientID;
        recordStatus = current.patientStatus;
        recordTimeAdmitted = current.timeAdmitted;
        recordMedication = current.medicationPrescribed;
        recordTests = current.testsDone;
        recordResults = current.results;
        recordHeight = current.height;
        recordWeight = current.weight;
        recordBloodType = current.bloodtype;
      }
    }

    //search for the patient
    for(int i =0; i < allPatients.size(); i++){
      Patient person = allPatients.get(i);
      if(allPatients.get(i).id.equals(recordId)){
        recordName = person.name;
        break;
      }
    }
    
    //create labels
    JLabel name = new JLabel("Name: "+ recordName);
    name.setBounds(30,0,150,30);
    frame.add(name);

    JLabel idLable = new JLabel("Id: "+recordId);
    idLable.setBounds(30,70,150,30);
    frame.add(idLable);

    JLabel patientStatus = new JLabel("Patient Status: "+ recordStatus);
    patientStatus.setBounds(30,110,500,30);
    frame.add(patientStatus);

    JLabel timeAdmitted = new JLabel("Time Admitted: "+ recordTimeAdmitted);
    timeAdmitted.setBounds(30,150,500,30);
    frame.add(timeAdmitted);

    JLabel bloodType = new JLabel("Blood Type: "+ recordBloodType);
    bloodType.setBounds(30,190,500,30);
    frame.add(bloodType);

    JLabel height = new JLabel("Height: "+ recordHeight);
    height.setBounds(30,230,500,30);
    frame.add(height);

    JLabel weight = new JLabel("Weight: "+ recordWeight);
    weight.setBounds(30,270,500,30);
    frame.add(weight);

    JTextArea medicationPrescribed = new JTextArea("Medication: "+ recordMedication);
    medicationPrescribed.setBounds(30,310,500,30);
    frame.add(medicationPrescribed);

    JTextArea testsDone = new JTextArea("Tests Done: "+ recordTests);
    testsDone.setBounds(30,350,500,30);
    frame.add(testsDone);

    JLabel results = new JLabel("Results: " + recordResults);
    results.setBounds(30,390,500,30);
    frame.add(results);

    JButton returnBtn = new JButton("Return");
    returnBtn.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(role.equals("doctor")){
          doctorUi((Doctor)requester);
        }
        else{
          patientUi((Patient)requester);
        }
        frame.dispose();
      }
    });
    returnBtn.setBounds(30,430,150,30);
    frame.add(returnBtn);
    frame.setVisible(true);
  }

    /**
     * creates a method that creates a window where the doctor can input things to create a report
      * @param requester as a Doctor object
     */
    public void createReportUi(Doctor requester){
      //need to be able to store the created report into the list after submitting
      // Create a new JFrame for the patient form
      JFrame frame = new JFrame("Patient Form");
      frame.setSize(400, 500);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      // Create labels for form fields
      JLabel nameLabel = new JLabel("Patient ID:");
      JLabel timeAdmittedLabel = new JLabel("Time Admitted:");
      JLabel statusLabel = new JLabel("Patient Status:");
      JLabel medicationsLabel = new JLabel("Medications:");
      JLabel testsLabel = new JLabel("Tests Done:");
      JLabel resultsLabel = new JLabel("Results:");
      JLabel bloodTypeLabel = new JLabel("Bloodtype:");
      JLabel heightLabel = new JLabel("Height:");
      JLabel weightLabel = new JLabel("Weight:");
      // Create form fields
      JTextField nameField = new JTextField(20);
      JComboBox<String> timeAdmittedField = new JComboBox<>();
      timeAdmittedField.addItem("00:00");
      timeAdmittedField.addItem("00:30");
      timeAdmittedField.addItem("01:00");
      timeAdmittedField.addItem("01:30");
      timeAdmittedField.addItem("02:00");
      timeAdmittedField.addItem("02:30");
      timeAdmittedField.addItem("03:00");
      timeAdmittedField.addItem("03:30");
      timeAdmittedField.addItem("04:00");
      timeAdmittedField.addItem("04:30");
      timeAdmittedField.addItem("05:00");
      timeAdmittedField.addItem("05:30");
      timeAdmittedField.addItem("06:00");
      timeAdmittedField.addItem("06:30");
      timeAdmittedField.addItem("07:00");
      timeAdmittedField.addItem("07:30");
      timeAdmittedField.addItem("08:00");
      timeAdmittedField.addItem("08:30");
      timeAdmittedField.addItem("09:00");
      timeAdmittedField.addItem("09:30");
      timeAdmittedField.addItem("10:00");
      timeAdmittedField.addItem("10:30");
      timeAdmittedField.addItem("11:00");
      timeAdmittedField.addItem("11:30");
      timeAdmittedField.addItem("12:00");
      timeAdmittedField.addItem("12:30");
      timeAdmittedField.addItem("13:00");
      timeAdmittedField.addItem("13:30");
      timeAdmittedField.addItem("14:00");
      timeAdmittedField.addItem("14:30");
      timeAdmittedField.addItem("15:00");
      timeAdmittedField.addItem("15:30");
      timeAdmittedField.addItem("16:00");
      timeAdmittedField.addItem("16:30");
      timeAdmittedField.addItem("17:00");
      timeAdmittedField.addItem("17:30");
      timeAdmittedField.addItem("18:00");
      timeAdmittedField.addItem("18:30");
      timeAdmittedField.addItem("19:00");
      timeAdmittedField.addItem("19:30");
      timeAdmittedField.addItem("20:00");
      timeAdmittedField.addItem("20:30");
      timeAdmittedField.addItem("21:00");
      timeAdmittedField.addItem("21:30");
      timeAdmittedField.addItem("22:00");
      timeAdmittedField.addItem("22:30");
      timeAdmittedField.addItem("23:00");
      timeAdmittedField.addItem("23:30");

      JTextField statusField = new JTextField(20);
      JTextArea medicationsField = new JTextArea(5,20);
      JTextArea testsField = new JTextArea(5,20);
      JTextArea resultsField = new JTextArea(5,20);
      JTextField bloodTypeField = new JTextField(20);
      JTextField heightField = new JTextField(20);
      JTextField weightField = new JTextField(20);

      // Add labels and form fields to the frame
      c.gridx = 0;
      c.gridy = 0;
      frame.add(nameLabel, c);
      c.gridy++;
      frame.add(timeAdmittedLabel, c);
      c.gridy++;
      frame.add(bloodTypeLabel, c);
      c.gridy++;
      frame.add(heightLabel, c);
      c.gridy++;
      frame.add(weightLabel, c);
      c.gridy++;
      frame.add(statusLabel, c);
      c.gridy+=2;
      frame.add(medicationsLabel, c);
      c.gridy+=2;
      frame.add(testsLabel, c);
      c.gridy+=2;
      frame.add(resultsLabel, c);
      c.gridx = 1;
      c.gridy = 0;
      frame.add(nameField, c);
      c.gridy++;
      frame.add(timeAdmittedField, c);
      c.gridy++;
      frame.add(bloodTypeField,c);
      c.gridy++;
      frame.add(heightField, c);
      c.gridy++;
      frame.add(weightField, c);
      c.gridy++;
      frame.add(statusField, c);
      c.gridy+=2;
      frame.add(medicationsField, c);
      c.insets = new Insets(10,0,0,0);  //top padding
      c.gridy+=2;
      frame.add(testsField, c);
      c.insets = new Insets(10,0,0,0);  //top padding
      c.gridy+=2;
      frame.add(resultsField, c);
      // Create a submit button that gets all the inputs from the fields 
      JButton submitButton = new JButton("Submit");
      submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          String name = nameField.getText();
          String timeAdmitted = timeAdmittedField.getSelectedItem().toString();
          String patientStatus = statusField.getText(); 
          String medications = medicationsField.getText(); 
          //get the medication field inputs and put them into an array to be added to an ArrayList
          String tests = testsField.getText(); 
          String results = resultsField.getText();
          String bloodType = bloodTypeField.getText();
          String height = heightField.getText();
          String weight = weightField.getText();
          ArrayList<Patient> searchPatient = new ArrayList<>();
          try{
            searchPatient = readFromFile("patients.ser", Patient.class);
          }
          catch(Exception err){
            System.out.println(err);
          }
          Patient tempPatient = searchForPatient(name, searchPatient);
          //if the patient couldn't be found, tempPatient should equal to null
          if(tempPatient == null){
            JOptionPane.showMessageDialog(null, "Check Patient ID!");
            doctorUi(requester);
            frame.dispose();
          }
          else {
          //add bloodtype, height and weight to the patient class because they are not added when receptionist creates the patient
          tempPatient.setHeight(height);
          tempPatient.setWeight(weight);
          tempPatient.setBloodType(bloodType);
          Report temp = new Report(name, requester.getName(), timeAdmitted, patientStatus, medications, tests, results, bloodType, height, weight);
          ArrayList<Report> tempList = new ArrayList<Report>();
          //read all the current reports and then add to the temp list
          try{
            tempList = readFromFile("reports.ser", Report.class);
          }
          catch(Exception err){
            System.out.println(err);
          }
          tempList.add(temp);
          //add the new report to the temp list
          try{
            Main.writeReports(tempList);
          }
          catch(Exception err){
            System.out.println(err);
          }
          doctorUi(requester);
          frame.dispose();
        }
        }
      });
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        frame.add(submitButton, c);
        frame.setVisible(true);
    }
  
  /**
  * creates a window for patients to view their appoinments
  * @param patient is the object of the patient requesting to view their appointment 
  */
  public void viewAppointments(Patient patient){
    JFrame frame = new JFrame("My Appointments");
    frame.setSize(600,400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Create a table model
    DefaultTableModel model = new DefaultTableModel();
    // Add columns to the table model
    model.addColumn("Doctor Name");
    model.addColumn("Time YYYY/MM/DD");
    model.addColumn("End time ");
    //add data to the table model by getting all the appointments stored in the appointments file and add them to the table if the name of the doctor corresponds with the user 
    ArrayList<Appointment> temp = new ArrayList<>();
    try{
        temp = readFromFile("appointments.ser", Appointment.class);
    }
    catch(Exception err){
        System.out.println(err);
    }
    //if the arraylist of apointments we read and wrote to isn't empty, then search for appointments that match the user
    if(temp.size() > 1){
      sortAppointTime(temp);
      for(int i = 0; i < temp.size(); i++){
        Appointment current = temp.get(i);
        if(current.getPatientName().equals(patient.id)){
          //add the patient's appointments to the table 
          model.addRow(new Object[]{current.getPatientName() ,current.getDateAndTime(), current.getAppointmentEndTime()});
        }
      }
    }
    // Create a table using the table model
    JTable table = new JTable(model);
    table.setDefaultEditor(Object.class, null); //don't let user edit the fields
    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);
    //add return button
    JButton returnBtn = new JButton("Return");
    returnBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            patientUi(patient);
            frame.dispose();
        }
    });
    frame.add(returnBtn, BorderLayout.PAGE_END);
    // Display the schedule GUI
    frame.setVisible(true);
  }
  /**
  * this method creates a ui for the doctor to see all their appointments
  * @param id is the object of the doctor requesting to view their schedule
  */
  public void viewSchedule(Doctor id){
    // Create a new JFrame for the schedule
    JFrame frame = new JFrame("Schedule");
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    //create a table model
    DefaultTableModel model = new DefaultTableModel();
    //add columns to the table model
    model.addColumn("Patient Name");
    model.addColumn("Time");
    model.addColumn("End time ");
    //add data to the table model by getting all the appointments stored in the appointments file and add them to the table if the name of the doctor corresponds with the user 
    ArrayList<Appointment> temp = new ArrayList<>();
    try{
        temp = readFromFile("appointments.ser", Appointment.class);
    }
    catch(Exception err){
        System.out.println(err);
    }
    for(int i = 0; i < temp.size(); i++){
        Appointment current = temp.get(i);
        if(current.getDoctorName().equals(id.getName())){
            model.addRow(new Object[]{current.getPatientName() ,current.getDateAndTime(), current.getAppointmentEndTime()});
        }
    }
    // Create a table using the table model
    JTable table = new JTable(model);
    table.setDefaultEditor(Object.class, null); //don't let user edit the fields
    // Add the table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);
    //add return button
    JButton returnBtn = new JButton("Return");
    returnBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            doctorUi(id);
            frame.dispose();
        }
    });
    frame.add(returnBtn, BorderLayout.PAGE_END);
    // Display the schedule GUI
    frame.setVisible(true);
    }
  
    /**
     * this method creates the doctor ui. It takes in the name of the doctor
     * @param doctor is the object of the doctor requesting to view their interface
     */
    public void doctorUi(Doctor doctor){
      JFrame frame = new JFrame("Doctor Dashboard");
      frame.setSize(1200, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(null);
      //add text
      JLabel usernameLabel = new JLabel("Welcome, Doctor!");
      usernameLabel.setBounds(30,0,150,30);
      frame.add(usernameLabel);
      //add report text
      JLabel reportLabel = new JLabel("Look up a patient report (Enter an id)");
      reportLabel.setBounds(30,30,300,30);
      frame.add(reportLabel);
      //add search field
      JTextField reportField = new JTextField(30);
      reportField.setBounds(30,70, 200,30);
      frame.add(reportField);
      //actually lookup patient
      JButton search = new JButton("Search");
      search.setBounds(240,70,90,30);
      frame.add(search);
      search.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          String searchQ = reportField.getText();
          viewReport(searchQ, doctor, "doctor");
          frame.dispose();
        }
      });
      frame.setVisible(true);

      //create a report button
      JButton createReport = new JButton("Create a Report");
      createReport.setBounds(30,110,150,30);
      frame.add(createReport);
      createReport.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          createReportUi(doctor); 
          frame.dispose();
        }
      });

      //view schedule button
      JButton schedule = new JButton("View Schedule");
      schedule.setBounds(30,150,150,30);
      schedule.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          viewSchedule(doctor);
          frame.dispose();
        }
      });
      frame.add(schedule);

      //view all records
      JButton viewRecords = new JButton("View all Patients");
      viewRecords.setBounds(30,190,200,30);
      frame.add(viewRecords);
      viewRecords.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          reportUi(doctor); //fix
          frame.dispose();
        }
      });
      //prescribe medication
      JLabel prescribeMeds = new JLabel("Prescribe Medication");
      prescribeMeds.setBounds(30,230,150,30);
      frame.add(prescribeMeds);

      JTextField patientName = new JTextField();
      patientName.setBounds(190,270,200,30);
      JLabel promptName = new JLabel("enter patient id");
      promptName.setBounds(30,270,150,30);
      frame.add(patientName);
      frame.add(promptName);

      JTextField medName = new JTextField();
      medName.setBounds(240,310,200,30);
      JLabel promptMed = new JLabel("enter medication name");
      promptMed.setBounds(30,310,200,30);
      frame.add(medName);
      frame.add(promptMed);

      JTextArea instructions = new JTextArea();
      instructions.setBounds(240,350,200,60);
      JLabel promptInstr = new JLabel();
      promptInstr.setText("instructions and dose");
      promptInstr.setBounds(30,350,200,60);
      frame.add(instructions);
      frame.add(promptInstr);

      JButton submitMed = new JButton("Submit");
      submitMed.setBounds(30,415,150,30);
      submitMed.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          String searchQ = patientName.getText();
          String addQ = medName.getText();
          String instructionVal = instructions.getText(); 
          ArrayList<Report> allReports = new ArrayList<Report>();
          //read all the reports stored 
          try{
            allReports = readFromFile("reports.ser", Report.class);
          }
          catch(Exception err){
            System.out.println(err);
          }
          for(int i = 0; i < allReports.size();i++){
            Report temp = allReports.get(i);
            //if a report is found, append the new medication and it's instructions 
            if(temp.patientID.equals(searchQ)){
              String tempMedication = temp.medicationPrescribed;
              
              temp.medicationPrescribed = tempMedication+", addQ"+"-"+instructionVal;
              break;
            }
          }
          //write records all back
          try {
            Main.writeReports(allReports);
          } catch (Exception err) {
            System.out.println(err);
          }
        }
    });
    frame.add(submitMed);
  }
  /**
  * creates the patient ui
  * @param patient is the object of the patient requesting to see their interface
  */
    public void patientUi(Patient patient){
      JFrame frame = new JFrame("patient");
      frame.setSize(1200, 600);
      frame.setLayout(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //add a welcome label
      JLabel welcome = new JLabel("Welcome, " + patient.getName() + "!");
      welcome.setBounds(30,0,200,30);
      frame.add(welcome);
      //view report
      JButton viewReport = new JButton("Look up my report");
      viewReport.setBounds(30,30,200,30);
      viewReport.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          viewReport(patient.id, patient, "patient");
          frame.dispose();
        }
      });
      frame.add(viewReport);
      //view appointments
      JButton viewAppointments = new JButton("View my Appointments");
      viewAppointments.setBounds(30,70,200,30);
      viewAppointments.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          viewAppointments(patient);
          frame.dispose();
        }
      });
      frame.add(viewAppointments);
      //request appointment
      JLabel requestLabel = new JLabel("Request an Appointment");
      requestLabel.setBounds(30,110,200,30);
      frame.add(requestLabel);

      JLabel idLabel = new JLabel("Enter your ID");
      idLabel.setBounds(30,150,150,30);
      frame.add(idLabel);
      JTextField inputId = new JTextField();
      inputId.setBounds(190,150,150,30);
      frame.add(inputId);

      //create drop downs for year,month, date, time and doctor
      JComboBox<String> yearSelector;
      JComboBox<String> monthSelector;
      JComboBox<String> dateSelector;
      JComboBox<String> timeSelector;
      JComboBox<String> doctorSelector;
      DefaultTableModel timeModel;

      //year drop down box
      yearSelector = new JComboBox<>();
      yearSelector.addItem("2022");
      yearSelector.addItem("2023");
      yearSelector.addItem("2024");
      yearSelector.addItem("2025");
      yearSelector.addItem("2026");
      yearSelector.addItem("2027");
      
      //month drop down box
      monthSelector = new JComboBox<>();
      monthSelector.addItem("01");
      monthSelector.addItem("02");
      monthSelector.addItem("03");
      monthSelector.addItem("04");
      monthSelector.addItem("05");
      monthSelector.addItem("06");
      monthSelector.addItem("07");
      monthSelector.addItem("08");
      monthSelector.addItem("09");
      monthSelector.addItem("10");
      monthSelector.addItem("11");
      monthSelector.addItem("12");

      //date drop down box
      dateSelector = new JComboBox<>();
      dateSelector.addItem("01");
      dateSelector.addItem("02");
      dateSelector.addItem("03");
      dateSelector.addItem("04");
      dateSelector.addItem("05");
      dateSelector.addItem("06");
      dateSelector.addItem("07");
      dateSelector.addItem("08");
      dateSelector.addItem("09");
      dateSelector.addItem("10");
      dateSelector.addItem("11");
      dateSelector.addItem("12");
      dateSelector.addItem("13");
      dateSelector.addItem("14");
      dateSelector.addItem("15");
      dateSelector.addItem("16");
      dateSelector.addItem("17");
      dateSelector.addItem("18");
      dateSelector.addItem("19");
      dateSelector.addItem("20");
      dateSelector.addItem("21");
      dateSelector.addItem("22");
      dateSelector.addItem("23");
      dateSelector.addItem("24");
      dateSelector.addItem("25");
      dateSelector.addItem("26");
      dateSelector.addItem("27");
      dateSelector.addItem("28");
      dateSelector.addItem("29");
      dateSelector.addItem("30");
      dateSelector.addItem("31");

      //time drop down box
      timeSelector = new JComboBox<>();
      timeSelector.addItem("00:00");
      timeSelector.addItem("00:30");
      timeSelector.addItem("01:00");
      timeSelector.addItem("01:30");
      timeSelector.addItem("02:00");
      timeSelector.addItem("02:30");
      timeSelector.addItem("03:00");
      timeSelector.addItem("03:30");
      timeSelector.addItem("04:00");
      timeSelector.addItem("04:30");
      timeSelector.addItem("05:00");
      timeSelector.addItem("05:30");
      timeSelector.addItem("06:00");
      timeSelector.addItem("06:30");
      timeSelector.addItem("07:00");
      timeSelector.addItem("07:30");
      timeSelector.addItem("08:00");
      timeSelector.addItem("08:30");
      timeSelector.addItem("09:00");
      timeSelector.addItem("09:30");
      timeSelector.addItem("10:00");
      timeSelector.addItem("10:30");
      timeSelector.addItem("11:00");
      timeSelector.addItem("11:30");
      timeSelector.addItem("12:00");
      timeSelector.addItem("12:30");
      timeSelector.addItem("13:00");
      timeSelector.addItem("13:30");
      timeSelector.addItem("14:00");
      timeSelector.addItem("14:30");
      timeSelector.addItem("15:00");
      timeSelector.addItem("15:30");
      timeSelector.addItem("16:00");
      timeSelector.addItem("16:30");
      timeSelector.addItem("17:00");
      timeSelector.addItem("17:30");
      timeSelector.addItem("18:00");
      timeSelector.addItem("18:30");
      timeSelector.addItem("19:00");
      timeSelector.addItem("19:30");
      timeSelector.addItem("20:00");
      timeSelector.addItem("20:30");
      timeSelector.addItem("21:00");
      timeSelector.addItem("21:30");
      timeSelector.addItem("22:00");
      timeSelector.addItem("22:30");
      timeSelector.addItem("23:00");
      timeSelector.addItem("23:30");

      //doctor drop down
      doctorSelector = new JComboBox<>();
      try {
        //get all the doctors and append them to the dropdown menu 
        ArrayList<Doctor> doctors = readFromFile("doctors.ser", Doctor.class);
        for (int i = 0; i < doctors.size(); i++){
          doctorSelector.addItem(doctors.get(i).getName());
        }
      }
      catch(EOFException e){
        JOptionPane.showMessageDialog(null, "No doctors available!");
      }
      catch(Exception e) {
        e.printStackTrace();
      }

      //create a submit button and then submit an appointment request when the button is clicked 
      JButton submit = new JButton("Submit");
      submit.setBounds(30,400,150,30);
      submit.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          String patientName = patient.getPatientID(); 
          String year = yearSelector.getSelectedItem().toString();
          String month = monthSelector.getSelectedItem().toString(); //month selection
          String date = dateSelector.getSelectedItem().toString(); //day selection
          String time = timeSelector.getSelectedItem().toString(); //time selection
          String doctorName = doctorSelector.getSelectedItem().toString(); //doctor selection
          Appointment temp = new Appointment(year+"/"+month + "/" + date + "/" + time, patientName, doctorName, time);
          patient.requestAppointment(temp);
          JOptionPane.showMessageDialog(null, "Submitted ");
        }
      });
      
      JButton modifyAppointment = new JButton("Change/Cancel Appointment");
      modifyAppointment.setBounds(30,440,200,30);
      modifyAppointment.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(null, "Please contact receptionist to cancel or change your appointment ");
        }
      });

      JPanel selectionPanel = new JPanel();
      selectionPanel.setLayout(new FlowLayout());
      selectionPanel.add(yearSelector);
      selectionPanel.add(monthSelector);
      selectionPanel.add(dateSelector);
      selectionPanel.add(timeSelector);
      selectionPanel.add(doctorSelector);

      selectionPanel.setBounds(30,190,200,200);
      frame.add(selectionPanel);
      frame.add(submit);
      frame.add(modifyAppointment);
      frame.setVisible(true);
    }
    /**
    * run the reception text interface
    * @param reception is the object of the receptionist requesting to use the interface
    */
    public static void receptionUi(Reception reception) throws IOException, ClassNotFoundException{
      ReceptionText rt = new ReceptionText();
      rt.menu(reception.id);
    }
  /**
  * this method searches the login file for the person
  * @param user is the username
  * @param pass is the password
  * @return String is the string of the match
  * @throws IOException if file read is not found
  */
  private String search(String user, String pass) throws IOException{
    BufferedReader inputStream = null;
    try {
      inputStream = new BufferedReader(new FileReader("login.txt"));
      String line;
      while ((line = inputStream.readLine()) != null) {
        String[] entry = line.split(" ");
        if(user.equals(entry[0]) && pass.equals(entry[1])){
          if(entry[2].equals("doctor")){
            return "Doctor";
          }
          else if(entry[2].equals("reception")){
            return "Reception";
          }
          else{
            return "Patient";
            }
          }
        }
      }
      catch (IOException err) {
        System.out.println(err);
      }
      finally {
        if (inputStream != null) {
      inputStream.close();
      }
    }
    return "";
  }

  public void sortAppointTime(ArrayList<Appointment> appointments) {
    Collections.sort(appointments, new Comparator<Appointment>() {
      @Override
      public int compare(Appointment a1, Appointment a2) {
        return a1.getDateAndTime().compareTo(a2.getDateAndTime());
      }
    });
  }

  @Override
  /**
  * Explanation - reads a file and makes an object ArrayList of the file
  * @param fileName - a String representing the file name
  * @param clazz - a Class of any object
  * @return an ArrayList that can hold any object
  * @throws IOException if file name is not found
  * @throws ClassNotFoundException if the class being converted to is not found
  */
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

  /**
  * Explanation - searches for a patient
  * @param id - a String that represents the patient ID
  * @param patients - an ArrayList that holds all patients
  */
  public Patient searchForPatient(String id, ArrayList<Patient> patients) {
    for (int i = 0; i < patients.size(); i++){
      if (patients.get(i).getUsername().equals(id)){
        return patients.get(i);
      }
    }
    return null;
  }
}