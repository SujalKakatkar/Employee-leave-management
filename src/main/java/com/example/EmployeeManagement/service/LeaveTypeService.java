package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

    private LeaveTypeRepository leaveTypeRepository;


    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    //add type this is only by HR
    public LeaveType createLeave(LeaveType leaveType){
        LeaveType newLeave = new LeaveType();
        newLeave.setName(leaveType.getName());
        newLeave.setDefaultDaysPerYear(leaveType.getDefaultDaysPerYear());
        newLeave.setIsPaid(leaveType.getIsPaid());

        return leaveTypeRepository.save(newLeave);
    }

    //delete the leavetype
    public void deleteLeave(String name){
        LeaveType leaveType = leaveTypeRepository.findByName(name).orElseThrow(
                ()-> new RuntimeException("the leave type not found")
        );

        leaveTypeRepository.delete(leaveType);
    }

}
