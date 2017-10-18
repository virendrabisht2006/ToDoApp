package com.app.todo.controller;

import com.app.todo.model.Task;
import com.app.todo.repository.TaskRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Api(value = "/", description = "API for CRUD operation in Task, one can add/delete/get task")
public class TodoController {

	@Autowired
	TaskRepository taskRepository;


	@RequestMapping("/todo")
	public String todo() {
		return "index";
	}



	@RequestMapping(value = "/tasks", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "/tasks", notes ="Save task into db", responseContainer = "Return HTTP 201 code on success and URI for added task")
	public ResponseEntity<?> saveTask(@RequestBody Task task, UriComponentsBuilder ucBuilder) {
		System.out.println("About to save task for taskName: "+ task.getTaskName());
		Task task1 = taskRepository.save(task);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tasks/search/findById/{id}").buildAndExpand(task1.getTaskId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "/tasks/id", notes = "Taskes id as input and delete the task for db for taskid", responseContainer = "Return HTTP 204 for on success")
	public ResponseEntity<?> deleteTask(@PathVariable("id") int id) {
		System.out.println("About to delete task for id: "+id);
		taskRepository.delete(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}


}
