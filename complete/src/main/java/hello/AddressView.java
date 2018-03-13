/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author ajl5735
 */
public class AddressView {

    private StringBuilder html = new StringBuilder();

    public AddressView() {

//        html.append("<b>Hello World!</b><BR>");
//        html.append("</html>");
    }

    public String getHtml() {
        html.append("<html><body>");
        html.append("<form action='/submit'>");
        html.append("Name: <input type='text' name='name'><br>"
                + "Street: <input type='text' name='street'><br>"
                + "State: <input type='text' name='state'><br>"
                + "Zip: <input type='text' name='zip'><br>");
        html.append("<input type='submit' value='Submit'><br>");
        html.append("</form>"
                + "</body></html>");
        return html.toString();
    }

    public String getHtml(HashMap mapPath) {
        String tempName = "", tempState = "", tempStreet = "";
        int tempZip = 0;
        String[] parts;

        if (mapPath.get("name") != null) {
            tempName = mapPath.get("name").toString();
            if (mapPath.get("name").toString().contains("+")) {
//                parts = mapPath.get("street").toString().split("\\+");
                tempName = tempName.replaceAll("\\+", " ");
//                System.out.println("parts 1 in view" + parts[0]);
//                System.out.println("parts 2 inview " + parts[1]);
                System.out.println("Name Cotains a +");
                System.out.println(tempName);
            }
        }
        if (mapPath.get("state") != null) {
            tempState = mapPath.get("state").toString();
        }
        if (mapPath.get("street") != null) {
            tempStreet = mapPath.get("street").toString();
            if (mapPath.get("street").toString().contains("+")) {
//                parts = mapPath.get("street").toString().split("\\+");
                tempStreet = tempStreet.replaceAll("\\+", " ");
//                System.out.println("parts 1 in view" + parts[0]);
//                System.out.println("parts 2 inview " + parts[1]);
                System.out.println("Street Cotains a +");
                System.out.println(tempStreet);
            }
        }
        if (mapPath.get("zip") != null) {
            tempZip = Integer.parseInt(mapPath.get("zip").toString());

        }

        html.append("<html><body>");
        html.append("<form action='/submit'>");
        html.append("Name: <input type='text' name='name' value=\'" + tempName + "\'><br>"
                //                + "Street: <input type='text' name='street' value="+ parts[0] +" " + parts[1] +"><br>"
                + "Street: <input type='text' name='street' value=\'" + tempStreet + "\'><br>"
                + "State: <input type='text' name='state' value=" + tempState + "><br>");
        if (tempZip == 0) {
            html.append("Zip: <input type='text' name='zip' value=" + "" + "><br>");
        } else {
            html.append("Zip: <input type='text' name='zip' value=" + tempZip + "><br>");
        }
        html.append("<input type='submit' value='Submit'><br>");
        html.append("</form> <br><br><h1>error</h1>"
                + "</body></html>");
        return html.toString();
    }

//    public String getHtml() {
//         html.append("<html><body>");
//        html.append("<form action='/submit'>");
//        html.append("Name: <input type='text' name='name'><br>"
//                + "Street: <input type='text' name='street'><br>"
//                + "State: <input type='text' name='state'><br>"
//                + "Zip: <input type='text' name='zip'><br>");
//        html.append("<input type='submit' value='Submit'><br>");
//        html.append("</form> <br><br><h1>error</h1>"
//                + "</body></html>");
//        return html.toString();
//    }
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
//                statusLine = "HTTP/1.1 200 OK" + "\r\n";
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
//
//            } else if (statusCode == 405) {
//                statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
//                out.writeBytes(statusLine);
//                out.writeBytes("\r\n");
//            } else {
//                statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
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
