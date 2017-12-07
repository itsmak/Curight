package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 12/1/2017.
 */

public class JSON_FEED {

    /*@SerializedName("Code")
    @Expose
    private Long Code;*/

    @SerializedName("age")
    @Expose
    String age;

    @SerializedName("ageinonth")
    @Expose
    String ageinonth;

    public String getAgeinonth() {
        return ageinonth;
    }

    public void setAgeinonth(String ageinonth) {
        this.ageinonth = ageinonth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    @SerializedName("vaccineList")
    @Expose
    private ArrayList<VaccineList> Vaccines;

    public ArrayList<VaccineList> getVaccines() {
        return Vaccines;
    }

    public void setVaccines(ArrayList<VaccineList> vaccines) {
        Vaccines = vaccines;
    }
}
