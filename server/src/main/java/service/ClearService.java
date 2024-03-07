package service;

import dataAccess.DataAccessException;
import dataAccess.DataAccessFunctions;
import dataAccess.MySqlDataAccess;

import java.sql.SQLException;

public class ClearService {
    public static void clearOut() throws DataAccessException, SQLException {
            MySqlDataAccess.clearoff();
    }
}
