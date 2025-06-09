package ASM;

import ASM.SM.purchaseRequistion;
import java.io.*;
import java.util.*;
import java.util.regex.*;

//https://www.w3schools.com/java/java_hashmap.asp
public class user {
    private static String loginID;
    private static  String loginPassword;
    
    private Map<String, Integer> positionCounters;  //declare variable name "positionCounters" of tyoe Map<String, Integer>
    private String userID;
    private String password;
    private String position;
    private String name;
    private String phoneNo;
    private String email;

    public user() {
        
    }
    
    public user(String password, String position, String name, String phoneNum){
        this.password=password;
        this.position=position;
        this.name=name;
        phoneNo=phoneNum;
        addUser();
        
        
    }
    
    public static void login(){
        Scanner userLogin =new Scanner(System.in);
        System.out.println("UserID:");
        loginID= userLogin.nextLine().trim();
        System.out.println("Password:");
        loginPassword= userLogin.nextLine().trim();
        
    }
    
    //check role method
    public  static boolean checkRole(String file){
        boolean x = false;
        try {
            File myObj = new File(file);
            Scanner openFile = new Scanner(myObj);
            String line;
            String fileID, filePassword;
            boolean stopLoop = true;
            
            while (openFile.hasNextLine() &&  stopLoop) {
                line = openFile.nextLine();
                while (line  != null) {
                    String[] parts = line.split(",");
                    fileID = parts[0];
                    filePassword = parts[1];
                    if ( (loginID.equals(fileID)) && (loginPassword.equals(filePassword)) ){
                        x= true;
                        stopLoop=false;
                        break;
                    }
                    else{
                        x= false;
                        break;
                    }
                }
            }
            openFile.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File  has an error occurred.");
        }
       return x;
    }
    
    public static String  getLoginID(){
        return loginID;
    }
    
    public static String  getLoginPassword(){
        return loginPassword;
    }
    
    //write into file"xxx_info.txt"(new user information); "xxx_id.txt"(new user ID and password)
    public void addUser() {
        positionCounters = new HashMap<>();
        //initialise the "positionCounters"
        positionCounters.put("PM", 1);
        positionCounters.put("SM", 1);
        positionCounters.put("admin", 1);
        
        if (validPhoneNumber(phoneNo)) {    //check the phone number is 10-digit or not
//            positionCounters=null;
            userID = generateUserID(); //auto generate userID
            email = generateEmail();      //auto generate user's email

            String userEntry = userID + "," + password + ","  + position + ","+ name + "," + phoneNo + "," + email;
            String filePath = position + "_info.txt";   //write into "xxx_info.txt"
            String userEntryID = userID + "," + password;
            String filePathID = position + "_id.txt";   //write into "xxx_id.txt"

            //BufferedReader can  improve efficiency
            //https://www.guru99.com/buffered-reader-in-java.html
            try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(filePath, true))) {
                writeToFile.write(userEntry);
                writeToFile.newLine();  //add a new line after wrting
                System.out.println("User information is added successfully into " + position + "_info.txt.");
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            try (BufferedWriter writeToFileID = new BufferedWriter(new FileWriter(filePathID, true))) {
                writeToFileID.write(userEntryID);
                writeToFileID.newLine();
                System.out.println("User ID and password are added successfully into " + position + "_id.txt.");
            } catch (IOException e) {   //"IOException" is used to print the error message
                System.err.println("An error occurred: " + e.getMessage());
                //System.err.println() is used for print an error message to the standard error stream
                //e.getMessage() is used for retrieve the error message, what went wrong
            }
        } else {
            System.err.println("Invalid phone number. Please enter a 10-digit number.");
        }
    }

    public static void modify( String ID, String fileName){
         Scanner input =new Scanner(System.in);
         try {
             int totalFileLine = documentation.countFileLine( fileName);
            String table[][]= new String[totalFileLine][];
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int count=0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineItem = line.split(",");
                if (count < table.length) {
                    table[count] = lineItem;
                    count++;
                } else {
                    System.err.println("Table is full, cannot add more items.");
                    break;
                }
            }
            boolean Loop=true;
            for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                    System.out.println("\n----------------------------------------------------------------------------------User Information----------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s","ID","Password","Position","Name","Phone Number","Email");
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s\n",table[i][0] ,table[i][1],table[i][2],table[i][3],table[i][4],table[i][5]);
                    while(Loop){
                        System.out.println("\nWhich category you want to modify");
                        System.out.println("1. Name ");
                        System.out.println("2. Password ");
                        System.out.println("3. Phone Number ");
                        String  modifyOption = input.nextLine();
                        switch(modifyOption){
                            case "1":
                                System.out.println("Enter NEW user name: ");
                                String  newUserName = input.nextLine().trim();
                                table[i][3]=newUserName;
                                Loop=false;
                                break;
                            case "2":
                                System.out.print("Enter New password: ");
                                String newPassword = input.nextLine();
                                if(fileName.equals("SM_info.txt")){
                                    purchaseRequistion.modifyFile("SM_id.txt", ID,newPassword);
                                }
                                else if(fileName.equals("PM_info.txt")){
                                    purchaseRequistion.modifyFile("PM_id.txt", ID,newPassword);
                                }
                                else if(fileName.equals("admin_info.txt")){
                                    purchaseRequistion.modifyFile("Admin_id.txt", ID,newPassword);
                                }
                                table[i][1]=newPassword;
                                Loop=false;
                                break;
                            case "3":
                                user User= new user();
                                String phoneNumber = "";
                                while (!User.validPhoneNumber(phoneNumber)) {
                                    System.out.print("Enter user phone number (10 digits): ");
                                    phoneNumber = input.nextLine();
                                    if (!User.validPhoneNumber(phoneNumber)) {
                                        System.err.println("Invalid phone number.");
                                    }
                                }
                                table[i][4]=phoneNumber;
                                Loop=false;
                                break;
                            default:
                                System.out.println("Invalid Enter Please Enter Again.  ");
                        }
                    }
                 }
            }
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
      
    //If the phone number is not valid, this block prints an error message
    public boolean validPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}"); // if true will return true
    }

    //auto generate user ID based on the position gotten
    //PM1, PM2, PM3, ...., PM10, PM11, ...., PM100, ...., PM1000, ...., PM9999, ....
    //SM1, SM2, SM3, ...., SM10, SM11, ...., SM100, ...., SM1000, ...., SM9999, ....
    //admin1, admin2, admin3, ...., admin10, admin11, ...., admin100, ...., admin1000, ...., PM9999, ....
    private String generateUserID() {
        //https://www.geeksforgeeks.org/hashmap-getordefaultkey-defaultvalue-method-in-java-with-examples/
        //"getOrDefault" get the integer or return default value if no value is mapped with the specified key
        int currentCounter = this.positionCounters.getOrDefault(position, 1);

        // Detect the next available counter for the position
        try {
            //BufferedReader is used to efficiently read characters from a character-input stream
            BufferedReader reader = new BufferedReader(new FileReader(position + "_info.txt"));
            //hold each line from the text file
            String line;
            //while loop to read the whole text file until the end line by line
            while ((line = reader.readLine()) != null) {
                //split the line into an array of string using ","
                String[] parts = line.split(",");
                
                if (parts.length > 0) {
                    String userIDPart = parts[0];
                    //check the userIDPart is start with position or not
                    if (userIDPart.startsWith(position)) {
                        //call method to retrieve numaric part of the userID
                        int number = extractNumber(userIDPart);
                        //Math.max() => return the lager value
                        currentCounter = Math.max(currentCounter, number + 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // Ignore and use the default counter value
        }
        //updates the positionCounters map with the newly calculated counter value for the given position
        positionCounters.put(position, currentCounter);

        String userIDGenerated = position + currentCounter;
        return userIDGenerated;
    }

    public int extractNumber(String IDPart) {
        //use "Pattern" class to creates a regular expression pattern
        //find sequences of numbers in strings
        //"\\d+" => one or more digits in a row, this is a regular expression
            //This pattern will match any consecutive sequence of digits within a string
        Pattern pattern = Pattern.compile("\\d+");
        //create "Matcher" object to find matches of the pattern in the input String "userIDPart"
        Matcher matcher = pattern.matcher(IDPart);
        if (matcher.find()) {
            //return the numeric part of user ID and convert it to integer by using "Integer.parseInt"
            return Integer.parseInt(matcher.group());
        }
        //if the pattern is not found in user ID
        return -1;
    }

    //auto generate email follow by the user's userID
    private String generateEmail() {
        return userID + "@mail.sigma.my";
    }
    
    public String getUserID(){
        return userID;
    }
    
    public void setUserID(String userID){
        this.userID = userID;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPosition(){
        return position;
    }
    
    public void setPosition(String position){
        this.position = position;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPhoneNo(){
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    
}
