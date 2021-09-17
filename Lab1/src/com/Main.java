package com;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("portal_audits/Windows/MSCT_Windows_10_1507_v1.0.0.audit");

        Scanner scanner = new Scanner(file);

        HashMap<String,HashMap<String,String>> policies = new HashMap<>();

        HashMap<String,String> policy;

        String line;

        String[] twoArguments;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();

                if (line.matches("\\s*<custom_item>\\s*")){
                    line = scanner.nextLine();
                    policy = new HashMap<>();

                    while (!line.matches("\\s*</custom_item>\\s*"))
                    {
                    line=line.trim();
                    twoArguments = line.split(" *: *",2);

                    if (twoArguments[1].matches("^\".*[^\"]$"))
                        while (!line.matches(".*\"$"))
                        {
                            line = scanner.nextLine();
                            twoArguments[1]+=" "+line;
                        }

                    policy.put(twoArguments[0],twoArguments[1]);
                    line = scanner.nextLine();
                    }

                    if ((policy.containsKey("reg_item"))&&(policy.containsKey("reg_key")))
                        policies.put(policy.get("reg_item").substring(1,policy.get("reg_item").length()-1)+"_"+policy.get("reg_key").substring(1,policy.get("reg_key").length()-1),policy);
                }
        }

        for (String key : policies.keySet())
            System.out.println(key+" "+policies.get(key)+"\n");

        scanner.close();

    }
}
