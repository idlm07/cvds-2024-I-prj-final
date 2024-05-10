package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionService {
    private final CotizacionRepository cotizacionRepository;

    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    public Cotizacion addConfiguration(Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }

    public Cotizacion getConfiguration(int numCotizacion) {
        return cotizacionRepository.findByNumCotizacion(numCotizacion).get(1);
    }

    public List<Cotizacion> getAllCotizacion() {
        return cotizacionRepository.findAll();
    }

    public Cotizacion updateCotizacion(Cotizacion cotizacion) {
        if (cotizacionRepository.findByNumCotizacion(cotizacion.getNumCotizacion()).size() == 0) {
            return cotizacionRepository.save(cotizacion);
        }
        return null;
    }

    public void deleteCotizacion(int numCotizacion) {
        cotizacionRepository.deleteById(numCotizacion);
    }

}
