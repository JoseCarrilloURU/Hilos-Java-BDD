package SQLLib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BDConx{
    private Connection connection;
    public ArrayList<HashMap<String,Object>> resAL = new ArrayList<>();

    private static int cont = 1;
    public int num;
    public static long elapsed, start;
    private int conx;
    public ResultSet rSet;
    public boolean threwError = false;

    private String db_name, db_user, db_password;

    public BDConx (String db_name, String db_user, String db_password, int num){
        this.num = num;
        this.db_name = db_name;
        this.db_user = db_user;
        this.db_password = db_password;
    }

    public synchronized boolean Connect(){
        try{
            Contador();
            String urlDriver = "jdbc:postgresql://localhost:5432/" + db_name;
            Class.forName("org.postgresql.Driver");
            start = System.nanoTime();
            System.out.println("Hilo #" + num + " conectando... Es el " + conx + "°");
            this.connection = DriverManager.getConnection(urlDriver, db_user, db_password);
            return true;
        } catch (Exception e){
            System.out.println(e);
            errorSalida(num);
            threwError = true;
            return false;
        }
        
    }
    private synchronized static void errorSalida(int num){
            System.out.println("Error ocurrido en la " + num + "° conexión.");
            System.exit(1);
        }

    private synchronized void Contador() {
            this.conx = cont;
            cont++;
        }

    public ResultSet QueryFromString(String query){
        try{
            Statement st = this.connection.createStatement();
            ResultSet resSet = st.executeQuery(query);

            ResultSetMetaData md = resSet.getMetaData();
            int columns = md.getColumnCount();
            
            while (resSet.next()){
                HashMap<String, Object> row = new HashMap<String, Object>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), resSet.getObject(i));
                }
                resAL.add(row); 
            }
            
            return resSet;

        } catch (Exception e){
            System.out.println(e);

            return rSet;
        }
    }
}

