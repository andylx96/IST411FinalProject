/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
//import com.fasterxml.jackson.annotation

/**
 *
 * @author ajl5735
 */
public class AddressModel {

    @NotNull
    String name;

    @NotNull
    String street;

    @NotNull
    @Min(00001)
    @Max(99999)
    int zip;

    @NotNull
    String state;

    boolean validate;

//    AddressModel(String name, String street, int zip, String state, boolean validate) {
//        this.name = name;
//        this.street = street;
//        this.zip = zip;
//        this.state = state;
//        this.validate = validate;
//    }
//
//    AddressModel() {
//        this.name = "";
//        this.street = "";
//        this.zip = 0;
//        this.state = "";
//        this.validate = false;
//    }
    public boolean isValidate() {
//        boolean valididty = false;

        if (!this.name.equalsIgnoreCase("")) {
            if (!this.street.equalsIgnoreCase("")) {

                if (!this.state.equalsIgnoreCase("")) {

                    if (this.zip != 0) {

                        if (this.zip > 0 && this.zip < 99999) {

                            if (this.street.matches(".*\\d+.*") && this.street.matches(".*[a-z].*")) {
                                this.validate = true;
                            }
                        }

                    }
                }

            }

        }

        return this.validate;
    }

    public void printInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Street: " + this.street);
        System.out.println("Zip: " + this.zip);
        System.out.println("State: " + this.state);
    }

//    @JsonGetter("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @JsonGetter("zip")
    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

//    @JsonGetter("state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @JsonGetter("street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String serializeToString() {
        String a = this.getName().replaceAll(" ", "_") + "," + this.street.replaceAll(" ", "_") + "," + this.state + "," + this.zip;
        System.out.println("String serialized: " + a);
        return a;
    }

    static AddressModel deSerializeFromString(String s) {
        AddressModel a = new AddressModel();
        String[] tempSplit;

        tempSplit = s.split(",");

        a.setName(tempSplit[0].replaceAll("_", "\\ "));
        a.setStreet(tempSplit[1].replaceAll("_", "\\ "));
        a.setState(tempSplit[2]);
        a.setZip(Integer.parseInt(tempSplit[3]));
        System.out.println("DeSerialized Value: " + a.getName() + " " + a.getStreet() + " " + a.getState() + " " + a.getZip());
        return a;
    }

    static public String serializeAsJSON(AddressModel am) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(am);
            System.out.println(json);

            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringEmp = new StringWriter();
            objectMapper.writeValue(stringEmp, am);
//            System.out.println("Stringemp\n" +stringEmp.toString());

            return stringEmp.toString();
        } catch (JsonProcessingException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to serilize as json");
        } catch (IOException ex) {
//                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("IO exception during json serializeing");
        }

        return json;
    }

    static public AddressModel deserializeAsJSON(String json) {
        AddressModel tempModel = new AddressModel();
        ObjectMapper objectMapper = new ObjectMapper();
//        StringBuffer jsonBuffer = new StringBuffer();
//        jsonBuffer.append(json);
        System.out.println("Json 2\n" + json);
        try {
            tempModel = objectMapper.readValue(json, AddressModel.class);

        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.out.println("Unable to deserialize Json");
        }

        System.out.println("Name: " + tempModel.getName());
        System.out.println("State: " + tempModel.getStreet());
        System.out.println("State: " + tempModel.getState());
        System.out.println("Zip: " + tempModel.getZip());
        return tempModel;
    }

}
