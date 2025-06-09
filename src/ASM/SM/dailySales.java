
package ASM.SM;

import ASM.*;
import java.io.*;
import java.util.*;

public class dailySales  implements SM {
    public String itemID;
    public String name;
    public String category;
    public String unitPrice;
    public int totalSale;
    
    
    public static void addItem_dailySales(String dailySalesFile, String itemID){
        //item_ID,name,category,Unit price,totalSale
        Scanner input =new Scanner(System.in);
        try{
            BufferedReader reader = new BufferedReader(new FileReader("item_info.txt"));
            String line;
            List<String> updatedLines = new ArrayList<>();
            
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length == 7 && parts[0].equals(itemID)){
                    item Item = new item(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]), parts[5], parts[6]);
                    int currentStock = Integer.parseInt(parts[4]);
                    int totalSale;
                    do{
                        totalSale = item.inputQuantity();
                        if(totalSale>currentStock){
                            System.out.println("There are no enough stock.");
                        } else {
                            currentStock -= totalSale;
                            parts[4] = String.valueOf(currentStock);
                            //decrease stock
                            updatedLines.add(String.join(",", parts));
                        }
                    }while(totalSale>currentStock);

                    String itemEntry = itemID + "," + Item.getName() + "," + Item.getCategory() + "," + Item.getPrice() + "," + totalSale;
                    
                    try{
                    BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(dailySalesFile, true));
                    writeNewItem.append(itemEntry);
                    writeNewItem.newLine();
                    writeNewItem.close();
                    System.out.println("The item is added successfully.");
                    } catch (IOException e) {
                        System.err.println("An error occurred: " + e.getMessage());
                    }
                } else {
                    updatedLines.add(line);
                }
            }
            reader.close();
            
            //decrease stock
            BufferedWriter writer = new BufferedWriter(new FileWriter("item_info.txt"));
            for (String updatedLine: updatedLines){
                writer.write(updatedLine + "\n");
            }
            writer.close();
        } catch (IOException e){
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    public static void addDailySales(){
        Scanner input =new Scanner(System.in);
        date Date = new date();
        int current_date_int = Date.getCurrentDate_line();
        int previousDate_int;
        
        String optionDate;
        System.out.println("\nAdd Daily Sales for today or previous date?");
        System.out.println("1. Today");
        System.out.println("2. Previous Date");
        System.out.println("Option:");
        optionDate = input.nextLine();
        switch(optionDate){
            case "1":
                //current date
                String current_date = Integer.toString(current_date_int);
                String currentDate_fileName = current_date+"_dailySales.txt";
                documentation.createFile(currentDate_fileName);
                
                boolean continousAddItem = false;
                do{
                    documentation.readFile("item_info.txt", true, "item");
                    String itemID;
                    do{
                        System.out.println("\nEnter the ITEM ID: ");
                        itemID = input.nextLine();
                    } while(documentation.findLine("item_info.txt", itemID, "validItemID"));
                    
                    addItem_dailySales(currentDate_fileName, itemID);
                    String inputOption;
                    do{
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
                    } while (!inputOption.equals("1") && !inputOption.equals("2"));
                } while (continousAddItem == true);
                break;
                
            case"2":
                //previous date
                String previousDate_fileName="";
                do{
                    String previousDate = Date.validIputDate();
                    previousDate_int = Integer.parseInt(previousDate);
                    if(previousDate_int > current_date_int){
                        System.out.println("This day haven't reached yet.");
                    } else {
                        previousDate_fileName = previousDate+"_dailySales.txt";
                    }
                }while (previousDate_int > current_date_int); 
                
                boolean continousAddItem1 = false;
                do{
                    documentation.readFile("item_info.txt", true, "item");
                    System.out.println("Item ID: ");
                    String itemID = input.nextLine();
                    addItem_dailySales(previousDate_fileName, itemID);
                    String inputOption;
                    do{
                        System.out.println("\nDo you want to input more items?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        inputOption = input.nextLine();
                        if (inputOption.equals("1")) {
                            continousAddItem1 = true;
                            break;
                        } else if (inputOption.equals("2")){
                            continousAddItem1 = false;
                            break;
                        } else {
                            System.out.println("Invalid input.");
                        }
                    } while (!inputOption.equals("1") && !inputOption.equals("2"));
                } while (continousAddItem1 == true);
                break;
            default:
                System.out.println("Invalid input");
        }
        
    }
    
    public static void deleteDailySales(String option){
        date checkingDate = new date();
        if(option.equals("allDailySales")){
            checkingDate.validDate_largerThanCurrentDate("allDailySales");
        }else if(option.equals("specificDailySales")){
            checkingDate.validDate_largerThanCurrentDate("specificDailySales");
        }
    }
    
    public static void addStock(String file, String id, String stock){
        int stock_int = Integer.parseInt(stock);
        List<List<String>> matrix = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file+".txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                matrix.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }
        // Modify the stock value based on input
        for (List<String> row : matrix) {
            if (row.get(0).equals(id)) {
                try {
                    int currentStock = Integer.parseInt(row.get(4));
                    int newStock = currentStock + stock_int;
                    row.set(4, Integer.toString(newStock));
                    System.out.println("Stock updated for item " + id + ": New stock = " + newStock);
                } catch (NumberFormatException e) {
                    System.out.println("Error converting stock to integer: " + e.getMessage());
                }
                break; 
            }
        }
        // Update the item file with the modified matrix
        try (FileWriter fileWriter = new FileWriter(file+".txt")) {
            for (List<String> row : matrix) {
                fileWriter.write(String.join(",", row) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void modifyStock(String fileName, String ID, String AddOrDecrease, int num){
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
            for(int i = 0;  i<table.length; i++){
                if (table[i][0].equals(ID)){
                    if (AddOrDecrease.equals("decrease")){
                        int OriTotalSales = Integer.parseInt(table[i][4]);
                        OriTotalSales = OriTotalSales - num;
                        String s=String.valueOf(OriTotalSales);
                        table[i][4]= s;
                    }
                    else if (AddOrDecrease.equals("increase")){
                        int OriTotalSales = Integer.parseInt(table[i][4]);
                        OriTotalSales = OriTotalSales + num;
                        String s=String.valueOf(OriTotalSales);
                        table[i][4]= s;
                    }
                }
            }
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();
         }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void Modify(String ID,  String fileName){
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
            int num;
            for(int i = 0;  i<table.length; i++){
                if (table[i][0].equals(ID)){
                    System.out.println("\n------------------------------------------------------Daily Sales Information------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s ","Item ID","Name","Category","Unit Price","Total Sales(Quantity)");
                    System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s\n",table[i][0] ,table[i][1],table[i][2],table[i][3],table[i][4]);
                    while(Loop){
                        System.out.println("\n\nDid you want to modify this item Total Sales \n1. Yes \n2. No \nEnter Number: ");
                        String  modifyOption = input.nextLine();
                        switch(modifyOption){
                            case "1":
                                int newTotalSales = item.inputTotalSales();
                                
                                int OriTotalSales = Integer.parseInt(table[i][4]);
                                
                                if (newTotalSales >OriTotalSales){
                                    //stock decrease
                                    num = newTotalSales-OriTotalSales;
                                    dailySales.modifyStock("item_info.txt",ID, "decrease", num);
                                }
                                else if (newTotalSales < OriTotalSales){
                                    //stock increase
                                    num = OriTotalSales - newTotalSales;
                                    dailySales.modifyStock("item_info.txt", ID, "increase", num);
                                }
                                else if (newTotalSales == OriTotalSales){
                                    
                                }
                                String s2=String.valueOf(newTotalSales);
                                table[i][4]=s2;
                                Loop=false;
                                break;

                            case "2":
                                 Loop=false;
                                 break;
                            default:
                                System.out.println("Invalid Enter Please Enter Again.  ");
                        }//switch
                    }
                }
            }//for
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}  
