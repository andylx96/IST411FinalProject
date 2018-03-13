/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

/**
 *
 * @author zabuz
 */
public class AddressListDataView {
    private StringBuilder html = new StringBuilder();

    public AddressListDataView() {
   
    }

    public String getHtml(String json) {
             html.append("<html><br>");
        html.append("<b>");
        html.append(json);
              html.append("</b><BR>");
        html.append("</html>");
        return html.toString();
    }
    
}
