package com.hse.vns.io;

import com.hse.vns.TestUtils;
import com.hse.vns.entity.Solution;
import org.junit.Assert;
import org.junit.Test;

public class ReaderTest extends TestUtils {

    @Test
    public void readerShouldCorrectlyProcessInputFile() {
        String fullName = getFile("20x20.txt");
        Solution s = Reader.read(fullName);
        Assert.assertNotNull(s);
        Assert.assertNotNull(s.matrix);
    }
}
