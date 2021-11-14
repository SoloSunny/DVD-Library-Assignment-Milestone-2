/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DvdLibrary;

import com.solomon.dvd.ui.DvdLibraryView;
import com.solomon.dvd.ui.UserIOConsoleImpl;
import com.solomon.dvdlibrary.controller.DvdLibraryController;
import com.solomon.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import com.solomon.dvdlibrary.dao.dvdLibraryDao;
import com.solomon.dvd.ui.UserIO;

/**
 *
 * @author solomon
 */

public class App {
     public static void main(String[]  args){
         
        UserIO myIo = new UserIOConsoleImpl();
        DvdLibraryView myView = new DvdLibraryView(myIo);
        dvdLibraryDao myDao = new DvdLibraryDaoFileImpl() {};
        DvdLibraryController controller = new DvdLibraryController(myDao, myView);
        controller.run();
     }  
}
