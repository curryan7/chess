package service;

import dataAccess.DataAccessException;
import dataAccess.DataAccessFunctions;

public class ClearService {
    public static void clearOut() throws DataAccessException {
            DataAccessFunctions.clearoff();
    }
}
