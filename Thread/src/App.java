import java.util.ArrayList;

public class App{
    public static void main(String[] args) throws Exception{
    
        int MaxConexiones = 150; //Empezando en 0
        int Espera = 50; //Espera entre el inicio de cada hilo

        ArrayList<HilosConx> Conexiones = new ArrayList<HilosConx>();

        long totalTiempo = System.nanoTime();

        for (int numConexion = 0; numConexion < MaxConexiones; numConexion++){
            HilosConx Hilos = new HilosConx(numConexion);
            Conexiones.add(numConexion, Hilos);
        }

        for (HilosConx Hilos : Conexiones){
            Hilos.start();
            Thread.sleep(Espera); // Tiempo de espera al iniciar cada hilo
        }

        System.out.println("Todos los hilos fueron conectados. Total de tiempo fue de " + totalTiempo/1000000000 + " s."); 
        System.exit(0);
    }
}