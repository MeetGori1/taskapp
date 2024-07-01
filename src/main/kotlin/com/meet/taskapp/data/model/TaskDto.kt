package com.meet.taskapp.data.model

import com.meet.taskapp.data.Priority
import java.time.LocalDateTime


//dto means data transfer object
data class TaskDto(

    val id: Long,

    val description: String ,

    val isReminderSet: Boolean,

    val isTaskOpen: Boolean,

    val createdOn: LocalDateTime ,

    val priority: Priority
)
