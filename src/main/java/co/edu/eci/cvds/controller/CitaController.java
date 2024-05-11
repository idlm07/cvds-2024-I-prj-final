package co.edu.eci.cvds.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/Cotizacion")
public class CitaController {

    private static final String LOGIN_PAGE = "Cotizacion/Agendamiento";

    // Manejador para la solicitud GET en /login/Agendamiento
    @GetMapping("Agendamiento")
    public String Agendamiento() {
        return "Cotizacion/Agendamiento"; // Retorna la vista "Agendamiento"
    }

}
