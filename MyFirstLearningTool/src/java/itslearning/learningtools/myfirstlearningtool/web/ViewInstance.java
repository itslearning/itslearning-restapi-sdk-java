package itslearning.learningtools.myfirstlearningtool.web;

import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.ElementPermission;
import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;
import itslearning.platform.restApi.sdk.common.entities.LearningObjectInstancePermissions;
import itslearning.platform.restApi.sdk.common.entities.UserInfo;
import itslearning.platform.restapi.sdk.learningtoolapp.CommunicationHelper;
import itslearning.platform.restapi.sdk.learningtoolapp.LearningObjectServicetRestClient;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AppLicense;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Notification;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Organisation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Testservlet to test some of the functionality in the SDK.
 * - Initializes a session
 * - Gets userInfo object ++
 * - Calls every restApi method.
 * - Generates a simple report
 * - Generates a simple Notification
 * @author Amund
 */
public class ViewInstance extends BaseServlet
{
    private String SuccessTerm = "<span style=\"color:green\">Success</span>";
    private String FailTerm =  "<span style=\"color:red\">Fail</span>";
    private String getLearningObjectInstaceSuccessString = "<li>"+SuccessTerm+": getLearningObjectInstance()</li>";
    private String getLearningObjectInstaceFailureString = "<li>"+FailTerm+": getLearningObjectInstance()</li>";
    private String updateLearningObjectInstaceSuccessString = "<li>"+SuccessTerm+": updateLearningObjectInstance()</li>";
    private String updateLearningObjectInstaceFailureString = "<li>"+FailTerm+": updateLearningObjectInstance()</li>";
    private String getPossibleAssessmentsSuccessString = "<li>"+SuccessTerm+": getPossibleAssessments()</li>";
    private String getPossibleAssessmentsFailureString = "<li>"+FailTerm+": getPossibleAssessments()</li>";
    private String getPossibleAssessmentItemsSuccessString = "<li>"+SuccessTerm+": getAssessmentItems()</li>";
    private String getPossibleAssessmentItemsFailureString = "<li>"+FailTerm+": getAssessmentItems()</li>";
    private String getPossibleAssessmentStatusesSuccessString = "<li>"+SuccessTerm+": getPossibleAssessmentStatuses()</li>";
    private String getPossibleAssessmentStatusesFailureString = "<li>"+FailTerm+": getPossibleAssessmentStatuses()</li>";
    private String getAssessmentStatusItemsSuccessString = "<li>"+SuccessTerm+": getAssessmentStatusItems()</li>";
    private String getAssessmentStatusItemsFailureString = "<li>"+FailTerm+": getAssessmentStatusItems()</li>";
    private String updateLearningObjectInstanceUserReportsSuccessString = "<li>"+SuccessTerm+": updateLearningObjectInstanceUserReport()</li>";
    private String updateLearningObjectInstanceUserReportSuccessString = "<li>"+SuccessTerm+": updateLearningObjectInstanceUserReports()</li>";
    private String updateLearningObjectInstanceUserReportFailureString = "<li>"+FailTerm+": updateLearningObjectInstanceUserReport()</li>";
    private String updateLearningObjectInstanceUserReportsFailureString = "<li>"+FailTerm+": updateLearningObjectInstanceUserReports()</li>";
    private String getLearningObjectInstanceUserReportSuccessString = "<li>"+SuccessTerm+": getLearningObjectInstanceUserReport()</li>";
    private String getLearningObjectInstanceUserReportFailureString = "<li>"+FailTerm+": getLearningObjectInstanceUserReport()</li>";
    private String getLearningObjectInstanceUserReportsSuccessString = "<li>"+SuccessTerm+": getLearningObjectInstanceUserReports()</li>";
    private String getLearningObjectInstanceUserReportsFailureString = "<li>"+FailTerm+": getLearningObjectInstanceUserReports()</li>";
    private String sendNotificationSuccessString = "<li>"+SuccessTerm+": sendNotification()</li>";
    private String sendNotificationFailureString = "<li>"+FailTerm+": sendNotification()</li>";
    private String getOrganisationSuccessString = "<li>"+SuccessTerm+": getOrganisations()</li>";
    private String getOrganisationFailureString = "<li>"+FailTerm+": getOrganisations()</li>";
    private String getAppLicensesSuccessString =  "<li>"+SuccessTerm+": getAppLicenses()</li>";
    private String getAppLicensesFailString =  "<li>"+FailTerm+": getAppLicenses()</li>";


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
	if (!isApiSessionCreated(request))
        {
            createSession(request);
        }

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ViewInstance</title>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<ul>");

        MySettings mySettings = new MySettings();

        // If your application is licensed, itslearning sends two parameters extra:
        // LicenseIds
        // ExternalLicenseIds
        // If you have the externalLicenseIds in your databasem you can check if the user has access to your
        // application on Add/ViewInstance pages by calling:
        // CommunicationHelper.getExternalLicenseIds(request);
        // This list can then be used to do a check against your application's licensedata.
        // You can also call
        // List<Integer> licenses = CommunicationHelper.getLicenseIds(request); for it's learning's internal licenseIds.
        // These two lists have the same sequence, so LicenseId[i] is the licenseId for ExternalLicenseId[i] which
        // is the external LicenseId your salesrepresentative has put into the license GUI in it's learning
        // List<String> externalLicenses = CommunicationHelper.getExternalLicenseIds(request);
        LearningObjectServicetRestClient restclient =
                new LearningObjectServicetRestClient(
                CommunicationHelper.getApiSession(request),
                mySettings.getSharedSecret(),
                mySettings.baseUrl);

        // Setup needed objects for test
        LearningObjectInstance loi = null;
        List<Assessment> assessments = null;
        List<AssessmentItem> assessmentItems = null;
        List<AssessmentStatus> possibleAssessmentStatuses = null;
        List<LearningObjectInstanceUserReport> reports = new ArrayList<LearningObjectInstanceUserReport>();
        LearningObjectInstanceUserReport report = new LearningObjectInstanceUserReport();

        // Get some important objects from session, just for inspecting in debug to see that they're OK
        UserInfo userInfo = CommunicationHelper.getUserInfo(request);
        ApiSession sess = CommunicationHelper.getApiSession(request);
        LearningObjectInstancePermissions permissions = CommunicationHelper.getPermissions(request);

        // Arguments needed
        int instanceId = CommunicationHelper.getLearningObjectInstanceId(request);
        int learningObjectId = CommunicationHelper.getLearningObjectId(request);
        int userId = CommunicationHelper.getUserInfo(request).getUserId();

        // LearningObjectInstance
        loi = testGetLearningObjectInstance(restclient, instanceId, learningObjectId, loi, out);
        // Example of usage of permissions. Note that LearningObjectInstancePermissions has the constants
        // to check a specific permission on the permissions object here
        if (permissions.hasPermission(LearningObjectInstancePermissions.EVALUATE))
        {
            loi = testUpdateLearningObjectInstance(loi, restclient, instanceId, learningObjectId, out);
        }
        else
        {
            out.println("<li>You do not have evaluate permissions or higher, skipping: testUpdateLearningObjectInstance</li>");
        }

        // Assessments
        testGetPossibleAssessments(restclient, instanceId, learningObjectId, assessments, out);
        testGetAssessmentItems(restclient, instanceId, learningObjectId, assessmentItems, out);
        testGetPossibleAssessmentStatuses(restclient, instanceId, learningObjectId, possibleAssessmentStatuses, out);

        // In order to get a list of assessmentStatusItems, the loi's assessmentStatusId must be set to
        // one of the possible assessment statuses!
        testGetAssessmentStatusItems(restclient, instanceId, learningObjectId, out);

        // Generate a mock user report for testing purposes
        report = generateMockUserReport(userId, loi);
        reports.add(report);

        // Test reports
        if (permissions.hasPermission(LearningObjectInstancePermissions.PARTICIPATE))
        {
            // We have a student, so we can update report/s
            testUpdateLearningObjectInstanceUserReport(restclient, report, instanceId, learningObjectId, userId, out);
            testUpdateLearningObjectInstanceUserReports(restclient, reports, instanceId, learningObjectId, out);
            testGetLearningObjectInstanceUserReport(restclient, instanceId, learningObjectId, userId, report, out);
        }
        else
        {
            out.println("<li>You do not have participate permissions, skipping: testUpdateLearningObjectInstanceUserReport, testUpdateLearningObjectInstanceUserReports, testGetLearningObjectInstanceUserReport</li>");
        }
        if (permissions.hasPermission(LearningObjectInstancePermissions.EVALUATE))
        {
            // We have a teacher, or someone with the evaluate permission that can get all userReports
            testGetLearningObjectInstanceUserReports(restclient, instanceId, learningObjectId, reports, out);
            
            testSendNotification(restclient, instanceId, learningObjectId, out);
        }
        else
        {
            out.println("<li>You do not have evaluate permissions or higher, skipping: testGetLearningObjectInstanceUserReports and testSendNotification" +
                    "<br />. Note: <i>Evaluate is only set for 'Learning activity' applications. 'Learning resource' applications will never have this permission.</i></li>");
        }

        testGetOrganisationsForCurrentUser(restclient, out);
        testGetAppLicensesForCurrentUser(restclient, out);
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

    private LearningObjectInstanceUserReport generateMockUserReport(int userId, LearningObjectInstance loi)
    {
        LearningObjectInstanceUserReport r = new LearningObjectInstanceUserReport();
        r.setComment("A comment");
        r.setFirstName("John");
        r.setLastName("Doe");
        r.setSimpleStatus(SimpleStatusType.Completed);
        r.setUserId(userId);
        r.setNumberOfTimesRead(new Integer(1));
        r.setSimplePercentScore(new Double(10));
        r.setAssessmentItemId(1);

        return r;
    }

    private void testGetAssessmentStatusItems(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, PrintWriter out)
    {
        // Testing getAssessmentStatusItems
        List<AssessmentStatusItem> assessmentStatusItems;
        try
        {
            assessmentStatusItems = restclient.getAssessmentStatusItems(instanceId, learningObjectId);
            if (assessmentStatusItems != null && !assessmentStatusItems.isEmpty())
            {
                out.println(getAssessmentStatusItemsSuccessString);
            }
            else
            {
                out.println(getAssessmentStatusItemsFailureString + ". Have you set assessmentStatus on your learningObjectInstance?");
            }
        } catch (Exception e)
        {
            out.println(getAssessmentStatusItemsFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetAssessmentItems(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, List<AssessmentItem> assessmentItems, PrintWriter out)
    {
        // Testing getPossibleAssessments
        try
        {
            assessmentItems = restclient.getAssessmentItems(instanceId, learningObjectId);
            if (assessmentItems != null)
            {
                out.println(getPossibleAssessmentItemsSuccessString);
                for (AssessmentItem assessmentItem : assessmentItems) {
                    out.println("<br>Description: " +assessmentItem.getDescription() + " ItemId: " + assessmentItem.getAssessmentItemId());
                }
            }
            else
            {
                out.println(getPossibleAssessmentItemsFailureString);
            }
        } catch (Exception e)
        {
            out.println(getPossibleAssessmentItemsFailureString + ". Exception was: " + e.toString());
        }
    }

    private LearningObjectInstance testGetLearningObjectInstance(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, LearningObjectInstance loi, PrintWriter out)
    {
        // Testing getLearningObjectInstance
        try
        {
            loi = restclient.getLearningObjectInstance(instanceId, learningObjectId);
            if (loi != null)
            {
                out.println(getLearningObjectInstaceSuccessString);
            }
            else
            {
                out.println(getLearningObjectInstaceFailureString);
            }
        } catch (Exception e)
        {
            out.println(getLearningObjectInstaceFailureString + ". Exception was: " + e.toString());
        }
        return loi;
    }

    private void testGetLearningObjectInstanceUserReport(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, int userId, LearningObjectInstanceUserReport report, PrintWriter out)
    {
        try
        {
            report = restclient.getLearningObjectInstanceUserReport(instanceId, learningObjectId, userId);
            if (report != null)
            {
                out.println(getLearningObjectInstanceUserReportSuccessString);
            }
            else
            {
                out.println(getLearningObjectInstanceUserReportFailureString + ". Nothing returned from the service, but was called correctly");
            }
        } catch (Exception e)
        {
            out.println(getLearningObjectInstanceUserReportFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetLearningObjectInstanceUserReports(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, List<LearningObjectInstanceUserReport> reports, PrintWriter out)
    {
        try
        {
            reports = restclient.getLearningObjectInstanceUserReports(instanceId, learningObjectId);
            if (reports != null && !reports.isEmpty())
            {
                out.println(getLearningObjectInstanceUserReportsSuccessString);
            }
            else
            {
                out.println(getLearningObjectInstanceUserReportsFailureString + ". Nothing returned from the service, but was called correctly");
            }
        } catch (Exception e)
        {
            out.println(getLearningObjectInstanceUserReportsFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetPossibleAssessmentStatuses(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, List<AssessmentStatus> possibleAssessmentStatuses, PrintWriter out)
    {
        // Testing getPossibleAssessmentStatuses
        try
        {
            possibleAssessmentStatuses = restclient.getPossibleAssessmentStatuses(instanceId, learningObjectId);
            if (possibleAssessmentStatuses != null)
            {
                out.println(getPossibleAssessmentStatusesSuccessString);
            }
            else
            {
                out.println(getPossibleAssessmentStatusesFailureString);
            }
        } catch (Exception e)
        {
            out.println(getPossibleAssessmentStatusesFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetPossibleAssessments(LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, List<Assessment> assessments, PrintWriter out)
    {
        // Testing getPossibleAssessments
        try
        {
            assessments = restclient.getPossibleAssessments(instanceId, learningObjectId);
            if (assessments != null)
            {
                out.println(getPossibleAssessmentsSuccessString);
            }
            else
            {
                out.println(getPossibleAssessmentsFailureString);
            }
        } catch (Exception e)
        {
            out.println(getPossibleAssessmentsFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testSendNotification(LearningObjectServicetRestClient restClient, int instanceId, int learningObjectId, PrintWriter out)
    {
        Notification notification = new Notification();
        notification.setLaunchParameter("launchParam=X_LaunchParameter");
        notification.setMessage("This is a message in the standard language.");
        notification.setReciverPermission(ElementPermission.Read);


        HashMap<String, String> localizedMessages = new HashMap<String, String>();

        localizedMessages.put("nb-no", "Dette er en beskjed på bokmål");
        localizedMessages.put("nn-no", "Dette er ein beskjed på nynorsk");

        notification.setLocalizedMessages(localizedMessages);
        try
        {
            restClient.sendNotification(notification, instanceId, learningObjectId);
            out.println(sendNotificationSuccessString);
        }
        catch(Exception e)
        {
            out.println(sendNotificationFailureString + ". Exception was: "+ e.toString());
        }

    }

    private LearningObjectInstance testUpdateLearningObjectInstance(LearningObjectInstance loi, LearningObjectServicetRestClient restclient, int instanceId, int learningObjectId, PrintWriter out)
    {
        // Testing updateLearningObjectInstance
        try
        {
            Date lastModified = loi.getModifiedUTC();
            // Change the title, just add something to check that its been changed after the call
            loi.setActiveFromUTC(new GregorianCalendar(2011,4,9).getTime());
            loi.setActiveToUTC(new GregorianCalendar(2012,4,16).getTime());

            loi.setModifiedUTC(new Date());
            loi.setAssessmentId(1);
            // Update it
            restclient.updateLearningObjectInstance(loi, instanceId, learningObjectId);
            // Get it again
            loi = restclient.getLearningObjectInstance(instanceId, learningObjectId);
            // If title is not the same, this is OK
            if (!loi.getModifiedUTC().equals(lastModified))
            {
                out.println(updateLearningObjectInstaceSuccessString);
            }
            else
            {
                out.println(updateLearningObjectInstaceFailureString);
            }
        } catch (Exception e)
        {
            out.println(updateLearningObjectInstaceFailureString + ". Exception was: " + e.toString());
        }
        return loi;
    }

    private void testUpdateLearningObjectInstanceUserReport(LearningObjectServicetRestClient restclient, LearningObjectInstanceUserReport report, int instanceId, int learningObjectId, int userId, PrintWriter out)
    {
        try
        {
            report.setComment("some comment");
            report.setAssessmentItemId(new Integer(1));
            restclient.updateLearningObjectInstanceUserReport(report, instanceId, learningObjectId, userId);
            out.println(updateLearningObjectInstanceUserReportSuccessString);
        } catch (Exception e)
        {
            out.println(updateLearningObjectInstanceUserReportFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testUpdateLearningObjectInstanceUserReports(LearningObjectServicetRestClient restclient, List<LearningObjectInstanceUserReport> reports, int instanceId, int learningObjectId, PrintWriter out)
    {
        try
        {
            restclient.updateLearningObjectInstanceUserReports(reports, instanceId, learningObjectId);
            out.println(updateLearningObjectInstanceUserReportsSuccessString);
        } catch (Exception e)
        {
            out.println(updateLearningObjectInstanceUserReportsFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetOrganisationsForCurrentUser(LearningObjectServicetRestClient restClient, PrintWriter out)
    {
        try
        {
            List<Organisation> organisations = restClient.getOrganisationsForCurrentUser();
            out.println(getOrganisationSuccessString);
            for(Organisation org : organisations)
            {
                out.println(String.format("Organisation: hierarchyId: %s - LegalId: %s - Title: %s - Type: %s</br>",
                        org.getHierarchyId().toString(), org.getLegalId(), org.getTitle(), org.getType().toString()));
            }
        } catch (Exception e)
        {
            out.println(getOrganisationFailureString + ". Exception was: " + e.toString());
        }
    }

    private void testGetAppLicensesForCurrentUser(LearningObjectServicetRestClient restClient, PrintWriter out)
    {
        try
        {
            List<AppLicense> appLicences = restClient.getAppLicensesForCurrentUser();
            out.println(getAppLicensesSuccessString);
            if(appLicences.isEmpty())
            {
                out.println("No licenses");
            }
            for(AppLicense appLic : appLicences)
            {
                out.println(String.format("AppLicenses: licenseId: %s - externalLicenseId: %s</br>",
                        appLic.getLicenseId().toString(), appLic.getExternalLicenseId()));
            }
        }
        catch (Exception e)
        {
            out.println(getAppLicensesFailString + ". Exception was: " + e.toString());
        }
    }
}
