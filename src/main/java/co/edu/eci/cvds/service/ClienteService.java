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
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }
    public Cliente getCLiente(String correo){
        return clienteRepository.findByCorreo(correo).get(0);
    }

    /**
     * Devuelve una lista de todos los clientes registrados en la base de datos.
     * @return lista de todos los clientes en la base de datos
     */
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }



}
