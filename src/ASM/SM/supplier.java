package ASM.SM;

import ASM.*;
import java.util.*;
import java.io.*;

public class supplier implements SM {
    private Map<String, Integer> supplierCounter;
    //supplier
    public String supplier_id;
    public String CompanyName;
    public String phoneNo;
    public String email;
    //item
    public String item_id;
    public String name;
    public String category;
    public double price;
    
    public supplier() {
        this.supplierCounter = new HashMap<>();
        supplierCounter.put("supplier", 1);
    }
   
    //add new supplier
    //supplier_ID,name,phoneNo,email
    public void addSupplier(String supplierID_char, String CompanyName, String phoneNo, String email) {
        Scanner input =new Scanner(System.in);
        user ValidPhoneNo = new user();
        String supplier_id = generateSupplierID(supplierID_char);
        this.supplier_id = supplier_id;
        this.CompanyName = CompanyName;
        this.phoneNo = phoneNo;
        this.email = email;
        boolean continousAddItem = false;
        String inputOption;
        
        if (ValidPhoneNo.validPhoneNumber(phoneNo)) {
            String supplierEntry = supplier_id + "," + CompanyName + "," + phoneNo + "," + email;
            String supplierFile = "supplier_info.txt";

            try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(supplierFile, true))) {
                writeToFile.write(supplierEntry);
                writeToFile.newLine();
                System.out.println("New supplier is added successfully into supplier_info.txt.");
                documentation.createFile(supplier_id + ".txt");
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        } else {
            System.err.println("Invalid phone number. Please enter a 10-digit number.");
        }
        
        System.out.println("Input those items from this supplier.");
        do{
            addSupplierItem(supplier_id);
            do{
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
            } while (!inputOption.equals("1") && !inputOption.equals("2"));
        }while (continousAddItem == true);
    }
    
    private String generateSupplierID(String supplierID_char) {
        int currentCounter = supplierCounter.getOrDefault("supplier", 1);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("supplier_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length > 0) {
                    String supplierIDPart = parts[0];
                    if (supplierIDPart.startsWith("supplier")) {
                        user extractNoFromID = new user();
                        int number = extractNoFromID.extractNumber(supplierIDPart);
                        currentCounter = Math.max(currentCounter, number + 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // Ignore and use the default counter value
        }

        supplierCounter.put("supplier", currentCounter);

        String supplierIDGenerated = "supplier" + currentCounter;
        return supplierIDGenerated;
    }
    
    public void addSupplierItem(String supplierID){
        //item_id,name,category,price
        String fileName= supplierID+".txt";
        System.out.println(supplierID);
        Scanner input =new Scanner(System.in);
        
        String item_id;
        do{
            System.out.println("\nEnter supplier Item ID that you want to add:");
            item_id = input.nextLine();
            this.item_id = item_id;
        } while(documentation.findLine(fileName, item_id, "validaddSupplierItemID"));
        
        System.out.println("Supplier Item Name: ");
        String name = input.nextLine();
        
        String category;
        do{
            System.out.println("Select category of item: ");
            System.out.println("1. Fresh Food");
            System.out.println("2. Fresh Produce");
            System.out.println("3. Grocery");
            String categoryChoice = input.nextLine();

            switch (categoryChoice) {
                case "1":
                    category = "fresh food";
                    this.category = category;
                    break;
                case "2":
                    category = "fresh produce";
                    this.category = category;
                    break;
                case "3":
                    category = "grocery";
                    this.category = category;
                    break;
                default:
                    System.out.println("Invalid category choice. Please choose a valid category.");
                    category = null;
                    this.category = category;
            }
        } while (category == null);
        
        double price = item.inputPrice();
        this.price = price;
        
        String itemEntry = item_id + "," + name + "," + category + "," + price;
        try{
        BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(supplierID + ".txt", true));
        writeNewItem.append(itemEntry);
        writeNewItem.newLine();
        writeNewItem.close();
        System.out.println("The item is added successfully.");
        } catch (IOException e) {
        System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    //Modify Supplier Info & supplier Item
    @Override
    public void Modify(String ID, String fileName ){
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
            if (fileName.equals("supplier_info.txt")){
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                       System.out.println("\n-------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-15s | %-40s | %-15s | %-15s ","Supplier ID","Company Name","phoneNo","Email");
                        System.out.println("\n-------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-15s | %-40s | %-15s | %-15s ",table[i][0] ,table[i][1],table[i][2],table[i][3]);
                        while (Loop){
                            System.out.println("\n\nWhich category you want to modify");
                            System.out.println("1. Company Name ");
                            System.out.println("2. phoneNo ");
                            System.out.println("3. Email ");
                            System.out.println("Enter the number 1-3 : ");
                            String  modifyOption = input.nextLine();
                            switch(modifyOption){
                                case "1":
                                   String supComName;
                                    do{
                                    System.out.println("Enter NEW Supplier Company Name : ");
                                    supComName = input.nextLine().trim();
                                    } while(documentation.findLine("supplier_info.txt", supComName, "validSupplierCompanyName"));
                                    table[i][1]=supComName;
                                    Loop=false;
                                    break;

                                case "2":
                                     user User = new user();
                                    String supplier_phoneNo = "";
                                    while (!User.validPhoneNumber(supplier_phoneNo)) {
                                        System.out.print("Enter Supplier phone number (10 digits): ");
                                        supplier_phoneNo = input.nextLine();
                                        if (!User.validPhoneNumber(supplier_phoneNo)) {
                                            System.err.println("Invalid phone number. Please enter a 10-digit number.");
                                        }
                                    }
                                    table[i][2]=supplier_phoneNo;
                                    Loop=false;
                                    break;

                                case "3":
                                    System.out.println("Enter supplier email: ");
                                    String supplier_email = input.nextLine().trim();
                                     table[i][3]=supplier_email;
                                     Loop=false;
                                     break;

                                default:
                                    System.out.println("Invalid Enter Please Enter Again.  ");
                            }//switch
                        }//while
                    }
                }//for
            }
            else{
                for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                       System.out.println("\n-------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-15s | %-40s | %-15s | %-15s ","Supplier ItemID","Item Name","Category","Price");
                        System.out.println("\n-------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-15s | %-40s | %-15s | %-15s ",table[i][0] ,table[i][1],table[i][2],table[i][3]);
                        while (Loop){
                            System.out.println("\n\nWhich category you want to modify");
                            System.out.println("1. Supplier Item ID");
                            System.out.println("2. Item Name");
                            System.out.println("3. Category ");
                            System.out.println("4. Price ");
                            System.out.println("Enter the number 1-4: ");
                            String  modifyOption = input.nextLine();
                            switch(modifyOption){
                                case "1":
                                    String supItemID;
                                    do{
                                    System.out.println("Enter NEW Supplier Item ID : ");
                                    supItemID = input.nextLine().trim();
                                    } while(documentation.findLine(fileName, supItemID, "validSupplierItemID"));
                                    table[i][0]=supItemID;
                                    Loop=false;
                                    break;

                                case "2":
                                    System.out.println("Item Name: ");
                                    String SupItemName = input.nextLine().trim();
                                    table[i][1]=SupItemName;
                                    Loop=false;
                                    break;

                                case "3":
                                    String SupItemCategory;
                                    do{
                                        System.out.println("Select category of item: ");
                                        System.out.println("1. Fresh Food");
                                        System.out.println("2. Fresh Produce");
                                        System.out.println("3. Grocery");
                                        String categoryChoice = input.nextLine();
                                        switch (categoryChoice) {
                                            case "1":
                                                SupItemCategory = "fresh food";
                                                break;
                                            case "2":
                                                SupItemCategory = "fresh produce";
                                                break;
                                            case "3":
                                                SupItemCategory = "grocery";
                                                break;
                                            default:
                                                System.out.println("Invalid category choice. Please choose a valid category.");
                                                SupItemCategory = null;
                                        }
                                    } while (SupItemCategory == null);
                                    table[i][2]=SupItemCategory;
                                    Loop=false;
                                    break;
                                    
                                case "4":
                                    double price = item.inputPrice();
                                    String stringPrice=String.valueOf(price);
                                    table[i][3]=stringPrice;
                                    Loop=false;
                                    break;

                                default:
                                    System.out.println("Invalid Enter Please Enter Again.  ");
                           
                            }//switch
                        }
                    }
                }//for
            }
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
     }//modify method
}
