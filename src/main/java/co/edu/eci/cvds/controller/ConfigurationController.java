package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Configuration;
import co.edu.eci.cvds.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    // Constructor que inyecta el servicio de configuración
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    // Manejador para la solicitud GET en /configuration/example
    @GetMapping("/example")
    public String example(Model model) {
        model.addAttribute("premio", configurationService.getPremio());
        return "example"; // Retorna la vista "example"
    }

    // Manejador para la solicitud GET en /configuration/example/api
    @GetMapping("/topgear/cotizacion")
    public String exampleApi() {
        return "/lista/cotizacionFinal"; // Retorna la vista "example-api"
    }

    // Manejador para la solicitud GET en /configuration/example/api/configurations
    @GetMapping("/example/api/configurations")
    @ResponseBody
    public List<Configuration> exampleApiConfigurations() {
        return configurationService.getAllConfigurations(); // Retorna todas las configuraciones como respuesta JSON
    }

    // Manejador para la solicitud POST en /configuration/example/api/configurations
    @PostMapping("/example/api/configurations")
    @ResponseBody
    public List<Configuration> exampleApiConfigurations(@RequestBody Configuration configuration) {
        configurationService.addConfiguration(configuration); // Agrega una nueva configuración
        return configurationService.getAllConfigurations(); // Retorna todas las configuraciones como respuesta JSON
    }
}
