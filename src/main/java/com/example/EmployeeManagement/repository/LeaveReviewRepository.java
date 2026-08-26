package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveReviewRepository extends JpaRepository<LeaveReview, Integer> {
}
