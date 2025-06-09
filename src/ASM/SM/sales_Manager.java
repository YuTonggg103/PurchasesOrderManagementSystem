
package ASM.SM;
import ASM.*;
import java.util.Scanner;

interface SM {
    public void Modify(String ID,  String fileName);
}

public class sales_Manager {
    public static void main(){
        
            String SMOption;
            boolean stopLoop= true;
            while (stopLoop){
                    System.out.println("\nChoose The Option:");
                    System.out.println("1. Item Entry");
                    System.out.println("2. Supplier Entry");
                    System.out.println("3. Daily Item-wise Sales Entry ");
                    System.out.println("4. Purchase Requisition (PR) Entry");
                    System.out.println("5. View Purchase Requisition (PR) ");
                    System.out.println("6. View Purchases Order (PO)");
                    System.out.println("7. Exit Sales Manager Page");
                    System.out.println("\nPlease Enter number 1-7 :");

                    //share obj
                    Scanner input =new Scanner(System.in);
                    supplier supp= new supplier();
                    date date = new date();
                    dailySales DailyS= new dailySales();
                    item Item= new item();
                    purchaseRequistion PR  = new purchaseRequistion();
                    
                    SMOption= input.nextLine();
                    switch (SMOption){
                        case "1":
                            item callItem = new item();
                            boolean stopLoop2=true;
                            String itemOption;
                            while(stopLoop2){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. Add Item");
                                System.out.println("2. Delete Item ");
                                System.out.println("3. Modify Item ");
                                System.out.println("4. Exit Item ");
                                System.out.println("\nPlease Enter number 1-4 :");
                                itemOption = input.nextLine();
                                
                                switch(itemOption){
                                    case "1":
                                        String itemIDCharacter = "SIGMA";
                                        String category;
                                        String categoryChoice;
                                        do{
                                            System.out.println("\nSelect category of item: ");
                                            System.out.println("1. Fresh Food");
                                            System.out.println("2. Fresh Produce");
                                            System.out.println("3. Grocery");
                                            categoryChoice = input.nextLine();
                                            
                                            switch (categoryChoice) {
                                                case "1":
                                                    category = "fresh food";
                                                    break;
                                                case "2":
                                                    category = "fresh produce";
                                                    break;
                                                case "3":
                                                    category = "grocery";
                                                    break;
                                                default:
                                                    System.out.println("Invalid category choice. Please choose a valid category.");
                                                    category = null;
                                            }
                                        } while (category == null);
                                        
                                        System.out.println("\nEnter item name: ");
                                        String item_name_input = input.nextLine();
                                        String item_name = item_name_input.toLowerCase();
                                        
                                        String item_supplierID;
                                        do{
                                            documentation.readFile("supplier_info.txt",true,"supplier");      
                                            System.out.println("\nEnter item supplier ID: ");
                                            item_supplierID = input.nextLine();
                                        } while(documentation.findLine("supplier_info.txt", item_supplierID, "validSupplierID"));

                                        double item_price_double = item.inputPrice();
                                        String price = String.valueOf(item_price_double);
                                        
                                        int item_stock_int;
                                        do{
                                            item_stock_int = item.inputStock();
                                        } while(item_stock_int <= 0);
                                        String item_stock = Integer.toString(item_stock_int);
                                        
                                        String expDateYesNo = "";
                                        String item_expDate = "";
                                        do{
                                            System.out.println("\nEnter item expired date: ");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");
                                            System.out.println("Choice: ");
                                            expDateYesNo = input.nextLine();
                                            if (expDateYesNo.equals("1")){
                                                item_expDate = date.validDate_smallerThanCurrentDate();
                                                break;
                                            } else if (expDateYesNo.equals("2")) {
                                                item_expDate = "-";
                                                break;
                                            } else {
                                                System.out.println("Invalid input.");
                                            }
                                        } while (!expDateYesNo.equals("1") && !expDateYesNo.equals("2"));
                                        
                                        //find is there a same item's name and supplierID or not
                                        
                                        if(documentation.findItem("item_info.txt", item_name, item_supplierID)){
                                            System.out.println("This item is available in item_info.txt.");
                                            System.out.println("Please go to modify the stock of this item.");
                                            
                                        } else {
                                            Item.addItem(itemIDCharacter, category, item_name, price, item_stock, item_expDate, item_supplierID);
                                        }
                                        break;
                                        
                                    case "2":
                                        documentation.readFile("item_info.txt",true,"item");                                        
                                        Scanner findItem = new Scanner(System.in);
                                        String itemIdWantDelete;
                                        System.out.println("\nPlease enter the item's id that you want to delete:");
                                        itemIdWantDelete = findItem.nextLine();
                                        documentation.deleteLine("item_info.txt", itemIdWantDelete,"");
                                        break;
                                        
                                    case "3":
                                        documentation.readFile("item_info.txt",true, "item");
                                        String inputItemID;
                                        do{
                                            System.out.println("\nEnter the ITEM ID that you want to modify : ");
                                            inputItemID = input.nextLine();
                                        } while(documentation.findLine("item_info.txt", inputItemID, "validItemID"));
                                        //modify
                                        Item.Modify(inputItemID, "item_info.txt");
                                        break;
                                        
                                    case "4":
                                        stopLoop2=false;
                                        break;
                                    
                                    default:
                                        System.out.println("Invalid Input . Please Enter Again.");
                                }
                            }
                            break;
                            
                        case "2":
                            String  SuppEntry;
                            boolean stopLoop1=true;
                            
                            while(stopLoop1){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. Add Supplier");
                                System.out.println("2. Delete Supplier ");
                                System.out.println("3. Modify Supplier ");
                                System.out.println("4. Exit Supplier ");
                                System.out.println("\nPlease Enter number 1-4 :");
                                SuppEntry = input.nextLine();
                                
                                switch(SuppEntry){
                                    case "1":
                                        String addNewORadd;
                                        do{
                                            System.out.println("\nChoose The Option:");
                                            System.out.println("1. Add New Supplier");
                                            System.out.println("2. Add Supplier Item ");
                                            System.out.println("\nChoice:  ");
                                            addNewORadd = input.nextLine();

                                            switch (addNewORadd) {
                                                case "1":
                                                    //supplier_ID,name,phoneNo,email
                                                    String supplierIDCharacter = "supplier";
                                                    System.out.println("\nEnter new supplier information: ");
                                                    String suppCompany_name;
                                                    do{
                                                        System.out.println("Enter Supplier Company Name : ");
                                                        suppCompany_name = input.nextLine();
                                                    } while(documentation.findLine("supplier_info.txt", suppCompany_name, "validSupplierCompanyName"));
                                                    
                                                    user User = new user();
                                                    String supplier_phoneNo = "";
                                                    while (!User.validPhoneNumber(supplier_phoneNo)) {
                                                        System.out.print("Enter Supplier phone number (10 digits): ");
                                                        supplier_phoneNo = input.nextLine();
                                                        if (!User.validPhoneNumber(supplier_phoneNo)) {
                                                            System.err.println("Invalid phone number. Please enter a 10-digit number.");
                                                        }
                                                    }
                                                    System.out.println("Enter supplier email: ");
                                                    String supplier_email = input.nextLine();
                                                    supp.addSupplier(supplierIDCharacter, suppCompany_name, supplier_phoneNo, supplier_email);
                                                    break;
                                                    
                                                case "2":
                                                    String inputOption = "";
                                                    boolean continousAddItem = false;
                                                    String supplierID;
                                                    
                                                    do{
                                                        documentation.readFile("supplier_info.txt",true,"supplier");      
                                                        System.out.println("Enter item supplier ID: ");
                                                        supplierID = input.nextLine();
                                                    } while(documentation.findLine("supplier_info.txt", supplierID, "validSupplierID"));

                                                    System.out.println("Input those items from this supplier.");
                                                    do{
                                                        supp.addSupplierItem(supplierID);
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
                                                    break;
                                                default:
                                                    System.out.println("Invalid input.");
                                                }
                                        } while(!addNewORadd.equals("1") && !addNewORadd.equals("2"));
                                        break;
                                        
                                    case "2":
                                        stopLoop= true;
                                        while(stopLoop){
                                        System.out.println("1. Delete Supplier \n2. Delete Supplier Supply Item");
                                        String  deleteOption = input.nextLine(); 
                                        
                                         switch(deleteOption){
                                            case "1": 
                                                String suppIdWantDelete;
                                                documentation.readFile("supplier_info.txt",true,"supplier");
                                                System.out.println("\nPlease enter the supplier's id that you want to delete:");
                                                suppIdWantDelete = input.nextLine();
                                                documentation.deleteLine("supplier_info.txt", suppIdWantDelete,"");
                                                documentation.deleteFile(suppIdWantDelete+".txt","supplier");
                                                stopLoop=false;
                                                break;
                                            case "2":
                                                documentation.readFile("supplier_info.txt",true,"supplierItem");
                                                System.out.println("\nPlease enter the supplier's id that you want to view for deleting item:");
                                                suppIdWantDelete = input.nextLine();
                                                documentation.readFile(suppIdWantDelete+".txt",true,"deleteSuppItem");
                                                stopLoop=false;
                                                break;
                                            default:
                                                System.out.println("Invalid Input . Please Enter Again.");
                                         }
                                       }
                                        break;
                                        
                                    case "3":
                                        stopLoop= true;
                                        while(stopLoop){
                                            System.out.println("Did you want to modify \n1. Modify Supplier Information \n2. Modify Information provided by the supplier for the item");
                                            String  modifyOption = input.nextLine(); 
                                            String supplierID,supplierItemID;
                                             switch(modifyOption){
                                                case "1": 
                                                    documentation.readFile("supplier_info.txt",true,"supplier");
                                                    do{
                                                    System.out.println("\nEnter SUPLLIER ID that you want to modify:");
                                                    supplierID = input.nextLine();
                                                    } while(documentation.findLine("supplier_info.txt", supplierID, "validSupplierID"));
                                                    supp.Modify(supplierID, "supplier_info.txt"); 
                                                    stopLoop=false;
                                                    break;

                                                case "2":
                                                    documentation.readFile("supplier_info.txt",false,"supplier");
                                                    do{
                                                    System.out.println("\nEnter SUPLLIER ID that you want to modify:");
                                                    supplierID = input.nextLine();
                                                    } while(documentation.findLine("supplier_info.txt", supplierID, "validSupplierID"));

                                                    String supplierIDFile= supplierID +".txt";
                                                    documentation.readFile(supplierIDFile,false,"supplierItem");

                                                   do{
                                                    System.out.println("\nEnter SUPLLIER Item ID that you want to modify:");
                                                    supplierItemID = input.nextLine();
                                                    } while(documentation.findLine(supplierIDFile, supplierItemID, "validItemID"));

                                                   supp.Modify(supplierItemID, supplierIDFile);
                                                   stopLoop=false;
                                                   break;

                                            default:
                                                System.out.println("Invalid Enter Please Enter Again. ");
                                            }    
                                    }
                                    break;
                                    
                                    case "4":
                                        stopLoop1=false;
                                        break;
                                        
                                    default : 
                                        System.out.println("Invalid Input . Please Enter Again.");
                                }
                            }
                            break;
                            
                        case "3":
                            boolean stopLoop3=true;
                            String dailySalesOption;
                            while(stopLoop3){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. Add Daily Sales");
                                System.out.println("2. Delete Daily Sales ");
                                System.out.println("3. Modify Daily Sales ");
                                System.out.println("4. Exit Daily Sales ");
                                System.out.println("\nPlease Enter number 1-4 :");
                                dailySalesOption = input.nextLine();
                                
                                switch(dailySalesOption){
                                    case "1":
                                        dailySales.addDailySales();
                                        break;
                                        
                                    case "2":  
                                        boolean stopLoop4=true;
                                        stopLoop4= true;
                                        while(stopLoop4){
                                        System.out.println("\nWhat you want to delete?");
                                        System.out.println("1. Delete Whole Daily Sales File\n2. Delete Specific line in Daily Slaes File\nEnter a Number:");
                                        String  deleteOption = input.nextLine(); 
                                        
                                         switch(deleteOption){
                                            case "1": 
                                                dailySales.deleteDailySales("allDailySales");
                                                stopLoop4=false;
                                                break;
                                            case "2":
                                                dailySales.deleteDailySales("specificDailySales");
                                                stopLoop4=false;
                                                break;
                                            default:
                                                System.out.println("Invalid Input . Please Enter Again.");
                                         }
                                        }                             
                                                                                
                                        break;
                                        
                                    case "3":
                                        String x;
                                        do{
                                            System.out.println("\nEnter a DATE that you want to modify Daily Sales : ");
                                            x = date.validIputDate() + "_dailySales.txt";
                                        }while(documentation.readFile(x, true,"DailySales"));
                                            
                                        String itemID;
                                        do{
                                            System.out.println("\nEnter a ITEM ID that you want to modify: ");
                                            itemID = input.nextLine();
                                        } while(documentation.findLine(x, itemID, "validItemID"));
                                        
                                        DailyS.Modify(itemID, x);
                                        break;
                                        
                                    case "4":
                                        stopLoop3=false;
                                        break;
                                    default:
                                        System.out.println("Invalid Input . Please Enter Again.");
                                }
                            }
                            
                            break;
                        case "4":
                            boolean stopLoop4=true, Loop=true;
                            String PROption;
                            while(stopLoop4){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. Add PR");
                                System.out.println("2. Delete PR ");
                                System.out.println("3. Modify PR ");
                                System.out.println("4. Exit PR ");
                                System.out.println("\nPlease Enter number 1-4 :");
                                PROption = input.nextLine();
                                
                                switch(PROption){
                                    case "1":
                                        PR.addPR_info();
                                        break;
                                    case "2":
                                        documentation.readFile("PR_info.txt",true,"PR");
                                        PR.deletePRFile();
                                        break;
                                    case "3":
                                        Loop=true;
                                        documentation.readFile("PR_info.txt",true,"PR");
                                        String PRID;
                                        do{
                                            System.out.println("\nInput the PR ID you want to modify:");
                                            PRID = input.nextLine();
                                        } while(documentation.findLine("PR_info.txt", PRID, "PRID_Status"));
                                        System.out.println("\n---------------------------------------------------------------PR Information-------------------------------------------------------------------------------");
                                        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s","Purchase Requisition ID","Sales Manager ID","Supplier ID","Request Date","Approval Staus");
                                        System.out.println("\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                        documentation.viewSpecificLine("PR_info.txt","PR_info",PRID);
                                        System.out.println("\n");
                                        String PRfileName = PRID+".txt";
                                        documentation.readFile(PRfileName,true,"PR_item");
                                        while (Loop){
                                            System.out.println("\nDo you want modify\n1. PR information \n2. PR item Information \nEnter number:");
                                            String PRoption = input.nextLine();
                                            switch(PRoption){
                                                case "1":
                                                    PR.Modify(PRID, "PR_info.txt");
                                                    Loop=false;
                                                    break;
                                                case "2":
                                                    PR.Modify(PRID, PRfileName);
                                                    Loop=false;
                                                    break;
                                                default:
                                                    System.out.println("Please enter again.");
                                                }
                                        }
                                        break;
                                    case "4":
                                        stopLoop4=false;
                                        break;
                                }
                            }
                            break;
                            
                        case "5":
                            documentation.readFile("PR_info.txt", true, "PR");
                            PR.viewPR();
                            break;
                            
                        case "6":
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
                            
                        case "7":
                            stopLoop=false;
                            break;
                            
                        default:
                            System.out.println("Invalid Input");
                    }//switch case
            }//while loop
    }// main 
}//class

