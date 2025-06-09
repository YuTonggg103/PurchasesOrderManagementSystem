
package ASM.PM;

import ASM.SM.*;
import ASM.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

interface PM {
    public void Modify(String ID,  String fileName);
}

public class purchase_Manager {
    
    public static String viewSpecificSuppItemInfor(String file){
        try{
            File txtfile = new File(file);
            Scanner openFile = new Scanner(txtfile);
            String line;
            boolean stopLoop = true;
            System.out.println("\n------------------------------------------------------------Supplied Item Information------------------------------------------------------------");
            System.out.printf("%-25s | %-25s | %-25s | %-25s","ID","Name","Category","Price");
            System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                
            while (openFile.hasNextLine() && stopLoop){
                line = openFile.nextLine();                
                String[] parts = line.split(",");
                System.out.printf("%-25s | %-25s | %-25s | %-25s\n",parts[0],parts[1],parts[2],parts[3]);   
            }    
            openFile.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File  has an error occurred.");
        }
        return null;
    }
    
    public static void main(){
            String PMOption;
            boolean stopLoop=true;
            
            while(stopLoop){
                    System.out.println("\nChoose The Option:");
                    System.out.println("1. View Item Information");
                    System.out.println("2. View Supplier Information ");
                    System.out.println("3. View Purchase Requisition (PR) ");
                    System.out.println("4. Purchase Oder (PO) Entry ");
                    System.out.println("5. View Purchase Oder (PO) ");
                    System.out.println("6. Exit Purchase  Manager Page");

                    purchaseRequistion PR  = new purchaseRequistion();
                    Scanner input =new Scanner(System.in);
                    System.out.println("\nPlease Enter number 1-6 :");
                    PMOption= input.nextLine();

                    switch (PMOption){
                        case "1":
                            documentation.readFile("item_info.txt", true,"item");
                            break;
                            
                        case "2":
                            String view_Option;
                            boolean stopLoop1=true;
                            while(stopLoop1){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. View All Supplier's Personal Information ");
                                System.out.println("2. View Specific Supplier's Supplied Item Information ");
                                System.out.println("3. Exit View Supplier Information  ");
                                System.out.println("\nPlease Enter number 1-3 :");
                                view_Option = input.nextLine();
                                switch(view_Option){
                                    case "1":                                        
                                        documentation.readFile("supplier_info.txt",true,"supplier");
                                        break;
                                    case "2":
                                        //print id and name of all supplier to view for choosing
                                        documentation.readFile("supplier_info.txt",false,"supplier");
                                        //ask input for supplier id to view
                                        Scanner findSupp = new Scanner(System.in);
                                        String suppIDtoFind;
                                        System.out.println("\nPlease enter the supplier's id that you want to view:");
                                        suppIDtoFind = findSupp.nextLine();
                                        documentation.findLine("supplier_info.txt", suppIDtoFind, "viewSpecificSupp");
                                        break;
                                    case "3":
                                        stopLoop1=false;
                                        break;
                                    default:
                                        System.out.println("Invalid Input . Please Enter Again.");
                                }
                            }
                            break;
                            
                        case "3":
                            documentation.readFile("PR_info.txt", true, "PR");
                            PR.viewPR();
                            break;
                            
                        case "4":
                            purchaseOrder PO = new purchaseOrder();
                            String PO_Option;
                            boolean stopLoop2=true;
                            while(stopLoop2){
                                System.out.println("\nChoose The Option:");
                                System.out.println("1. Add PO");
                                System.out.println("2. Delete PO ");
                                System.out.println("3. Modify PO ");
                                System.out.println("4. Exit PO ");
                                System.out.println("\nPlease Enter number 1-4 :");
                                PO_Option = input.nextLine();
                                
                                switch(PO_Option){
                                    case "1":
                                        // Check if there are pending PRs
                                        boolean hasPendingPR = PR.hasPendingPR(); 

                                        if (!hasPendingPR) {
                                            System.out.println("There are no purchase requisitions pending.");
                                        } else {
                                            PO.addPO_info();
                                        }
                                        break;
                                    case "2":
                                        PO.deletePO();
                                        break;
                                    case "3":
                                        Boolean Loop=true;
                                        documentation.readFile("PO_info.txt",true,"PO");
                                        String POID;
                                        do{
                                            System.out.println("Input the PO ID you want to modify:");
                                            POID = input.nextLine();
                                        } while(documentation.findLine("PO_info.txt", POID, "POID"));
                                         String PRID = documentation.viewSpecificLine("PO_info.txt","PO_getPRID",POID); // get PRID from PO
                                        String PO_status = documentation.viewSpecificLine("PR_info.txt","PR_status",PRID); //get POstatus from PR file
                                        System.out.println("\n--------------------------------------------------------------------PO Information--------------------------------------------------------------------"); 
                                        System.out.printf("%-25s | %-25s | %-25s | %-25s|%-25s","Purchase Order ID","Purchase Requisition ID","Purchase Manager ID","Approval Date"," Status");
                                        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                        documentation.viewSpecificLine("PO_info.txt","PO_info",POID); 
                                        System.out.print("| "+PO_status);
                                        while (Loop){
                                            System.out.println("\nDo you want modify PO status \n1.YES\n2.NO\nEnter number:");
                                            String POoption = input.nextLine();
                                            switch(POoption){
                                                case "1":
                                                    PO.Modify(POID, "PO_info.txt");
                                                    PO.Modify(PRID, "PR_info.txt");
                                                    Loop=false;
                                                    break;
                                                case "2":
                                                    Loop=false;
                                                    break;
                                                default:
                                                    System.out.println("Invalid input. Please enter again.");
                                                }
                                        }
                                        break;
                                        
                                    case "4":
                                        stopLoop2=false;
                                        break;
                                }
                            }
                            break;
                            
                        case "5":
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
                        case "6":
                            stopLoop=false;
                            break;
                        default:
                            System.out.println("Invalid Input . Please Enter Again.");
                }
            }
    }
}
