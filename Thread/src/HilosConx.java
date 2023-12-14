import SQLLib.BDConx;

public class HilosConx extends Thread{
    public static int conectados = 1;
    public int num;
    public boolean error = false;
    BDConx conx;


    public HilosConx(int num){
        super();
        this.num=num;
    }

    @Override
    public synchronized void run(){
        try{
            this.conx = new BDConx(
            "basededatos", 
            "postgres",
            "postgres",
            num);
            conx.Connect();
            Conectado(num);
            ExcQuery();

        } catch (Error e){
            System.out.println(e);
            error = true;
        }
    }

    private synchronized static void Conectado(int num){
        BDConx.elapsed = System.nanoTime()-BDConx.start;
        System.out.println("\nHilo " + num + " conectado. Fue la conexión #" + conectados + " y tomó " + BDConx.elapsed/1000000  +" ms. \n");
        conectados++;
    }

        private void ExcQuery(){
        
            conx.QueryFromString("SELECT modelo FROM autos");
            try{
                Thread.sleep(500); // Espera después del Query
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

}