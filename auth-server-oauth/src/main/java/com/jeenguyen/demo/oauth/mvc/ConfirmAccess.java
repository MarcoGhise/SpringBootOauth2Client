package com.jeenguyen.demo.oauth.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("authorizationRequest")
public class ConfirmAccess {

	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}
		
		model.put("scopes", createScopes(model, request));

		return new ModelAndView("confirm_access", model);
	}


	private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
		StringBuilder builder = new StringBuilder("<ul>");
		@SuppressWarnings("unchecked")
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request
				.getAttribute("scopes"));
		for (String scope : scopes.keySet()) {
			String approved = "true".equals(scopes.get(scope)) ? " checked" : "";
			String denied = !"true".equals(scopes.get(scope)) ? " checked" : "";
			String value = SCOPE.replace("%scope%", scope).replace("%key%", scope).replace("%approved%", approved)
					.replace("%denied%", denied);
			builder.append(value);
		}
		builder.append("</ul>");
		return builder.toString();
	}

	private static String SCOPE = "<button name=\"%key%\" type=\"submit\" value=\"true\">Approve</button><button name=\"%key%\" type=\"submit\" value=\"false\">Cancel</button>";

}
