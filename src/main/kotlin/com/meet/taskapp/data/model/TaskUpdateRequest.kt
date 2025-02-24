package com.meet.taskapp.data.model

import com.meet.taskapp.data.Priority
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class TaskUpdateRequest(
    val description: String?,
    val isReminderSet: Boolean?,
    val isTaskOpen: Boolean?,
    val priority: Priority?
)
