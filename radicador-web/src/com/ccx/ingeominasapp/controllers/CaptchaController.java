package com.ccx.ingeominasapp.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

@Stateful
@SessionScoped
@Named("captcha")
public class CaptchaController {

	@Inject
	MessageSingleton message;
	@Inject
	IngeominasController radweb;
	private Boolean captchaRendered = false;
	private Boolean challengeFailRendered = false;

	public Boolean getCaptchaRendered() {
		return captchaRendered;
	}

	public void setCaptchaRendered(Boolean captchaRendered) {
		this.captchaRendered = captchaRendered;
	}

	public Boolean getChallengeFailRendered() {
		return challengeFailRendered;
	}

	public void setChallengeFailRendered(Boolean challengeFailRendered) {
		this.challengeFailRendered = challengeFailRendered;
	}

	// ----------------------------------------------------------------
	// ACTION LISTENERS
	// ----------------------------------------------------------------

	public void cancelPressedCaptcha(ActionEvent e) {
		captchaRendered = false;
		challengeFailRendered = false;
		radweb.setEnRadicacion(false);
	}

	public String okPressedCaptcha() {
		String result = null;
		try {
			if (!validateCaptcha()) {
				challengeFailRendered = true;
			} else {
				captchaRendered = false;
				challengeFailRendered = false;
				radweb.setGuardado(true);
				message.addMessage("page.saved.message.ok");
				if (radweb.getEnRadicacion())
					result = "radicar";
			}
		} catch (IOException e1) {
			challengeFailRendered = true;
			e1.printStackTrace();
		}
		return result;
	}

	public void cancelCaptcha(ActionEvent e) {
		captchaRendered = false;
		challengeFailRendered = false;
	}

	public boolean validateCaptcha() throws IOException {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String challenge = request.getParameter("recaptcha_challenge_field");
		String challengeResponse = request
				.getParameter("recaptcha_response_field");
		return validateCaptcha2(request.getRemoteAddr(), challenge,
				challengeResponse);
	}

	public boolean validateCaptcha2(String address, String challenge,
			String challengeResponse) {
		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(
				"6LdSZNISAAAAAC3LiAH283M9M1wMFH0wma2BaiYH",
				"6LdSZNISAAAAAGs80HGnXzX7hE80EJnm3vjkWfmR", false);
		ReCaptchaResponse response = captcha.checkAnswer(address, challenge,
				challengeResponse);
		return response.isValid();
	}

	public boolean validateCaptcha(String challenge, String challengeResponse)
			throws IOException {
		boolean captchaFailed = false;
		if (challenge == null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			challenge = request.getParameter("recaptcha_challenge_field");
			challengeResponse = request
					.getParameter("recaptcha_response_field");
		}
		URL url = new URL("http://www.google.com/recaptcha/api/verify");
		URLConnection urlConn = url.openConnection();
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		DataOutputStream printout = new DataOutputStream(
				urlConn.getOutputStream());
		String content = "privatekey="
				+ URLEncoder.encode("6LdSZNISAAAAAGs80HGnXzX7hE80EJnm3vjkWfmR",
						"UTF-8") + "&remoteip="
				+ URLEncoder.encode("186.80.1.121", "UTF-8") + "&challenge="
				+ URLEncoder.encode(challenge, "UTF-8") + "&response="
				+ URLEncoder.encode(challengeResponse, "UTF-8");
		printout.writeBytes(content);
		printout.flush();
		printout.close();
		InputStreamReader inputReader = new InputStreamReader(
				urlConn.getInputStream());
		BufferedReader inputBuffer = new BufferedReader(inputReader);
		String result = inputBuffer.readLine();
		if (result.equals("false")) {
			captchaFailed = true;
		}
		return captchaFailed;
	}
}
