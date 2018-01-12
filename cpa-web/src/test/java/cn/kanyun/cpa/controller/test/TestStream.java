package cn.kanyun.cpa.controller.test;

import org.junit.Test;

import java.io.*;

public class TestStream {

    public final String path = this.getClass().getClassLoader().getResource("")+"banner.txt";
    @Test
    private void readSystemOut() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            OutputStreamWriter put = new OutputStreamWriter(System.out);
            String len;
            while ((len = br.readLine()) != null) {
                put.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    private void readPrint() {
//        PrintWriter pw = new PrintWriter(System.out);
        try (BufferedReader br = new BufferedReader(new FileReader(this.path));PrintWriter pw = new PrintWriter(System.out,true)) {
//            PrintWriter pw = new PrintWriter(System.out);
            String len;
            while ((br.readLine()) != null) {
                pw.println(br.readLine());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
