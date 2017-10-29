package com.example.cleeg.lighthouse.models;

public class Patient {

    private String username;
    private boolean appointment;
    private Integer roomNumber;
    private String uid;
    private String name;
    private String email;
    private Integer phoneNumber;
    private String birthDate;
    private Integer age;
    private String maritalStatus;
    private Integer numChildren;
    private String medicinalAllergies;
    private String medications;
    private String healthInsurance;
    private String medicalHistory;
    private String familyHistory;
    private boolean immunizationUpToDate;
    private String emergencyContactName;
    private Integer emergencyContactPhone;

    public Patient() {}

    public Patient(String username, boolean appointment, Integer roomNumber, String uid) {
        this.username = username;
        this.appointment = appointment;
        this.roomNumber = roomNumber;
        this.uid = uid;
    }

    public Patient(String name, String email, Integer phoneNumber, String birthDate,
                   Integer age, String maritalStatus, Integer numChildren, String medicinalAllergies,
                   String medications, String healthInsurance, String medicalHistory,
                   String familyHistory, int immunizationUpToDate, String emergencyContactName,
                   Integer emergencyContactPhone) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.numChildren = numChildren;
        this.medicinalAllergies = medicinalAllergies;
        this.medications = medications;
        this.healthInsurance = healthInsurance;
        this.medicalHistory = medicalHistory;
        this.familyHistory = familyHistory;
        if (immunizationUpToDate == 1) {
            this.immunizationUpToDate = true;
        } else {
            this.immunizationUpToDate = false;
        }
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getUsername() { return this.username; }
    public boolean getAppointment() { return this.appointment; }
    public Integer getRoomNumber() { return this.roomNumber; }
    public String getUid() { return this.uid; }
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public Integer getPhoneNumber() { return this.phoneNumber; }
    public String getBirthDate() { return this.birthDate; }
    public Integer getAge() { return this.age; }
    public String getMaritalStatus() { return this.maritalStatus; }
    public Integer getNumChildren() { return this.numChildren; }
    public String getMedicinalAllergies() { return this.medicinalAllergies; }
    public String getMedications() { return this.medications; }
    public String getHealthInsurance() { return this.healthInsurance; }
    public String getMedicalHistory() { return this.medicalHistory; }
    public String getFamilyHistory() { return this.familyHistory; }
    public Boolean getImmunizationUpToDate() { return this.immunizationUpToDate; }
    public String getEmergencyContactName() { return this.emergencyContactName; }
    public Integer getEmergencyContactPhone() { return this.emergencyContactPhone; }
}
