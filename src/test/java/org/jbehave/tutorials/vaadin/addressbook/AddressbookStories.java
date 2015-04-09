package org.jbehave.tutorials.vaadin.addressbook;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class AddressbookStories extends JUnitStories {

	private WebDriver webDriver;

	public AddressbookStories() {
		super();
		webDriver = new FirefoxDriver();
	}

	@Override
	public Configuration configuration() {
		Configuration configuration = new MostUsefulConfiguration()//
				.useStoryReporterBuilder(//
				new StoryReporterBuilder()//
						.withFormats(Format.HTML));
		return configuration;
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), new AddressbookSteps(webDriver));
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder()
				.findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story"), null);
	}

}
