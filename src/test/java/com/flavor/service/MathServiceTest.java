package com.flavor.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathServiceTest {

    private final MathService mathService = new MathService();

    @Test
    public void testMatrixMultiplication() {
        int[][] a = {
            {1, 2},
            {3, 4}
        };

        int[][] b = {
            {2, 0},
            {1, 2}
        };

        int[][] expected = {
            {4, 4},
            {10, 8}
        };

        int[][] result = mathService.multiplyMatrices(a, b);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testCrossProduct() {
        int[] u = {1, 0, 0};
        int[] v = {0, 1, 0};

        int[] expected = {0, 0, 1};

        assertArrayEquals(expected, mathService.crossProduct(u, v));
    }

    @Test
    public void testCrossProduct_InvalidLength() {
        int[] u = {1, 2};
        int[] v = {3, 4};

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                mathService.crossProduct(u, v));

        assertTrue(exception.getMessage().contains("размерности 3"));
    }
}
