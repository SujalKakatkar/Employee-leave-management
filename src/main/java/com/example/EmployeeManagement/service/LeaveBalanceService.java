package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LeaveBalanceResponse;
import com.example.EmployeeManagement.entity.LeaveBalance;
import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.mapper.ConvertToDto;
import com.example.EmployeeManagement.repository.LeaveBalanceRepository;
import com.example.EmployeeManagement.repository.LeaveTypeRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveBalanceService {
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final UserRepository userRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository, UserRepository userRepository, LeaveTypeRepository leaveTypeRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.userRepository = userRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }


    //create balance of an employee
    public void createLeaveBalance(String username, Integer year) {
        // find the user
        User user = userRepository.findByUsernameAndEnabledTrue(username).orElseThrow(
                () -> new RuntimeException("user not found")
        );
        //get all the leave types
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        List<LeaveBalance> leaveBalancesList = new ArrayList<>();
        for (LeaveType leaveType : leaveTypeList) {
            LeaveBalance temp = new LeaveBalance();
            temp.setLeaveType(leaveType);
            temp.setUser(user);
            temp.setYear(year);
            temp.setAllocatedDays(Double.valueOf(leaveType.getDefaultDaysPerYear()));
            temp.setUsedDays(0.0);
            leaveBalancesList.add(temp);
        }

        //add the type with userid on the balance table
        leaveBalanceRepository.saveAll(leaveBalancesList);


    }
    //all
    public List<LeaveBalanceResponse> getAll(){


       List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
       List<LeaveBalanceResponse> responseList = new ArrayList<>();
       for(LeaveBalance leaveBalance : leaveBalanceList){
           responseList.add(ConvertToDto.convertToLeaveBalanceResponse(leaveBalance));
       }

       return responseList;
    }

    public List<LeaveBalanceResponse> getAllBalanceByUsername(String username){
        List<LeaveBalance> leaveBalanceList =  leaveBalanceRepository.findByUser_Username(username);
        List<LeaveBalanceResponse> responseList = new ArrayList<>();
        for(LeaveBalance leaveBalance : leaveBalanceList){
            responseList.add(ConvertToDto.convertToLeaveBalanceResponse(leaveBalance));
        }
        return  responseList;
    }

    //this service is used when approved
    public void updateLeaveBalance(Integer userId, Integer leaveTypeId, Double leaveDays, Integer year) {
        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByUser_UserIdAndLeaveType_LeaveTypeIdAndYear(userId, leaveTypeId,year)
                .orElseThrow(
                        () -> new RuntimeException("leaveBalance not found")
                );

        double newUsedDays = leaveBalance.getUsedDays() + leaveDays;

        if (newUsedDays > leaveBalance.getAllocatedDays()) {
            throw new RuntimeException("You don't have enough leave balance");
        }

        leaveBalance.setUsedDays(newUsedDays);
        leaveBalanceRepository.save(leaveBalance);


    }

}
