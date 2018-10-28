package com.company;

import java.util.ArrayList;
import java.util.List;

public class DataHutang {
    private int nomer;
    private float pendapatan;
    private float hutang;
    private float fuzzy;

    private List<Float> fPendapatan = new  ArrayList<>();
    private List<Float> fHutang = new ArrayList<>();
    private List<StatusHutang> sHutang= new ArrayList<>();
    private List<StatusPendapatan> sPendapatan= new ArrayList<>();
    private List<FuzzyRules> fuzzyRules = new ArrayList<>();

    public DataHutang(int nomer, float pendapatan, float hutang) {
        this.nomer = nomer;
        this.pendapatan = pendapatan;
        this.hutang = hutang;
    }

    public int getNomer() {
        return nomer;
    }

    public void setNomer(int nomer) {
        this.nomer = nomer;
    }

    public float getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(float pendapatan) {
        this.pendapatan = pendapatan;
    }

    public float getHutang() {
        return hutang;
    }

    public void setHutang(float hutang) {
        this.hutang = hutang;
    }

    public List<Float> getfPendapatan() {
        return fPendapatan;
    }

    public void setfPendapatan(List<Float> fPendapatan) {
        this.fPendapatan = fPendapatan;
    }

    public List<Float> getfHutang() {
        return fHutang;
    }

    public void setfHutang(List<Float> fHutang) {
        this.fHutang = fHutang;
    }

    public List<StatusHutang> getsHutang() {
        return sHutang;
    }

    public void setsHutang(List<StatusHutang> sHutang) {
        this.sHutang = sHutang;
    }

    public List<StatusPendapatan> getsPendapatan() {
        return sPendapatan;
    }

    public void setsPendapatan(List<StatusPendapatan> sPendapatan) {
        this.sPendapatan = sPendapatan;
    }

    public List<FuzzyRules> getFuzzyRules() {
        return fuzzyRules;
    }

    public void setFuzzyRules(List<FuzzyRules> fuzzyRules) {
        this.fuzzyRules = fuzzyRules;
    }

    public enum StatusHutang {
        KECIL, LUMAYAN, BESAR
    }
    public enum StatusPendapatan {
        RENDAH, SEDANG, TINGGI
    }
    public enum Rules {
        TIDAK, LAYAK
    }

    public float getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(float fuzzy) {
        this.fuzzy = fuzzy;
    }

    public static class FuzzyRules{
        private Rules rules;
        private float value;

        public Rules getRules() {
            return rules;
        }

        public FuzzyRules(Rules rules, float value) {
            this.rules = rules;
            this.value = value;
        }

        public void setRules(Rules rules) {
            this.rules = rules;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

    }

    @Override
    public String toString() {
        return new StringBuilder("Nomor=").append(nomer).append(";")
                .append("Pendapatan="). append(pendapatan).append(";")
                .append("Hutang=").append(hutang).append(";")
                .append("Fuzzy=").append(fuzzy).append(";").toString();
    }
}
