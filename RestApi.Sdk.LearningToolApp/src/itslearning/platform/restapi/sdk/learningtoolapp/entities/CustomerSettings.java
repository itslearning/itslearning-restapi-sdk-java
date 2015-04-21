/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 *
 * @author oleg.tarusov
 */
public class CustomerSettings {
    private String plagiarismCode;
    private String plagiarismEmail;
    private String plagiarismShowStudentName;
    private boolean usePlagiarism;    
    
    /**
     * @return the plagiarismCode
     */
    public String getPlagiarismCode()
    {
        return plagiarismCode;
    }

    /**
     * @param plagiarismCode the plagiarismCode to set
     */
    public void setPlagiarismCode(String plagiarismCode)
    {
        this.plagiarismCode = plagiarismCode;
    }

    /**
     * @return the plagiarismEmail
     */
    public String getPlagiarismEmail()
    {
        return plagiarismEmail;
    }

    /**
     * @param plagiarismEmail the firstName to set
     */
    public void setPlagiarismEmail(String plagiarismEmail)
    {
        this.plagiarismEmail = plagiarismEmail;
    }

    /**
     * @return the plagiarismShowStudentName
     */
    public String getPlagiarismShowStudentName()
    {
        return plagiarismShowStudentName;
    }

    /**
     * @param plagiarismShowStudentName the plagiarismShowStudentName to set
     */
    public void setPlagiarismShowStudentName(String plagiarismShowStudentName)
    {
        this.plagiarismShowStudentName = plagiarismShowStudentName;
    }
    
    /**
     * @return usePlagiarism
     */
    public boolean getUsePlagiarism()
    {
        return usePlagiarism;
    }

    /**
     * @param usePlagiarism the usePlagiarism to set.
     */
    public void setUsePlagiarism(boolean usePlagiarism)
    {
        this.usePlagiarism = usePlagiarism;
    }   
}
