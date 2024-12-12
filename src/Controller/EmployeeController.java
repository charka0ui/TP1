package Controller;

import DAO.EmployeeDAOImpl;
import Model.Employee;
import Vue.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private final EmployeeDAOImpl employeeDAO;
    private final Vue view;

    public EmployeeController(EmployeeDAOImpl employeeDAO, Vue view) {
        this.employeeDAO = employeeDAO;
        this.view = view;

        // Ajouter Button
        view.getAjouter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nom = view.getNom().getText();
                    String prenom = view.getPrenom().getText();
                    String email = view.getEmail().getText();
                    String tel = view.getTel().getText();
                    double salaire = Double.parseDouble(view.getSal().getText());
                    String roleString = (String) view.getRoleComboBox().getSelectedItem();
                    Employee.Role role = Employee.Role.valueOf(roleString);
                    String posteString = (String) view.getPostesComboBox().getSelectedItem();
                    Employee.Poste poste = Employee.Poste.valueOf(posteString);

                    if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Employee emp = new Employee(0, nom, prenom, tel, email, salaire, role, poste);
                    employeeDAO.add(emp);
                    JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.getAfficher().doClick();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Invalid salary value!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getAfficher().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Employee> allEmployees = employeeDAO.findAll();
                List<String> employeeStrings = new ArrayList<>();
                employeeStrings.add(String.format(
                        "| %-5s | %-15s | %-15s | %-15s | %-25s | %-10s | %-20s | %-10s |",
                        "ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"
                ));
                for (Employee emp : allEmployees) {
                    employeeStrings.add(emp.toString());
                }
                String[] employeeArray = employeeStrings.toArray(new String[0]);
                JList<String> updatedList = new JList<>(employeeArray);
                view.setEmployeeList(updatedList); // Update the reference in Vue
                JPanel p3 = view.getP3();
                p3.removeAll();
                p3.add(new JScrollPane(updatedList));
                p3.revalidate();
                p3.repaint();
            }
        });

        view.getSupprimer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployeeString = view.getEmployeeList().getSelectedValue();
                if (selectedEmployeeString == null) {
                    JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(selectedEmployeeString.split("\\|")[1].trim());

                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Delete employee
                    employeeDAO.delete(id);
                    JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Trigger "Afficher" action to refresh the list
                    view.getAfficher().doClick();
                }
            }
        });


        view.getModifier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployeeString = view.getEmployeeList().getSelectedValue();
                if (selectedEmployeeString == null) {
                    JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(selectedEmployeeString.split("\\|")[1].trim());
                String nom = view.getNom().getText();
                String prenom = view.getPrenom().getText();
                String email = view.getEmail().getText();
                String tel = view.getTel().getText();
                double salaire = Double.parseDouble(view.getSal().getText());
                String roleString = (String) view.getRoleComboBox().getSelectedItem();
                Employee.Role role = Employee.Role.valueOf(roleString);
                String posteString = (String) view.getPostesComboBox().getSelectedItem();
                Employee.Poste poste = Employee.Poste.valueOf(posteString);

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee emp = new Employee(0, nom, prenom, tel, email, salaire, role, poste);
                employeeDAO.update(emp , id);
                view.getAfficher().doClick();
            }
        });    }
}
