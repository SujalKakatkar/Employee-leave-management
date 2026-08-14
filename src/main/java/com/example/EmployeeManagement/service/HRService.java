package com.example.EmployeeManagement.service;


import com.example.EmployeeManagement.dto.user.UserResponse;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.Role;
import com.example.EmployeeManagement.mapper.ConvertToDto;
import com.example.EmployeeManagement.repository.LeaveApprovalRepository;
import com.example.EmployeeManagement.repository.LeaveRequestRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        User Hr = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(
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

    //disable employee
    public void disableEmployee(String username){
        //check if exists
        User user = userRepository.findByUsernameAndEnabledTrue(username).orElseThrow(
                ()-> new RuntimeException("user not found")
        );
        //check is it a manager
        if(user.getRole() == Role.MANAGER){
        //add null the manger id refenced by this manager
            List<User> userList = userRepository.findAllByManager_UserId(user.getUserId());
            for(User temp : userList) temp.setManager(null);

            userRepository.saveAll(userList);
        }
        //isEnable false
        user.setEnabled(false);
        userRepository.save(user);
        //add all the search with isenble true for users
    }



    //get all employees
    public List<UserResponse> getAllEmployees(){
        List<User> userList = userRepository.findAllByRoleInAndEnabledTrue(List.of(Role.EMPLOYEE, Role.MANAGER));

        List<UserResponse> userResponsesList = new ArrayList<>();
        for (User user : userList){
            userResponsesList.add(ConvertToDto.convertToUserResponse(user));

        }

        return userResponsesList;

    }

    // get all request
    public List<UserResponse> getAllManagers(){
        List<User> userList = userRepository.findAllByRoleInAndEnabledTrue(List.of(Role.MANAGER));

        List<UserResponse> userResponsesList = new ArrayList<>();
        for (User user : userList){
            userResponsesList.add(ConvertToDto.convertToUserResponse(user));

        }

        return userResponsesList;

    }

    //get all approved


    //get all summary


    //update employee
}
