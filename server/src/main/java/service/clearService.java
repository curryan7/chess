package service;

import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import dataAccess.dataAccessFunctions;

public class clearService {

    public static void clearOut() throws DataAccessException {
        dataAccessFunctions.clearoff();
    }
}
