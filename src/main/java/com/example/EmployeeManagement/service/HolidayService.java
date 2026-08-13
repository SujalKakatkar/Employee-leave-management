package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.entity.Holiday;
import com.example.EmployeeManagement.repository.HolidayRepository;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }


    //create holiday
    public Holiday createHoliday(Holiday holiday){
        Holiday newHoliday = new Holiday();
        newHoliday.setDate(holiday.getDate());
        newHoliday.setName(holiday.getName());
        return holidayRepository.save(newHoliday);
    }

    //delete holiday
    public void deleteHoliday(String name){
        Holiday holiday = holidayRepository.findByName(name).orElseThrow(
                ()-> new RuntimeException("holiday with this name not exists")
        );
        holidayRepository.delete(holiday);
    }

}
