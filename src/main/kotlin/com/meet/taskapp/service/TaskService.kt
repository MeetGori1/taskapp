package com.meet.taskapp.service

import com.meet.taskapp.data.Task
import com.meet.taskapp.data.model.TaskCreateRequest
import com.meet.taskapp.data.model.TaskDto
import com.meet.taskapp.data.model.TaskUpdateRequest
import com.meet.taskapp.exception.BadRequestException
import com.meet.taskapp.exception.TaskNotFoundException
import com.meet.taskapp.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val repository: TaskRepository) {
    private fun mappingsEntityToDto(task: Task) = TaskDto(
        task.id,
        task.description,
        task.isReminderSet,
        task.isTaskOpen,
        task.createdOn,
        task.priority
    )

    private fun mappingFromRequestToEntity(task: Task, request: TaskCreateRequest) {
        task.description = request.description
        task.isReminderSet = request.isReminderSet
        task.isTaskOpen = request.isTaskOpen
        task.priority = request.priority
    }

    private fun checkTaskForId(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException("Task With Id : $id is not Exist!")
        }
    }

    fun getTaskById(id: Long): TaskDto {
        checkTaskForId(id)
        val task = repository.findTaskById(id)
        return mappingsEntityToDto(task)
    }

    fun getAllTask(): List<TaskDto> =
        repository.findAll().stream().map(this::mappingsEntityToDto).collect(Collectors.toList())

    fun getAllOpenTask(): List<TaskDto> =
        repository.queryAllOpenTasks().stream().map(this::mappingsEntityToDto).collect(Collectors.toList())

    fun getAllClosedTask(): List<TaskDto> =
        repository.queryAllClosedTasks().stream().map(this::mappingsEntityToDto).collect(Collectors.toList())

    fun createTask(request: TaskCreateRequest): TaskDto {
        if (repository.doesDescriptionExist(request.description)) {
            throw BadRequestException("There is already a task with the Description: ${request.description}")
        } else {
            val task = Task()
            mappingFromRequestToEntity(task, request)
            val saveTask = repository.save(task)
            return mappingsEntityToDto(saveTask)
        }
    }

    fun updateTask(id: Long, request: TaskUpdateRequest): TaskDto {
        checkTaskForId(id)
        val task: Task = repository.findTaskById(id)
        for (prop in TaskUpdateRequest::class.memberProperties) {
            if (prop.get(request) != null) {
                val field: Field? = ReflectionUtils.findField(Task::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, task, prop.get(request))
                }
            }
        }

        val savedTask = repository.save(task)
        return mappingsEntityToDto(savedTask)
    }

    fun deleteTask(id: Long):String{
        checkTaskForId(id)
        repository.deleteById(id)
        return "Task with the Id: $id has been deleted."
    }
}