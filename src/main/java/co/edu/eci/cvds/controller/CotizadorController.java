package co.edu.eci.cvds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cotizador")
public class CotizadorController {
    @GetMapping("")
    public String pagina(){
        return "cotizador/pagina";
    }
    
}
