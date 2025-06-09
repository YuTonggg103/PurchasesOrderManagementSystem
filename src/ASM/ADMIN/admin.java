
package ASM.ADMIN;

import ASM.SM.*;
import ASM.*;
import java.util.Scanner;

public class admin {
   
    public static void main(){
            String AdminOption;
            boolean stopLoop=true;
            
            while(stopLoop){
                    System.out.println("\nChoose The Option:");
                    System.out.println("1. User Entry");
                    System.out.println("2. View Item");
                    System.out.println("3. View Purchase Requisition (PR) ");
                    System.out.println("4. View Purchase Oder (PO) ");
                    System.out.println("5. View Daily item sales ");
                    System.out.println("6. Exit Admin Page");
                    System.out.println("\nPlease Enter number 1-6 :");

                    // create obj
                    Scanner input =new Scanner(System.in);
                    date date = new date();
                    
                    AdminOption= input.nextLine();
                        switch (AdminOption){
                            case "1":
                                String  addUserEntry;
                                boolean stopLoop1=true;
                                while(stopLoop1){
                                    System.out.println("\nChoose The Option:");
                                    System.out.println("1. Add User");
                                    System.out.println("2. Delete User ");
                                    System.out.println("3. Modify User ");
                                    System.out.println("4. View User ");
                                    System.out.println("5. Exit User ");
                                    System.out.println("\nPlease Enter number 1-4 :");
                                    addUserEntry = input.nextLine();

                                    switch(addUserEntry){
                                        case "1":
                                            user User = new user();
                                            String position;
                                            String positionChoice;
                                            do {
                                                System.out.println("Select user position:");
                                                System.out.println("1. Admin");
                                                System.out.println("2. Purchase Manager");
                                                System.out.println("3. Sale Manager");
                                                System.out.print("Enter the number corresponding to the position: ");
                                                positionChoice = input.nextLine();
                                                switch (positionChoice) {
                                                    case "1":
                                                        position = "admin";
                                                        break;
                                                    case "2":
                                                        position = "PM";
                                                        break;
                                                    case "3":
                                                        position = "SM";
                                                        break;
                                                    default:
                                                        System.err.println("Invalid position choice. Please choose a valid position.");
                                                        position = null;
                                                }
                                            } while (position == null);
                                            
                                            String name;
                                            do{
                                                System.out.print("Enter user name: ");
                                                name = input.nextLine().trim();
                                                if(name.isEmpty()){
                                                    System.out.println("Invalid input.\n");
                                                }
                                            }while (name.isEmpty());

                                            String phoneNumber = "";
                                            while (!User.validPhoneNumber(phoneNumber)) {
                                                System.out.print("Enter user phone number (10 digits): ");
                                                phoneNumber = input.nextLine();
                                                if (!User.validPhoneNumber(phoneNumber)) {
                                                    System.err.println("Invalid phone number.");
                                                }
                                            }

                                            String password;
                                            do{
                                                System.out.print("Enter user password: ");
                                                password = input.nextLine().trim();
                                                if(password.isEmpty()){
                                                    System.out.println("Invalid input.\n");
                                                }
                                            }while (password.isEmpty());
//                                            User.addUser(position, name, phoneNumber, password);
                                            user addUser = new user(password,position, name, phoneNumber );
//                                            User.user(password,position, name, phoneNumber );
                                            break;
                                        
                                        case "2":  
                                            String deleteChoice,deleteuser;
                                                do{
                                                   System.out.println("Select the user you want to delete: ");
                                                   System.out.println("1. Admin");
                                                   System.out.println("2. Sales Manager");
                                                   System.out.println("3. Purchase Manager");
                                                   deleteChoice = input.nextLine();
                                                   switch (deleteChoice) {
                                                       case "1":
                                                           deleteuser = "Admin";
                                                           documentation.readFile("admin_info.txt",true,"user_info");
                                                           System.out.println("\nPlease enter the ID that you want to delete: ");
                                                           String deleteAdminID = input.nextLine();
                                                           documentation.deleteLine("Admin_id.txt", deleteAdminID,"");
                                                           break;
                                                       case "2":
                                                           deleteuser = "Sales Manager";
                                                           documentation.readFile("SM_info.txt",true,"user_info");
                                                           System.out.println("\nPlease enter the ID that you want to delete: ");
                                                           String deleteSMID = input.nextLine();
                                                           documentation.deleteLine("SM_id.txt", deleteSMID,"");
                                                           break;
                                                       case "3":
                                                           deleteuser = "Purchase Manager";
                                                           documentation.readFile("PM_info.txt",true,"user_info");
                                                           System.out.println("\nPlease enter the ID that you want to delete:");
                                                           String deletePMID = input.nextLine();
                                                           documentation.deleteLine("PM_id.txt", deletePMID,"");
                                                           break;
                                                       default:
                                                           System.out.println("Invalid category choice. Please choose a valid category.");
                                                           deleteuser = null;
                                                   }
                                               } while (deleteuser == null);
                                            break;
                                            
                                        case "3":
                                           String modifyUser,modifySM,modifyPM,modifyAdmin ;
                                            boolean Loop=true;
                                            while(Loop){
                                                System.out.println("Modify\n1.Sales Manager\n2.Purchase Manager\n3.Admin\nEnter a Number:");
                                                modifyUser = input.nextLine();
                                                switch(modifyUser){
                                                    case "1":
                                                        documentation.readFile("SM_info.txt",true, "user_info");
                                                        do{
                                                            System.out.println("Enter the SM ID you want to modify:");
                                                            modifySM = input.nextLine();
                                                        }while(documentation.findLine("SM_info.txt", modifySM, "validItemID"));
                                                        user.modify(modifySM, "SM_info.txt");
                                                        Loop=false;
                                                        break;
                                                    case "2":
                                                        documentation.readFile("PM_info.txt",true, "user_info");
                                                        do{
                                                            System.out.println("Enter the PM ID you want to modify:");
                                                            modifyPM = input.nextLine();
                                                        }while(documentation.findLine("PM_info.txt", modifyPM, "validItemID"));
                                                        user.modify(modifyPM, "PM_info.txt");
                                                        Loop=false;
                                                        break;
                                                    case "3":
                                                        documentation.readFile("admin_info.txt",true, "user_info");
                                                        do{
                                                            System.out.println("Enter the Admin ID you want to modify:");
                                                            modifyAdmin = input.nextLine();
                                                        }while(documentation.findLine("admin_info.txt", modifyAdmin, "validItemID"));
                                                        user.modify(modifyAdmin, "admin_info.txt");
                                                        Loop=false;
                                                        break;
                                                }
                                            }
                                            break;
                                       
                                        case "4":
                                            String viewOption ;
                                            Loop=true;
                                            while(Loop){
                                                System.out.println("\nWhich catogory User  you want to view?\n1.Sales Manager\n2.Purchase Manager\n3.Admin\n4.View users by search ID\n5.Exits View User\n\nEnter a Number:");
                                                viewOption = input.nextLine();
                                                switch(viewOption){
                                                    case "1":
                                                        documentation.readFile("SM_info.txt",true,"user_info");
                                                        break;
                                                    case "2":
                                                        documentation.readFile("PM_info.txt",true,"user_info");
                                                        break;
                                                    case "3":
                                                        documentation.readFile("admin_info.txt",true,"user_info");
                                                        break;
                                                    case "4":
                                                        System.out.println("Enter a User ID you want to view: ");
                                                        String viewUserID = input.nextLine();
                                                        if(viewUserID.startsWith("S")){
                                                            System.out.println("\n----------------------------------------------------------------------------------User Information----------------------------------------------------------------------------------");
                                                            System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s","ID","Name","Password","Position","Phone Number","Email");
                                                            System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                                            boolean x = documentation.findLine("SM_info.txt", viewUserID, "validUserID");
                                                            if (x){
                                                                documentation.viewSpecificLine("SM_info.txt", "viewSpecificUser", viewUserID);
                                                            }
                                                        }else if(viewUserID.startsWith("P")){
                                                            boolean x = documentation.findLine("PM_info.txt", viewUserID, "validUserID");
                                                            if (x){
                                                                System.out.println("\n----------------------------------------------------------------------------------User Information----------------------------------------------------------------------------------");
                                                                System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s","ID","Name","Password","Position","Phone Number","Email");
                                                                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                                                documentation.viewSpecificLine("PM_info.txt", "viewSpecificUser", viewUserID);
                                                            }
                                                        }else if(viewUserID.startsWith("a")){
                                                            boolean x = documentation.findLine("admin_info.txt", viewUserID, "validUserID");
                                                            if (x){
                                                                System.out.println("\n----------------------------------------------------------------------------------User Information----------------------------------------------------------------------------------");
                                                                System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s","ID","Name","Password","Position","Phone Number","Email");
                                                                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                                                documentation.viewSpecificLine("admin_info.txt", "viewSpecificUser", viewUserID);
                                                            }
                                                        }else{
                                                            System.out.println("Don't have this User ID ^.^");
                                                        }
                                                        break;
                                                    case "5":
                                                        Loop=false;
                                                        break;
                                                    default:
                                                        System.out.println("Invalid Input");
                                                }
                                            }
                                            break;
                                         case "5":
                                            stopLoop1=false;
                                            break;
                                        default:
                                            System.out.println("Invalid input");
                                    }
                                }
                                break;
                            case "2":
                                documentation.readFile("item_info.txt", true,"item");
                                break;
                                
                            case "3":
                                purchaseRequistion PR  = new purchaseRequistion();
                                documentation.readFile("PR_info.txt", true, "PR");
                                 PR.viewPR();
                                break;
                                
                            case "4":
                                documentation.readFile("PO_info.txt",false,"PO");
                                String POID;
                                do{
                                    System.out.println("\nInput the PO ID you want to view:");
                                    POID = input.nextLine();
                                } while(documentation.findLine("PO_info.txt", POID, "POID"));
                                 String PRID = documentation.viewSpecificLine("PO_info.txt","PO_getPRID",POID); // get PRID from PO
                                String PO_status = documentation.viewSpecificLine("PR_info.txt","PR_status",PRID); //get POstatus from PR file
                                System.out.println("\n------------------------------------------------------------PO Information-----------------------------------------------------------------------");
                                System.out.printf("%-25s | %-25s | %-25s | %-25s|%-25s","Purchase Order ID","Purchase Requisition ID","Purchase Manager ID","Approval Date"," Status");
                                System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                                documentation.viewSpecificLine("PO_info.txt","PO_info",POID); 
                                System.out.print("| "+PO_status+ "\n");
                                documentation.readFile(POID+".txt",true,"PO_item");
                                break;
                                
                            case "5":
                                String x;
                                boolean repeat ;
                                do{
                                    System.out.println("\nEnter a DATE that you want to view Daily Sales : ");
                                    x = date.validIputDate() + "_dailySales.txt";
                                    repeat = documentation.readFile(x, true,"DailySales");
                                }while(repeat);
                                break;
                                
                            case "6":
                                stopLoop=false;
                                break;
                            default:
                                System.out.println("Invalid Input . Please Enter Again.");
                    }//switch case
            } //while loop
        }//main
} // class














