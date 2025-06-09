
package ASM;

import ASM.PM.*;
import ASM.SM.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class documentation {
    public String newFile_name;
    
    
    public static void createFile(String newFile_name){
        try{
            File newFile = new File(newFile_name);
            if (newFile.createNewFile()){
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exixts.");
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static boolean readFile(String file, boolean displayALLcolumns, String selection){
        boolean x= false;
        try{
            File txtfile = new File(file);
            Scanner openFile = new Scanner(txtfile);
            String line;
            boolean stopLoop = true;
            
            if(displayALLcolumns){
                if(selection.equals("item")){
                    System.out.println("\n----------------------------------------------------------------------------------Item Information----------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s | %-25s ","ID","Name","Category","Price","Stock","Expired Date","Supplier ID");
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("user_info")){
                    System.out.println("\n----------------------------------------------------------------------------------User Information----------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s","ID","Password","Position","Name","Phone Number","Email");
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("supplier")){
                    System.out.println("\n--------------------------------------------------------------------------------Supplier Information--------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s ","ID","Company Name","Phone","Email");
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("supplierItem")||selection.equals("deleteSuppItem")){
                    System.out.println("\n-----------------------------------------------------------------------------Supplier's Item Information-----------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s ","Item ID","Item Name","Category","Price");
                    System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("DailySales") || selection.equals("deleteSpecificDailySales")){
                    System.out.println("\n------------------------------------------------------Daily Sales Information------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s ","Item ID","Name","Category","Unit Price","Total Sales");
                    System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("PR") || selection.equals("PR_pending")){
                    System.out.println("\n----------------------------------------------------------PR Information----------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s","Purchase Requisition ID","Sales Manager ID","Supplier ID","Request Date","Approval Staus");
                    System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("PO")){
                    System.out.println("\n------------------------------------------------------------PO Information-----------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s | %-25s | %-25s","Purchase Order ID","Purchase Requisition ID","Purchase Manager ID","Approval Date");
                    System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("PR_item")||selection.equals("deleteSpecificPRrecord")){
                    System.out.println("\n--------------------------------------------------------------PR Item Information-------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s","PR Item ID","Item Quantities");
                    System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                }
                else if(selection.equals("PO_item")){
                    System.out.println("\n----------------------------------------------------------------PO Item Information---------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s","PO Item ID","Item Quantities");
                    System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
            else{
                if (selection.equals("PO")){
                    System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.printf("%-25s","PO ID");
                    System.out.println("\n----------------------------------------------------------------------------------------------");
                }else{
                     System.out.println("----------------------------------------------------------------------------------------------");
                    System.out.printf("%-25s | %-25s ","ID","Name");
                    System.out.println("\n----------------------------------------------------------------------------------------------");
                }
                
            }
            
            while (openFile.hasNextLine() && stopLoop){
                line = openFile.nextLine();                
                String[] parts = line.split(",");
                
                if(displayALLcolumns){
                    if(selection.equals("item")){
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6]);
                    }
                    else if(selection.equals("user_info")){
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]);
                    }
                    else if(selection.equals("supplier") || selection.equals("supplierItem")||selection.equals("deleteSuppItem")){
                        System.out.printf("%-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3]);
                    }
                    else if(selection.equals("deleteDailySales")){  
                        dailySales.addStock("item_info",parts[0],parts[4]);     
                    }
                    else if(selection.equals("DailySales")|| selection.equals("deleteSpecificDailySales")){  
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3],parts[4]);
                    }
                    else if(selection.equals("PR")){
                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3],parts[4]);
                    }
                    else if(selection.equals("PR_pending")){
                        if(parts[4].equals("pending")){
                            System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3],parts[4]);
                        }
                        else{
                            //don't print those line approval status is "approve" or "reject"
                        }
                    }
                    else if(selection.equals("PO")){
                        System.out.printf("%-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3]);
                    }
                    else if(selection.equals("PR_item")||selection.equals("deleteSpecificPRrecord") || selection.equals("PO_item")){
                        System.out.printf("%-25s | %-25s \n",parts[0],parts[1]);
                    }
                }
                else{
                     if (selection.equals("PO")){
                        System.out.printf("%-25s\n",parts[0]);
                    }else{
                        System.out.printf("%-25s | %-25s\n",parts[0],parts[1]);
                    }
                }
            }            
            openFile.close();
            if(selection.equals("deleteSuppItem")){
                File supplierFile = new File(file);
                if(supplierFile.exists()){
                    String suppItemWantDelete;
                    Scanner input = new Scanner(System.in);
                        System.out.println("\nPlease enter the item id that you want to delete:");
                        suppItemWantDelete = input.nextLine();
                        documentation.deleteLine(file, suppItemWantDelete,"");
                }
            }else if(selection.equals("deleteSpecificDailySales")){
                File supplierFile = new File(file);
                if(supplierFile.exists()){
                    String dsItemWantDelete;
                    Scanner input = new Scanner(System.in);
                        System.out.println("\nPlease enter the item id that you want to delete:");
                        dsItemWantDelete = input.nextLine();
                        documentation.deleteLine(file, dsItemWantDelete,"deleteSpecificDS");
                }
            }else if(selection.equals("deleteSpecificPRrecord")){    
                File PRFile = new File(file);
                    if(PRFile.exists()){
                        String PRItemWantDelete;
                        Scanner input = new Scanner(System.in);
                        System.out.println("\nPlease enter the item id that you want to delete:");
                        PRItemWantDelete = input.nextLine();
                        documentation.deleteLine(file, PRItemWantDelete,"deleteSpecificPRrecord");
                    }
                }
        }
        catch(FileNotFoundException e){
            //check daily sales file
            if (selection.equals("DailySales")){
                System.out.println("Don't have this Daily Sales Record. Please enter another DATE.");
                x=true;
            }else if(selection.equals("supplierItem")){
                System.out.println("Don't have this Supplier Record. Please enter another DATE.");
            }else if(selection.equals("deleteSuppItem")){
                System.out.println("Don't have this Item Record.");
                x=true;
            }else if(selection.equals("PO_item") ||  selection.equals("PR_item") ){
                System.out.println("Don't have this ID.");
            }else{
                System.out.println("Not found this file.");
            }
        }
        return x;
    }       
    
    //findline=validate method
    //k=ID , q=what you want to do
    public static boolean findLine(String file,String k, String q){
      boolean found = true ,x=true,found1=false;
      try{
          File txtFile = new File(file);
            Scanner openFile2 = new Scanner(txtFile);
            String line;
            while (openFile2.hasNextLine() ) {
                x=true;                
                line = openFile2.nextLine();
                String[] parts = line.split(",");
                if(q.equals("viewSpecificSupp")){
                    if ( k.equals(parts[0])) {
                        purchase_Manager.viewSpecificSuppItemInfor(k + ".txt");
                            found = false;
                            x=false;
                            break;
                    }   
                }
                else if(q.equals("validSupplierID") || q.equals("validItemID") || q.equals("PRID") || q.equals("validPRItemID") || q.equals("POID") ){
                    if ( k.equals(parts[0])) {
                        found = false;
                        x=false;
                        break;
                    }
                }else if(q.equals("PRID_Status")){;
                    if ( k.equals(parts[0])) {
                        if (parts[4].equals("pending")){
                            found = false;
                            x=false;
                            break;
                        }else if (! parts[4].equals("pending")){
                            found=false;
                            found1 = true;
                            x= true;
                            break;
                        }
                    }
                }
                else if (q.equals("validSupplierItemID")|| q.equals("validaddSupplierItemID")){
                    if ( k.equals(parts[0])) {
                        found = false;
                        found1=true;
                        x=true;
                        break;
                    }else{
                        found = false;
                        found1=false;
                        x=false;
                    }
                }
                else  if (q.equals("validSupplierCompanyName") ) {
                    if(k.equals(parts[1])) {
                        found = true;
                        x=true;
                        break;
                    }
                    else{
                        found = false;
                        x=false;
                    }
                }
                else  if ( q.equals("validUserID")) {
                    if(k.equals(parts[0])) {
                        found = false;
                        x=true;
                        break;
                    }
                    else{
                        found = true;
                        x=false;
                    }
                }
                else  if (q.equals("find_pending_PR")) {
                    if ( k.equals(parts[0])) {
                        if (parts[4].equals("pending")){                           
                             deleteFile(k+".txt","deleteWholePR");
                             deleteLine("PR_info.txt",k,"deleteWholePR");
                             found=false;
                             break;
                        }
                        else {
                            System.out.println("This PR ID is not a pending file, CANNOT delete!");                            
                             found = false;
                             x=false;
                        }
                    }
                }
                else  if (q.equals("find_pending_PR_spec")) {
                    if ( k.equals(parts[0])) {
                        if (parts[4].equals("pending")){                            
                            documentation.readFile(k+".txt",true,"deleteSpecificPRrecord");
                            found=false;
                            break;
                        }
                        else{
                            System.out.println("This PR ID is not a pending file, CANNOT delete!");                            
                             found = false;
                             x=false;
                        }
                    }
                }
                else  if (q.equals("PO_delete")) {
                    if ( k.equals(parts[0])) { 
                        deleteFile(k+".txt","deletingPO");                        
                        deleteLine("PO_info.txt", k,"deletePO");
                        deleteFile(parts[1]+".txt","deletingPRduetoPO");    
                        deleteLine("PR_info.txt", parts[1],"deletePRduetoPO");
                        found=false;
                        break;
                    }
                }
            }
            openFile2.close();
            if(found && (q.equals("viewSpecificSupp") || q.equals("validSupplierID")|| q.equals("validItemID") 
                    || q.equals("PRID_Status") || q.equals("PRID") || q.equals("POID") || q.equals("find_pending_PR_spec")  )){
                
                 System.out.println("-------------------NOT Found This ID !----------------------------- ");
             }
            else if (found && q.equals("validSupplierCompanyName")){
                System.out.println("Already have this Supplier Company Name. Cannot Use this Name.  T^T ");
            }
            else if (found && q.equals("validSupplierItemID")){
                System.out.println("Invalid input this ITEM ID already exist. ");
            }
            else if (found && q.equals("validaddSupplierItemID")){
                x=false;
            }
            else if (found1 && q.equals("validaddSupplierItemID")){
                System.out.println("This ID already used. ");
            }
            else if (found1 && q.equals("PRID_Status")){
                System.out.println("You are only can modify the PR which status in PENDING. This PR is already been APRROVED or REJECTED. ");
            }
            else if (found && q.equals("validPRItemID")){
                System.out.println("This ID does not in this Supplier.");
            }
            else if (found && q.equals("validUserID")){
                System.out.println("Don't have this User ^.^");
            }
            else if (found && q.equals("find_pending_PR")){
                System.out.println("Don't have this PR");
            }
            else if (found && q.equals("find_pending_PR_spec")){
                System.out.println("Don't have this PR");
            }
            else if (found && q.equals("PO_delete")){
                System.out.println("Don't have this PO");
            }
          }
          catch(FileNotFoundException e){
              System.out.println("File  has an error occurred.");
          }
          return x;   
    }
       
    public static boolean findItem(String fileName, String itemName, String supplierID) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String name = parts[1].trim();
                    String supplier = parts[6].trim();
                    if (name.equalsIgnoreCase(itemName) && supplier.equalsIgnoreCase(supplierID)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     public static String deleteLine(String file, String id,String option){
          try {
            File txtFile = new File(file);
            Scanner openFile = new Scanner(txtFile);
            StringBuilder newContent = new StringBuilder();
            boolean found = false;
            
            while (openFile.hasNextLine()) {
                String line = openFile.nextLine();                
                String[] parts = line.split(",");
                
                if (parts[0].equals(id)) {
                    System.out.println("ID " + "\"" + id + "\"" + " deleted sucessfully. ");
                    if(option.equals("deleteSpecificDS")){
                        dailySales.addStock("item_info",parts[0],parts[4]);
                    }
                    found = true;
                }else{
                    newContent.append(line).append(System.lineSeparator());
                }
            }
            openFile.close();
            if(!found){
                System.out.println("ID " + "\"" + id + "\"" + " is not found. ");
            }
            Path filePath = txtFile.toPath();
            Files.write(filePath,newContent.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Error while deleting.");
        }
        return null;    
     }  
      
     public static void deleteFile(String file, String selection2){
        File deleteFile = new File(file);
        if(deleteFile.exists()){
            //delete txt file by using the File.delete() method of File class.
            if(selection2.equals("dailySales")){
                readFile(file,true,"deleteDailySales");
                deleteFile.delete();
                System.out.println(  "\"" + file + "\"" + " deleted sucessfully");
            }else if(selection2.equals("supplier")){
                deleteFile.delete();
                System.out.println(  "\"" + file + "\"" + " deleted sucessfully");            
            }else if(selection2.equals("deleteWholePR")){
                deleteFile.delete();
                System.out.println(  "\"" + file + "\"" + " deleted sucessfully");            
            }else if(selection2.equals("deletingPO")){
                deleteFile.delete();
                System.out.println(  "\"" + file + "\"" + " deleted sucessfully");            
            }
            else if(selection2.equals("deletingPRduetoPO")){
                deleteFile.delete();
                System.out.println(  "\"" + file + "\"" + " deleted sucessfully");            
            }else{
                System.out.println(".-----------------Error while deleting file.-----------------");
            }            
        }else{
            System.out.println( "Not found this " + "\"" +file + "\"" + " file.");
        }
     }   
        
     public static String viewSpecificLine (String file, String viewSomething, String ID) {
        String x = null;
        try {
            File myObj = new File(file);
            Scanner openFile = new Scanner(myObj);
            String line;
            while (openFile.hasNextLine()) {
                line = openFile.nextLine();
                String[] parts = line.split(",");
                if (viewSomething.equals("PR_supplierID")) {  // get PR Supplier
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0] ,parts[1],parts[2],parts[3],parts[4]);
                        }
                    }
                    x=parts[2]; 
                }else if (viewSomething.equals("viewSpecificUser")) {  // get PR Supplier
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s\n",parts[0] ,parts[1],parts[2],parts[3],parts[4],parts[5]);
                        }
                    }
                    x=parts[2]; 
                }else if (viewSomething.equals("PR_item")) {  // print PR item
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            System.out.printf("%-25s | %-25s\n",parts[0] ,parts[1]);
                        }
                    }
                }else if (viewSomething.equals("PO_info")) {  //print PO info
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            System.out.printf("%-25s | %-25s | %-25s | %-25s", parts[0] ,parts[1],parts[2],parts[3]);
                        }
                    }
                }else if (viewSomething.equals("PR_info")) {  //print PO info
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s", parts[0] ,parts[1],parts[2],parts[3],parts[4]);
                        }
                    }
                }else if (viewSomething.equals("PO_getPRID")) {  //get PR ID
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            x=parts[1];
                        }
                    }
                }else if (viewSomething.equals("PR_status")) {  // get PR status
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            x=parts[4];
                        }
                    }
                }else if (viewSomething.equals("item_getSupplierID")) {
                    if (!ID.isEmpty()){
                        if (parts[0].equals(ID)){
                            x=parts[6];
                           
                        }
                    }
                }
            }
            openFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
        }
         return x;
    }//view method

     //count file line method
     public static int countFileLine(String fileName){
         int totalFileLine=0;
         try {
            File myObj = new File(fileName);
            Scanner openFile = new Scanner(myObj);
            while (openFile.hasNextLine()) {
                openFile.nextLine();
                totalFileLine++;
            } openFile.close();
        }
         catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
        }
         return totalFileLine;
     }
     
     public static void rewriteArrayToTextFile(String filePath, String[][] array) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            for (String[] row : array) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    rowBuilder.append(row[i]);
                    if (i < row.length - 1) {
                        rowBuilder.append(",");
                    }
                }
                fileWriter.write(rowBuilder.toString() + System.lineSeparator());
            }
            
            fileWriter.close();
            System.out.println("Update successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     public static void writeArray (String fileName, String[] array) {
        try{
               BufferedWriter writeFile = new BufferedWriter(new FileWriter(fileName));
               for(int i=0; i<array.length; i++){
                   writeFile.write(array[i]+"");
                   writeFile.newLine();
               }
               writeFile.close();
           } catch (IOException e) {
               System.err.println("An error occurred: " + e.getMessage());
           }
     }
}
    
    

