package com.senacor.example.wicket.taskapp.task.service;

import com.senacor.example.wicket.taskapp.task.model.Task;
import com.senacor.example.wicket.taskapp.task.model.TaskArray;
import com.senacor.example.wicket.taskapp.task.model.TaskIdComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Service for task lists.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskListReadService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(TaskCrudService.class);

    /**
     * The base URL of the server.
     */
    private static final String BASE_URL = "http://tomcat-senacor.rhcloud.com/rest_ws-1.0/tasks/";

    /**
     * Read the list of all tasks.
     * @return The task list.
     */
    public List<Task> readTaskList() {
        RestTemplate restTemplate = defaultRestTemplate();

        ResponseEntity<TaskArray> responseEntity = restTemplate
                .exchange(BASE_URL,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<TaskArray>() {
                        });

        TaskArray taskArray = responseEntity.getBody();
        List<Task> taskList = Arrays.asList(taskArray.getContent());
        Collections.sort(taskList, new TaskIdComparator());
        log.debug("Just read the task list: {}.", taskList);
        return taskList;
    }

    /**
     * Create a new <code>RestTemplate</code> instance
     * with <code>HttpComponentsClientHttpRequestFactory</code>
     * and <code>MappingJackson2HttpMessageConverter</code>
     * and <code>StringHttpMessageConverter</code>
     * as well as <code>AcceptHeaderHttpRequestInterceptor</code>.
     * @return The new instance.
     */
    private RestTemplate defaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        List<ClientHttpRequestInterceptor> ris = new ArrayList<>(1);
        ris.add(new AcceptHeaderHttpRequestInterceptor(MediaType.APPLICATION_JSON));
        restTemplate.setInterceptors(ris);

        List<HttpMessageConverter<?>> converters = new ArrayList<>(1);
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(converters);

        return restTemplate;
    }

}
