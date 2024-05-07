package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
    private final VehiculoService vehiculoService;

    @Autowired
    public HomeController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;

    }

    @GetMapping("")
    public String home(Model model, @RequestParam(required = false) String marca, @RequestParam(required = false) String modelo) {
        List<Vehiculo> vehiculosList = vehiculoService.getVehiculos();
        HashSet<String> marcas = new HashSet<>();
        HashSet<String> modelos = new HashSet<>();
        HashSet<String> vehicleYears = new HashSet<>();
        for(Vehiculo vehiculo : vehiculosList) {
            marcas.add(vehiculo.getMarca());
        }
        if(marca == null) marca = "";
        else{
            marcas.remove(marca);
            vehiculosList = vehiculoService.getVehiculosByMarca(marca);
            for(Vehiculo vehiculo : vehiculosList) {
                modelos.add(vehiculo.getModel());
            }
        }
        if(marca.isEmpty() || modelo == null) modelo = "";
        else {
            modelos.remove(modelo);
            vehiculosList = vehiculoService.getVehiculosByMarcaModelo(marca,modelo);
            for(Vehiculo vehiculo : vehiculosList) {
                vehicleYears.add(vehiculo.getYearVehicle());
            }
        }
        model.addAttribute("marca", marca);
        model.addAttribute("modelo", modelo);
        model.addAttribute("marcas", marcas);
        model.addAttribute("modelos", modelos);
        model.addAttribute("vehicleYears", vehicleYears);
        return "index";
    }






}
