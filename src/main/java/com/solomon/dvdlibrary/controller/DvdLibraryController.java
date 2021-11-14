/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solomon.dvdlibrary.controller;

import com.solomon.dvd.dto.DVD;
import com.solomon.dvd.ui.DvdLibraryView;
import com.solomon.dvd.ui.UserIOConsoleImpl;
import com.solomon.dvdlibrary.dao.DvdDaoException;
import com.solomon.dvdlibrary.dao.dvdLibraryDao;
import java.util.List;
import com.solomon.dvd.ui.UserIO;

/**
 *
 * @author solomon
 */

 

public class DvdLibraryController {
    
    private final UserIO io = new UserIOConsoleImpl();
     private final dvdLibraryDao dao;
    private final DvdLibraryView view;
   
    private String title;
  
    /**
     *
     * @param dao
     * @param view
     */
    public DvdLibraryController(dvdLibraryDao dao,DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
        
    }
    
    public void run(){
        boolean keepRunning = true;
        int menuSelection = 0;
        try{
            while (keepRunning){
                    io.print("Main Menu");
                    io.print("1. Create DVD ");
                    io.print("2. Remove DVD");
                    io.print("3. Edit DVD");
                    io.print("4. List DVDs");
                    io.print("5. Display DVD");
                    io.print("6. Search DVD"); 
                    io.print("7. Exit");  
                         
                    
                 menuSelection = getMenuSelection();
                 
                 switch (menuSelection){
                    
                    case 1:
                        createDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        editDVD();
                        break;
                    case 4:
                        listDVDs();
                        break;
                    case 5:
                        viewDVD();
                        break;
                    case 6:
                        searchDVD();
                        break;
                    case 7:
                        keepRunning = false;
                        break;
                    default:
                        unknownCommand();
                        
                }
            }
          exitMessage();
        }catch (DvdDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
             
    }
   
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();   
    
    }
    
    
    private void listDVDs() throws DvdDaoException {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVD();
        view.displayDVDList(dvdList);
        
    }
    
    private void viewDVD()throws DvdDaoException{
        view.diplayDisplayDVDBanner();
        String title = view.getDVDTitleChoice();
        DVD dvd = dao.getDvd(title);
        view.displayDVD(dvd);
    }
            
    
    private void removeDVD()throws DvdDaoException{
        view.displayRemoveDVDBanner();
        String dvdTitle = view.getDVDTitleChoise();
        DVD removedDVD = dao.removeDVD(dvdTitle);
        view.displayRemoveResult(removedDVD);
        
    }
    private void createDVD() throws DvdDaoException{
        view.displayCreatDVDBanner();
        DVD newDvd = view.getDVDInfo();
        dao.addDVD(newDvd.getTitle(), newDvd);
        view.displayCreatSuccessBanner();
        
    }
    
    
  
    private void searchDVD() throws DvdDaoException{
        view.displaySearchBanner();
        String title = view.getDVDTitleChoice();
        DVD dvd = dao.searchDvd(title);
        view.searchDvdResult(dvd);
        view.displayDVD(dvd);
    }
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
        
    }
    private void exitMessage(){
        view.displayExitBanner();
    }
    
 private void editDVD()throws DvdDaoException{
        view.displayEditDVDBanner();
        
        String dvdTitle = view.getDVDEditTitleChoice();
        DVD editDVD = dao.getDvd(dvdTitle);
        boolean newer = view.displayEditResult(editDVD);
       
        
        if(newer){
            view.editMenu();
            int choice = view.editHelper();
            String replaceValue = view.getEditValue(choice);
            int result = dao.editDVD(editDVD, choice, replaceValue);
            view.displaySuccessEdit();
            
        }
        
    }
}
