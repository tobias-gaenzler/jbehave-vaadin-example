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

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * The acceptance test execution starts here. Configuration, steps factory and story paths are defined.
 * 
 * @author Tobias Gänzler
 */
@RunWith(JUnitReportingRunner.class)
public class AddressbookStories extends JUnitStories {

	public AddressbookStories() {
		super();
	}

	@Override
	public Configuration configuration() {
		Configuration configuration = new MostUsefulConfiguration()//
				.useStoryReporterBuilder(//
				new StoryReporterBuilder()//
						// create html report
						.withFormats(Format.HTML));
		return configuration;
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		// simplest form of a steps factory
		return new InstanceStepsFactory(configuration(), new AddressbookSteps());
	}

	@Override
	protected List<String> storyPaths() {
		// include all stories found in classpath
		return new StoryFinder()
				.findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story"), null);
	}

}
