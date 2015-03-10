package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * The class represents learning objective data model.
 * 
 * @author Yaroslav.Kulik
 */
public class LearningObjective {
    private int _id;
    private String _title;
    private String _description;
    
    /**
     * @return The learning objective's identifier.
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id The learning objective's identifier.
     */
    public void setId(int id) {
        _id = id;
    }
    
    /**
     * @return The learning objective's title.
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title The learning objective's title.
     */
    public void setTitle(String title) {
        _title = title;
    }
    
    /**
     * @return The learning objective's description.
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @param description The learning objective's description.
     */
    public void setDescription(String description) {
        _description = description;
    }
}
