
package ASM.SM;

import ASM.*;
import java.util.*;
import java.io.*;

public class item implements SM {
    //item_id,category,name,price,stock,expDate,supplierID
    private Map<String, Integer> SIGMACounter;
    private String itemID;
    private String category;
    private String name;
    private double price;
    private int stock;
    private String expDate;
    private String supplierID;

    public item() {
        this.SIGMACounter = new HashMap<>();
        SIGMACounter.put("SIGMA", 1);
    }

    public item(String itemID, String name, String category, double price, int stock, String expDate, String supplierID) {
        this.itemID=itemID;
        this.category=category;
        this.name=name;
        this.price=price;
        this.stock=stock;
        this.expDate=expDate;
        this.supplierID=supplierID;
    }

    public void addItem(String itemID_char, String category, String name, String price, String stock, String expDate, String supplierID){
        String item_id = generateItemID(itemID_char);
        
        String itemEntry = item_id + "," + name + "," + category + "," + price +","+stock + "," + expDate + "," + supplierID;
        String itemFile = "item_info.txt";
        
        try{
            BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(itemFile, true));
            writeNewItem.append(itemEntry);
            writeNewItem.newLine();
            writeNewItem.close();
            System.out.println("New item is added successfully into item_info.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    private String generateItemID(String itemID_char) {
        int currentCounter = SIGMACounter.getOrDefault("SIGMA", 1);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("item_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length > 0) {
                    String itemIDPart = parts[0];
                    if (itemIDPart.startsWith("SIGMA")) {
                        user extractNoFromID = new user();
                        int number = extractNoFromID.extractNumber(itemIDPart);
                        currentCounter = Math.max(currentCounter, number + 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // Ignore and use the default counter value
        }

        SIGMACounter.put("SIGMA", currentCounter);

        String itemIDGenerated = "SIGMA" + currentCounter;
        return itemIDGenerated;
    }
    
    public static double inputPrice() {
    Scanner input = new Scanner(System.in);
    double price;
    
    do {
        try {
            System.out.println("\nPrice: ");
            String price_string = input.nextLine();
            price = Double.parseDouble(price_string);
            
            if (price < 0) {
                System.out.println("Invalid input. Please enter a non-negative price value.");
            } else {
                break; // Exit the loop if input is successfully parsed and non-negative
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    } while (true);
    
    return price;
}

    public static int inputStock() {
        Scanner input = new Scanner(System.in);
        int stock;

        do {
            try {
                System.out.println("\nStock: ");
                String stock_string = input.nextLine();
                stock = Integer.parseInt(stock_string);

                if (stock < 0) {
                    System.out.println("Invalid input. Please enter a non-negative stock value.");
                } else {
                    break; // Exit the loop if input is successfully parsed and non-negative
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (true);

        return stock;
    }
  
    public static int inputQuantity() {
    Scanner input = new Scanner(System.in);
    int quantity;
    
    do {
        try {
            System.out.println("\nQuantity: ");
            String quantity_string = input.nextLine();
            quantity = Integer.parseInt(quantity_string);
            
            if (quantity < 0) {
                System.out.println("Invalid input. Please enter a non-negative quantity.");
            } else {
                break; // Exit the loop if input is successfully parsed and non-negative
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    } while (true);
    
    return quantity;
}
  
    public static int inputTotalSales() {
    Scanner input = new Scanner(System.in);
    int totalSales;
    
    do {
        try {
            System.out.println("\nTotal Sales: ");
            String totalSales_string = input.nextLine();
            totalSales = Integer.parseInt(totalSales_string);
            
            if (totalSales < 0) {
                System.out.println("Invalid input. Please enter a non-negative total sales value.");
            } else {
                break; // Exit the loop if input is successfully parsed and non-negative
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    } while (true);
    
    return totalSales;
}

    
    @Override
    public void Modify( String ID, String fileName){
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
            //search line
            boolean Loop=true;
            for(int i = 0;  i<table.length; i++){
                    if (table[i][0].equals(ID)){
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-16s | %-30s| %-15s| %-15s| %-15s| %-15s| %-15s ","ItemID","Name","Categrory","Price","Stock","Exp Date","SupplierID");
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-16s | %-30s| %-15s| %-15s| %-15s| %-15s| %-15s\n ",table[i][0] ,table[i][1],table[i][2],table[i][3],table[i][4],table[i][5],table[i][6]);
                    while(Loop){
                        System.out.println("\nWhich category you want to modify");
                        System.out.println("1. Name ");
                        System.out.println("2. Categrory ");
                        System.out.println("3. Price ");
                        System.out.println("4. Stock ");
                        System.out.println("5. Exp Date ");
                        System.out.println("6. SupplierID ");
                        System.out.println("Enter the number 1-6 : ");
                        String  modifyOption = input.nextLine();
                        switch(modifyOption){
                            case "1":
                                String  newItemName;
                                String item1_supplierID ;
                                do{
                                    System.out.println("\nEnter NEW item name: ");
                                    newItemName = input.nextLine().trim();
                                    item1_supplierID = documentation.viewSpecificLine("item_info.txt", "item_getSupplierID", ID);
                                    while(documentation.findItem("item_info.txt", newItemName, item1_supplierID)){
                                        System.out.println("This item provided by this supplier already exists.");
                                        break;
                                    }
                                }while(documentation.findItem("item_info.txt", newItemName, item1_supplierID));
                                table[i][1]=newItemName;
                                Loop=false;
                                break;

                            case "2":
                                String category;
                                System.out.println("Modify Categrory");
                                do{
                                        System.out.println("\nEnter New Categrory:");
                                        System.out.println("1. Fresh Food");
                                        System.out.println("2. Fresh Produce");
                                        System.out.println("3. Grocery");
                                        System.out.println("Enter a Number: ");
                                        String  categoryChoice = input.nextLine();
                                        switch (categoryChoice) {
                                            case "1":
                                                category = "fresh food";
                                                table[i][2]=category;
                                                break;
                                            case "2":
                                                category = "fresh produce";
                                                table[i][2]=category;
                                                break;
                                            case "3":
                                                category = "grocery";
                                                table[i][2]=category;
                                                break;
                                            default:
                                                System.err.println("Invalid category choice. Please choose a valid category.");
                                                category = null;
                                        }
                                    } while (category == null);
                                Loop=false;
                                break;

                            case "3":
                                double price =inputPrice();
                                String stringPrice=String.valueOf(price);
                                table[i][3]=stringPrice;
                                Loop=false;
                                break;

                            case "4":
                                double stock = inputStock();
                                String stringStock=String.valueOf(stock);
                                table[i][4]=stringStock;
                                Loop=false;
                                 break;

                            case "5":
                                System.out.println("Enter item expired date: ");
                                date Date =new date();
                                String item_expDate = Date.validDate_smallerThanCurrentDate();
                                table[i][5]=item_expDate;
                                Loop=false;
                                break;

                            case "6":
                                String item_supplierID;
                                    do{
                                    System.out.println("Enter NEW item supplier ID: ");
                                    item_supplierID = input.nextLine().trim();
                                    } while(documentation.findLine("supplier_info.txt", item_supplierID, "validSupplierID"));
                                table[i][6]=item_supplierID;
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
    //get set method
    public String getItemID(){
        return itemID;
    }
    
    public void setItemID(String itemID){
        this.itemID = itemID;
    }
    
    public String getCategory(){
        return category;
    } 
    
    public void setCategory(String category){
        this.category = category;
    } 
    
    public String getName(){
        return name;
    } 
    
    public void setName(String name){
        this.name = name;
    } 
    
    public double getPrice(){
        return price;
    } 
    
    public void setPrice(double price){
        this.price = price;
    } 
    
    public int getStock(){
        return stock;
    } 
    
    public void setStock(int stock){
        this.stock = stock;
    } 
    
    public String getExpDate(){
        return expDate;
    } 
    
    public void setExpDate(String expDate){
        this.expDate = expDate;
    } 
    
    public String getSupplierID(){
        return supplierID;
    }
    
    public void setSupplierID(String supplierID){
        this.supplierID = supplierID;
    }
}
