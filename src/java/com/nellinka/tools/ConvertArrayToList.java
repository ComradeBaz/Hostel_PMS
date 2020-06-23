/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ComradeBaz
 */
public class ConvertArrayToList {
    public static List<Integer> convertIntArrayToList(int[] input) {

        List<Integer> list = new ArrayList<>();
        for (int i : input) {
            list.add(i);
        }
        return list;

    }
}
