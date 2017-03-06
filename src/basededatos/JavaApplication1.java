package basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
      
        //pasos de la coneccion a la base de datos
        String user = "root";
        String pass = "";
        String streamm = "jdbc:mysql://localhost:3306/alumno";
        
        Connection c = DriverManager.getConnection(streamm, user, pass);
        
        //grabo un alumno en la base de datos
        /*Alumno p = new Alumno("jose","campos");
        System.out.println(grabarAlumno(c, p));  
        */         
        getAlumnos(c).stream().forEach(System.out::println);
        
        //me muestra todo el campo del id 1
        System.out.println(getAlumno(c, 1));
        
        //si lñe mando un id que no existe me devuelve un null
        System.out.println(getAlumno(c, 2));
        
        System.out.println("--------------------------");
                
        //me muestra la lista de todos los alumno de la base de datos
        System.out.println(getAlumnos(c));
        
        System.out.println("--------------------------");
        
        //actualizar tabla
        Alumno q = new Alumno("jeanpierre","chero");
        modificarAlumno(c,q,3);
            
        getAlumnos(c).stream().forEach(System.out::println);
        


    }
    public static List<Alumno> getAlumnos(Connection c) throws SQLException {
        
        Statement st = c.createStatement();
        List<Alumno> alumnos = new ArrayList();
        ResultSet rs = st.executeQuery("select * from alumnos");
        
        
        while(rs.next()){
            Alumno a = new Alumno(rs.getInt("id"),rs.getString("nombre"), rs.getString("apellido"));
            alumnos.add(a);
        }
        st.close();
        return alumnos;
    }
    
    public static  Alumno getAlumno(Connection c, int id) throws SQLException {
        
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("select * from alumnos where id = " +id);
        Alumno a = null;
        
        if(rs.next()){
            a = new Alumno(rs.getInt("id"),rs.getString("nombre"), rs.getString("apellido"));
           
        }
        st.close();
        return a;
    }
    
    /*public static void grabarAlumno(Connection c, Alumno a) throws SQLException {
    
        Statement st = c.createStatement();
        st.execute("insert into alumnos(id, nombre,apellido)" + 
                "values ("+a.getId()+",'"+a.getNombre()+"','"+a.getApellido()+"')");
        st.close();
                
    }*/
    
    /*public static void grabarAlumno(Connection c, Alumno a) throws SQLException {
        
        Statement st = c.createStatement();
        String insertSql = "insert into alumnos(id, nombre,apellido)" + 
                "values (?,?,?)";
        PreparedStatement ps = c.prepareStatement(insertSql);
        ps.setInt(1,a.getId());
        ps.setString(2,a.getNombre());
        ps.setString(3,a.getApellido());
        ps.executeUpdate();
        ps.close();
                
    }*/
    
    
    //grabar alumnos con el id auto_increment y me retorna
    public static int grabarAlumno(Connection c, Alumno a) throws SQLException {
        
        Statement st = c.createStatement();
        String insertSql = "insert into alumnos(nombre,apellido)" + 
                "values (?,?)";
        PreparedStatement ps = c.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,a.getNombre());
        ps.setString(2,a.getApellido());
        ps.executeUpdate();
        st.close();
        //me retorna el id en que se grabo el alumno
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
        
        
    }
    //Actualizar modo1
    /*public static void modificarAlumno(Connection c, Alumno a, int id) throws SQLException {
        
        Statement st = c.createStatement();
        st.execute("update alumnos set nombre = '"+a.getNombre()+"',"
                + " apellido = '"+a.getApellido()+"'"+" WHERE id = '"+id+"'");
        st.close();
        
    }*/
    
    //actualizar modo2
    public static void modificarAlumno(Connection c, Alumno a, int id) throws SQLException {
        
        Statement st = c.createStatement();        
        String insertSql = "update alumnos set nombre = ?, apellido = ? where id = ?";
        PreparedStatement ps = c.prepareStatement(insertSql);
        ps.setString(1,a.getNombre());
        ps.setString(2,a.getApellido());
        ps.setInt(3, id);
        ps.executeUpdate();
        st.close();
    }
    
    
    
    
    
}
