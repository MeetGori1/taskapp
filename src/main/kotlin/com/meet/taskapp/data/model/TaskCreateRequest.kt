package com.meet.taskapp.data.model

import com.meet.taskapp.data.Priority
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class TaskCreateRequest(
    @NotBlank(message = "Task description can't be empty")
    val description: String,

    val isReminderSet: Boolean,

    val isTaskOpen: Boolean,

    @NotBlank(message = "Task create_on can't be empty")
    val createdOn: LocalDateTime,

    val priority: Priority
)
