package hello;

import com.fasterxml.jackson.databind.JsonNode;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class GreetingController extends WebMvcConfigurerAdapter {

    private AddressListModel alm = AddressListModel.readJSONFromFile("JsonUser");
//        alm.saveJSONToFile("JsonUser")

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
//        JsonNode js =AddressListModel.serializeAsJSON2(AddressListModel.readJSONFromFile("JsonUser.json"));
        return "greeting";
    }

    @GetMapping("/address")
    public String addressForm(Model model) {
        model.addAttribute("addressModel", new AddressModel());
        System.out.println(model);
        return "address";
    }

    @PostMapping("/address")
    public String addressSubmit(@Valid @ModelAttribute AddressModel addressModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("error, not valid");
            return "address";
        } else {
            alm.getAddressArrayList().add(addressModel);
            alm.saveJSONToFile("JsonUser");
            System.out.println("ArrayListSize is: " + this.alm.getAddressArrayList().size());
//        

            return "result";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("alm", alm.getAddressArrayList());
//        System.out.println(model);
        return "list";
    }

    @GetMapping("/test")
    public String test(Model model) {
        alm.saveJSONToFile("JsonUser");
        model.addAttribute("string", AddressListModel.serializeAsJSON2(alm).toString());
//        System.out.println(model);
        return "test";
    }
    
    
    @GetMapping("/test2")
    public String test2(Model model) {
        alm.saveJSONToFile("JsonUser");
        model.addAttribute("string", AddressListModel.serializeAsJSON2(alm).asText());
//        System.out.println(model);
        return "test";
    }

//    @GetMapping("/fw.jpg")
//    public String img(Model model) {
//        String fileName = "fw.jpg";
//
//        if (checkFile(fileName) == true) {
//
//            File file = new File(fileName);
//
//            byte[] bytesFromFile;
//            try {
//                bytesFromFile = getBytesFromFile(file);
//                
//                String s = Base64.encodeBase64String(bytesFromFile);
//                model.addAttribute("image", s);
//                
//
//            } catch (IOException ex) {
//                System.out.println("File Not Found");
//            }
//
//        }
//
//        return "image";
//    }
    @ResponseBody
    @RequestMapping(value = "/*.jpg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto() throws IOException {

        String fileName = "fw.jpg";
        byte[] bytesFromFile = null;

        if (checkFile(fileName) == true) {

            File file = new File(fileName);

            try {
                bytesFromFile = getBytesFromFile(file);

                String s = Base64.encodeBase64String(bytesFromFile);
//                model.addAttribute("image", s);

            } catch (IOException ex) {
                System.out.println("File Not Found");
            }

        }

        return bytesFromFile;
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

    public static byte[] getBytesFromFile(File f) throws IOException {

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
    }
}
