/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solomon.dvdlibrary.dao;

import com.solomon.dvd.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author solomon
 */
public class DvdLibraryDaoFileImpl implements dvdLibraryDao{
    private Map<String, DVD> dvds = new HashMap<>();
    
    public static final String DVD = "Dvd.txt";
    public static final String DELIMITER = "::";
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DvdDaoException {
        loadLibrary();
        DVD newDVD = dvds.put(title, dvd);
        writeDvd();
        return newDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DvdDaoException{
        loadLibrary();
        DVD removedDVD = dvds.remove(title);
        writeDvd();
        return removedDVD;
    }

    @Override
    public int editDVD(DVD editDVD, int choice, String replace) throws DvdDaoException{
        loadLibrary();
        int result = 0;
        DVD edit = editDVD;
  
        
     switch (choice) {
         case 1:
             edit.setReleaseDate(replace);
             dvds.replace(edit.getTitle(), edit);
             result = 1;
             break;
         case 2:
             edit.setMpaaRating(replace);
             dvds.replace(edit.getTitle(), edit);
             result = 2;
             break;
         case 3:
             edit.setDirectorName(replace);
             dvds.replace(edit.getTitle(), edit);
             result = 3;
             break;
         case 4:
             edit.setStudio(replace);
             dvds.replace(edit.getTitle(), edit);
             result = 4;
             break;
         case 5:
             edit.setUserRating(replace);
             dvds.replace(edit.getTitle(), edit);
             result = 5;
         
             break;
         default:
             break;
     }
        
        writeDvd();
        return result;
    }
    

    @Override
    public List<DVD> getAllDVD() throws DvdDaoException {
        loadLibrary();
        return new ArrayList<>(dvds.values());
    }

    @Override
    public DVD searchDvd(String title) throws DvdDaoException {
        return dvds.get(title);
    }
     /**
     *
     * @param title
     * @return
     */
     @Override
    public DVD getDvd(String title) throws DvdDaoException {
        loadLibrary();
        return dvds.get(title);
    }
    
    private DVD unmarshallDVD(String dvdAsText){
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        
        DVD dvdFromFile = new DVD(title);
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setMpaaRating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserRating(dvdTokens[5]);
    
        return dvdFromFile;
    }
    
    private void loadLibrary() throws DvdDaoException {
        Scanner scanner;

        try {
            
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD)));
        } catch (FileNotFoundException e) {
            throw new DvdDaoException(
                    "-_- Could not load roster data into memory.", e);
        }
        String currentLine;
      
        DVD currentDVD;
        
        while (scanner.hasNextLine()) {
            
            currentLine = scanner.nextLine();
           
            currentDVD = unmarshallDVD(currentLine);

            dvds.put(currentDVD.getTitle(), currentDVD);
        }
        
        scanner.close();
    }
    
    private String marshallDVD(DVD aDVD){
    
    String dvdAsText = aDVD.getTitle() + DELIMITER;

    dvdAsText += aDVD.getReleaseDate() + DELIMITER;

    dvdAsText += aDVD.getMpaaRating() + DELIMITER;

    dvdAsText += aDVD.getDirectorName() + DELIMITER;
    
    dvdAsText += aDVD.getStudio() + DELIMITER;
    
    dvdAsText += aDVD.getUserRating();
   
    return dvdAsText;
}
    
private void writeDvd() throws DvdDaoException {
    PrintWriter out;

    try {
        out = new PrintWriter(new FileWriter(DVD));
    } catch (IOException e) {
        throw new DvdDaoException(
                "Could not save student data.", e);
    }

    String dvdAsText;
    List<DVD> dvdList = this.getAllDVD();
    for (DVD currentDVD : dvdList) {
     
        dvdAsText = marshallDVD(currentDVD);
       
        out.println(dvdAsText);
        
        out.flush();
    }
    
    out.close();
}

}
