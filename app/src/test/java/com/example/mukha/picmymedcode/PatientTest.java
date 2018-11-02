//reference: https://stackoverflow.com/a/47890524 accessed on 2018-11-01

package com.example.mukha.picmymedcode;

import junit.framework.TestCase;

public class PatientTest extends TestCase {

    public void testGetEmail() {
        Patient patient = new Patient("username",
                "pass", "test@email.com", "1111111111");
        assertEquals("test@email.com", patient.getEmail());

    }

    public void testSetEmail() {
        Patient patient = new Patient("username",
                "pass", "test@email.com", "1111111111");
        patient.setEmail("email@email.ca");
        assertEquals("email@email.ca", patient.getEmail());

    }

    public void testGetPhoneNumber() {
        Patient patient = new Patient("username",
                "pass", "test@email.com", "1111111111");
        assertEquals(1111111111, patient.getPhoneNumber());
    }

    public void testSetPhoneNumber() {
        Patient patient = new Patient("username",
                "pass", "test@email.com", "1111111111");
        patient.setPhoneNumber("1231231234");
        assertEquals(1231231234,patient.getPhoneNumber());
    }

    public void testGetProblemList() {
        Patient patient = new Patient("username",
                "pass", "test@email.com", "1111111111");
        assertTrue(patient.getProblemList() instanceof ProblemList);
    }
}