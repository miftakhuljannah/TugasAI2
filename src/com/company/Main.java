package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Fuzzy fuzzy = new Fuzzy();
        fuzzy.readCSV("DataTugas2.csv");
        fuzzy.fuzzyHutang();
        fuzzy.fuzzyPendapatan();
        fuzzy.hitungFuzzyRules();
        // fuzzy.print20Layak();
        try {
            fuzzy.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
