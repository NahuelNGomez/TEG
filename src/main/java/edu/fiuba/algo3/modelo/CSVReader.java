package edu.fiuba.algo3.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
    String CSVPath;

    public CSVReader(String path){
        CSVPath = path;
    }

    public ArrayList<ArrayList<String>> readCSV(){
        String SEPARATOR = ",";
        BufferedReader reader = null;
        ArrayList<ArrayList<String>> readFile = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(CSVPath));
            String line = reader.readLine();

            while (line != null) {
                String[] fields = line.split(SEPARATOR);
                ArrayList<String> readLine = new ArrayList<>();

                for(int i = 0; i < fields.length; i++){
                    readLine.add(fields[i]);
                }

                readFile.add(readLine);
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return readFile;
    }
}
