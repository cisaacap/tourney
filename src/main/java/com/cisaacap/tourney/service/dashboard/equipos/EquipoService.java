package main.java.com.cisaacap.tourney.service.dashboard.equipos;

import main.java.com.cisaacap.tourney.repository.dashboard.equipos.EquipoRepository;

public class EquipoService {

    EquipoRepository teamRepo;
    
    public EquipoService(EquipoRepository teamRepo) {
        this.teamRepo = teamRepo;
    }
    
    public boolean save(String nombreEquipo, String ciudad) {
        return teamRepo.save(nombreEquipo, ciudad);
    }
    
}
