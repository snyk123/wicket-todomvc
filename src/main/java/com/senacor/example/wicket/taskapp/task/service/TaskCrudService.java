package com.senacor.example.wicket.taskapp.task.service;

import com.senacor.example.wicket.taskapp.task.model.Task;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service with methods to create, read, update and delete a task.
 * @author Sven Ludwig, Senacor Technologies AG
 */
public class TaskCrudService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(TaskCrudService.class);

    /**
     * The base URL of the server.
     */
    private static final String BASE_URL = "http://tomcat-senacor.rhcloud.com/rest_ws-1.0/tasks/";

    /**
     * Create a new task.
     * <p/>
     * The given task must not have an <code>id</code>.
     * @param task The task. Not <code>null</code>.
     * @return The ID of the created task.
     */
    public Long createTask(final Task task) {
        Validate.notNull(task);
        Validate.isTrue(null == task.getId());

        task.setId(null);
        task.setCompleted(false);
        task.setVersion(null);

        RestTemplate restTemplate = defaultRestTemplate();

        ResponseEntity<Task> responseEntity = restTemplate.postForEntity(BASE_URL, task, Task.class);
        URI locationUri = responseEntity.getHeaders().getLocation();
        String id = locationUri.getPath().substring(locationUri.getPath().lastIndexOf('/') + 1);
        log.debug("Just created task with id: {}.", id);
        return Long.valueOf(id);
    }

    /**
     * Read a task.
     * <p/>
     * The given task must already have an <code>id</code>.
     * @param existingTask The given task. Not <code>null</code>.
     */
    public Task readTask(final Task existingTask) {
        Validate.notNull(existingTask);
        Validate.notNull(existingTask.getId());

        RestTemplate restTemplate = defaultRestTemplate();

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", existingTask.getId());

        ResponseEntity<Task> result = restTemplate.getForEntity(BASE_URL + "{id}", Task.class, uriVariables);

        Task task = result.getBody();
        log.debug("Just read: {}.", task);
        return task;
    }

    /**
     * Update a given task.
     * <p/>
     * The given task must already have an <code>id</code>.
     * @param task The given task. Not <code>null</code>.
     */
    public void updateTask(final Task task) {
        Validate.notNull(task);
        Validate.notNull(task.getId());

        RestTemplate restTemplate = defaultRestTemplate();

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", task.getId());

        restTemplate.put(BASE_URL + "{id}", task, uriVariables);
        log.debug("Just updated: {}.", task);
    }

    /**
     * Delete a given task.
     * <p/>
     * The given task must already have an <code>id</code>.
     * @param task The given task. Not <code>null</code>.
     */
    public void deleteTask(final Task task) {
        Validate.notNull(task);
        Validate.notNull(task.getId());

        RestTemplate restTemplate = defaultRestTemplate();

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", task.getId());

        restTemplate.delete(BASE_URL + "{id}", uriVariables);
        log.debug("Just deleted: {}.", task);
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
