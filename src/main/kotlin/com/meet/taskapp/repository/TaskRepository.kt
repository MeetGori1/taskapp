package com.meet.taskapp.repository

import com.meet.taskapp.data.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TaskRepository : JpaRepository<Task, Long> {

    fun findTaskById(id: Long): Task

    @Query(value = "SELECT * FROM task WHERE is_task_open =TRUE", nativeQuery = true)
    fun queryAllOpenTasks(): List<Task>

    @Query(value = "SELECT * FROM task WHERE is_task_open =FALSe", nativeQuery = true)
    fun queryAllClosedTasks(): List<Task>

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM task WHERE description = ?1", nativeQuery = true)
    fun doesDescriptionExist(description: String): Boolean
}