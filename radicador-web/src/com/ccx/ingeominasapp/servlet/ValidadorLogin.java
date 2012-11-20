package com.ccx.ingeominasapp.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ccx.ingeominasapp.controllers.CaptchaController;
import com.ccx.ingeominasapp.controllers.IngeominasController;

/**
 * Servlet implementation class ValidadorLogin
 */
@WebServlet("/login")
public class ValidadorLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	IngeominasController radweb;
	@Inject
	private CaptchaController captcha;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidadorLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String challenge = request.getParameter("recaptcha_challenge_field");
		String challengeResponse = request
				.getParameter("recaptcha_response_field");
		Long tipoDocumento = Long
				.valueOf(request.getParameter("tipoDocumento"));
		String PIN = request.getParameter("j_password");
		String numeroDocumento = request.getParameter("numeroDocumento");
		String remoteAddress = request.getRemoteAddr();
		if (captcha.validateCaptcha2(remoteAddress, challenge,
				challengeResponse)) {
			// Validate PIN
			boolean validLogin = radweb.validarLogin(PIN, tipoDocumento,
					numeroDocumento);
			if (!validLogin) {
				response.sendRedirect("?error=invalid");
			} else {
				session.setAttribute("CCXAuthenticated", new Boolean(true));
				response.sendRedirect("pages/radweb.jsf");
			}
		} else {
			response.sendRedirect("?error=captcha");
		}

	}
}
