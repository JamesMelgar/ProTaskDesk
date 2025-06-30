package com.gyt.seguros.pro.task.desk.dal.model;

import com.gyt.seguros.pro.task.desk.dal.model.enums.TaskStatus;
import com.gyt.seguros.pro.task.desk.dal.model.enums.TaskPriority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "task",
        indexes = {
                @Index(name = "task_name_idx", columnList = "task_name"),
                @Index(name = "task_status_idx", columnList = "status"),
                @Index(name = "task_priority_idx", columnList = "priority"),
                @Index(name = "task_due_date_idx", columnList = "due_date"),
                @Index(name = "task_project_id_idx", columnList = "project_id"),
                @Index(name = "task_assigned_to_user_id_idx", columnList = "assigned_to_user_id"),
                @Index(name = "task_created_by_user_id_idx", columnList = "created_by_user_id")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name", nullable = false, length = 255)
    private String taskName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id", nullable = false)
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TaskStatus.PENDING;
        }
        if (priority == null) {
            priority = TaskPriority.MEDIUM;
        }
        if (version == null) {
            version = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Task(Project project, String taskName, String description,
                TaskStatus status, TaskPriority priority, LocalDate dueDate,
                User assignedTo, User createdBy) {
        this.project = project;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
    }
}
