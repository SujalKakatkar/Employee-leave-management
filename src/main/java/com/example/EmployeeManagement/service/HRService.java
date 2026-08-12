package com.example.EmployeeManagement.service;


import com.example.EmployeeManagement.dto.UserRequest;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.Role;
import com.example.EmployeeManagement.repository.LeaveApprovalRepository;
import com.example.EmployeeManagement.repository.LeaveRequestRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class HRService {

    private final UserRepository userRepository;
    private LeaveApprovalRepository leaveApprovalRepository;
    private LeaveRequestRepository leaveRequestRepository;

    public HRService(UserRepository userRepository, LeaveApprovalRepository leaveApprovalRepository, LeaveRequestRepository leaveRequestRepository) {
        this.userRepository = userRepository;
        this.leaveApprovalRepository = leaveApprovalRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }


    //Create a manger with existing employee
    public void promoteManger(Integer empId, String email){
        //get the employee
        //if it is null return
        User user = userRepository.findById(empId).orElseThrow(
                ()->new RuntimeException("can't find employee")
        );

        User Hr = userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("can't find employee")
        );

        //set role to manger
        user.setRole(Role.MANAGER);
        user.setManager(Hr);
        userRepository.save(user);

    }


    // assign the manger
    public void assignManger(Integer empId, Integer mangerId){
        //get the employee
        User user = userRepository.findById(empId).orElseThrow(
                ()->new RuntimeException("can't find employee")
        );
        //passing them a manger which is existed with manger id
        User manager = userRepository.findById(mangerId).orElseThrow(
                ()->new RuntimeException("can't find employee")
        );
        if(manager.getRole() == Role.EMPLOYEE) {
            throw  new RuntimeException("the manger id is invalid");
        }
        user.setManager(manager);
        userRepository.save(user);



    }



    //get all employees
    // get all request

    //get all approved

    //get all summary


    //update employee
}
