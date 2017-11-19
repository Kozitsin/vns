package com.hse.vns.algo;

import com.hse.vns.TestUtils;
import com.hse.vns.entity.Solution;
import com.hse.vns.io.Reader;
import org.junit.Test;

import java.util.Arrays;

public class VNSTest extends TestUtils {

    @Test
    public void testOn20x20() {
        Solution s = Reader.read(getFile("5x5.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
        System.out.println(Arrays.deepToString(result.matrix));
        System.out.println(result.clusters);

    }
}

//0.08264462809917356
//0.07079646017699115