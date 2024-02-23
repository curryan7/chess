package service;

import model.UserData;
import spark.Request;
import spark.Response;
import dataAccess.dataAccessFunctions;

public class registerService {
    public registerUser(UserData user){
        dataAccessFunctions.clearoff();
    }
}
