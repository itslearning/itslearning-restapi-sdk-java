POSSIBLE BUGS IN IT'S LEARNING's RESTAPI:
- Sequence of XML elements are important. Must be alphabetical, if not returns 401 forbidden
- Some get methods return empty bodies. Bug, or not filled?
  These are:
    * GetPossibleAssessmentStatuses
    * GetAssessmentStatusItems
- Some methods return unauthorized:
    * GetLearningObjectInstanceUserReport
    * GetLearningObjectInstanceUserReports