package com.prepeez.toclearningstudent.pojo;

/**
 * Created by Nana on 11/13/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPumpLevel {
    public static HashMap<String, List<String>> getData(String key) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> p1 = new ArrayList<String>();
        p1.add("Mathematics");
        p1.add("English");
        p1.add("Information Communication Training");
        p1.add("Integrated Science");


        List<String> jhs = new ArrayList<String>();
        jhs.add("Mathematics");
        jhs.add("English");
        jhs.add("Information Communication Training");
        jhs.add("Integrated Science");
        jhs.add("French");
        jhs.add("Social Studies");
        jhs.add("Religious and Moral Studies");
        jhs.add("Home Economics");
        jhs.add("Pre-Technical Skills");

        List<String> shs = new ArrayList<String>();
        shs.add("Science");
        shs.add("General Arts");
        shs.add("Visual Arts");
        shs.add("Business");
        shs.add("Home Economics");

        List<String> electronics = new ArrayList<String>();
        electronics.add("Arduino Programming");
        electronics.add("Circuit Design");
        electronics.add("Circuit Simulation");
        electronics.add("Circuit Construction");

        List<String> ICT = new ArrayList<String>();
        ICT.add("Sofware Engineering (Mobile)");
        ICT.add("Sofware Engineering (Web)");
        ICT.add("Sofware Engineering (Desktop)");
        ICT.add("Ethical Hacking");
        ICT.add("Graphic Design");
        ICT.add("3D Animation)");
        ICT.add("Network and Cyber Security");
        ICT.add("Laptop and Mobile Phone Repair");

        List<String> vocational = new ArrayList<String>();
        vocational.add("Hair Dressing");
        vocational.add("Tailoring");
        vocational.add("Catering");
        vocational.add("Fresh Yogurt Making");
        vocational.add("Batik Tye and Dye");
        vocational.add("Soap Making");

        List<String> family = new ArrayList<String>();
        family.add("Couselor Opanin Kojo Kyere");
        family.add("Counselor Adofoli");
        family.add("Counselor Luterodt");
        if(key.contains("Basic"))
        expandableListDetail.put("Basic Level", jhs);

        if(key.contains("Secondary"))
        expandableListDetail.put("Secondary Level", shs);

        if(key.contains("ICT"))
        expandableListDetail.put("Professional ICT", ICT);

        if(key.contains("Electronics"))
        expandableListDetail.put("Electronics", electronics);

        if(key.contains("Vocation"))
        expandableListDetail.put("Vocational Training", vocational);

        if(key.contains("Family"))
        expandableListDetail.put("Family and Relationships", family);
    //    expandableListDetail.put("JHS 3", jhs);
   //    expandableListDetail.put("Primary 1", p1);
  //      expandableListDetail.put("Primary 5", p1);
        return expandableListDetail;
    }
}