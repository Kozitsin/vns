package com.hse.vns;

public abstract class TestUtils {
    protected String getFile(String problemName) {
        return this.getClass().getClassLoader().getResource(problemName).getPath();
    }
}
