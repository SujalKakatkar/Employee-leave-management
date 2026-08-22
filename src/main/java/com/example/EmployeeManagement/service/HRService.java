package com.example.EmployeeManagement.service;


import com.example.EmployeeManagement.dto.ReportResponse;
import com.example.EmployeeManagement.dto.user.UserResponse;
import com.example.EmployeeManagement.entity.LeaveBalance;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.Role;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.EmployeeManagement.exceptions.RoleMismatchException;
import com.example.EmployeeManagement.mapper.MapToDto;
import com.example.EmployeeManagement.repository.LeaveApprovalRepository;
import com.example.EmployeeManagement.repository.LeaveBalanceRepository;
import com.example.EmployeeManagement.repository.LeaveRequestRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HRService {

    private final UserRepository userRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private LeaveApprovalRepository leaveApprovalRepository;
    private LeaveRequestRepository leaveRequestRepository;

    public HRService(UserRepository userRepository, LeaveBalanceRepository leaveBalanceRepository, LeaveApprovalRepository leaveApprovalRepository, LeaveRequestRepository leaveRequestRepository) {
        this.userRepository = userRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveApprovalRepository = leaveApprovalRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }


    //Create a manger with existing employee
    public void promoteManger(Integer empId, String email) {
        //get the employee
        //if it is null return
        User user = userRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFoundException("can't find employee")
        );

        if (user.getRole() == Role.MANAGER) {
            throw new RoleMismatchException("user is already manger");
        }

        User Hr = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(
                () -> new ResourceNotFoundException("can't find hr")
        );

        //set role to manger
        user.setRole(Role.MANAGER);
        user.setManager(Hr);
        userRepository.save(user);

    }


    // assign the manger
    public void assignManger(Integer empId, Integer mangerId) {
        //get the employee
        User user = userRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFoundException("can't find employee")
        );

        if (user.getRole() == Role.MANAGER) {
            throw new RoleMismatchException("one manager can't assign to other manager");
        }

        //passing them a manger which is existed with manger id
        User manager = userRepository.findById(mangerId).orElseThrow(
                () -> new ResourceNotFoundException("can't find employee")
        );
        if (manager.getRole() == Role.EMPLOYEE || manager.getRole() == Role.HR) {
            throw new RoleMismatchException("the manger id is invalid");
        }
        user.setManager(manager);
        userRepository.save(user);


    }

    //disable employee
    public void disableEmployee(String username) {
        //check if exists
        User user = userRepository.findByUsernameAndEnabledTrue(username).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        //check is it a manager
        if (user.getRole() == Role.MANAGER) {
            //add null the manger id refenced by this manager
            List<User> userList = userRepository.findAllByManager_UserId(user.getUserId());
            for (User temp : userList) temp.setManager(null);

            userRepository.saveAll(userList);
        }
        //isEnable false
        user.setEnabled(false);
        userRepository.save(user);

    }

    public UserResponse getUser(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("user not found")
        );

        return MapToDto.mapToUserResponse(user);
    }


    //get all employees
    public List<UserResponse> getAllEmployees() {
        List<User> userList = userRepository.findAllByRoleInAndEnabledTrue(List.of(Role.EMPLOYEE, Role.MANAGER));

        List<UserResponse> userResponsesList = new ArrayList<>();
        for (User user : userList) {
            userResponsesList.add(MapToDto.mapToUserResponse(user));

        }

        return userResponsesList;

    }

    // get all request
    public List<UserResponse> getAllManagers() {
        List<User> userList = userRepository.findAllByRoleInAndEnabledTrue(List.of(Role.MANAGER));

        List<UserResponse> userResponsesList = new ArrayList<>();
        for (User user : userList) {
            userResponsesList.add(MapToDto.mapToUserResponse(user));

        }

        return userResponsesList;

    }

    public ReportResponse getEmployeeReport(Integer empId) {

        User user = userRepository.findById(empId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        //the total is all the types of leaves in the year
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAllByUser_UserId(empId);

        if (leaveBalanceList.isEmpty()) {
            throw new ResourceNotFoundException("no leaves found");
        }

        double allocatedDays = 0;
        double usedDays = 0;
        for (LeaveBalance leaveBalance : leaveBalanceList) {
            allocatedDays += leaveBalance.getAllocatedDays();
            usedDays += leaveBalance.getUsedDays();
        }

        ReportResponse reportResponse = new ReportResponse();

        reportResponse.setUserId(user.getUserId());
        reportResponse.setManagerId(user.getManager() != null ? user.getManager().getUserId() : null);
        reportResponse.setName(user.getName());
        reportResponse.setRole(user.getRole());
        reportResponse.setTotalLeaves(allocatedDays);
        reportResponse.setUsedLeaves(usedDays);

        return reportResponse;
    }


    //todo: learn simple and opitmal way to fetch the list of rows and think about N+1 problem while implementing this

//    public List<ReportResponse> getAllReports() {
//
//        List<User> userList = userRepository.findAllByRoleInAndEnabledTrue(List.of(Role.EMPLOYEE, Role.MANAGER));
//
//        for(User user : userList){
//
//        }
//
//    }
}
