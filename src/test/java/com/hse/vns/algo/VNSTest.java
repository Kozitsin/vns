package com.hse.vns.algo;

import com.hse.vns.TestUtils;
import com.hse.vns.entity.Solution;
import com.hse.vns.io.Reader;
import org.junit.Test;

import java.util.Arrays;

public class VNSTest extends TestUtils {

    @Test
    public void testOn5x5() {
        Solution s = Reader.read(getFile("5x5.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }

    @Test
    public void testOn20x20() {
        Solution s = Reader.read(getFile("20x20.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }

    @Test
    public void testOn24x40() {
        Solution s = Reader.read(getFile("24x40.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }

    @Test
    public void testOn30x50() {
        Solution s = Reader.read(getFile("30x50.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }

    @Test
    public void testOn30x90() {
        Solution s = Reader.read(getFile("30x90.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }

    @Test
    public void testOn37x53() {
        Solution s = Reader.read(getFile("37x53.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(result.printMatrix());
        System.out.println(result.clusters);
        System.out.println(result.printMachine());
        System.out.println(result.printParts());
    }
}