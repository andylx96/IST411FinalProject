/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author ajl5735
 */
public class PublicView {

    private StringBuilder html = new StringBuilder();
//    private ImageIcon image = new ImageIcon("fw.jpg");
    
    public PublicView() {

//        html.append("<b>Hello World!</b><BR>");
//        html.append("</html>");
    }

    public String getHtml(String filePath) throws IOException {
//        html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n" +
//"   \"http://www.w3.org/TR/html4/strict.dtd\">");
        html.append("<html><body>");

//        html.append("<img src=\"" + image.getImage() + "\" alt=\"Flowers in Chania\">");
               html.append("<img src=\"" + "fw.jpg" + "\" alt=\"Flowers in Chania\">");
        
               
               File f = new File("rb.png");
               byte[] buffer = new byte[1024];
               ByteArrayOutputStream os = new ByteArrayOutputStream();
               FileInputStream fis = new FileInputStream(f);
               int read;
               while ((read = fis.read(buffer))!= -1){
               os.write(buffer, 0 ,read);
               }
               fis.close();
               os.close();
               os.toByteArray();
               
//        html.append(image.getImage());
//        html.append(html)
        
        System.out.println("FilePath from View test: <img src=\"" + "\" alt=\"Flowers in Chania\">");

        html.append("</body></html>");
        return html.toString();
    }
    
        public static byte[] getBytes(File f) throws IOException {
            
               BufferedImage o = ImageIO.read(f);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(o, "png", b);
        byte[] img = b.toByteArray();
            return img;
        
//               byte[] buffer = new byte[1024];
//               ByteArrayOutputStream os = new ByteArrayOutputStream();
//               FileInputStream fis = new FileInputStream(f);
//               int read;
//               while ((read = fis.read(buffer))!= -1){
//               os.write(buffer, 0 ,read);
//               }
//               fis.close();
//               os.close();
//             return  os.toByteArray();
               
    
}}
