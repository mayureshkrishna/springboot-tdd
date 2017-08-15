package com.bth.bdd.todolist

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Ignore
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification


@ActiveProfiles("test")
@SpringBootTest(classes = ToDoListApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToDoListApplicationSpec extends Specification {
	
	private TestRestTemplate restTemplate = new TestRestTemplate()
	
   // Inject random port of test Web Server:
//   @Value('${local.server.port}')
   def int serverPort = 8080
   
   @Shared
   def url; 
	
	def setup() {
		println('serverPort = "' + serverPort + '"')
		url = "http://localhost:" + serverPort + "/toDoList"
		println('URL ="' + url + '"')
	}
	
	def 'Adding an item to an empty list should result in a list with one item'() {
		given: 'my to do list is empty'
			def list = restTemplate.getForEntity(url, List.class).getBody()
			assert(list.size() == 0)
		
		when: 'I add "Shave yak" to the list'
			restTemplate.postForObject(url,"Shave yak", String.class)
		
		then: 'the list should contain one item'
			def response = restTemplate.getForEntity(url, List.class)
			response.statusCode.value == 200
			def newList = response.getBody()
			newList.size() == 1
		
		and: 'the description should be "Shave yak"'	
			newList.get(0) == "Shave yak"	
	}
	
	@Ignore
	def 'Completing a task should remove it from the list' () {
		given: 'my to do list contains the task "Shave yak"'
		
		and:  'my to do list contains the task "Prepare workshop"'
		
		when: 'I complete the task "Shave yak"'
		
		then: 'it should be removed from the list'
		
		and: 'the list should still contain the task "Prepare workshop"'
		
	}

}