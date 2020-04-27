package io.fripointer.lib.dto;

public class UserSetupRequest {
    
    private String universityId;
    
    private String facultyId;
    
    public String getUniversityId() {
        return universityId;
    }
    
    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }
    
    public String getFacultyId() {
        return facultyId;
    }
    
    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }
}
