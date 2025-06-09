
package ASM;

import ASM.PM.*;
import ASM.SM.*;
import ASM.ADMIN.*;

public class System_Main_Page {

    public static  void main(String[] args){
        boolean stopLoop=true;
        while (stopLoop){
            user.login();

            if (user.checkRole("Admin_id.txt")){
                int AdminOption;
                System.out.println("Successful Login to Admin Page");
                admin.main();
            }
            else if (user.checkRole("PM_id.txt")){
                int PMOption;
                System.out.println("Successful Login to PURCHASE MANAGER Page");
                purchase_Manager.main();
            }
            else if (user.checkRole("SM_id.txt")){
                int SMOption;
                System.out.println("Successful Login to SALES MANAGER Page");
                sales_Manager.main();
            }
            else{
                System.out.println("Invalid Username or Password");
                System.out.println("\nPlease enter again");
            }
        }//while loop
    } //main method
} // class
