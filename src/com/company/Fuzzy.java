package com.company;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Fuzzy {

    private List<DataHutang> listData = new ArrayList<>();

    public void readCSV(String file){
        String csvFile = file;
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                DataHutang dataHutang = new DataHutang(Integer.valueOf(data[0]), Float.valueOf(data[1]), Float.valueOf(data[2]));
                listData.add(dataHutang);
                System.out.println("Data [nomor= " + dataHutang.getNomer() + " , pendapatan=" + dataHutang.getPendapatan() + " , hutang=" + dataHutang.getHutang() +"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getMaxPendapatan() {
        return listData.stream().max(Comparator.comparing(DataHutang::getPendapatan)).get().getPendapatan();
    }
    public float getMinPendapatan() {
        return listData.stream().min(Comparator.comparing(DataHutang::getPendapatan)).get().getPendapatan();
    }
    public float getMaxHutang() {
        return listData.stream().max(Comparator.comparing(DataHutang::getHutang)).get().getHutang();
    }
    public float getMinHutang() {
        return listData.stream().min(Comparator.comparing(DataHutang::getHutang)).get().getHutang();
    }
}
