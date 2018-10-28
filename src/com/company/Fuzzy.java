package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.min;

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

    public void fuzzyPendapatan() {
        for (DataHutang data : listData) {
            float x = data.getPendapatan();
            List<Float> fuzzy = new ArrayList<>();
            List<DataHutang.StatusPendapatan> status = new ArrayList<>();
            if (x >= 0 && x < 0.9f) {
                fuzzy.add(trapesium(x, 0, 0, 0.4f, 0.9f));
                status.add(DataHutang.StatusPendapatan.RENDAH);
            }
            if(x >= 0.4f && x < 1.7f) {
                fuzzy.add(trapesium(x, 0.4f, 0.9f, 1.4f, 1.7f));
                status.add(DataHutang.StatusPendapatan.SEDANG);
            }
            if(x >= 1.4f && x < 2f) {
                fuzzy.add(trapesium(x, 1.4f, 1.7f, 2f, 2f));
                status.add(DataHutang.StatusPendapatan.TINGGI);
            }

            data.setfPendapatan(fuzzy);
            data.setsPendapatan(status);
        }
    }

    public void fuzzyHutang() {
        for (DataHutang data : listData) {
            float x = data.getHutang();
            List<Float> fuzzy = new ArrayList<>();
            List<DataHutang.StatusHutang> status = new ArrayList<>();

            if (x >= 0 && x < 35f) {
                fuzzy.add(trapesium( x, 0, 0, 25f, 35f));
                status.add(DataHutang.StatusHutang.KECIL);
            }
            if(x >= 25f && x < 75f) {
                fuzzy.add(trapesium(x, 25f, 35f, 65f, 75f));
                status.add(DataHutang.StatusHutang.LUMAYAN);
            }
            if(x >= 65f && x < 100f) {
                fuzzy.add(trapesium(x, 65f, 75f, 100f, 100f));
                status.add(DataHutang.StatusHutang.BESAR);
            }

            data.setfHutang(fuzzy);
            data.setsHutang(status);
        }
    }

    private float trapesium(float x, float a, float b, float c, float d) {
        if (x <= x && x >= d)
            return 1;
        else if (x > a && x < b )
            return (x-a) / (b - a);
        else if (b <= x && x <= c)
            return 1;
        else
            return -(x-d)/(d-c);
    }

    public void hitungFuzzyRules() {
        for (DataHutang data : listData) {
            float[] infer = inferensi(data);
            data.setFuzzy(defuzzy(infer));
        }
    }

    private float defuzzy(float[] infer) {
        float[] bobot = { 40, 70};
        return (infer[0] * bobot[0] + infer[1] * bobot[1]) / (infer[0] + infer[1]);
    }

    private float[] inferensi(DataHutang data) {
        float hasil [] = { 0, 0};
        for (Float i : data.getfPendapatan()) {
            for (Float j: data.getfHutang()) {
                DataHutang.FuzzyRules fuzzyRules = fuzzyRules(data.getsPendapatan().get(data.getfPendapatan().indexOf(i)), i, data.getsHutang().get(data.getfHutang().indexOf(j)), j);
                if (fuzzyRules.getRules() == DataHutang.Rules.LAYAK && hasil[0] < fuzzyRules.getValue())
                    hasil[0] = fuzzyRules.getValue();
                else if(fuzzyRules.getRules() == DataHutang.Rules.TIDAK && hasil [1] < fuzzyRules.getValue())
                    hasil[1] = fuzzyRules.getValue();
            }
        }
        return hasil;
    }

    private DataHutang.FuzzyRules fuzzyRules(DataHutang.StatusPendapatan sPendapatan, float fPendapatan, DataHutang.StatusHutang sHutang, float fHutang) {
        if (sPendapatan == DataHutang.StatusPendapatan.RENDAH && sHutang == DataHutang.StatusHutang.KECIL) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.TIDAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.RENDAH && sHutang == DataHutang.StatusHutang.LUMAYAN) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.LAYAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.RENDAH && sHutang == DataHutang.StatusHutang.BESAR) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.LAYAK, min(fPendapatan, fHutang));
        }

        else if (sPendapatan == DataHutang.StatusPendapatan.SEDANG && sHutang == DataHutang.StatusHutang.KECIL) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.TIDAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.SEDANG && sHutang == DataHutang.StatusHutang.LUMAYAN) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.LAYAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.SEDANG && sHutang == DataHutang.StatusHutang.BESAR) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.LAYAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.TINGGI && sHutang == DataHutang.StatusHutang.KECIL) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.TIDAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.TINGGI && sHutang == DataHutang.StatusHutang.LUMAYAN) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.TIDAK, min(fPendapatan, fHutang));
        }
        else if (sPendapatan == DataHutang.StatusPendapatan.TINGGI && sHutang == DataHutang.StatusHutang.BESAR) {
            return new DataHutang.FuzzyRules(DataHutang.Rules.LAYAK, min(fPendapatan, fHutang));
        }
        return null;
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

    public void printFuzzy() {
        for (DataHutang data: listData){
            System.out.println(data);
        }
    }

    public void print20Layak() {
        listData.sort(Comparator.comparing(DataHutang::getFuzzy));
        int i = 0;
        for (DataHutang data : listData) {
            i++;
            System.out.println(data);
            if (i >= 20) {
                return;
            }
        }
    }

    public void writeToFile() throws IOException {
        listData.sort(Comparator.comparing(DataHutang::getFuzzy));
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (DataHutang data : listData) {
            i++;
            sb.append(data.getNomer()).append("\n");
            if (i >= 20) {
                break;
            }
        }
        File file = new File("output.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(sb.toString());
        System.out.println(sb.toString());
        writer.flush();
        writer.close();
    }
}
