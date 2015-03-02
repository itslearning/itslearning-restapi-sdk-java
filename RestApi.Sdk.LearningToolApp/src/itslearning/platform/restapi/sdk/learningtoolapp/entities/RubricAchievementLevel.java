
package itslearning.platform.restapi.sdk.learningtoolapp.entities;

/**
 * Rubric achievement level entity
 * 
 * @author Yaroslav Kulik
 */
public class RubricAchievementLevel {
    private int _id;
    private String _text;
    private int _orderNo;
    
    /**
     * @return Identifier of the achievement level.
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id Identifier of the achievement level.
     */
    public void setId(int id) {
        _id = id;
    }
    
    /**
     * @return The achievement level's text.
     */
    public String getText() {
        return _text;
    }

    /**
     * @param text The achievement level's text.
     */
    public void setText(String text) {
        _text = text;
    }
    
    /**
     * @return The achievement level's order number.
     */
    public int getOrderNo() {
        return _orderNo;
    }

    /**
     * @param orderNo The achievement level's order number.
     */
    public void setOrderNo(int orderNo) {
        _orderNo = orderNo;
    }
}
