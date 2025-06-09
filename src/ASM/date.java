package ASM;

import java.util.Scanner;

import java.time.LocalDate;

public class date {
    private int year;
    private int month;
    private int day;
    
    Scanner input =new Scanner(System.in);
    
    public date(){
        LocalDate currentDate = LocalDate.now();
        year = currentDate.getYear();
        month = currentDate.getMonthValue();
        day = currentDate.getDayOfMonth();
    }
    
    public int getCurrentDate_line(){
        return year*10000 + month*100 + day;
    }
    
    public int getCurrentYear_line(){
        return year*10000;
    }
    
    public int getCurrentYear(){
        return year;
    }
    
    public void setCurrentYear(int year){
        this.year = year;
    }
    
    public int getCurrentMonth_line(){
        return month*100;
    }
    
    public int getCurrentMonth(){
        return month;
    }
    
    public void setCurrentMonth(int month){
        this.month = month;
    }
    
    public int getCurrentDay(){
        return day;
    }
    
    public void setCurrentDay(int day){
        this.day = day;
    }
    
    public int inputInt() {
    int integer;
    
    do {
        try {
            System.out.println("\nInput number: ");
            String integer_string = input.nextLine();
            integer = Integer.parseInt(integer_string);
            
            if (integer < 0) {
                System.out.println("Invalid input. Please enter a non-negative total sales value.");
            } else {
                break; // Exit the loop if input is successfully parsed and non-negative
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    } while (true);
    
    return integer;
}
    
    public String validIputDate(){
        do{
            System.out.println("\nYear:");
            year = inputInt();
            if(year < 1000 || year >9999){
                System.out.println("Invalid year.  Please enter again.");
            }
        }while(year < 1000 || year > 9999);
        do{
            System.out.println("\nMonth:");
            month = inputInt();
            if(month<1 || month >12){
                System.out.println("Invalid month.  Please enter again.");
            }
        }while(month < 1 || month > 12);
        do{
            System.out.println("\nDay:");
            day = inputInt();
            if((month == 4 || month == 6 || month == 9 || month == 11 ) && day  == 31){
                System.out.println("Invalid day for the selected month");
            }else if(day < 1 || (day > 30 && (month == 4 || month == 6 || month == 9 || month == 11)) || day >31){
                System.out.println("Invalid day. Please enter again.");
            }
        }while(day < 1 || (day > 30 && (month == 4 || month == 6 || month == 9 || month == 11)) || day >31);

        //format month and day with leading zeros
        String formattedMonth = String.format("%02d",month);
        String formattedDay = String.format("%02d",day);
        
        return year+formattedMonth+formattedDay;
    }
    
    public String validDate_largerThanCurrentDate(String selection){
        int currentDate = getCurrentDate_line();
        int valid_date;
        String inputDate_String;
        int inputDate;
        do{
            inputDate_String = validIputDate();
            inputDate = Integer.parseInt(inputDate_String);
            valid_date = currentDate - inputDate;            
            
            if (valid_date >= 0){
                if(selection.equals("allDailySales")){
                    System.out.println("Available date for checking.");
                    documentation.deleteFile(inputDate+"_dailySales.txt","dailySales");
                }else if(selection.equals("specificDailySales")){
                    System.out.println("Available date for checking.");
                    documentation.readFile(inputDate+"_dailySales.txt",true,"deleteSpecificDailySales");
                } else if(selection.equals("addItem")){
                    System.out.println("This is a available date.");
                }         
                
            } else {
                System.out.println("This is not a available date.");
            }
        } while (valid_date < 0);  
        
        String validDate_String = Integer.toString(inputDate);
        return validDate_String;
    }    
   
    public String validDate_smallerThanCurrentDate(){
        int currentDate = getCurrentDate_line();
        int valid_date;
        String inputDate_String;
        int inputDate;
        do{
             inputDate_String = validIputDate();
            inputDate = Integer.parseInt(inputDate_String);
            valid_date = currentDate - inputDate;
            if (valid_date <= 0){
                System.out.println("This is a valid date.");
            } else {
                System.out.println("This is not a valid date.");
            }
        } while (valid_date > 0);  
        
        String validDate_String = Integer.toString(inputDate);
        return validDate_String;
    }
}