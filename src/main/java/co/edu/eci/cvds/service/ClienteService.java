package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository ClienteRepository) {
        this.clienteRepository = ClienteRepository;
    }

    public Cliente addConfiguration(Cliente Cliente) {
        return clienteRepository.save(Cliente);
    }

    public Cliente getConfiguration(int id) {
        return clienteRepository.findBycedula(id).get(1);
    }

    public List<Cliente> getAllCliente() {
        return clienteRepository.findAll();
    }

    public Cliente updateCliente(Cliente Cliente) {
        if (clienteRepository.findBycedula(Cliente.getCedula()).size() == 0) {
            return clienteRepository.save(Cliente);
        }
        return null;
    }

    public void deleteCliente(int id) {
        clienteRepository.deleteById(id);
    }
}
