package com.ccx.radicador.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.ccx.ex.ActorException;

@XmlRootElement
public class Scenario {

	private List<Actor> actores = new ArrayList<Actor>();
	private String id;
	private String radicacionId;
	private Boolean bIcefaces = false;
	private String strAjaxcondition;
	private String strIcefacesscript;

	public Scenario() {
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public Boolean getIcefaces() {
		return bIcefaces;
	}

	public void setIcefaces(Boolean bIcefaces) {
		this.bIcefaces = bIcefaces;
	}

	@XmlElementWrapper(name = "actores")
	@XmlElement(name = "actor")
	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public String getRadicacionId() {
		return radicacionId;
	}

	public void setRadicacionId(String radicacionId) {
		this.radicacionId = radicacionId;
	}

	public void addActor(Actor theActor) {
		actores.add(theActor);
	}

	public void init(String icefacesscript, String ajaxcondition) {
		this.strAjaxcondition = ajaxcondition;
		this.strIcefacesscript = icefacesscript;
	}

	private void setValue(WebDriver driver, WebElement element, String value) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value = arguments[1]", element, value);
	}

	private void click(WebElement w, WebDriver driver,
			final Wait<WebDriver> wait) {
		Actions builder = new Actions(driver);
		Action clickControl = builder.moveToElement(w).click(w).build();
		clickControl.perform();
	}

	public void play(WebDriver driver, Wait<WebDriver> wait)
			throws ActorException {
		Iterator<Actor> it = actores.iterator();
		if (bIcefaces) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(strIcefacesscript);
		}
		while (it.hasNext()) {
			final Actor actor = it.next();
			System.out.println("Control: " + actor.getName());
			try {
				WebElement webElem = wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath(actor.getXpath())));
				if (actor.getType() == ActorType.TEXT) {
					setValue(driver, webElem, actor.getData());
				} else if (actor.getType() == ActorType.FILE) {
					webElem.sendKeys(actor.getData());
				} else if (actor.getType() == ActorType.SELECT) {
					Select select = new Select(webElem);
					select.deselectByVisibleText(actor.getData());
					select.selectByVisibleText(actor.getData());
				} else if (actor.getType() == ActorType.RADIO) {
					click(webElem, driver, wait);
				} else if (actor.getType() == ActorType.CHECKBOX) {
					click(webElem, driver, wait);
				} else if (actor.getType() == ActorType.BUTTON) {
					click(webElem, driver, wait);
				} else if (actor.getType() == ActorType.LINK) {
					click(webElem, driver, wait);
				}
				// Wait until control finishes update
				if (bIcefaces && actor.getDoesSubmit()) {
					try {
						WebElement conditionId = driver.findElement(By
								.xpath(strAjaxcondition));
						wait.until(ExpectedConditions.stalenessOf(conditionId));
					} catch (NoSuchElementException e) {
						// do nothing. this is expected.
					}
				}
			} catch (Exception ex) {
				ActorException e = new ActorException(actor.getName());
				throw e;
			}
		}
		System.out.println("Scenario completado");
	}

	@Override
	public String toString() {
		StringBuffer sbr = new StringBuffer();
		sbr.append("Id: ");
		sbr.append(id);
		sbr.append(" | ");
		sbr.append("RadicacionId: ");
		sbr.append(radicacionId);
		sbr.append(" | ");
		sbr.append("\n");
		for (Actor a : actores) {
			sbr.append("\t");
			sbr.append(a.toString());
			sbr.append("\n");
		}
		return sbr.toString();
	}
}
