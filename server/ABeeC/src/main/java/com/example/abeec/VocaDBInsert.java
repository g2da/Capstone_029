package com.example.abeec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class VocaDBInsert {
    public static ArrayList<String> vocaDB(){
        BufferedReader reader = null;
        ArrayList<String> vocaList = new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new FileReader("abeec.txt")

            );
            String str;
            while((str=reader.readLine())!=null){
                String[] module = str.split("'");

/*                System.out.print(module[0] +  "    :    ");
                System.out.println(module[1]);*/
                vocaList.add(module[1]);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*        ArrayList<String> eng = new ArrayList<>();
        ArrayList<String> kor = new ArrayList<>();
        vocaList.forEach(str->{
            String[] module = str.split(",");
            eng.add(module[0]);
            kor.add(module[1]);

        });*/
        return vocaList;

    }
}
