package com.flavor.service;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    // Перемножение двух матриц
    public int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    // Векторное произведение двух векторов в 3D
    public int[] crossProduct(int[] u, int[] v) {
        if (u.length != 3 || v.length != 3) {
            throw new IllegalArgumentException("Вектор должен быть размерности 3");
        }
        return new int[]{
                u[1] * v[2] - u[2] * v[1],
                u[2] * v[0] - u[0] * v[2],
                u[0] * v[1] - u[1] * v[0]
        };
    }
}
