package com.senacor.example.wicket.taskapp;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

public class TestTaskPage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new TaskWicketApplication());
	}

	@Test
	public void pageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(TaskPage.class);

		//assert rendered page class
		tester.assertRenderedPage(TaskPage.class);
	}
}
