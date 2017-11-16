package com.hse.vns.io;

import com.hse.vns.entity.Solution;
import com.hse.vns.exceptions.DataProcessingException;

import java.io.*;

import static java.net.URLDecoder.decode;


public class Reader {
    public static Solution read(String fileName) {
        Solution s;
        boolean[][] matrix;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(decode(fileName, "UTF-8"))))) {
            String[] size = reader.readLine().split(" ");
            int m = Integer.valueOf(size[0]);
            int p = Integer.valueOf(size[1]);// read dimension
            matrix = new boolean[m][p];

            for (int i = 0; i < m; i++){
                String[] positions = reader.readLine().split(" ");
                for (int j = 1; j < positions.length; j++){
                    int v = Integer.valueOf(positions[j]) - 1;
                    matrix[i][v] = true;
                }
            }

            s = new Solution(m, p, matrix);
            s.countEigens();

        } catch (IOException ex) {
            throw new DataProcessingException(ex);
        }

        return s;
    }
}
