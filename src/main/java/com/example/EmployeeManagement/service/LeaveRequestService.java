package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LeaveRequestCreateRequest;
import com.example.EmployeeManagement.dto.LeaveRequestResponse;
import com.example.EmployeeManagement.entity.*;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.mapper.MapToDto;
import com.example.EmployeeManagement.mapper.MapToEntity;
import com.example.EmployeeManagement.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public LeaveRequestResponse requestLeave(LeaveRequestCreateRequest leaveRequestCreateRequest, String email) {
        //convert the dto to real entity
        User user = userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(
                        () -> new RuntimeException("user not found")
                );
        //validate the leave type id
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequestCreateRequest.getLeaveTypeId()).orElseThrow(
                () -> new RuntimeException("user not found")
        );

        //validate the leaveBalance

        if(!leaveBalanceRepository.existsByUser_UserId(user.getUserId())){
            throw new RuntimeException("user don't have any balance for repo");
        }

        //if that type of balace is availble
        LeaveBalance leaveBalance =
                leaveBalanceRepository.findByUser_UserIdAndLeaveType_LeaveTypeIdAndYear(user.getUserId(),leaveType.getLeaveTypeId(),leaveRequestCreateRequest.getStartDate().getYear()).orElseThrow(
                        ()-> new RuntimeException("leave type balance not found")
                );

        if(Objects.equals(leaveBalance.getUsedDays(), leaveBalance.getAllocatedDays())){
            throw new RuntimeException("the user has used all of their leaves");
        }


        LeaveRequest leaveRequest = MapToEntity.mapToLeaveRequest(leaveRequestCreateRequest, leaveType, user);

        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();

        //validate the dates if they are future of past
        if (startDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("start date cannot be before today");
        }

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("end date cannot be before start");
        }

        //validate the span of dates has holidays
        List<Holiday> holidayList = holidayRepository.findAll();

        Long holidayCount = holidayList.stream().filter(
                holiday -> isDateInRange(holiday.getDate(), startDate, endDate)
        ).count();

        Long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long actualDays = days - holidayCount;


        //todo: add check point the actual days should not cross the limit of lee


        leaveRequest.setNumberOfDays((double) actualDays);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        //add the request to the table
        leaveRequestRepository.save(leaveRequest);

        return MapToDto.mapToLeaveRequestResponse(leaveRequest);
    }


    public List<LeaveRequestResponse> getLeaveRequests(String email){
        //find the user with email
        User user = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(
                ()->new RuntimeException("user not found")
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
