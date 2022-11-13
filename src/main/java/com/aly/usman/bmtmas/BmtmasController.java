package com.aly.usman.bmtmas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.print.PrintService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

@RestController

public class BmtmasController {
    @Autowired
    PembacaProperties pembacaProperties;
    @CrossOrigin()
    // @CrossOrigin(origins = "http://192.168.1.199")
    @PostMapping("/cetak/port-printer")
    public String cetakDokumen(@RequestBody PrinterModel printerModel) {
        JSONObject respon = new JSONObject();
        try {
            OutputStream out = new FileOutputStream(pembacaProperties.getPortPrinter());
            Writer writer = new OutputStreamWriter(out, "UTF-8");

            for (int i = 0; i < printerModel.isiCetak.size(); i++) {
                writer.write("  " + printerModel.isiCetak.get(i).toString() + "\n");
            }
            writer.write((char) 13); //perintah CR
            writer.write((char) 12); //perintah untuk form feed alias eject
            writer.close();

            respon.put("pesan", "Berhasil cetak dokumen");
            return respon.toString();
        } catch (IOException e) {
            respon.put("pesan", "Gagal cetak dokumen " + e);
            return respon.toString();
        }
    }

    @CrossOrigin()
    @GetMapping("/daftar/printer")
    public String[] getListPrinter() {
        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
        return printServicesNames;
    }

    @CrossOrigin()
    @PostMapping("/cetak/nama-printer")
    public String cetakViaNamaPrinter(@RequestBody PrinterModel printerModel) {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printerModel.getNamaPrinter());
        EscPos escpos;
        JSONObject respon = new JSONObject();

        try {
            escpos = new EscPos(new PrinterOutputStream(printService));
            for (int i = 0; i < printerModel.isiCetak.size(); i++) {
                escpos.writeLF(" " + printerModel.isiCetak.get(i).toString() + " ");
            }
            escpos.close();
            respon.put("pesan", "Berhasil cetak dokumen");
            return respon.toString();
        } catch (IOException e) {
            respon.put("pesan", "Gagal cetak dokumen " + e);
            return respon.toString();
        }
    }

    @CrossOrigin()
    @GetMapping("/mac")
    public String getMac1() throws SocketException {
        JSONObject resp = new JSONObject();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            byte[] hardwareAddress = ni.getHardwareAddress();
            if (hardwareAddress != null) {
                String[] hexadecimalFormat = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) {
                    hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                }
                System.out.println(String.join(":", hexadecimalFormat));
                resp.put("macaddress", String.join(":", hexadecimalFormat));
                resp.put("Status", "OK");
            }
        }
        return resp.toString();
    }

    @CrossOrigin()
    @GetMapping()
    public String test() {
        JSONObject obj = new JSONObject();
        org.json.JSONObject obj1 = new org.json.JSONObject();
        obj.put("http://localhost:9909", "Untuk bantuan");
        obj.put("http://localhost:9909/mac", "Untuk mendapatkan macaddress lokal.");
        obj.put("http://localhost:9909/cetak/port-printer", "Untuk melakukan cetak langsung ke port printer");
        obj.put("http://localhost:9909/cetak/nama-printer", "Untuk melakukan cetak ke printer tertentu");
        obj.put("http://localhost:9909/daftar/printer", "Untuk mendapatkan daftar printer yang ada di komputer");
        obj.put("parameter", "namaPrinter dan array isiCetak");
        // obj.toString();
        obj1.put("End Point", obj);

        return String.valueOf(obj1);// obj;
    }
}
