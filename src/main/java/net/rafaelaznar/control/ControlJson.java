/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.operaciones.GenericOperation;

/**
 *
 * @author rafa
 */
public class ControlJson extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        //retardo debug
//        try {
//            Thread.sleep(80);
//        } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
        //control de autenticación
        if (request.getSession().getAttribute("usuarioBean") == null) {            
            //pte mensaje de error
        } else {
            String op = request.getParameter("op");
            String ob = request.getParameter("ob");
            String callop = Character.toUpperCase(ob.charAt(0)) + ob.substring(1) + Character.toUpperCase(op.charAt(0)) + op.substring(1);
            try {
                try {
                    GenericOperation operation = (GenericOperation) Class.forName("net.rafaelaznar.operaciones." + callop).newInstance();
                    String data = operation.execute(request, response);
                    request.setAttribute("contenido", data);
                    getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControlJson.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControlJson.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ControlJson.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

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
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ControlJson.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
