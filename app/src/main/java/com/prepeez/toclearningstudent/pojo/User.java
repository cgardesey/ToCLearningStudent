package com.prepeez.toclearningstudent.pojo;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 2CLearning on 12/16/2017.
 */

public class User {
    private String userId;
    HashMap<String, Object> timestampCreated;
    private String profilePicUrl;
    private int titleId;
    private String firstName;
    private String lastName;
    private String otherNames;
    private int genderId;
    private String dateOfBirth;
    private String homeAdress;
    private int maritalStatusId;
    private ArrayList<Phone> contacts;
    private String emailAddress;
    private int highestEducationalLevelId;
    private String nameOfInstitution;
    private ArrayList<Certification> certifications;
    private int employmentStatusId;
    private int yearsOfExperienceId;
    private String companyOfEmployment;
    private String jobTitle;
    private ArrayList<Course> courses;

    private int userRegistered;

    public User(){

    }

    public User(String userId, String profilePicUrl, int titleId, String firstName, String lastName, String otherNames, int genderId, String dateOfBirth, String homeAdress, int maritalStatusId, ArrayList<Phone> contacts, String emailAddress, int highestEducationalLevelId, String nameOfInstitution, ArrayList<Certification> certifications, int employmentStatusId, int yearsOfExperienceId, String companyOfEmployment, String jobTitle, ArrayList<Course> courses, int userRegistered) {
        this.userId = userId;
//        HashMap<String, Object> timestampNow = new HashMap<>();
//        timestampNow.put("timestamp", ServerValue.TIMESTAMP);
//        this.timestampCreated = timestampNow;
        this.profilePicUrl = profilePicUrl;
        this.titleId = titleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherNames = otherNames;
        this.genderId = genderId;
        this.dateOfBirth = dateOfBirth;
        this.homeAdress = homeAdress;
        this.maritalStatusId = maritalStatusId;
        this.contacts = contacts;
        this.emailAddress = emailAddress;
        this.highestEducationalLevelId = highestEducationalLevelId;
        this.nameOfInstitution = nameOfInstitution;
        this.certifications = certifications;
        this.employmentStatusId = employmentStatusId;
        this.yearsOfExperienceId = yearsOfExperienceId;
        this.companyOfEmployment = companyOfEmployment;
        this.jobTitle = jobTitle;
        this.courses = courses;
        this.userRegistered = userRegistered;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Object> getTimestampCreated(){
        return timestampCreated;
    }
    @Exclude
    public long getTimestampCreatedLong(){
        return (long)timestampCreated.get("timestamp");
    }

    public void setTimestampCreated(HashMap<String, Object> timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }

    public int getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(int maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public ArrayList<Phone> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Phone> contacts) {
        this.contacts = contacts;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getHighestEducationalLevelId() {
        return highestEducationalLevelId;
    }

    public void setHighestEducationalLevelId(int highestEducationalLevelId) {
        this.highestEducationalLevelId = highestEducationalLevelId;
    }

    public String getNameOfInstitution() {
        return nameOfInstitution;
    }

    public void setNameOfInstitution(String nameOfInstitution) {
        this.nameOfInstitution = nameOfInstitution;
    }

    public ArrayList<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(ArrayList<Certification> certifications) {
        this.certifications = certifications;
    }

    public int getEmploymentStatusId() {
        return employmentStatusId;
    }

    public void setEmploymentStatusId(int employmentStatusId) {
        this.employmentStatusId = employmentStatusId;
    }

    public int getYearsOfExperienceId() {
        return yearsOfExperienceId;
    }

    public void setYearsOfExperienceId(int yearsOfExperienceId) {
        this.yearsOfExperienceId = yearsOfExperienceId;
    }

    public String getCompanyOfEmployment() {
        return companyOfEmployment;
    }

    public void setCompanyOfEmployment(String companyOfEmployment) {
        this.companyOfEmployment = companyOfEmployment;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public int getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(int userRegistered) {
        this.userRegistered = userRegistered;
    }
}
