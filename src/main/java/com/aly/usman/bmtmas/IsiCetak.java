package com.aly.usman.bmtmas;

public class IsiCetak {
    private String Isi;

    public IsiCetak(String isi) {
        Isi = isi;
    }

    public String getIsi() {
        return Isi;
    }

    public void setIsi(String isi) {
        Isi = isi;
    }
    @Override
    public String toString() {
        return Isi;
    }
}
