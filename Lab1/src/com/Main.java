package com;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("portal_audits/Windows/MSCT_Windows_10_20H2_v1.0.0.audit");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.matches("^#.*")){
            line.split("<custom_item>");
            System.out.println(line);}
        }
        scanner.close();

    }
}
