/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ajl5735
 */
public class AddressListTableView {

    private StringBuilder html = new StringBuilder();
    private String dataPath;

    public AddressListTableView() {

    }

    public String getHtml(String dataPath, AddressListModel alm) {

//        AddressListModel alm = new AddressListModel();

//        File tmpDir = new File(dataPath + ".json");
//        System.out.println(dataPath + ".json");
//        boolean exists = tmpDir.exists();
//        if (exists == true) {
//
//            String tempLine = "";
//            try {
//                FileReader fin;
//                fin = new FileReader(dataPath + ".json");
//                Scanner scan = new Scanner(fin);
//                while (scan.hasNext()) {
//
//                    tempLine += scan.nextLine();
//
////                    alm.getAddressArrayList().add(AddressModel.deSerializeFromString(tempLine));
//                }
//                System.out.println(tempLine);
//                alm = AddressListModel.deserializeAsJSON(tempLine);
//            } catch (FileNotFoundException ex) {
//                System.out.println("File Not Found for DB");
//            }
//
//            System.out.println("alm size is: " + alm.getAddressArrayList().size());
//
//        } else {
//            System.out.println("No File Found");
//        }

        html.append("<html>Table<br>");

        html.append("<table border=\"1\"><caption>User List</caption>");

        for (int i = 0; i < alm.getAddressArrayList().size(); i++) {
            html.append("<tr><td>");
            html.append(alm.getAddressArrayList().get(i).getName());
            html.append("</td><td>");
            html.append(alm.getAddressArrayList().get(i).getStreet());
            html.append("</td><td>");
            html.append(alm.getAddressArrayList().get(i).getState());
            html.append("</td><td>");
            html.append(alm.getAddressArrayList().get(i).getZip());
            html.append("</td></tr>");
        }

//        
        html.append("</table></html>");
        return html.toString();
    }

}
