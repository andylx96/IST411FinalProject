/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajl5735
 */
public class AddressListModel {
//    AddressModel addresses = new AddressModel();

    boolean validate;
    ArrayList<AddressModel> addressArrayList;

//    AddressListModel() {
//        validate = true;
//        addressArrayList = new ArrayList();
//
//    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public ArrayList<AddressModel> getAddressArrayList() {
        return addressArrayList;
    }

    public void setAddressArrayList(ArrayList<AddressModel> addressArrayList) {
        this.addressArrayList = addressArrayList;
    }

    public void saveToFile(String fileName) {
        System.out.println(this.addressArrayList.size());

        FileWriter fout;
        try {
            fout = new FileWriter("fileName.csv", false);

            for (int i = 0; i < this.addressArrayList.size(); i++) {

                fout.write(this.addressArrayList.get(i).serializeToString() + "\n");
//                fout.write(this.addressArrayList.get(i).getName() + "," + this.addressArrayList.get(i).getStreet() + "," + this.addressArrayList.get(i).getState() + "," + this.addressArrayList.get(i).getZip() + "\n");

//                System.out.println("save to file : " + this.addressArrayList.get(i).getName() + "," + this.addressArrayList.get(i).getStreet() + "," + this.addressArrayList.get(i).getState() + "," + this.addressArrayList.get(i).getZip() + "\n");
            }
            fout.close();
        } catch (IOException ex) {
            System.out.println("Unable To Create File");
        }
    }

    public void saveJSONToFile(String fileName) {
//        System.out.println(this.addressArrayList.size());

        FileWriter fout;
        try {
            fout = new FileWriter(fileName + ".json", false);

            AddressListModel alm = new AddressListModel();
            alm.setAddressArrayList(this.addressArrayList);

            fout.write(AddressListModel.serializeAsJSON2(alm).toString());
            fout.close();
        } catch (IOException ex) {
            System.out.println("Unable To Create File");
        }
    }

    public void saveJSONToFile2(JsonNode jn, File f) {
//        System.out.println(this.addressArrayList.size());
//
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(f, jn);

//        FileWriter fout;
//        try {
//            fout = new FileWriter(fileName+".json", false);
//            
//            AddressListModel alm = new AddressListModel();
//            alm.setAddressArrayList(this.addressArrayList);
//            
//         fout.write(AddressListModel.serializeAsJSON2(alm).toString());
//            fout.close();
//        } catch (IOException ex) {
//            System.out.println("Unable To Create File");
//        }
        } catch (IOException ex) {
//        Logger.getLogger(AddressListModel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("unable to save to file2");
        }
    }

    static public AddressListModel readJSONFromFile(String fileName) {

        File tmpDir = new File(fileName + ".json");
        System.out.println(fileName + ".json");
        boolean exists = tmpDir.exists();
        if (exists == true) {

            AddressListModel alm = new AddressListModel();
            String tempLine = "";
            try {
                FileReader fin;
                fin = new FileReader(fileName + ".json");
                Scanner scan = new Scanner(fin);
                while (scan.hasNext()) {

                    tempLine += scan.nextLine();

//                    alm.getAddressArrayList().add(AddressModel.deSerializeFromString(tempLine));
                }

                alm = AddressListModel.deserializeAsJSON(tempLine);
                System.out.println("TESTTTTTT: " + tempLine);
            } catch (FileNotFoundException ex) {
                System.out.println("File Not Found for DB");
            }

            System.out.println("alm size is: " + alm.getAddressArrayList().size());
            return alm;

        } else {
            System.out.println("No File Found");
            return new AddressListModel();
        }

    }

    static public AddressListModel makeAddressListFromFile(String fileName) {

        File tmpDir = new File("fileName.csv");
        boolean exists = tmpDir.exists();
        if (exists == true) {

            AddressListModel alm = new AddressListModel();
            String tempLine;
            try {
                FileReader fin;
                fin = new FileReader("fileName.csv");
                Scanner scan = new Scanner(fin);
                while (scan.hasNext()) {

                    tempLine = scan.next();

                    alm.getAddressArrayList().add(AddressModel.deSerializeFromString(tempLine));

                }
            } catch (FileNotFoundException ex) {
                System.out.println("File Not Found for DB");
            }

            System.out.println("alm size is: " + alm.getAddressArrayList().size());
            return alm;

        } else {
            System.out.println("No File Found");
            return new AddressListModel();
        }

    }

//    static public String serializeAsJSON(AddressListModel alm) {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = "";
//        try {
//            json = ow.writeValueAsString(alm);
//            System.out.println(json);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            StringWriter stringEmp = new StringWriter();
//            objectMapper.writeValue(stringEmp, alm);
////            System.out.println("Stringemp\n" +stringEmp.toString());
//
////            return json;
//        } catch (JsonProcessingException ex) {
////            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Unable to serilize as json");
//        } catch (IOException ex) {
////                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("IO exception during json serializeing");
//        }
//        System.out.println("Serialized JSON: " +json);
//        return json;
//    }
    static public AddressListModel deserializeAsJSON(String json) {
        AddressListModel tempModel = new AddressListModel();
        ObjectMapper objectMapper = new ObjectMapper();
//        StringBuffer jsonBuffer = new StringBuffer();
//        jsonBuffer.append(json);
        System.out.println("Json 2\n" + json);
        try {
            tempModel = objectMapper.readValue(json, AddressListModel.class);

        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.out.println("Unable to deserialize Json");
        }

        System.out.println("Name: " + tempModel.getAddressArrayList().get(0).getName());
        System.out.println("State: " + tempModel.getAddressArrayList().get(0).getStreet());
        System.out.println("State: " + tempModel.getAddressArrayList().get(0).getState());
        System.out.println("Zip: " + tempModel.getAddressArrayList().get(0).getZip());
        System.out.println("alm size for json is " + tempModel.getAddressArrayList().size());
        return tempModel;
    }

    static public JsonNode serializeAsJSON2(AddressListModel alm) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        JsonNode tempNode = null;
        try {
            json = ow.writeValueAsString(alm);
//            System.out.println(json);

            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringEmp = new StringWriter();
            objectMapper.writeValue(stringEmp, alm);
//            System.out.println("Stringemp\n" +stringEmp.toString());

            tempNode = objectMapper.valueToTree(alm);
//            System.out.println("temp node");
//            System.out.println(tempNode);
//            return json;
        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to serilize as json");
        } catch (IOException ex) {
//                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO exception during json serializeing");
        }
//        System.out.println("Serialized JSON: " + json);
        return tempNode;
    }
}
