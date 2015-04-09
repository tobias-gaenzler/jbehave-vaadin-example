package org.jbehave.tutorials.vaadin.addressbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class AddressbookSteps {

	private WebDriver webDriver;

	public AddressbookSteps(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;
	}

	@Given("The addressbook is opened")
	public void openAddressBook() {
		webDriver.navigate().to("http://localhost:8080/");
		waitForVaadin();
	}

	@When("The add contact button is pressed")
	public void clickAddContactButton() {
		WebElement button = webDriver.findElement(By.id("add-contact"));
		button.click();
		waitForVaadin();
	}

	@Then("A new contact has been added")
	public void checkThatNewContactHasBeenAdded() {
		WebElement firstNameField = webDriver.findElement(By.cssSelector(".v-formlayout table input"));
		assertThat(firstNameField.getAttribute("value"), is("New"));

		WebElement firstNameInTable = webDriver.findElement(By
				.cssSelector(".v-splitpanel-first-container .v-table-body tr:nth-child(1) td:nth-child(1)"));
		assertThat(firstNameInTable.getText(), is("New"));
		WebElement lastNameInTable = webDriver.findElement(By
				.cssSelector(".v-splitpanel-first-container .v-table-body tr:nth-child(1) td:nth-child(2)"));
		assertThat(lastNameInTable.getText(), is("Contact"));

	}

	public void waitForVaadin() {
		try {
			new WebDriverWait(webDriver, 5).until(vaadinIsReady());
		} catch (TimeoutException exe) {
		}
	}

	private Predicate<WebDriver> vaadinIsReady() {
		return new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver driver) {
				try {
					JavascriptExecutor executor = (JavascriptExecutor) webDriver;
					return (Boolean) executor.executeScript(//
							" if(!window.vaadin) { return false;}" + //
									"for (var client in window.vaadin.clients) { " + //
									" if (window.vaadin.clients[client].isActive()) { " + //
									"   return false;" + //
									"  }" + //
									"}" + //
									"return true;");
				} catch (WebDriverException e) {
					if ((e.getMessage() != null) && e.getMessage().contains("window.vaadin is undefined")) {
						return false;
					} else {
						throw e;
					}
				}
			}
		};
	}

	@AfterStories
	public void afterStories() {
		webDriver.close();
	}

}
