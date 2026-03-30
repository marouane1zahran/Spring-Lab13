package com.example.auth.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SsoController {

    // Page d'accueil publique
    @GetMapping("/")
    public String showHomePage() {
        return "accueil";
    }

    // Page protégée (Tableau de bord)
    @GetMapping("/dashboard")
    public String showDashboard(Model uiModel, @AuthenticationPrincipal OAuth2User authenticatedUser) {

        // Extraction des informations fournies par Google/Keycloak via le token
        String fullName = authenticatedUser.getAttribute("name");
        String emailAddress = authenticatedUser.getAttribute("email");
        String avatarUrl = authenticatedUser.getAttribute("picture");

        // Ajout des variables au modèle Thymeleaf
        uiModel.addAttribute("userName", fullName);
        uiModel.addAttribute("userEmail", emailAddress);
        uiModel.addAttribute("userAvatar", avatarUrl);

        return "tableau-de-bord";
    }
}