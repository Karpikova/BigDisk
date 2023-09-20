package org.example;

import java.io.File;

public class DirectorySizeChecker {
    private String directoryPath;
    private long limitLengthInByte;
    private static final int KB = 1024;

    public DirectorySizeChecker(String directoryPath, long limitLengthInByte) {
        this.directoryPath = directoryPath;
        this.limitLengthInByte = limitLengthInByte;
    }

    public void checkDirectory() {
        checkDirectoryRecursive(directoryPath, 0);
    }

    private long checkDirectoryRecursive(String currentPath, int nestedLevel) {
        nestedLevel++;
        File file = new File(currentPath);
        File[] listFiles = file.listFiles();
        if (isaNormalDirectory(file, listFiles)) {
            checkDirectoryAction(nestedLevel, file, listFiles);
        } else if (isaNonReadableDirectory(file, listFiles)) {
            return 0;
        } else {
            return checkFileLength(file, nestedLevel);
        }
        return 0;
    }

    private boolean isaNonReadableDirectory(File file, File[] listFiles) {
        return file.isDirectory() && listFiles == null;
    }

    private void checkDirectoryAction(int nestedLevel, File file, File[] files) {
        long length = 0;
        for (File nestedFile : files) {
            length = length + checkDirectoryRecursive(nestedFile.getAbsolutePath(), nestedLevel);
        }
        assessTheSituation(file, length, nestedLevel);
    }

    private boolean isaNormalDirectory(File file, File[] listFiles) {
        return file.isDirectory() && listFiles != null;
    }

    private long checkFileLength(File file, int nestedLevel) {
        long length = file.length();
        assessTheSituation(file, length, nestedLevel);
        return length;
    }

    private void assessTheSituation(File file, long length, int nestedLevel) {
        if (length > limitLengthInByte) {
            for (int i = 0; i < nestedLevel; i++) {
                System.out.print("\t");
            }
            System.out.println(file.getAbsolutePath() + " РАЗМЕРА " + length / KB / KB + " mb");
        }
    }
}
