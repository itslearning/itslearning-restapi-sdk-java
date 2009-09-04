/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restapi.sdk.learningtoolapp.CommunicationHelper;
import itslearning.platform.restapi.sdk.learningtoolapp.LearningObjectServiceClientRest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amund
 */
public class ViewInstance extends BaseServlet
{

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        //CommunicationHelper.initApiSession(request, new MySettings());
        //createSession(request);
        if (!isApiSessionCreated(request))
        {
            createSession(request);
        }

        PrintWriter out = response.getWriter();
        LearningObjectServiceClientRest restclient = new LearningObjectServiceClientRest(CommunicationHelper.getApiSession(request),
                                                                                        "http://betarest.itslearning.com");
        restclient.getLearningObjectInstance(CommunicationHelper.getLearningObjectInstanceId(request), CommunicationHelper.getLearningObjectId(request));

        response.setContentType("text/html;charset=UTF-8");
        String firstName = CommunicationHelper.getUserInfo(request).getFirstName();
        String lastName = CommunicationHelper.getUserInfo(request).getLastName();

        try
        {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewInstance</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewInstance at " + request.getContextPath() + "</h1>");
            out.println("<p>Session initialized!</p>");
            out.println("<p>Your name is: " + firstName + " " + lastName);
            out.println("</body>");
            out.println("</html>");

        } finally
        {
            out.close();
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}
