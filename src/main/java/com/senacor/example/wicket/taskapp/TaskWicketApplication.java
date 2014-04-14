package com.senacor.example.wicket.taskapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application.
 */
public class TaskWicketApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return TaskPage.class;
    }

    @Override
    public void init() {
        super.init();

        // remove wicket-tags from the output
        getMarkupSettings().setStripWicketTags(true);

        // standard-encoding for markup template files
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

        // character-encoding in the response header
        // Content-Type text/html; charset=utf-8
        getRequestCycleSettings().setResponseRequestEncoding("utf-8");
    }

}
