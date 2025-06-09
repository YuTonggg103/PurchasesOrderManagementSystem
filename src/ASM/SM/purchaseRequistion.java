
package ASM.SM;

import ASM.*;
import java.io.*;
import java.util.*;

public class purchaseRequistion implements SM{
    
    //share obj
    Scanner input = new Scanner (System.in);
    
    private Map<String, Integer> PRCounter;
    
    public String PR_id;
    public String SM_id;
    public String supplier_id;
    public int requestDate;
    public String item_id;
    public int quantity;
    
    public purchaseRequistion() {
        this.PRCounter = new HashMap<>();
        PRCounter.put("PR", 1);
    }
    
    public boolean hasPendingPR() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PR_info.txt"));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Check if the status is "pending"
                if ("pending".equals(parts[4])) {
                    return true; // There is pending PR
                }
            }

            // Close the file (no pending PR found)
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return false; // No pending PR found
    }
    
    private String generatePRID() {
        int currentCounter = PRCounter.getOrDefault("PR", 1);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("PR_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length > 0) {
                    String PRIDPart = parts[0];
                    if (PRIDPart.startsWith("PR")) {
                        user extractNoFromID = new user();
                        int number = extractNoFromID.extractNumber(PRIDPart);
                        currentCounter = Math.max(currentCounter, number + 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // Ignore and use the default counter value
        }

        PRCounter.put("PR", currentCounter);

        String PRIDGenerated = "PR" + currentCounter;
        return PRIDGenerated;
    }
    
    public void addPR_info(){
        //PR_id,SM_id,supplier_id,requestDate,approveStatus
        date Date = new date();
        Scanner input = new Scanner(System.in);
        String PR_id = generatePRID();
        this.PR_id = PR_id;
        String SM_id = user.getLoginID();
        this.SM_id = SM_id;
                
        String supplier_id;
        do{
            documentation.readFile("supplier_info.txt",true,"supplier");      
            System.out.println("\nEnter item supplier ID: ");
            supplier_id = input.nextLine();
            this.supplier_id = supplier_id;
        } while(documentation.findLine("supplier_info.txt", supplier_id, "validSupplierID"));
        
        String PREntry = PR_id + "," + SM_id + "," + supplier_id + "," + Date.getCurrentDate_line() + ",pending";
        String PRFile = "PR_info.txt";
        this.requestDate = Date.getCurrentDate_line();
        
        try{
            BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(PRFile, true));
            writeNewItem.append(PREntry);
            writeNewItem.newLine();
            writeNewItem.close();
            System.out.println("\nNew PR is added successfully into PR_info.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        
        documentation.createFile(PR_id+".txt");
        
        String inputOption;
        boolean continousAddItem = false;
        do{
            addPR(PR_id+".txt", supplier_id+".txt");
            do {
                System.out.println("\nDo you want to input more items?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                inputOption = input.nextLine();
                if (inputOption.equals("1")) {
                    continousAddItem = true;
                    break;
                } else if (inputOption.equals("2")){
                    continousAddItem = false;
                    break;
                } else {
                    System.out.println("Invalid input.");
                }
            }while(!inputOption.equals("1") && !inputOption.equals("2"));
        }while (continousAddItem == true);
    }
    
    public void addPR(String fileName, String fileName_readSupplierItem){
        Scanner input = new Scanner(System.in);
        
        String item_id;
        boolean x,y;
        do{
            do{
                documentation.readFile(fileName_readSupplierItem, true, "supplierItem");
                System.out.println("\nItem ID: ");
                item_id = input.nextLine();
                this.item_id = item_id;
                x= documentation.findLine(fileName_readSupplierItem, item_id, "validItemID");
            } while(x);
            y =documentation.findLine(fileName, item_id, "validaddSupplierItemID") ;
        }while(y);
        
        int quantity = item.inputQuantity();
        this.quantity = quantity;

       String PR_item_Entry = item_id + "," + quantity;

       try{
           BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(fileName, true));
           writeNewItem.append(PR_item_Entry);
           writeNewItem.newLine();
           writeNewItem.close();
           System.out.println("New item is added successfully into " + fileName);
       } catch (IOException e) {
           System.err.println("An error occurred: " + e.getMessage());
       }
    }

    @Override
    public void Modify(String PRID, String fileName) {
     
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
            boolean Loop=true ;
            if (fileName.equals("PR_info.txt")){
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(PRID)){
                       System.out.println("\n--------------------------------------------------------------------PR Information--------------------------------------------------------------------");
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s","Purchase Requisition ID","Sales Manager ID","Supplier ID","Request Date","Approval Staus");
                        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s",table[i][0] ,table[i][1],table[i][2],table[i][3],table[i][4]);
                        while (Loop){
                            System.out.println("\n\nYou want to modify Supplier ID ? 1.YES  2. NO\nEnter a number:");
                            String  modifyOption = input.nextLine().trim();
                            switch(modifyOption){
                                case "1":
                                   String supplierID;
                                    do{
                                    System.out.println("Enter NEW Supplier ID : ");
                                    supplierID = input.nextLine().trim();
                                    } while(documentation.findLine("supplier_info.txt", supplierID, "validSupplierID"));
                                    table[i][2]=supplierID;
                                    table[i][1]=user.getLoginID();
                                    System.out.println("Sales Manager's ID Updated Automatically.");
                                    System.out.println(user.getLoginID());
                                    String PRfileName = PRID+".txt";
                                    //delete this file 
                                    documentation.deleteFile(PRfileName,"deleteWholePR");
                                    // add new PR item  file 
                                    documentation.createFile(PRfileName);
                                    String inputOption;
                                    boolean continousAddItem = false;
                                    do{
                                        addPR(PRfileName, supplierID+".txt");
                                        do {
                                            System.out.println("Do you want to input more items?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");
                                            inputOption = input.nextLine();
                                            if (inputOption.equals("1")) {
                                                continousAddItem = true;
                                                break;
                                            } else if (inputOption.equals("2")){
                                                continousAddItem = false;
                                                break;
                                            } else {
                                                System.out.println("Invalid input.");
                                            }
                                        }while(!inputOption.equals("1") && !inputOption.equals("2"));
                                    }while (continousAddItem == true);
                                    
                                    Loop=false;
                                    break;

                                case "2":
                                    Loop=false;
                                    break;
                                default:
                                    System.out.println("Invalid Enter Please Enter Again.  ");
                            }
                        }
                    }
                }
            }
            else{
                 String PRitemID;
                do{
                     System.out.println("Enter the PR item ID that you want to modify: ");
                    PRitemID = input.nextLine();
                } while(documentation.findLine(fileName, PRitemID, "PRID"));
                                        
                String supplierID = documentation.viewSpecificLine("PR_info.txt","PR_supplierID",PRitemID);
                String supplierIDfile = supplierID +".txt";
                System.out.println(supplierID +"&"+supplierIDfile);
                
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(PRitemID)){
                        System.out.println("\n------------------------------------------------------------------PR Item Information------------------------------------------------------------------");
                        System.out.printf("%-16s | %-16s","PR Item ID","Item Quantities");
                        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-16s | %-16s \n",table[i][0] ,table[i][1]);
                        
                        while (Loop){
                            System.out.println("\nYou want to modify which categories? \n1.PR Item ID \n2. Item Quantities \nInput Number:");
                            String  modifyOption = input.nextLine().trim();
                            switch(modifyOption){
                                case "1":
                                   String NewPRitemID;
                                    do{
                                    System.out.println("Enter NEW PR item ID : ");
                                    NewPRitemID = input.nextLine().trim();
                                    } while(documentation.findLine(supplierIDfile, NewPRitemID, "validPRItemID"));
                                    table[i][0]=NewPRitemID;
                                    System.out.println(PRID);
                                    modifyFile("PR_info.txt", PRID,null);
                                    Loop=false;
                                    break;

                                case "2":
                                    int quantity = item.inputQuantity();
                                    String stringQuantity = String.valueOf(quantity);
                                    table[i][1]=stringQuantity;
                                    Loop=false;
                                    break;
                                    
                                default:
                                    System.out.println("Invalid Enter Please Enter Again.  ");
                            }
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
    
    public static void modifyFile(String fileName, String ID, String Password){
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
            if (fileName.equals("PR_info.txt")){
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                        table[i][1]=user.getLoginID();
                        System.out.println("Sales Manager Updated Automatically:"+ table[i][1]);
                    }
                }
            }else if (fileName.equals("SM_id.txt") || fileName.equals("PM_id.txt") || fileName.equals("Admin_id.txt")){
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                        table[i][1]=Password;
                        System.out.println("Login Password Updated:"+ table[i][1]);
                    }
                }
            }
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePRFile(){
        Scanner sc = new Scanner(System.in);
        boolean stopLoop5=true;
        while(stopLoop5){
            System.out.println("\nWhat you want to delete?");
            System.out.println("1. Delete the pending PR\n2. Delete specific line in pending PR\nEnter a Number:");
            String option = sc.nextLine();
            switch(option){
                case "1":    
                    System.out.println("\nEnter the PR ID that you want to delete: ");
                    String PRIDdelete = sc.nextLine();
                    documentation.findLine("PR_info.txt",PRIDdelete,"find_pending_PR");
                    stopLoop5=false;
                    break;
                case "2":
                    System.out.println("\nEnter the PR ID that you find for deleting specific record: " );
                    String PRID_spec_delete = sc.nextLine();
                    documentation.findLine("PR_info.txt",PRID_spec_delete,"find_pending_PR_spec");
                    stopLoop5=false;
                    break;
                default:
                    System.out.println("Invalid Input . Please Enter Again.");
            }
        }
    }
    
    public void viewPR(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter the PR ID that you want to view: ");
        String PRIDview = sc.nextLine();
        documentation.readFile(PRIDview+".txt",true,"PR_item");
    }
    
}//class
