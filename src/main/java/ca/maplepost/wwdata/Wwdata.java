/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ca.maplepost.wwdata;

import java.util.Properties;

/**
 *
 * @author peterslack
 */
public class Wwdata {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Properties properties = System.getProperties();
        // Java 8
        properties.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
