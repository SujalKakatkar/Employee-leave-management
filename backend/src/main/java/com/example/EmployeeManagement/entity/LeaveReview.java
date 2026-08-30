package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.ApproverRole;
import com.example.EmployeeManagement.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "leave_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @OneToOne
    @JoinColumn(name = "leave_request_id", nullable = false, unique = true)
    private LeaveRequest leaveRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    private ApproverRole reviewerRole;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String comments;

    @CreationTimestamp
    private LocalDateTime actedAt;
}
