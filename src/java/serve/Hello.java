/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serve;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thomas
 */
public class Hello extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            URL url = new URL(" https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=b5de6f93918ffa787d55d31b86f1ad0c&per_page=10&page=1&format=json&nojsoncallback=1&api_sig=85a1a32a427fd8faeb6ded17b80682e9");
            InputStream is = url.openStream();
            JsonParser parser = Json.createParser(is);
            String farm = null, server = null, id = null, secret = null, title = null, urlOut;

            while (parser.hasNext()) {
                JsonParser.Event e = parser.next(); /* some fun there */

                if (e == Event.KEY_NAME) {
                    switch (parser.getString()) {
                        case "farm":
                            parser.next();
                            farm = parser.getString(); /* farm will now be the {farm} part of the flickr image URL */
                            System.out.println(farm);
                            break;
                        case "server":
                            parser.next();
                            server = parser.getString();
                            System.out.println(server);
                            break;
                        case "id":
                            parser.next();
                            id = parser.getString();
                            System.out.println(id);

                            break;
                        case "secret":
                            parser.next();
                            secret = parser.getString();
                            System.out.println(secret);
                            break;
                        case "title":
                            parser.next();
                            title = parser.getString();
                            System.out.println(title);
                            break;
                    }

                } else if(e == Event.END_OBJECT){
                    urlOut = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
                    if (farm != null) {
                        out.println("<figure><img src='" + urlOut + "'><figcaption>" + title + "</figcaption></figure><br>");
                    }
                }else if(e == Event.END_ARRAY){
                    out.close();
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
