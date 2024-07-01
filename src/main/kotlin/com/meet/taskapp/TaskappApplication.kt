package com.meet.taskapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskappApplication

fun main(args: Array<String>) {
	runApplication<TaskappApplication>(*args)
}
