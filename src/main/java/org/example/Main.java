package org.example;

public class Main {
    public static void main(String[] args) {
        DirectorySizeChecker disk = new DirectorySizeChecker("C:/", 500000000);
        System.out.println(disk);
        disk.checkDirectory();
    }
}
