package com.example.esha.personalhealthrecord.POJO;

import java.util.List;

public class PatientRecord {
    private String patient_first_name;
    private String patient_last_name;
    private String age;
    private String contact;
    private String gender;
    private String doctor_first_name;
    private String doctor_last_name;
    private String medical_tests;
    private String medical_diagnosis;
    private String prescription;
    private String treatment_date;
    private List<String> patient_file;


    public String getPatient_first_name() {
        return patient_first_name;
    }

    public void setPatientFirstName(String patient_first_name) {
        this.patient_first_name = patient_first_name;
    }

    public String getPatient_last_name() {
        return patient_last_name;
    }

    public void setPatient_last_name(String patient_last_name) {
        this.patient_last_name = patient_last_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoctor_first_name() {
        return doctor_first_name;
    }

    public void setDoctor_first_name(String doctor_first_name) {
        this.doctor_first_name = doctor_first_name;
    }

    public String getDoctor_last_name() {
        return doctor_last_name;
    }

    public void setDoctor_last_name(String doctor_last_name) {
        this.doctor_last_name = doctor_last_name;
    }

    public String getMedical_tests() {
        return medical_tests;
    }

    public void setMedical_tests(String medical_tests) {
        this.medical_tests = medical_tests;
    }

    public String getMedical_diagnosis() {
        return medical_diagnosis;
    }

    public void setMedical_diagnosis(String medical_diagnosis) {
        this.medical_diagnosis = medical_diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getTreatment_date() {
        return treatment_date;
    }

    public void setTreatment_date(String treatment_date) {
        this.treatment_date = treatment_date;
    }

    public List<String> getPatient_file() {
        return patient_file;
    }

    public void setPatient_file(List<String> patient_file) {
        this.patient_file = patient_file;
    }
}
