/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.DataOutputStream;
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
public class AddressListView {

    private StringBuilder html = new StringBuilder();
    private String dataPath;

    public AddressListView() {

    }

    public String getHtml(String dataPath) {
        ArrayList<String> tempNameList = new ArrayList<String>();
        ArrayList<String> tempStreetList = new ArrayList<String>();
        ArrayList<String> tempStateList = new ArrayList<String>();
        ArrayList<String> tempZipList = new ArrayList<String>();
        String tempLine;
        try {
            FileReader fin;
            fin = new FileReader(dataPath);
            Scanner scan = new Scanner(fin);
            while (scan.hasNext()) {

                tempLine = scan.next();
                String[] inArray = tempLine.split("\\,");
                   System.out.println("in array " + inArray.length);
//                   System.out.println(inArray[0]);
//                   System.out.println(inArray[2]);
//                   System.out.println(inArray[3]);
//                   System.out.println(inArray[4]);
                tempNameList.add(inArray[0].replaceAll("_", " "));
                tempStreetList.add(inArray[1].replaceAll("_", " "));
                tempStateList.add(inArray[2].replaceAll("_", " "));
                tempZipList.add(inArray[3].replaceAll("_", " "));

                System.out.println("NameList " + tempNameList);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found for DB");
        }

        html.append("<html>Table<br>");

        html.append("<table border=\"1\"><caption>User List</caption>");

        for (int i = 0; i < tempNameList.size(); i++) {
            html.append("<tr><td>");
            html.append(tempNameList.get(i));
            html.append("</td><td>");
            html.append(tempStreetList.get(i));
            html.append("</td><td>");

            html.append(tempStateList.get(i));
            html.append("</td><td>");

            html.append(tempZipList.get(i));

            html.append("</td></tr>");

        }

        html.append("</table></html>");
        return html.toString();
    }

//    public void sendResponse(Socket socket,
//            int statusCode, String html) {
//        String statusLine;
//        String serverHeader = "Server: WebServer\r\n";
//        String contentTypeHeader = "Content-Type: text/html\r\n";
//
//        try (DataOutputStream out
//                = new DataOutputStream(socket.getOutputStream());) {
//            System.out.println("Status Code :" + statusCode);
//            if (statusCode == 200) {
//                statusLine = "HTTP/1.0 200 OK" + "\r\n";
//                String contentLengthHeader = "Content-Length: "
//                        + html.length() + "\r\n";
//
////                out.writeBytes("HTTP/1.0 200 OK\n\n");
////                out.writeBytes("HTML");
////                out.writeBytes("\n");
//                out.writeBytes(statusLine);
//                out.writeBytes(serverHeader);
//                out.writeBytes(contentTypeHeader);
//                out.writeBytes(contentLengthHeader);
//                out.writeBytes("\r\n");
//                out.writeBytes(html);
////                System.out.println(html);
//
//            } else if (statusCode == 405) {
//                statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
//                out.writeBytes(statusLine);
//                out.writeBytes("\r\n");
//            } else {
//                statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
//                out.writeBytes(statusLine);
//                out.writeBytes("\r\n");
//            }
//
//            out.close();
//        } catch (IOException ex) {
//            // Handle exception
//        }
//    }
}
