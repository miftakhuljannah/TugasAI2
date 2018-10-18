package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Fuzzy fuzzy = new Fuzzy();
        fuzzy.readCSV("DataTugas2.csv");
        System.out.println(fuzzy.getMaxPendapatan() + " " + fuzzy.getMinPendapatan() + " " + fuzzy.getMaxHutang() + " " + fuzzy.getMinHutang());
    }

}
