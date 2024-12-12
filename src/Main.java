import java.sql.Connection;
import Controller.EmployeeController;
import DAO.* ;
import Model.Employee;
import Vue.Vue;


public class Main {
    public static void main(String[] args) {
        EmployeeController e = new EmployeeController(new EmployeeDAOImpl() , new Vue());
    }
}
