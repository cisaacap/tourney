package main.java.com.cisaacap.tourney.model.deporte;

public class Deportes {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idDeporte;
    String nombreDeporte;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */
    public Deportes(int idDeporte, String nombreDeporte) {
        this.idDeporte = idDeporte;
        this.nombreDeporte = nombreDeporte;
    }

    /*  ======================================
            GETTERS&SETTER
          ====================================== */
    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getNombreDeporte() {
        return nombreDeporte;
    }

    public void setNombreDeporte(String nombreDeporte) {
        this.nombreDeporte = nombreDeporte;
    }

}
