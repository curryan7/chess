package service;

import dataAccess.DataAccessException;
import dataAccess.dataAccessFunctions;

import javax.xml.crypto.Data;

public class clearService {
    public static void clearOut() throws DataAccessException {
            dataAccessFunctions.clearoff();

    }
}
