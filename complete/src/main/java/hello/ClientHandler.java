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
import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author ajl5735
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    HelloWorldView helloView = new HelloWorldView();
    AddressView addressView = new AddressView();
    AddressModel user;
    ThankYouView tyView = new ThankYouView();
    HashMap mapPath = null;
    String[] parts;
    AddressListView listView = new AddressListView();
    AddressListModel addressListModel;
    PublicView publicView = new PublicView();
//    ImageIcon image = new ImageIcon("fw/jpg");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos;
    ErrorView errorView = new ErrorView();
    AddressListDataView addressListDataView = new AddressListDataView();
    AddressListTableView addressListTableView = new AddressListTableView();
    AddressListJSONView addressListJSONView = new AddressListJSONView();

    public ClientHandler(Socket socket, AddressListModel alm) {
        this.socket = socket;
//        System.out.println("\nClientHandler Started for "
//                + this.socket);
//        this.addressListModel = AddressListModel.makeAddressListFromFile("fileName.csv");
        this.addressListModel = alm;
//        handleRequest(this.socket);
//        System.out.println("ClientHandler Terminated for "
//                + this.socket + "\n");
//        try {
//            dos = new DataOutputStream(socket.getOutputStream());
//        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    @Override
    public void run() {
//         this.socket = socket;
        System.out.println("\nClientHandler Started for "
                + this.socket);
//        this.addressListModel = AddressListModel.makeAddressListFromFile("fileName.csv");
//        this.addressListModel = AddressListModel.readJSONFromFile("JsonUser");
        handleRequest(this.socket);
        System.out.println("ClientHandler Terminated for "
                + this.socket + "\n");
    }

    public void handleRequest(Socket socket) {
//OutputStream os = socket.getOutputStream()
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            String headerLine = in.readLine();
            StringTokenizer tokenizer
                    = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();

            if (httpMethod.equals("GET")) {
                System.out.println("Get method processed");
                String path = tokenizer.nextToken();
                System.out.println("Path is: " + path);

                if (path.equals("/hello")) {

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");
                    out.write(helloView.getHtml());
                    out.write("\n");

                } else if (path.equals("/address")) {

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");
                    out.write(addressView.getHtml());
                    out.write("\n");

                } //   
                else if (path.equals("/list")) {

//                    addressListModel.saveToFile("fileName");
                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");
                    out.write(listView.getHtml("fileName.csv"));
                    out.write("\n");

                } else if (path.equals("/list2")) {

//addressListModel.saveToFile("fileName");
                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");

//                    addressListModel.saveJSONToFile("JsonUser");
                    out.write(addressListTableView.getHtml("JsonUser", this.addressListModel));
                    out.write("\n");

                } else if (path.equals("/test2")) {

//addressListModel.saveToFile("fileName");
                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");

//                    addressListModel.saveJSONToFile("JsonUser");
                    out.write(addressListDataView.getHtml(AddressListModel.serializeAsJSON2(this.addressListModel).toString()));
                    out.write("\n");

                } //
                else if (path.equals("/test")) {

//                    BETA CODE ONLY

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");

                    File jsonFile = new File("user.json");
                    AddressModel testModel = new AddressModel();
                    testModel.setName("andy");
                    testModel.setStreet("123 Street");
                    testModel.setState("PA");
                    testModel.setZip(16803);
                    ObjectMapper mapper = new ObjectMapper();

                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = ow.writeValueAsString(testModel);
                    System.out.println(json);

                    System.out.println("Attempting to deserialize");
                    AddressModel test2 = AddressModel.deserializeAsJSON(json);

                    out.write("\n");

                } else if (path.equals("/test3")) {

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");
                    out.write(addressListJSONView.getHtml("JsonUser"));

                    out.write("\n");

//                sendResponse(socket, 405, "Method Not Allowed");
                } // 
                else if (path.equals("/test5")) {

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");

                    JsonNode testNode = AddressListModel.serializeAsJSON2(this.addressListModel);
                    addressListModel.saveJSONToFile2(testNode, new File("JsonUser.json"));
                    out.write(addressListTableView.getHtml("JsonUser", this.addressListModel));
                    out.write("\n");

//                sendResponse(socket, 405, "Method Not Allowed");
                } // 
                else if (path.equals("/test4")) {

                    System.out.println("Path received");
                    out.write("HTTP/1.1 200 OK\n\n");
//                    out.write(addressListJSONView.getHtml("JsonUser"));
                    JsonNode testNode = AddressListModel.serializeAsJSON2(this.addressListModel);

                    out.write(testNode.toString());
                    out.write("\n");

//                sendResponse(socket, 405, "Method Not Allowed");
                } // 
                else if (path.contains("/submit")) {

                    System.out.println("Submit Recv");
                    if (path.indexOf("?") >= 0) {
                        String params = path.substring(path.indexOf("?") + 1);
                        mapPath = new HashMap<String, String>();
                        for (String pair : params.split("&")) {
                            String[] keyVal = pair.split("=");
                            if (keyVal.length == 2) {
                                mapPath.put(keyVal[0], keyVal[1]);
                            }
                        }
                    }

//                    System.out.println(mapPath.toString());

                    user = new AddressModel();
                    if (mapPath.get("name") != null) {

                        if (mapPath.get("name").toString().contains("+")) {
                            user.setName(mapPath.get("name").toString().replaceAll("\\+", "\\ "));
                        } else {
                            user.setName(mapPath.get("name").toString());
                        }
                    }
                    if (mapPath.get("state") != null) {

                        user.setState(mapPath.get("state").toString());
                    }

                    if (mapPath.get("zip") != null) {
                        user.setZip(Integer.parseInt(mapPath.get("zip").toString()));
                    }

                    if (mapPath.get("street") != null) {

                        if (mapPath.get("street").toString().contains("+")) {
                            user.setStreet(mapPath.get("street").toString().replaceAll("\\+", " "));
                        } else {
                            user.setStreet(mapPath.get("street").toString());
//
                        }

                    }

                    if (user.isValidate() == true) {
                        synchronized (this.addressListModel){
                        System.out.println("User is valid");
                        out.write("HTTP/1.1 200 OK\n\n");
                        out.write(tyView.getHtml());

                        out.write("\n");
//                        data being modifed
                        addressListModel.getAddressArrayList().add(user);
//                        System.out.println(user.serializeToString());
//                        System.out.println(addressListModel.getAddressArrayList().size());
//                        Data being modified
                        addressListModel.saveJSONToFile("JsonUser");
                            System.out.println("ArrayListSize is: " + this.addressListModel.getAddressArrayList().size());
//                        addressListModel.saveToFile("fileName.csv");
                        }
                    } else {
                        System.out.println("User is NOT valid");

                        System.out.println("Path received");
                        out.write("HTTP/1.1 200 OK\n\n");

                        out.write(addressView.getHtml(mapPath
                        ));

                        out.write("\n");

                    }

                    user.printInfo();

                } else {
                    System.out.println("404 Error");
//                    out.write("HTTP/1.1 404 Not Found\n\n");

                    if (checkFile(path) == true) {
//                        out.write("HTTP/1.1 200 OK\n".getBytes());
//                        
//                        if (path.endsWith(".png")){
//                        out.write("content-type:image/png\n");
//                            System.out.println("ends with png");
//                        }else if (path.endsWith(".jpg")){
//                        out.write("content-type:image/jpg\n");
//                            System.out.println("ends with jpg");}
//                            

//                        out.write("content-type: test/html\n");
//                        System.out.println("Content-Type: image/png\n\n");
//                        out.write("test");
                        socket.getOutputStream().write("HTTP/1.1 200 OK\n".getBytes());
//socket.getOutputStream().write("content-type:image/png\n".getBytes());
//socket.getOutputStream().write("HTTP/1.1 200 OK\n".getBytes());

                        File file = new File(path.replaceFirst("/", ""));
                        System.out.println("File Chekc inside else: " + path.replaceFirst("/", ""));

                        socket.getOutputStream().write("HTTP/1.1 200 OK".getBytes());
                        socket.getOutputStream().write("Content-Type: image/png\n\n".getBytes());

//                        socket.getOutputStream().write("Content-Type: test/html\n".getBytes());
                        byte[] bytesFromFile = PublicView.getBytes(file);
                        socket.getOutputStream().write(bytesFromFile);
                        System.out.println(bytesFromFile.length);

//                        socket.getOutputStream().write("\n".getBytes());
//                        out.write("\n");
                        System.out.println("TestTrue");
                        socket.getOutputStream().flush();
                        socket.getOutputStream().close();

                    } else {

                        out.write("HTTP/1.1 404 Not Found\n\n");
                        out.write(errorView.getHtml());
                        out.write("\n");
                        System.out.println("FileNotFoundEither");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(String fileName) {

        FileWriter fout;
        try {
            fout = new FileWriter("fileName.csv", false);

            for (int i = 0; i < addressListModel.getAddressArrayList().size(); i++) {

                fout.write(user.getName() + "," + user.getStreet() + "," + user.getState() + "," + user.getZip() + "\n");
                fout.close();
            }

        } catch (IOException ex) {
            System.out.println("Unable To Create File");
        }
    }

    static public boolean checkFile(String path) {

        File tmpDir = new File(path.replaceFirst("/", ""));
        boolean exists = tmpDir.exists();
        if (exists == true) {
            System.out.println("File Check is true at: " + path.replaceFirst("/", ""));

            return true;
        } else {
            System.out.println("FileCheck is False at: " + path.replaceFirst("/", ""));
            return false;
        }

    }

}
