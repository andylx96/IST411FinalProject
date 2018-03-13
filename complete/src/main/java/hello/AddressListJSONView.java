/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author zabuz
 */
public class AddressListJSONView {

    private StringBuilder html = new StringBuilder();
    private String dataPath;

    public AddressListJSONView() {

    }

    public String getHtml(String dataPath) {

        AddressListModel alm = new AddressListModel();

        File tmpDir = new File(dataPath + ".json");
        System.out.println(dataPath + ".json");
        boolean exists = tmpDir.exists();
        if (exists == true) {

            String tempLine = "";
            try {
                FileReader fin;
                fin = new FileReader(dataPath + ".json");
                Scanner scan = new Scanner(fin);
                while (scan.hasNext()) {

                    tempLine += scan.next();

//                    alm.getAddressArrayList().add(AddressModel.deSerializeFromString(tempLine));
                }

                alm = AddressListModel.deserializeAsJSON(tempLine);
            } catch (FileNotFoundException ex) {
                System.out.println("File Not Found for DB");
            }

            System.out.println("alm size is: " + alm.getAddressArrayList().size());

        } else {
            System.out.println("No File Found");
        }

        html.append("<html><br>");

//        html.append("<table border=\"1\"><caption>User List</caption>");
        for (int i = 0; i < alm.getAddressArrayList().size(); i++) {
//            html.append("Name: ");
            html.append(alm.getAddressArrayList().get(i).getName().replaceAll(" ", "_")+",");
//            html.append("\nStreet: ");
            html.append(alm.getAddressArrayList().get(i).getStreet().replaceAll(" ", "_")+",");
//            html.append("\nState: ");
            html.append(alm.getAddressArrayList().get(i).getState().replaceAll(" ", "_")+",");
//            html.append("\nZip: ");
            html.append(alm.getAddressArrayList().get(i).getZip()+",");
//            html.append("</td></tr>");
            html.append("<br>");
        }

//        
        html.append("</table></html>");
        return html.toString();
    }

}
