package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}
