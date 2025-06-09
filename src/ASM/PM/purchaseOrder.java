
package ASM.PM;

import ASM.*;
import java.io.*;
import java.util.*;

public class purchaseOrder implements PM{
    private Map<String, Integer> POCounter;
    public String PO_id;
    public String PM_id;
    public String PR_id;
    public int approveDate;
    
    public purchaseOrder() {
        this.POCounter = new HashMap<>();
        POCounter.put("PO", 1);
    }
    
    private String generatePOID() {
        int currentCounter = POCounter.getOrDefault("PO", 1);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("PO_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length > 0) {
                    String POIDPart = parts[0];
                    if (POIDPart.startsWith("PO")) {
                        user extractNoFromID = new user();
                        int number = extractNoFromID.extractNumber(POIDPart);
                        currentCounter = Math.max(currentCounter, number + 1);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            // Ignore and use the default counter value
        }

        POCounter.put("PO", currentCounter);

        String POIDGenerated = "PO" + currentCounter;
        return POIDGenerated;
    }
    
    public void addPO_info(){
        //PO_id,PR_id,PM_id,approveDate
        date Date = new date();
        Scanner input = new Scanner(System.in);
        String PO_id = generatePOID();
        this.PO_id = PO_id;
        String PM_id = user.getLoginID();
        this.PM_id = PM_id;
        
        String PR_id;
        do{
            documentation.readFile("PR_info.txt",true,"PR_pending");      
            System.out.println("Enter PR ID: ");
            PR_id = input.nextLine();
            this.PR_id = PR_id;
            documentation.readFile(PR_id+".txt", true, "PR_item");
        } while(documentation.findLine("PR_info.txt", PR_id, "PRID_Status"));
        
        String POEntry = PO_id + "," + PR_id + "," + PM_id + "," + Date.getCurrentDate_line();
        this.approveDate = Date.getCurrentDate_line();
        String POFile = "PO_info.txt";
        updatePRStatus(PR_id);
        
        try{
            BufferedWriter writeNewItem = new BufferedWriter(new FileWriter(POFile, true));
            writeNewItem.append(POEntry);
            writeNewItem.newLine();
            writeNewItem.close();
            System.out.println("New PO is added successfully into PO_info.txt.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        
        //https://linuxhint.com/read-text-file-and-store-to-array-in-java/
        //https://java2blog.com/write-array-to-file-java/
        documentation.createFile(PO_id+".txt");
        List<String> PRText = new ArrayList<String>();
        try{
            BufferedReader getText = new BufferedReader(new FileReader(PR_id+".txt"));
            String text = getText.readLine();
            while (text != null) {
                PRText.add(text);
                text = getText.readLine();
            }
            getText.close();
            //write
            try {
                FileWriter writer = new FileWriter(PO_id + ".txt");

                for (String line : PRText) {
                    writer.write(line + "\n");
                }
                writer.close();
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    public void updatePRStatus(String PRID){
        try{
            File file = new File("PR_info.txt");
            Scanner sc = new Scanner(file);
            List<String> lines = new ArrayList<>();
            Scanner input = new Scanner(System.in);
            
            while(sc.hasNextLine()){
                lines.add(sc.nextLine());
            }
            sc.close();
            
            //Find the index of the PR in the list
            int PRIndex  = -1;
            for(int i = 0; i<lines.size(); i++){
                if(lines.get(i).startsWith(PRID + ",")){
                    PRIndex = i;
                    break;
                }
            }
            
            if(PRIndex != -1){
                String approvalStatus, approvalStatus_choice;
                String[] PRParts = lines.get(PRIndex).split(",");
                do{
                    System.out.println("\n1. Approve");
                    System.out.println("2. Reject");
                    System.out.println("Enter the Number:");
                    approvalStatus_choice = input.nextLine();
                    switch (approvalStatus_choice){
                        case "1":
                            approvalStatus = "approve";
                            PRParts[4] = "approve";
                            break;
                        case"2":
                            approvalStatus = "reject";
                            PRParts[4] = "reject";
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose a choice.");
                            approvalStatus = null;
                    }  
                }while (approvalStatus == null);
                
                //update the line in the list and write the updates contents back to the file
                lines.set(PRIndex, String.join(",", PRParts));
                FileWriter writer = new FileWriter("PR_info.txt");
                for(String line: lines){
                    writer.write(line+"\n");
                }
                writer.close();
                System.out.println(PRID + " status updated successfully.");
                
            } else{ 
                System.out.println("PR not found. No changes were made.");
            }
        }catch(IOException e){
            System.err.println("An error occurred: " + e.getMessage());
        }
        
    }

    @Override
    public void Modify(String ID, String fileName) {
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
                        System.out.println("Original Status:"+table[i][4]);
                        if(table[i][4].equals("reject")){
                            table[i][4]= "approve";
                            System.out.println("New Status:"+table[i][4]);
                        }else if (table[i][4].equals("approve")){
                            table[i][4]= "reject";
                            System.out.println("New Status:"+table[i][4]);
                        }
                    }
                }
            }else if (fileName.equals("PO_info.txt")){
                for(int i = 0;  i<table.length; i++){
                     if (table[i][0].equals(ID)){
                        table[i][2]=user.getLoginID();
                        System.out.println("Purchases Manager's ID Updated Automatically:"+ table[i][2]);
                     }
                }
            }
            documentation.rewriteArrayToTextFile(fileName, table);
            bufferedReader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deletePO(){
        documentation.readFile("PO_info.txt",true,"PO");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter the PO ID that you want to delete: ");
        String POIDdelete = sc.nextLine();
        documentation.findLine("PO_info.txt",POIDdelete,"PO_delete");
    }
}
