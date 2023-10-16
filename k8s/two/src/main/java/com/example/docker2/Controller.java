package com.example.docker2;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    int noOfContents=2;
    String out="";


    private String calculate_Sum(File  file ,String productName,String param1) throws FileNotFoundException {
        Scanner scanner1 = new Scanner(file);
            // Read the file line by line
            System.out.println("sssssssssssssssssssssssssssssssssss");
            int sum=0;
            int flag=0;
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                System.out.println(line);
                String[] split_line=line.split(",");
                System.out.println("product from file"+split_line[0]);
                System.out.println("product from input"+productName);
                if(split_line[0].equalsIgnoreCase(productName))
                    {
                    flag=1;
                    sum=sum+Integer.parseInt(split_line[1].trim());
                    }
                }  
                scanner1.close();          
                if(flag==0)
                return "{\n\"file\": "+"\""+param1+"\""+", \n\"sum\":  "+"\""+sum+"\""+"\n}";
                else
                return "{\n\"file\": "+"\""+param1+"\""+", \n\"sum\":  "+"\""+sum+"\""+"\n}";

    }

    private boolean checkNoOFContents(String line,int line_no)
    {
       System.out.println("printing line"+line);
       if(!line.endsWith(",")){
       String[] split_line=line.split(",");
       System.out.println("SPlitlibne length= "+split_line.length );
       if(line_no==0 && split_line[0].toLowerCase().strip().equals("product") && split_line[1].toLowerCase().strip().equals("amount"))
       {
           System.out.println("entering only for line"+line_no);
           return true;
       }
       else if(line_no!=0 && split_line.length==noOfContents)
       return true;
       else
       return false;
    }
    else{
        return false;
    }
   }

   private boolean parseCsv(File file) {
    try {
        // Create a Scanner object to read the file
        System.out.println("parsding in container 2");
        Scanner scanner = new Scanner(file);
        boolean value=true;
        int line_no=0;
        // Read the file line by line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            value= checkNoOFContents(line,line_no); 
            line_no++;
            if(!value)
            return value; 
        
        }
        scanner.close();
    }
    catch (IOException e) {
        e.printStackTrace();
    }
    
    return true;

}
   

    @GetMapping("/performsum")
    public String senOutput(@RequestParam("param1") String param1,@RequestParam("param2") String param2) throws FileNotFoundException {

        
        //String filePath = "docker-2\\docker-2\\input\\"+param1;
        //String currentDir = System.getProperty("user.dir");

        //String filePath =currentDir+"\\"+"input"+"\\"+param1;
        String filePath = "src/rani_PV_dir/"+param1;

        File file = new File(filePath);
        boolean flag=false;
            flag=parseCsv(file);
            if(!flag)
            {
                 return "{\n\"file\": "+"\""+param1+"\""+", \n\"error\": \"Input file not in CSV format.\"\n}";
            }
            else
            {
             return calculate_Sum(file, param2,param1);
            }
       
    }



}

