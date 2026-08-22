package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LeaveRequestCreateRequest;
import com.example.EmployeeManagement.dto.LeaveRequestResponse;
import com.example.EmployeeManagement.entity.*;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.exceptions.InvalidLeaveOperationException;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.MapToDto;
import com.example.EmployeeManagement.mapper.MapToEntity;
import com.example.EmployeeManagement.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LeaveRequestService {

    private LeaveRequestRepository leaveRequestRepository;

    private UserRepository userRepository;
    private LeaveTypeRepository leaveTypeRepository;
    private LeaveBalanceRepository leaveBalanceRepository;
    private HolidayRepository holidayRepository;


    private boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    //request for leave
    @Transactional
    public LeaveRequestResponse requestLeave(LeaveRequestCreateRequest leaveRequestCreateRequest, String email) {
        User user = userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user not found")
                );
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequestCreateRequest.getLeaveTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("Leave type not found")
        );


        if(!leaveBalanceRepository.existsByUser_UserIdAndUser_EnabledTrue(user.getUserId())){
            throw new InvalidLeaveOperationException("user don't have any balance for repo");
        }

        LeaveBalance leaveBalance =
                leaveBalanceRepository.findByUser_UserIdAndLeaveType_LeaveTypeIdAndYear(user.getUserId(),leaveType.getLeaveTypeId(),leaveRequestCreateRequest.getStartDate().getYear()).orElseThrow(
                        ()-> new ResourceNotFoundException("leave type balance not found")
                );

        if(Objects.equals(leaveBalance.getUsedDays(), leaveBalance.getAllocatedDays())){
            throw new InvalidLeaveOperationException("the user has used all of their leaves");
        }


        LeaveRequest leaveRequest = MapToEntity.mapToLeaveRequest(leaveRequestCreateRequest, leaveType, user);

        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();



        //validate the span of dates has holidays
        List<Holiday> holidayList = holidayRepository.findAll();

        Long holidayCount = holidayList.stream().filter(
                holiday -> isDateInRange(holiday.getDate(), startDate, endDate)
        ).count();

        Long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long actualDays = days - holidayCount;


        if(leaveBalance.getUsedDays() + actualDays > leaveBalance.getAllocatedDays()){
            throw new InvalidLeaveOperationException("you don't have that much leaves left");
        }

        leaveRequest.setNumberOfDays((double) actualDays);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        //add the request to the table
        leaveRequestRepository.save(leaveRequest);

        return MapToDto.mapToLeaveRequestResponse(leaveRequest);
    }


    public List<LeaveRequestResponse> getLeaveRequests(String email){
        //find the user with email
        User user = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(
                ()->new ResourceNotFoundException("user not found")
        );

        //get the user id and find the leave request
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAllByEmployee_UserId(user.getUserId());


        List<LeaveRequestResponse> responseList = new ArrayList<>();

        //convert the leave request in the dto and
        for(LeaveRequest leaveRequest : leaveRequestList){
           responseList.add(
                   MapToDto.mapToLeaveRequestResponse(leaveRequest)
           );

        }

        //return
        return responseList;
    }



}
