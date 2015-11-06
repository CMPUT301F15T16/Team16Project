package com.loveboyuan.smarttrader;

import junit.framework.TestCase;

/**
 * Created by jiahui on 11/6/15.
 */

/*
 partially covers use case 12
 */
public class ProfileTest extends TestCase {

    public void testSetFirstName() throws Exception {
        Profile profile = new Profile("Joe", "Cox");
        profile.setFirstName("John");
        assertTrue("ProfileTest: first name changing test failed",
                profile.getFirstName().equals("John"));
    }

    public void testSetLastName() throws Exception {
        Profile profile = new Profile("Joe", "Cox");
        profile.setLastName("Williams");
        assertTrue("ProfileTest: last name changing test failed",
                profile.getLastName().equals("Williams"));
    }

    public void testSetAge() throws Exception {
        Profile profile = new Profile("Joe", "Cox");
        profile.setAge(18);
        assertTrue("ProfileTest: age changing test failed",
                profile.getAge() == 18);
    }
}