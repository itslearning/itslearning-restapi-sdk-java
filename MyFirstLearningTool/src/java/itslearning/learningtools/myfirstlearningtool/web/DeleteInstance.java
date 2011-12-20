/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restApi.sdk.common.RequestParamsHandler;
import itslearning.platform.restapi.sdk.learningtoolapp.CommunicationHelper;
import itslearning.platform.restapi.sdk.learningtoolapp.ViewLearningToolRequestParams;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nikita.khudyakov
 */
public class DeleteInstance extends BaseServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        // Validate if the request is not expired and if the signature is valid
        CommunicationHelper.ValidateQueryString(request, new MySettings());
                                               
        // Use this parameter to identify if it is safe to delete shared content, it is True when last instance of the learning
        // object is deleted and now it is safe to delete learning object. For instance if you have test shared between several courses when one instance
        // is deleted this parameter will be False and you can only delete test results for deleted instance but not test content (description, questions).
        boolean safeToDeleteLearningObject = false;
        if (request.getParameter("SafeToDeleteLearningObject") != null)
        {
            safeToDeleteLearningObject = boolean.class.cast(request.getParameter("SafeToDeleteLearningObject"));
        }
        
        boolean isErrorOccured = false;
        // Then your clean up code goes...
        
        // NOTE: If your operation takes long time (delete files, communicate with external systems, ...) save delete request 
        // and do delete asynchronously

         
        //Setting up response
        response.setContentType("text/html;charset=UTF-8");        
        if (!isErrorOccured)
        {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else
        {
            // If something went wrong and you are not able to delete data or save request - 
            // just return error status and write problem description into response. 
            // Request will be sent again in this case.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            
            try 
            {               
                out.println("Error occured");                 
            } 
            finally 
            {            
            out.close();
            }
        }                        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
