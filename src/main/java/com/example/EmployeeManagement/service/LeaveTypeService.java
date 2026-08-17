package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LeaveTypeCreateRequest;
import com.example.EmployeeManagement.dto.LeaveTypeResponse;
import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.mapper.MapToDto;
import com.example.EmployeeManagement.mapper.MapToEntity;
import com.example.EmployeeManagement.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;


    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    //add type this is only by HR
    public LeaveTypeResponse createLeave(LeaveTypeCreateRequest leaveTypeCreateRequest){


        LeaveType leaveType = leaveTypeRepository.save(MapToEntity.mapToLeaveType(leaveTypeCreateRequest));

        return MapToDto.mapToLeaveTypeResponse(leaveType);
    }

    //delete the leavetype
    public void deleteLeave(String name){
        LeaveType leaveType = leaveTypeRepository.findByName(name).orElseThrow(
                ()-> new RuntimeException("the leave type not found")
        );

        leaveTypeRepository.delete(leaveType);
    }

    public List<LeaveTypeResponse> getAllTypes() {

        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        List<LeaveTypeResponse> leaveTypeResponses = new ArrayList<>();
        for(LeaveType leaveType : leaveTypeList){
            leaveTypeResponses.add(
                    MapToDto.mapToLeaveTypeResponse(leaveType)
            );
        }
        return leaveTypeResponses;
    }
}
