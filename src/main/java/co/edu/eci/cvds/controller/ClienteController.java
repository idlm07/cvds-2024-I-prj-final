package co.edu.eci.cvds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint para obtener todos los clientes
    @GetMapping("/")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Endpoint para obtener un cliente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo cliente
    @PostMapping("/")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    // Endpoint para actualizar los datos de un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setCorreo(clienteDetails.getCorreo());
        cliente.setTelefono(clienteDetails.getTelefono());
        cliente.setMarca(clienteDetails.getMarca());
        Cliente clienteActualizado = clienteRepository.save(cliente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    // Endpoint para eliminar un cliente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clienteRepository.delete(cliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
