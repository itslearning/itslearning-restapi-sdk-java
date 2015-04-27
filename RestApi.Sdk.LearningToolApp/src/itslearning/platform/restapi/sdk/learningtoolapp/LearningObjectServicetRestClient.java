package itslearning.platform.restapi.sdk.learningtoolapp;

import itslearning.platform.restApi.sdk.common.CryptographyHelper;
import itslearning.platform.restApi.sdk.common.ExceptionHandler;
import itslearning.platform.restApi.sdk.common.QueryStringBuilder;
import itslearning.platform.restApi.sdk.common.ThreadSafeDateFormat;
import itslearning.platform.restApi.sdk.common.entities.ApiSession;
import itslearning.platform.restApi.sdk.common.entities.Constants.OrganisationType;
import itslearning.platform.restApi.sdk.common.entities.Constants.SimpleStatusType;
import itslearning.platform.restApi.sdk.common.entities.Constants.EducationSegment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AppLicense;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Assessment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatus;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.AssessmentStatusItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.CollaborationParticipant;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.CustomerSettings;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.EntityConstants;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.EntityConstants.OrderDirection;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstance;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUser;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReport;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjectInstanceUserReportCommentOnComment;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.LearningObjective;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Notification;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Organisation;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.OrganisationRole;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.RubricAchievementLevel;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.RubricCriteriaItem;
import itslearning.platform.restapi.sdk.learningtoolapp.entities.Site;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import javax.xml.ws.http.HTTPException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import itslearning.platform.restapi.sdk.learningtoolapp.HttpDeleteWithBody;
import org.apache.http.entity.StringEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 * Bundled implementation of the ILearningObjectServiceRestClient
 * @author Amund Trovåg
 */
public class LearningObjectServicetRestClient implements ILearningObjectServiceRestClient
{

    private HttpClient _httpClient = new HttpClient();
    private DefaultHttpClient _httpClientForDelete = new DefaultHttpClient();
    private static ThreadSafeDateFormat sdf = new ThreadSafeDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static
    {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    private ApiSession _apiSession;
    private String _baseUri;
    private String _sharedSecret;

    /**
     *
     * @param apiSession
     * @param baseUri the baseUri of the restApi, e.g. http://betarest.itslearning.com depending on the environment. This must be set by client
     * in order to communicate.
     */
    public LearningObjectServicetRestClient(ApiSession apiSession, String sharedSecret, String baseUri)
    {
        if (apiSession == null || baseUri == null || baseUri.isEmpty())
        {
            throw new IllegalArgumentException("The client needs an apisession and a baseUri to work");
        }
        this._apiSession = apiSession;
        this._baseUri = baseUri;
        this._sharedSecret = sharedSecret;
    }

    protected enum HttpMethodType
    {

        GET, PUT, POST, DELETE;
    }
    
    private String intArrayToCsvString(int [] items)
    {
        StringBuilder result = new StringBuilder();
        for(int item : items) {
            result.append(item);
            result.append(",");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }

    private List<AssessmentStatusItem> deserializeXMLToListOfAssessmentStatusItems(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentStatusItem> result = new ArrayList<AssessmentStatusItem>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentStatusItem";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentStatusItem");

        for (Node node : nodes)
        {
            AssessmentStatusItem assessmentStatusItem = new AssessmentStatusItem();
            Node n = node.selectSingleNode("loi:AssessmentStatusId");
            if (n.hasContent())
            {
                assessmentStatusItem.setAssessmentStatusId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:AssessmentStatusItemId");
            if (n.hasContent())
            {
                assessmentStatusItem.setAssessmentStatusItemId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentStatusItem.setTitle(n.getStringValue());
            }
            result.add(assessmentStatusItem);
        }

        return result;
    }

    /**
     * xml looks like this
<EntityList xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
  <CurrentPageIndex>0</CurrentPageIndex>
  <EntityArray>
    <Organization>
      <HierarchyId>1</HierarchyId>
      <LegalId>FK</LegalId>
      <Title>Fylkeskommune</Title>
      <Type>Site</Type>
    </Organization>
    <Organization>
      <HierarchyId>6</HierarchyId>
      <LegalId>S-B</LegalId>
      <Title>School B</Title>
      <Type>School</Type>
    </Organization>
  </EntityArray>
  <PageSize>2</PageSize>
  <Total>2</Total>
</EntityList>
     *
     * @param responseBodyAsStream
     * @return
     */
    private List<Organisation> deserializeXMLToOrganisations(InputStream xmlStream) throws DocumentException
    {
        List<Organisation> result = new ArrayList<Organisation>();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//org:ArrayOfOrganization";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("org", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/org:Organization");

        for(Node n : nodes)
        {
            Organisation organisation = new Organisation();
            Node node = n.selectSingleNode("org:HierarchyId");
            if(node.hasContent()){
                organisation.setHierarchyId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("org:SyncLocationId");
            if(node.hasContent()){
                organisation.setSyncLocationId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("org:LegalId");
            if(node.hasContent()){
                organisation.setLegalId(node.getStringValue());
            }
            node = n.selectSingleNode("org:Title");
            if(node.hasContent()){
                organisation.setTitle(node.getStringValue());
            }
            node = n.selectSingleNode("org:Type");
            if(node.hasContent())
            {
                try
                {
                    organisation.setType(OrganisationType.valueOf(node.getStringValue()));
                }
                catch(IllegalArgumentException iea){
                    organisation.setType(OrganisationType.Unknown);
                }
            }
            result.add(organisation);
        }

        return result;
    }
    
    /**
     * xml looks like this
<ArrayOfOrganizationRole xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
	<OrganizationRole>
		<HierarchyId>1</HierarchyId>
		<HomeOrganization>false</HomeOrganization>
		<Role>Administrator</Role>
	</OrganizationRole>
	<OrganizationRole>
		<HierarchyId>2</HierarchyId>
		<HomeOrganization>false</HomeOrganization>
		<Role>Administrator</Role>
	</OrganizationRole>
	<OrganizationRole>
		<HierarchyId>3</HierarchyId>
		<HomeOrganization>false</HomeOrganization>
		<Role>Administrator</Role>
	</OrganizationRole>
	<OrganizationRole>
		<HierarchyId>5</HierarchyId>
		<HomeOrganization>false</HomeOrganization>
		<Role>Administrator</Role>
	</OrganizationRole>
	<OrganizationRole>
		<HierarchyId>6</HierarchyId>
		<HomeOrganization>true</HomeOrganization>
		<Role>Administrator</Role>
	</OrganizationRole>
</ArrayOfOrganizationRole>
     *
     * @param responseBodyAsStream
     * @return
     */
    private List<OrganisationRole> deserializeXMLToOrganisationRoles(InputStream xmlStream) throws DocumentException
    {
        List<OrganisationRole> result = new ArrayList<OrganisationRole>();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//org:ArrayOfOrganizationRole";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("org", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/org:OrganizationRole");

        for(Node n : nodes)
        {
            OrganisationRole organisationRole = new OrganisationRole();
            Node node = n.selectSingleNode("org:HierarchyId");
            if(node.hasContent()){
                organisationRole.setHierarchyId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("org:HomeOrganization");
            if(node.hasContent()){
                organisationRole.setHomeOrganization(Boolean.parseBoolean(node.getStringValue()));
            }
            node = n.selectSingleNode("org:Role");
            if(node.hasContent()){
                organisationRole.setRole(node.getStringValue());
            }
            result.add(organisationRole);
        }

        return result;
    }
    
    private List<AppLicense> deserializeXMLToAppLicenses(InputStream xmlStream) throws DocumentException {
        List<AppLicense> result = new ArrayList<AppLicense>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAppLicense";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AppLicense");

        for(Node n : nodes)
        {
            AppLicense appLicense = new AppLicense();
            Node node = n.selectSingleNode("loi:LicenseId");
            if(node.hasContent()){
                appLicense.setLicenseId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:ExternalLicenseId");
            if(node.hasContent()){
                appLicense.setExternalLicenseId(node.getStringValue());
            }
            result.add(appLicense);
        }
        return result;
    }
    
    private Site deserializeXMLToSite(InputStream xmlStream) throws ParseException, DocumentException
    {
        Site site = null;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);
        String lElem = "//loi:Site";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("Site"))
        {
            site = new Site();

            Node node = root.selectSingleNode(lElem + "/loi:Segment");
            if(node.hasContent())
            {
                try
                {
                    site.setSegment(EducationSegment.valueOf(node.getStringValue()));
                }
                catch(IllegalArgumentException iea){
                    site.setSegment(EducationSegment.Other);
                }
            }
            node = root.selectSingleNode(lElem + "/loi:CountryCode");
            if (node.hasContent())
            {
                site.setCountryCode(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Name");
            if (node.hasContent())
            {
                site.setName(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:ShortName");
            if (node.hasContent())
            {
                site.setShortName(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:BaseUrl");
            if (node.hasContent())
            {
                site.setBaseUrl(node.getStringValue());
            }
        }
        return site;
    }
    
    private CustomerSettings deserializeXMLToCustomerSettings(InputStream xmlStream) throws ParseException, DocumentException
    {
        CustomerSettings customerSettings = null;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);
        String lElem = "//loi:CustomerSettings";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("CustomerSettings"))
        {
            customerSettings = new CustomerSettings();

            Node node = doc.selectSingleNode("loi:PlagiarismCode");
            if(node.hasContent()){
                customerSettings.setPlagiarismCode(node.getStringValue());
            }
            node = doc.selectSingleNode("loi:PlagiarismEmail");
            if(node.hasContent()){
                customerSettings.setPlagiarismEmail(node.getStringValue());
            }
            node = doc.selectSingleNode("loi:PlagiarismShowStudentName");
            if(node.hasContent()){
                customerSettings.setPlagiarismShowStudentName(node.getStringValue());
            }
            node = doc.selectSingleNode("loi:UsePlagiarism");
            if(node.hasContent()){
                customerSettings.setUsePlagiarism(Boolean.parseBoolean(node.getStringValue()));
            }
        }
        return customerSettings;
    }
    
    /**
     * xml looks like this
<ArrayOfRubricCriteriaItem xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
    <RubricCriteriaItem>
        <Id>1</Id>
        <Title>First criteria item</Title>
        <LearningObjectiveId>33</LearningObjectiveId>
        <AchievementLevels>
            <RubricAchievementLevel>
                <Id>1</Id>
                <Text>Advanced</Text>
                <OrderNo>1</OrderNo>
            </RubricAchievementLevel>
            <RubricAchievementLevel>
                <Id>2</Id>
                <Text>Proficient</Text>
                <OrderNo>2</OrderNo>
            </RubricAchievementLevel>
            <RubricAchievementLevel>
                <Id>3</Id>
                <Text>Basic</Text>
                <OrderNo>4</OrderNo>
            </RubricAchievementLevel>
            <RubricAchievementLevel>
                <Id>4</Id>
                <Text>Below basic</Text>
                <OrderNo>4</OrderNo>
            </RubricAchievementLevel>
        </AchievementLevels>
    </RubricCriteriaItem>
</ArrayOfRubricCriteriaItem>
     *
     * @param responseBodyAsStream
     * @return
     */
    private List<RubricCriteriaItem> deserializeXMLToCriteria(InputStream xmlStream) throws DocumentException
    {
        List<RubricCriteriaItem> result = new ArrayList<RubricCriteriaItem>();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfRubricCriteriaItem";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:RubricCriteriaItem");

        for(Node n : nodes)
        {
            RubricCriteriaItem rubric = new RubricCriteriaItem();
            Node node = n.selectSingleNode("loi:Id");
            if(node.hasContent()){
                rubric.setId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:Title");
            if(node.hasContent()){
                rubric.setTitle(node.getStringValue());
            }
            node = n.selectSingleNode("loi:LearningObjectiveId");
            if(node.hasContent()){
                rubric.setLearningObjectiveId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:AchievementLevels");
            if(node.hasContent()){
                rubric.setAchievementLevels(getRubricAchievementLevelsFromXml(n.selectNodes("loi:RubricAchievementLevel")));
            }
            
            result.add(rubric);
        }

        return result;
    }
    
    private List<LearningObjective> deserializeXMLToListOfLearningObjectives(InputStream xmlStream) throws DocumentException
    {
        List<LearningObjective> result = new ArrayList<LearningObjective>();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfLearningObjective";
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));

        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:LearningObjective");

        for(Node n : nodes)
        {
            LearningObjective lo = new LearningObjective();
            Node node = n.selectSingleNode("loi:Id");
            if(node.hasContent()){
                lo.setId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:Title");
            if(node.hasContent()){
                lo.setTitle(node.getStringValue());
            }
            node = n.selectSingleNode("loi:Description");
            if(node.hasContent()){
                lo.setDescription(node.getStringValue());
            }
            result.add(lo);
        }

        return result;
    }
    
    private List<RubricAchievementLevel> getRubricAchievementLevelsFromXml(List<Node> nodes)
    {
        List<RubricAchievementLevel> result = new ArrayList<RubricAchievementLevel>();

        for(Node n : nodes)
        {
            RubricAchievementLevel level = new RubricAchievementLevel();
            Node node = n.selectSingleNode("loi:Id");
            if(node.hasContent()){
                level.setId(Integer.parseInt(node.getStringValue()));
            }
            node = n.selectSingleNode("loi:Text");
            if(node.hasContent()){
                level.setText(node.getStringValue());
            }
            node = n.selectSingleNode("loi:OrderNo");
            if(node.hasContent()){
                level.setOrderNo(Integer.parseInt(node.getStringValue()));
            }
            result.add(level);
        }
        
        return result;
    }

    private List<LearningObjectInstanceUserReport> deserializeXMLToListOfLearningObjectInstanceUserReport(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<LearningObjectInstanceUserReport> result = new ArrayList<LearningObjectInstanceUserReport>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfLearningObjectInstanceUserReport";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:LearningObjectInstanceUserReport");

        for(Node n : nodes){
            LearningObjectInstanceUserReport singleReport = new LearningObjectInstanceUserReport();
            fillSingleLearningObjectInstanceUserReportEntityFromXml(singleReport, root, lElem, n);
            result.add(singleReport);
        }

        return result;
    }
    
    private void fillSingleLearningObjectInstanceUserEntityFromXml(LearningObjectInstanceUser singleUser, Element root, String lElem, Node n)
    {
        Node node = n.selectSingleNode("loi:FirstName");
        if (node.hasContent())
        {
            singleUser.setFirstName(node.getStringValue());
        }
        node = n.selectSingleNode("loi:LastName");
        if (node.hasContent())
        {
            singleUser.setLastName(node.getStringValue());
        }

        node = n.selectSingleNode("loi:UserId");
        if (node.hasContent())
        {
            singleUser.setUserId(Integer.parseInt(node.getStringValue()));
        }

        // All following nodes are extended data which will only be sent if the app is allowed to receive them.
        // If one of the fields is there, all will be. 
        node = root.selectSingleNode(lElem + "/loi:Custom1");
        if( node != null)
        {
            if (node.hasContent())
            {
                singleUser.setCustom1(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom2");
            if (node.hasContent())
            {
                singleUser.setCustom2(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom3");
            if (node.hasContent())
            {
                singleUser.setCustom3(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom4");
            if (node.hasContent())
            {
                singleUser.setCustom4(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom5");
            if (node.hasContent())
            {
                singleUser.setCustom5(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom1Id");
            if (node.hasContent())
            {
                singleUser.setCustom1Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom2Id");
            if (node.hasContent())
            {
                singleUser.setCustom2Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom3Id");
            if (node.hasContent())
            {
                singleUser.setCustom3Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom4Id");
            if (node.hasContent())
            {
                singleUser.setCustom4Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Custom5Id");
            if (node.hasContent())
            {
                singleUser.setCustom5Id(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Mobile");
            if (node.hasContent())
            {
                singleUser.setMobile(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:SyncKey");
            if (node.hasContent())
            {
                singleUser.setSyncKey(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:Email");
            if (node.hasContent())
            {
                singleUser.setEmail(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:ProfileImageUrl");
            if (node.hasContent())
            {
                singleUser.setProfileImageUrl(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:ProfileImageSmallUrl");
            if (node.hasContent())
            {
                singleUser.setProfileImageSmallUrl(node.getStringValue());
            }
        } // End of extended data.
    }
    
    private void fillSingleLearningObjectInstanceUserReportEntityFromXml(LearningObjectInstanceUserReport singleReport, Element root, String lElem, Node n)
    {
        fillSingleLearningObjectInstanceUserEntityFromXml(singleReport, root, lElem, n);
            
        Node node = n.selectSingleNode("loi:AssessmentItemId");
        if (node.hasContent())
        {
            singleReport.setAssessmentItemId(Integer.parseInt(node.getStringValue()));
        }
        node = n.selectSingleNode("loi:AssessmentItemTitle");
        if (node.hasContent())
        {
            singleReport.setAssessmentItemTitle(node.getStringValue());
        }
        node = n.selectSingleNode("loi:AssessmentStatusItemId");
        if (node.hasContent())
        {
            singleReport.setAssessmentStatusItemId(Integer.parseInt(node.getStringValue()));
        }
        node = n.selectSingleNode("loi:AssessmentStatusItemTitle");
        if (node.hasContent())
        {
            singleReport.setAssessmentStatusItemTitle(node.getStringValue());
        }
        node = n.selectSingleNode("loi:Comment");
        if (node.hasContent())
        {
            singleReport.setComment(node.getStringValue());
        }
        node = n.selectSingleNode("loi:NumberOfTimesRead");
        if (node.hasContent())
        {
            singleReport.setNumberOfTimesRead(Integer.parseInt(node.getStringValue()));
        }
        node = n.selectSingleNode("loi:SimplePercentScore");
        if (node.hasContent())
        {
            singleReport.setSimplePercentScore(Double.parseDouble(node.getStringValue()));
        }
        node = n.selectSingleNode("loi:Score");
        if (node.hasContent())
        {
            singleReport.setScore(Double.parseDouble(node.getStringValue()));
        }
        node = n.selectSingleNode("loi:SimpleStatus");
        if (node.hasContent())
        {
            singleReport.setSimpleStatus(SimpleStatusType.valueOf(node.getStringValue()));
        }
    }

    private List<LearningObjectInstanceUser> deserializeXMLToListOfLearningObjectInstanceUser(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<LearningObjectInstanceUser> result = new ArrayList<LearningObjectInstanceUser>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfLearningObjectInstanceUser";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:LearningObjectInstanceUser");

        for(Node n : nodes){
            LearningObjectInstanceUser singleUser = new LearningObjectInstanceUser();
            fillSingleLearningObjectInstanceUserEntityFromXml(singleUser, root, lElem, n);
            result.add(singleUser);
        }

        return result;
    }

    
    private List<AssessmentStatus> deserializeXMLToListOfPossibleAssessmentStatuses(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentStatus> result = new ArrayList<AssessmentStatus>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentStatus";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentStatus");

        for (Node node : nodes)
        {
            AssessmentStatus assessmentStatus = new AssessmentStatus();
            Node n = node.selectSingleNode("loi:AssessmentStatusId");
            if (n.hasContent())
            {
                assessmentStatus.setAssessmentStatusId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentStatus.setTitle(n.getStringValue());
            }
            result.add(assessmentStatus);
        }

        return result;
    }

    private LearningObjectInstanceUserReport deserializeXMLToLearningObjectInstanceUserReport(InputStream xmlStream) throws ParseException, DocumentException
    {
        LearningObjectInstanceUserReport result = new LearningObjectInstanceUserReport();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:LearningObjectInstanceUserReport";
        
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("LearningObjectInstanceUserReport"))
        {
            result = new LearningObjectInstanceUserReport();
            fillSingleLearningObjectInstanceUserReportEntityFromXml(result, root, lElem, root);
        }

        return result;
    }
    
    private List<CollaborationParticipant> deserializeXMLToListOfCollaborationParticipant(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<CollaborationParticipant> result = new ArrayList<CollaborationParticipant>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfCollaborationParticipant";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:CollaborationParticipant");

        for (Node node : nodes)
        {
            CollaborationParticipant collaborationParticipant = new CollaborationParticipant();
            Node n = node.selectSingleNode("loi:FirstName");
            if (n.hasContent())
            {
                collaborationParticipant.setFirstName(n.getStringValue());
            }
            n = node.selectSingleNode("loi:LastName");
            if (n.hasContent())
            {
                collaborationParticipant.setFirstName(n.getStringValue());
            }
            n = node.selectSingleNode("loi:CollaborationId");
            if (n.hasContent())
            {
                collaborationParticipant.setUserId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:UserId");
            if (n.hasContent())
            {
                collaborationParticipant.setUserId(Integer.parseInt(n.getStringValue()));
            }
            result.add(collaborationParticipant);
        }

        return result;
    }
    
    private void fillLearningObjectInstanceUserXmlElement(Element element, LearningObjectInstanceUser user)
    {
        if (user.getFirstName() != null)
        {
            element.addElement("FirstName").addText(user.getFirstName());
        }
        if (user.getFirstName() != null)
        {
            element.addElement("FirstName").addText(user.getFirstName());
        }
        if (user.getLastName() != null)
        {
            element.addElement("LastName").addText(user.getLastName());
        }
        if (user.getEmail() != null)
        {
            element.addElement("Email").addText(user.getEmail());
        }
        if (user.getMobile() != null)
        {
            element.addElement("Mobile").addText(user.getMobile());
        }
        if (user.getSyncKey() != null)
        {
            element.addElement("SyncKey").addText(user.getSyncKey());
        }
        if (user.getProfileImageUrl() != null)
        {
            element.addElement("ProfileImageUrl").addText(user.getProfileImageUrl());
        }
        if (user.getProfileImageSmallUrl() != null)
        {
            element.addElement("ProfileImageSmallUrl").addText(user.getProfileImageSmallUrl());
        }
        if (user.getCustom1() != null)
        {
            element.addElement("Custom1").addText(user.getCustom1());
        }
        if (user.getCustom2() != null)
        {
            element.addElement("Custom2").addText(user.getCustom2());
        }
        if (user.getCustom3() != null)
        {
            element.addElement("Custom3").addText(user.getCustom3());
        }
        if (user.getCustom4() != null)
        {
            element.addElement("Custom4").addText(user.getCustom4());
        }
        if (user.getCustom5() != null)
        {
            element.addElement("Custom5").addText(user.getCustom5());
        }
        if (user.getCustom1Id() != null)
        {
            element.addElement("Custom1Id").addText(user.getCustom1Id());
        }
        if (user.getCustom2Id() != null)
        {
            element.addElement("Custom2Id").addText(user.getCustom2Id());
        }
        if (user.getCustom3Id() != null)
        {
            element.addElement("Custom3Id").addText(user.getCustom3Id());
        }
        if (user.getCustom4Id() != null)
        {
            element.addElement("Custom4Id").addText(user.getCustom4Id());
        }
        if (user.getCustom5Id() != null)
        {
            element.addElement("Custom5Id").addText(user.getCustom5Id());
        }
        element.addElement("UserId").addText(Integer.toString(user.getUserId()));
    }
    
    private void fillLearningObjectInstanceUserReportXmlElement(Element element, LearningObjectInstanceUserReport userReport)
    {
        fillLearningObjectInstanceUserXmlElement(element, userReport);
        if (userReport.getAssessmentItemId() != null)
        {
            element.addElement("AssessmentItemId").addText(userReport.getAssessmentItemId().toString());
        }
        if (userReport.getAssessmentItemTitle() != null)
        {
            element.addElement("AssessmentItemTitle").addText(userReport.getAssessmentItemTitle());
        }
        if (userReport.getAssessmentStatusItemId() != null)
        {
            element.addElement("AssessmentStatusItemId").addText(userReport.getAssessmentStatusItemId().toString());
        }
        if (userReport.getAssessmentStatusItemTitle() != null)
        {
            element.addElement("AssessmentStatusItemTitle").addText(userReport.getAssessmentStatusItemTitle());
        }
        if (userReport.getComment() != null)
        {
            element.addElement("Comment").addText(userReport.getComment());
        }
        if (userReport.getNumberOfTimesRead() != null)
        {
            element.addElement("NumberOfTimesRead").addText(userReport.getNumberOfTimesRead().toString());
        }
        if (userReport.getSimplePercentScore() != null)
        {
            element.addElement("SimplePercentScore").addText(userReport.getSimplePercentScore().toString());
        }
        if (userReport.getScore() != null)
        {
            element.addElement("Score").addText(userReport.getScore().toString());
        }
        if (userReport.getSimpleStatus() != null)
        {
            element.addElement("SimpleStatus").addText(userReport.getSimpleStatus().toString());
        }
    }

    private String serializeLearningObjectInstanceUserReportToXML(LearningObjectInstanceUserReport userReport)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("LearningObjectInstanceUserReport");
        root.setQName(new QName("LearningObjectInstanceUserReport", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
        
        fillLearningObjectInstanceUserReportXmlElement(root, userReport);
        
        return root.asXML();
    }
    
    private String serializeLearningObjectInstanceUserReportCommentOnCommentToXML(LearningObjectInstanceUserReportCommentOnComment reportComment)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("LearningObjectInstanceUserReportCommentOnComment");
        root.setQName(new QName("LearningObjectInstanceUserReportCommentOnComment", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
        
        if (reportComment.getUserId() != null)
        {
            root.addElement("UserId").addText(reportComment.getUserId().toString());
        }
        if (reportComment.getCommentText() != null)
        {
            root.addElement("CommentText").addText(reportComment.getCommentText());
        }
        if (reportComment.getCommentSyncKey() != null)
        {
            root.addElement("CommentSyncKey").addText(reportComment.getCommentSyncKey());
        }
        if (reportComment.getModifiedUtc() != null)
        {
            root.addElement("ModifiedUtc").addText(sdf.format(reportComment.getModifiedUtc()));
        }
        return root.asXML();
    }

    private String serializeNotificationToWrappedXML(Notification notification)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("notification");
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
        root.add(new Namespace("a", EntityConstants.NAMESPACE_ENTITIES));

        if (notification.getLaunchParameter() != null)
        {
            root.addElement("a:LaunchParameter").addText(notification.getLaunchParameter().toString());
        }
        if (notification.getLocalizedMessages() != null)
        {
            Element n = root.addElement("a:LocalizedMessages");
            n.add(new Namespace("b", EntityConstants.NAMESPACE_ARRAYS));

            HashMap<String,String> messages = notification.getLocalizedMessages();
            for(String lang : messages.keySet())
            {
                Element messageKeyValuePair = n.addElement("b:KeyValueOfstringstring");
                messageKeyValuePair.addElement("b:Key").addText(lang);
                messageKeyValuePair.addElement("b:Value").addText(messages.get(lang));
            }
        }
        if( notification.getMessage() != null )
        {
            root.addElement("a:Message").addText(notification.getMessage());
        }
        if( notification.getReciverPermission() != null )
        {
            root.addElement("a:ReciverPermission").addText(notification.getReciverPermission().toString());
        }

        return root.asXML();
    }
    
    private String serializeUserIdsToWrappedXML(int[] receiverUserIds)
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("receiverUserIds");
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
        root.add(new Namespace("a", EntityConstants.NAMESPACE_ARRAYS));
        
        for(int id : receiverUserIds)
        {
            Element idElement = root.addElement("a:int");
            idElement.setText(Integer.toString(id));
        }
        
        return root.asXML();
    }
    
    private String serializeListOfIntToWrappedXML(int[] ids)
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("ArrayOfint");
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
        root.add(new Namespace("a", EntityConstants.NAMESPACE_ARRAYS));
        
        for(int id : ids)
        {
            Element idElement = root.addElement("a:int");
            idElement.setText(Integer.toString(id));
        }
        
        return root.asXML();
    }

    private String serializeNotificationToXML(Notification notification)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("Notification");
        root.setQName(new QName("Notification", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        if (notification.getLaunchParameter() != null)
        {
            root.addElement("LaunchParameter").addText(notification.getLaunchParameter().toString());
        }
        if (notification.getLocalizedMessages() != null)
        {
            Element n = root.addElement("LocalizedMessages");
            n.add(new Namespace("a", EntityConstants.NAMESPACE_ARRAYS));

            HashMap<String,String> messages = notification.getLocalizedMessages();
            for(String lang : messages.keySet())
            {
                Element messageKeyValuePair = n.addElement("a:KeyValueOfstringstring");
                messageKeyValuePair.addElement("a:Key").addText(lang);
                messageKeyValuePair.addElement("a:Value").addText(messages.get(lang));
            }
        }
        if( notification.getMessage() != null )
        {
            root.addElement("Message").addText(notification.getMessage());
        }
        if( notification.getReciverPermission() != null )
        {
            root.addElement("ReciverPermission").addText(notification.getReciverPermission().toString());
        }

        return root.asXML();
    }

    private String serializeLearningObjectInstanceUserReportsToXML(List<LearningObjectInstanceUserReport> userReports)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("ArrayOfLearningObjectInstanceUserReport");
        root.setQName(new QName("ArrayOfLearningObjectInstanceUserReport", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        for (LearningObjectInstanceUserReport userReport : userReports)
        {
            Element n = root.addElement("LearningObjectInstanceUserReport");
            fillLearningObjectInstanceUserReportXmlElement(n, userReport);
        }
        return root.asXML();
    }

    private String serializeLearningObjectInstanceToXML(LearningObjectInstance instance)
    {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("LearningObjectInstance");
        root.setQName(new QName("LearningObjectInstance", new Namespace("", EntityConstants.NAMESPACE_ENTITIES)));
        root.add(new Namespace("i", "http://www.w3.org/2001/XMLSchema-instance"));

        if (instance.getActiveFromUTC() != null)
        {
            String activeFromUTCText = sdf.format(instance.getActiveFromUTC());
            root.addElement("ActiveFromUtc").addText(activeFromUTCText);
        }
        if (instance.getActiveToUTC() != null)
        {
            String activeToUTCText = sdf.format(instance.getActiveToUTC());
            root.addElement("ActiveToUtc").addText(activeToUTCText);
        }

        root.addElement("AssessmentId").addText(instance.getAssessmentId().toString());
        root.addElement("AssessmentStatusId").addText(instance.getAssessmentStatusId().toString());
        root.addElement("IsAssessmentVisible").addText(String.valueOf(instance.getIsAssessmentVisible()));
        root.addElement("CreatedByUserId").addText("" + instance.getCreatedByUserId());

        if (instance.getCreatedUTC() != null)
        {
            String createdUTCText = sdf.format(instance.getCreatedUTC());
            root.addElement("CreatedUtc").addText(createdUTCText);
        }
        root.addElement("CourseCode").addText("" + instance.getCourseCode());
        root.addElement("CourseId").addText("" + instance.getCourseId());
        root.addElement("CourseSyncKey").addText("" + instance.getCourseSyncKey());
        root.addElement("CourseOrganisationId").addText("" + instance.getCourseOrganisationId());
        root.addElement("CourseOrganisationSyncKey").addText("" + instance.getCourseOrganisationSyncKey());
        if (instance.getMaxScore() != null)
        {
            root.addElement("MaxScore").addText("" + instance.getMaxScore());
        }
        if (instance.getDeadLineUTC() != null)
        {
            String deadlineUTCText = sdf.format(instance.getDeadLineUTC());
            root.addElement("DeadlineUtc").addText(deadlineUTCText);
        }
        
        root.addElement("IsObligatory").addText(String.valueOf(instance.isIsObligatory()));

        root.addElement("LearningObjectId").addText("" + instance.getLearningObjectId());
        root.addElement("LearningObjectInstanceId").addText("" + instance.getLearningObjectInstanceId());

        if (instance.getModifiedUTC() != null)
        {
            String modifiedUTCText = sdf.format(instance.getModifiedUTC());
            root.addElement("ModifiedUtc").addText(modifiedUTCText);
        }
        
        root.addElement("Title").addText(instance.getTitle());


        return root.asXML();
    }

    private List<AssessmentItem> deserializeXMLToListOfAssessmentItems(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<AssessmentItem> result = new ArrayList<AssessmentItem>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessmentItem";
        
        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:AssessmentItem");

        for (Node node : nodes)
        {
            AssessmentItem assessmentItem = new AssessmentItem();
            Node n = node.selectSingleNode("loi:AssessmentId");
            if (n.hasContent())
            {
                assessmentItem.setAssessmentId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:AssessmentItemId");
            if (n.hasContent())
            {
                assessmentItem.setAssessmentItemId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Description");
            if (n.hasContent())
            {
                assessmentItem.setDescription(n.getStringValue());
            }
            n = node.selectSingleNode("loi:PercentFromAndIncl");
            if (n.hasContent())
            {
                assessmentItem.setPercentFromAndIncl(Double.parseDouble(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:PercentTo");
            if (n.hasContent())
            {
                assessmentItem.setPercentTo(Double.parseDouble(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessmentItem.setTitle(n.getStringValue());
            }
            result.add(assessmentItem);
        }
        return result;
    }

    private List<Assessment> deserializeXMLToListOfAssessments(InputStream xmlStream) throws ParseException, DocumentException
    {
        List<Assessment> result = new ArrayList<Assessment>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);

        String lElem = "//loi:ArrayOfAssessment";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();

        List<Node> nodes = root.selectNodes(lElem + "/loi:Assessment");

        for (Node node : nodes)
        {
            Assessment assessment = new Assessment();
            Node n = node.selectSingleNode("loi:AssessmentId");
            if (n.hasContent())
            {
                assessment.setAssessmentId(Integer.parseInt(n.getStringValue()));
            }
            n = node.selectSingleNode("loi:Description");
            if (n.hasContent())
            {
                assessment.setDescription(n.getStringValue());
            }
            n = node.selectSingleNode("loi:Title");
            if (n.hasContent())
            {
                assessment.setTitle(n.getStringValue());
            }
            result.add(assessment);
        }
        return result;
    }

    private LearningObjectInstance deserializeXMLToLearningObjectInstance(InputStream xmlStream) throws ParseException, DocumentException
    {
        LearningObjectInstance loi = null;

        SAXReader reader = new SAXReader();
        Document doc = reader.read(xmlStream);
        String lElem = "//loi:LearningObjectInstance";

        doc.getRootElement().setQName(new QName(doc.getRootElement().getQName().getName(),
                new Namespace("loi", doc.getRootElement().getNamespaceURI())));
        Element root = doc.getRootElement();
        if (root.getName().equals("LearningObjectInstance"))
        {
            loi = new LearningObjectInstance();

            Node node = root.selectSingleNode(lElem + "/loi:ActiveToUtc");
            if (node.hasContent())
            {
                loi.setActiveToUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:DeadlineUtc");
            if (node.hasContent())
            {
                loi.setDeadLineUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ActiveFromUtc");
            if (node.hasContent())
            {
                loi.setActiveFromUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedUtc");
            if (node.hasContent())
            {
                loi.setCreatedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ModifiedUtc");
            if (node.hasContent())
            {
                loi.setModifiedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:Title");
            if (node.hasContent())
            {
                loi.setTitle(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:LearningObjectInstanceId");
            if (node.hasContent())
            {
                loi.setLearningObjectInstanceId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:LearningObjectId");
            if (node.hasContent())
            {
                loi.setLearningObjectId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:ModifiedUtc");
            if (node.hasContent())
            {
                loi.setModifiedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedUtc");
            if (node.hasContent())
            {
                loi.setCreatedUTC(sdf.parse(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CreatedByUserId");
            if (node.hasContent())
            {
                loi.setCreatedByUserId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:IsObligatory");
            if (node.hasContent())
            {
                loi.setIsObligatory(Boolean.parseBoolean(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentId");
            if (node.hasContent())
            {
                loi.setAssessmentId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:AssessmentStatusId");
            if (node.hasContent())
            {
                loi.setAssessmentStatusId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:IsAssessmentVisible");
            if (node.hasContent())
            {
                loi.setIsAssessmentVisible(Boolean.parseBoolean(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CourseCode");
            if (node.hasContent())
            {
                loi.setCourseCode(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:CourseId");
            if (node.hasContent())
            {
                loi.setCourseId(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:CourseSyncKey");
            if (node.hasContent())
            {
                loi.setCourseSyncKey(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:CourseOrganisationId");
            if (node.hasContent())
            {
                loi.setCourseOrganisationId(Integer.parseInt(node.getStringValue()));
            }
            node = root.selectSingleNode(lElem + "/loi:CourseOrganisationSyncKey");
            if (node.hasContent())
            {
                loi.setCourseOrganisationSyncKey(node.getStringValue());
            }
            node = root.selectSingleNode(lElem + "/loi:MaxScore");
            if (node.hasContent())
            {
                loi.setMaxScore(Double.parseDouble(node.getStringValue()));
            }
        }
        return loi;
    }
    
    private String AppendPagingParams (String uri, int pageIndex, int pageSize, LearningObjectInstanceUserReport.OrderBy orderBy, OrderDirection orderDirection)
    {
        QueryStringBuilder query = new QueryStringBuilder(uri, false);
        if (pageSize > 0)
        {
            if (pageIndex >= 0)
            {
                query.AddParameter("pageindex", Integer.toString(pageIndex));
            }
            query.AddParameter("pagesize", Integer.toString(pageSize));
        }
        if (orderBy != LearningObjectInstanceUserReport.OrderBy.None)
        {
            query.AddParameter("orderby", orderBy.toString());
            if (orderDirection == OrderDirection.Desc)
            {
                query.AddParameter("orderdirection", orderDirection.toString());
            }
        }
        return query.getQueryString();
    }

    private String AppendPagingParams (String uri, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy, OrderDirection orderDirection)
    {
        QueryStringBuilder query = new QueryStringBuilder(uri, false);
        if (pageSize > 0)
        {
            if (pageIndex >= 0)
            {
                query.AddParameter("pageindex", Integer.toString(pageIndex));
            }
            query.AddParameter("pagesize", Integer.toString(pageSize));
        }
        if (orderBy != LearningObjectInstanceUser.OrderBy.None)
        {
            query.AddParameter("orderby", orderBy.toString());
            if (orderDirection == OrderDirection.Desc)
            {
                query.AddParameter("orderdirection", orderDirection.toString());
            }
        }
        return query.getQueryString();
    }

    
    /**
     * Initializes the http client with correct httpmethod. Adds Authorization header to request.
     * @param client
     * @param uri
     * @param methodType
     * @return
     */
    protected HttpMethod getInitializedHttpMethod(HttpClient client, String uri, HttpMethodType methodType)
    {
        Calendar now = GregorianCalendar.getInstance();
        _apiSession.setLastRequestDateTimeUtc(new Date(now.getTimeInMillis()));
        _apiSession.setHash(CryptographyHelper.computeHash(_apiSession, _sharedSecret));

        String authHeader = AuthorizationHelper.toAuthorizationHeader(_apiSession);

        HttpMethod method;
        switch (methodType)
        {
            case GET:
                method = new GetMethod(uri);
                break;
            case PUT:
                method = new PutMethod(uri);
                method.setRequestHeader("Content-Type", "text/xml");
                break;
            case POST:
                method = new PostMethod(uri);
                method.setRequestHeader("Content-Type", "text/xml");
                break;
            case DELETE:
                method = new DeleteMethod(uri);
                break;
            default:
                method = new GetMethod(uri);
        }
        HttpParams params = DefaultHttpParams.getDefaultParams();
        method.setRequestHeader("Authorization", authHeader);
        client.setParams((HttpClientParams) params);

        return method;
    }
    
    /**
     * Get a list of collaborations participants for instance.
     * @param learningObjectId
     * @param instanceId
     * @param collaborationIds The array of user IDs to filter by.
     * @return
     * @throws java.lang.Exception
     */
    public List<CollaborationParticipant> getLearningObjectInstanceCollaborationsParticipants(int learningObjectId, int instanceId, int[] collaborationIds) throws Exception {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/collaborations/participants?collaborationIds=%s", learningObjectId, instanceId, intArrayToCsvString(collaborationIds));
               
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<CollaborationParticipant> collaborationParticipants = new ArrayList<CollaborationParticipant>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                collaborationParticipants = deserializeXMLToListOfCollaborationParticipant(method.getResponseBodyAsStream());
            }
        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return collaborationParticipants;
    }

    public void sendNotification(Notification notification, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Notification", learningObjectId, instanceId );
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);
        String reportAsXml = serializeNotificationToXML(notification);
        InputStream is = new ByteArrayInputStream(reportAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // POST methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public void sendNotificationToUsers(Notification notification, int learningObjectId, int instanceId, int[] receiverUserIds, int senderUserId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/NotificationToUsers", learningObjectId, instanceId);
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);
        String reportAsXml = serializeNotificationToWrappedXML(notification);
        String userIdsAsXml = serializeUserIdsToWrappedXML(receiverUserIds);
        String senderUserIdAsXml = "<senderUserId>" + Integer.toString(senderUserId) + "</senderUserId>";
        String openingTag = "<SendNotificationToUsers xmlns=\"http://tempuri.org/\">";
        String closingTag = "</SendNotificationToUsers>";

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append(openingTag);
        xmlBuilder.append(reportAsXml);
        xmlBuilder.append(userIdsAsXml);
        xmlBuilder.append(senderUserIdAsXml);
        xmlBuilder.append(closingTag);
        
        method.setRequestEntity(new StringRequestEntity(xmlBuilder.toString(), "text/xml", "UTF-8"));
        
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // POST methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public void setUpdated(int instanceId, int learningObjectId) throws Exception 
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Updated", learningObjectId, instanceId );
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);

        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // POST methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public LearningObjectInstance getLearningObjectInstance(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        LearningObjectInstance loi = null;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                loi = deserializeXMLToLearningObjectInstance(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return loi;
    }

    public List<Assessment> getPossibleAssessments(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessments", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<Assessment> assessments = new ArrayList<Assessment>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessments = deserializeXMLToListOfAssessments(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessments;
    }

    public List<AssessmentItem> getAssessmentItems(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentItems", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentItem> assessmentItems = new ArrayList<AssessmentItem>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentItems = deserializeXMLToListOfAssessmentItems(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentItems;
    }

    public List<AssessmentStatus> getPossibleAssessmentStatuses(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/PossibleAssessmentStatuses", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentStatus> assessmentStatuses = new ArrayList<AssessmentStatus>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentStatuses = deserializeXMLToListOfPossibleAssessmentStatuses(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentStatuses;
    }

    public List<AssessmentStatusItem> getAssessmentStatusItems(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/AssessmentStatusItems", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<AssessmentStatusItem> assessmentStatusItems = new ArrayList<AssessmentStatusItem>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                assessmentStatusItems = deserializeXMLToListOfAssessmentStatusItems(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return assessmentStatusItems;
    }
    
    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId) throws Exception
    {
        return getLearningObjectInstanceUserReports(instanceId, learningObjectId, 0, 0, LearningObjectInstanceUserReport.OrderBy.None, OrderDirection.Asc);
    }

    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId, int pageIndex, int pageSize) throws Exception
    {
        return getLearningObjectInstanceUserReports(instanceId, learningObjectId, pageIndex, pageSize, LearningObjectInstanceUserReport.OrderBy.None, OrderDirection.Asc);
    }
    
    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUserReport.OrderBy orderBy) throws Exception
    {
        return getLearningObjectInstanceUserReports(instanceId, learningObjectId, pageIndex, pageSize, orderBy, OrderDirection.Asc);
    }

    public List<LearningObjectInstanceUserReport> getLearningObjectInstanceUserReports(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUserReport.OrderBy orderBy, OrderDirection orderDirection) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
        uri = AppendPagingParams(uri, pageIndex, pageSize, orderBy, orderDirection);
        
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<LearningObjectInstanceUserReport> reports = new ArrayList<LearningObjectInstanceUserReport>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                reports = deserializeXMLToListOfLearningObjectInstanceUserReport(method.getResponseBodyAsStream());
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return reports;
    }

    public int getLearningObjectInstanceUserReportsCount(int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/count", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        int reportsCount = 0;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                SAXReader reader = new SAXReader();
                Document doc = reader.read(method.getResponseBodyAsStream());
                reportsCount = Integer.parseInt(doc.getRootElement().getText());
            }
        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return reportsCount;
    }

    public LearningObjectInstanceUserReport getLearningObjectInstanceUserReport(int instanceId, int learningObjectId, int userId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        LearningObjectInstanceUserReport report = new LearningObjectInstanceUserReport();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    report = deserializeXMLToLearningObjectInstanceUserReport(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return report;
    }

    public void updateLearningObjectInstanceUserReports(List<LearningObjectInstanceUserReport> userReports, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
        PostMethod method = (PostMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.POST);
        String reportsAsXml = serializeLearningObjectInstanceUserReportsToXML(userReports);
        InputStream is = new ByteArrayInputStream(reportsAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // POST methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public void updateLearningObjectInstanceUserReport(LearningObjectInstanceUserReport userReport, int instanceId, int learningObjectId, int userId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s", learningObjectId, instanceId, userId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String reportAsXml = serializeLearningObjectInstanceUserReportToXML(userReport);
        InputStream is = new ByteArrayInputStream(reportAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }
    
    /*
     * Updates comment log on report (assessment etc.) for user with access to learning object instance. 
     * @param reportComment Comment log entry.
     * @param instanceId Learning object instance Id.
     * @param learningObjectId Learning object Id.
     * @param userId Id of the user whose report will be updated.
    */
    public void updateLearningObjectInstanceUserReportComment(LearningObjectInstanceUserReportCommentOnComment reportComment, int instanceId, int learningObjectId, int userId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports/%s/comments", learningObjectId, instanceId, userId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String commentAsXml = serializeLearningObjectInstanceUserReportCommentOnCommentToXML(reportComment);
        InputStream is = new ByteArrayInputStream(commentAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }
    
    /*
     * Updates comment log on report (assessment etc.) for all participants on the collaboration.
     * @param reportComment Comment log entry.
     * @param instanceId Learning object instance Id.
     * @param learningObjectId Learning object Id.
     * @param collaborationId Id of the collaboration (users group) whose reports will be updated.
    */
    public void updateLearningObjectInstanceUserReportCommentForCollaboration(LearningObjectInstanceUserReportCommentOnComment reportComment, int instanceId, int learningObjectId, int collaborationId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Collaborations/%s/Report/comments", learningObjectId, instanceId, collaborationId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String commentAsXml = serializeLearningObjectInstanceUserReportCommentOnCommentToXML(reportComment);
        InputStream is = new ByteArrayInputStream(commentAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }
    
    /*
     * Updates comment log on report (assessment etc.) for all participants on the collaboration.
     * @param reportComment Comment log entry.
     * @param learningObjectId Learning object Id.
     * @param instanceId Learning object instance Id.     
     * @param collaborationId Id of the collaboration (users group) whose reports will be updated.
    */
    public void updateLearningObjectInstanceUserReportForCollaboration(LearningObjectInstanceUserReport userReport, int learningObjectId, int instanceId, int collaborationId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Collaborations/%s/Report", learningObjectId, instanceId, collaborationId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String userReportAsXml = serializeLearningObjectInstanceUserReportToXML(userReport);
        InputStream is = new ByteArrayInputStream(userReportAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }
    
     /**
     * Deletes learning object instance user reports for specified collaboration ids
     * @param instanceId
     * @param learningObjectId
     * @param collaborationIds
     * @throws java.lang.Exception
     */
    public void deleteLearningObjectInstanceUserReportsForCollaborations(int learningObjectId, int instanceId, int[] collaborationIds) throws Exception {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/collaborations/Reports", learningObjectId, instanceId);
        HttpDeleteWithBody method = new HttpDeleteWithBody(uri);       

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append(serializeListOfIntToWrappedXML(collaborationIds));
        
        method.setEntity(new StringEntity(xmlBuilder.toString()));
        
        try
        {
            HttpResponse response = _httpClientForDelete.execute(method);
            StatusLine sl = response.getStatusLine();
            int statusCode = sl.getStatusCode();
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.abort();
        }
    }
    
    /**
     * Deletes learning object instance user reports for specified collaboration ids
     * @param userIds
     * @param learningObjectId
     * @param instanceId
     * @throws java.lang.Exception
     */
    public void deleteLearningObjectInstanceUserReports(int[] userIds, int learningObjectId, int instanceId) throws Exception {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Reports", learningObjectId, instanceId);
        HttpDeleteWithBody method = new HttpDeleteWithBody(uri);        

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append(serializeListOfIntToWrappedXML(userIds));
        
        method.setEntity(new StringEntity(xmlBuilder.toString()));
        
        try
        {
            HttpResponse response = _httpClientForDelete.execute(method);
            StatusLine sl = response.getStatusLine();
            int statusCode = sl.getStatusCode();
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.abort();
        }
    }

    public void updateLearningObjectInstance(LearningObjectInstance instance, int instanceId, int learningObjectId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s", learningObjectId, instanceId);
        PutMethod method = (PutMethod) getInitializedHttpMethod(_httpClient, uri, HttpMethodType.PUT);
        String loiAsXml = serializeLearningObjectInstanceToXML(instance);
        InputStream is = new ByteArrayInputStream(loiAsXml.getBytes("UTF-8"));
        method.setRequestEntity(new InputStreamRequestEntity(is));
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            // Put methods, may return 200, 201, 204
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED && statusCode != HttpStatus.SC_NOT_MODIFIED)
            {
                throw new HTTPException(statusCode);
            }

        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
    }

    public List<Organisation> getOrganisationsForCurrentUser() throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/OrganizationsForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<Organisation> organizationsForUser = new ArrayList<Organisation>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    organizationsForUser = deserializeXMLToOrganisations(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return organizationsForUser;
    }

    public List<AppLicense> getAppLicensesForCurrentUser() throws Exception{
        List<AppLicense> appLicenses = new ArrayList<AppLicense>();
        String uri = String.format(_baseUri + "/LearningObjectService.svc/AppLicensesForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    appLicenses = deserializeXMLToAppLicenses(method.getResponseBodyAsStream());
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return appLicenses;
    }
    
    public Site getSiteForCurrentUser() throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/SiteForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        Site siteForUser = null;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    siteForUser = deserializeXMLToSite(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return siteForUser;
    }
    
    /**
     * Returns customer settings
     */
    public CustomerSettings getCustomerSettings() throws Exception {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/CustomerSettings");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        CustomerSettings customerSettings = null;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    customerSettings = deserializeXMLToCustomerSettings(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return customerSettings;
    }
    
    public List<OrganisationRole> getOrganisationRolesForCurrentUser() throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/OrganizationRolesForCurrentUser");
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<OrganisationRole> organizationRolesForUser = new ArrayList<OrganisationRole>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    organizationRolesForUser = deserializeXMLToOrganisationRoles(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }


        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return organizationRolesForUser;
    }
    
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId) throws Exception
    {
        return getLearningObjectInstanceUsers(instanceId, learningObjectId, null, false, 0, 0, LearningObjectInstanceUser.OrderBy.None, OrderDirection.Asc);
    }

    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int pageIndex, int pageSize) throws Exception
    {
        return getLearningObjectInstanceUsers(instanceId, learningObjectId, null, false, pageIndex, pageSize, LearningObjectInstanceUser.OrderBy.None, OrderDirection.Asc);
    }
    
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy) throws Exception
    {
        return getLearningObjectInstanceUsers(instanceId, learningObjectId, null, false, pageIndex, pageSize, orderBy, OrderDirection.Asc);
    }
    
    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy, OrderDirection orderDirection) throws Exception
    {
        return getLearningObjectInstanceUsers(instanceId, learningObjectId, null, false, pageIndex, pageSize, orderBy, orderDirection); 
    }

    public List<LearningObjectInstanceUser> getLearningObjectInstanceUsers(int instanceId, int learningObjectId, int[] userIds, boolean includeTeachers, int pageIndex, int pageSize, LearningObjectInstanceUser.OrderBy orderBy, OrderDirection orderDirection) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Users", learningObjectId, instanceId);
        
        uri = appendLearningObjectInstanceUsersExtraParameters(uri, userIds, includeTeachers);
        uri = AppendPagingParams(uri, pageIndex, pageSize, orderBy, orderDirection);
        
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<LearningObjectInstanceUser> users = new ArrayList<LearningObjectInstanceUser>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                users = deserializeXMLToListOfLearningObjectInstanceUser(method.getResponseBodyAsStream());
            }
        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return users;
    }
    
    public int getLearningObjectInstanceUsersCount(int instanceId, int learningObjectId) throws Exception
    {
        return getLearningObjectInstanceUsersCount(instanceId, learningObjectId, null, false);
    }

    public int getLearningObjectInstanceUsersCount(int instanceId, int learningObjectId, int[] userIds, boolean includeTeachers) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Users/count", learningObjectId, instanceId);
        uri = appendLearningObjectInstanceUsersExtraParameters(uri, userIds, includeTeachers);
        
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        int usersCount = 0;
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                SAXReader reader = new SAXReader();
                Document doc = reader.read(method.getResponseBodyAsStream());
                usersCount = Integer.parseInt(doc.getRootElement().getText());
            }
        } catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        } finally
        {
            method.releaseConnection();
        }
        return usersCount;
    }
    
    public List<Organisation> getOrganisationsForLearningObjectInstance(int learningObjectId, int instanceId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/Organizations", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<Organisation> organizationsForLearningToolCreator = new ArrayList<Organisation>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    organizationsForLearningToolCreator = deserializeXMLToOrganisations(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return organizationsForLearningToolCreator;
    }
    
    public List<RubricCriteriaItem> getRubricCriteria(int learningObjectId, int instanceId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/RubricCriteria", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<RubricCriteriaItem> criteriaCreator = new ArrayList<RubricCriteriaItem>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    criteriaCreator = deserializeXMLToCriteria(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return criteriaCreator;
    }
    
    public List<LearningObjective> getLearningObjectives(int learningObjectId, int instanceId) throws Exception
    {
        String uri = String.format(_baseUri + "/LearningObjectService.svc/learningObjects/%s/instances/%s/LearningObjectives", learningObjectId, instanceId);
        HttpMethod method = getInitializedHttpMethod(_httpClient, uri, HttpMethodType.GET);
        List<LearningObjective> objectivesCreator = new ArrayList<LearningObjective>();
        try
        {
            int statusCode = _httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK)
            {
                throw new HTTPException(statusCode);
            }
            else
            {
                if (Integer.parseInt(method.getResponseHeader("Content-Length").getValue()) > 0)
                {
                    objectivesCreator = deserializeXMLToListOfLearningObjectives(method.getResponseBodyAsStream());
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.handle(ex);
        }
        finally
        {
            method.releaseConnection();
        }
        return objectivesCreator;
    }
    
    private String appendLearningObjectInstanceUsersExtraParameters(String uri, int[] userIds, boolean includeTeachers)
    {
        QueryStringBuilder query = new QueryStringBuilder(uri, false);
        if (includeTeachers)
        {
            query.AddParameter("includeTeachers", Boolean.toString(includeTeachers));
        }
        if (userIds != null && userIds.length > 0)
        {
            query.AddParameter("userIds", intArrayToCsvString(userIds));
        }
        return query.getQueryString();
    }
}