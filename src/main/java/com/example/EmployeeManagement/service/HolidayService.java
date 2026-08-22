package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.HolidayCreateRequest;
import com.example.EmployeeManagement.dto.HolidayResponse;
import com.example.EmployeeManagement.entity.Holiday;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.MapToDto;
import com.example.EmployeeManagement.mapper.MapToEntity;
import com.example.EmployeeManagement.repository.HolidayRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }


    //create holiday
    public HolidayResponse createHoliday(HolidayCreateRequest holidayCreateRequest){

        Holiday holiday = holidayRepository.save(MapToEntity.mapToHoliday(holidayCreateRequest));

        return MapToDto.mapToHolidayResponse(holiday);
    }

    //delete holiday
    public void deleteHoliday(String name){
        Holiday holiday = holidayRepository.findByName(name).orElseThrow(
                ()-> new ResourceNotFoundException("holiday with this name not exists")
        );
        holidayRepository.delete(holiday);
    }

    public List<HolidayResponse> getAllHolidays() {
        List<Holiday> holidayList = holidayRepository.findAll();
        List<HolidayResponse> responseList = new ArrayList<>();
        for (Holiday holiday : holidayList){
            responseList.add(
                    MapToDto.mapToHolidayResponse(holiday)
            );
        }

        return responseList;
    }
}
