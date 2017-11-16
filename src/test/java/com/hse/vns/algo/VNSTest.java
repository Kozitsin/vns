package com.hse.vns.algo;

import com.hse.vns.TestUtils;
import com.hse.vns.entity.Solution;
import com.hse.vns.io.Reader;
import org.junit.Test;

public class VNSTest extends TestUtils {

    @Test
    public void testOn20x20() {
        Solution s = Reader.read(getFile("20x20.txt"));
        Solution result = VNS.execute(s);
        System.out.println(result.GE);
    }
}
