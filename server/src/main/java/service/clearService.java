package service;

import dataAccess.DataAccessException;
import dataAccess.DataAccessFunctions;

public class clearService {
    public static void clearOut() throws DataAccessException {
            DataAccessFunctions.clearoff();
    }
}
