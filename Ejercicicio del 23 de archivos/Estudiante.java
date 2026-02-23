import java.io.Serializable;

public class Estudiante implements Serializable{

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int matricula;
    private double promedio;


    public Estudiante (String nombre, int matricula, double promedio){

        this.nombre = nombre;
        this.matricula = matricula;
        this.promedio = promedio;



    }

    public String getNombre(){
        return nombre;

    }


    public int getMatricula(){
        return matricula;
    }

    public double getPromedio(){
        return promedio;
    }

    @Override
    public String toString(){
        return "Matricula:" + matricula + " | nombre: "+ nombre + " | promedio: " + promedio;
    }

    
}
