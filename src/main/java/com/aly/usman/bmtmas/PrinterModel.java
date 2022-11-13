package com.aly.usman.bmtmas;

import java.util.List;

public class PrinterModel {
    private String namaPrinter;
    List<IsiCetak> isiCetak;
    public PrinterModel(String namaPrinter, List<IsiCetak> isiCetak) {
        this.namaPrinter = namaPrinter;
        this.isiCetak = isiCetak;
    }
    public String getNamaPrinter() {
        return namaPrinter;
    }
    public void setNamaPrinter(String namaPrinter) {
        this.namaPrinter = namaPrinter;
    }
    public List<IsiCetak> getIsiCetak() {
        return isiCetak;
    }
    public void setIsiCetak(List<IsiCetak> isiCetak) {
        this.isiCetak = isiCetak;
    }
}
