package cn.kanyun.cpa.controller.test;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TestStream extends TestCase {

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

    @Test
    public void testString() {
        String a = "Hello";
        String b = new String("Hello");
        System.out.println(a == b);
        Assert.assertNotEquals(a,b);
    }
}
