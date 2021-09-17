package com;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    HashMap<String,HashMap<String,String>> policies = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("portal_audits/Windows/MSCT_Windows_10_20H2_v1.0.0.audit");
        Scanner scanner = new Scanner(file);
        HashMap<String,String> policy;
        String line;
        String[] twoArguments= new String[] {};
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
                if (line.matches("\\s*<custom_item>\\s*")){
                    line = scanner.nextLine();
                    policy = new HashMap<>();
                    while (!line.matches("\\s*</custom_item>\\s*"))
                    {
                    line=line.trim();
                    twoArguments = line.split(" *:",2);
                    if (twoArguments[0].matches("^\".*[^\"]$"))
                        while (!line.matches(".*\"$"))
                        {twoArguments[0]+=" "+line;
                        line = scanner.nextLine();}
                    policy.put(twoArguments[0],twoArguments[1]);
                    line = scanner.nextLine();
                    }
                    System.out.println(policy);
                }
        }
        scanner.close();

    }
}
