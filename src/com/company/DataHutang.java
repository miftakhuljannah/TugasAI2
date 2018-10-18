package com.company;

public class DataHutang {
    private int nomer;
    private float pendapatan;
    private float hutang;

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
}
