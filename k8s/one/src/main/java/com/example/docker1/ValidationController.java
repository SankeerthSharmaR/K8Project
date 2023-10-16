package com.example.docker1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ValidationController {

    int noOfContents=2;
    String out="";
    @PostMapping("/store-file")
    public ResponseEntity<String> getOutput(@RequestBody JsonInput input) throws IOException {

        String fileName=input.getFile();
        String data=input.getData();
        System.out.println("filename is:"+fileName+"data is:"+data);
        //return createFile(fileName,data);
        return validateFilename(fileName,data);
        //return "hello";
    }



    private ResponseEntity<String> validateFilename(String fileName,String data) throws IOException {

        if( fileName==null)
        {        
            System.out.println("inside null");    
             out= "{\n\"file\": null, \n\"error\": \"Invalid JSON input.\"\n}";
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);

            
        }

        //String currentDir = System.getProperty("user.dir");

       String filePath ="src/rani_PV_dir/"+fileName;
       // String filePath = currentDir+"\\"+/rani_PV_dir"+fileName;

        //String currentDir = System.getProperty("user.dir");
       // System.out.println("Current working directory is: " + currentDir);
        System.out.println("file path is:"+filePath);
        File file = new File(filePath);
        System.out.println(filePath);
        String fname=fileName;
        
        System.out.println("filename i s: "+fname);
        if(fname.isEmpty() || fileName==null)
        {        
            System.out.println("inside null");    
             out= "{\n\"file\": null, \n\"error\": \"Invalid JSON input .\"\n}";
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);

            
        }
        
              try {
            String content=data;
            String[] splitContent = content.split("\n");            
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
               for (String line : splitContent) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }

            bufferedWriter.close();
            System.out.println("File created and content written sucesfuly:  " + file.getAbsolutePath());
            out= "{\n\"file\": "+"\""+fname+"\""+", \n\"message\": \"Success.\"\n}";
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);

        } catch (IOException e) {
            out= "{\n\"file\": "+"\""+fname+"\""+", \n\"error\": \"Error while storing the file to the storage.\"\n}";
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);
            //System.out.println("An error occurred: " + e.getMessage());
        }
           

        
    }

    @PostMapping("/calculate")
    //@RequestBody JsonInput input
    private  ResponseEntity<String> calculate(@RequestBody JsonInput2 input) throws IOException {

        String secondUrl = "http://localhost:5000/performsum";
        String param1 = input.getFile();

        String param2 = input.getProduct();
        //String currentDir = System.getProperty("user.dir");
       
        if(param1 == null)
        {        
            System.out.println("inside null");    
             out= "{\n\"file\": null, \n\"error\": \"Invalid JSON input.\"\n}";
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);

            
        }
        String filePath ="src/rani_PV_dir/"+param1;
        File file=new File(filePath);
        if(!file.exists())
        {
            out= "{\n\"file\": "+"\""+param1+"\""+",\n\"error\": \"File not found.\"\n}";
             return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(out);
        }
        else{


            StringBuilder urlBuilder = new StringBuilder(secondUrl);
            urlBuilder.append("?param1=").append(URLEncoder.encode(param1, "UTF-8"));
            urlBuilder.append("&param2=").append(URLEncoder.encode(param2, "UTF-8"));

            URL url;
            
                url = new URL(urlBuilder.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                try {
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                       response.append("\n");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                in.close();

                String jsonResponse = response.toString();
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
            
            }
        }
 

   


}

