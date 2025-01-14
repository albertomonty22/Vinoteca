package gui;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Properties;

public class Modelo {

    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    //objeto conexion
    private Connection conexion;

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public Modelo() {
        getPropValues();
    }

    void conectar() {
        try {
            conexion= DriverManager.getConnection("jdbc:mysql://"+ip+":3306/vinoteca",user,password);
        } catch (SQLException e) {
            //sino conecta voy a cargar el script directamente
            //voy a llamar a un metodo que se llama leerFichero
            try {
                conexion=DriverManager.getConnection("jdbc:mysql://"+ip+":3306",user,password);
                PreparedStatement statement=null;
                String code = leerFichero();
                String[] query =code.split("--");
                for (String aQuery:query) {
                    statement=conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement!=null;
                statement.close();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void desconectar() {
        try {
            conexion.close();
            conexion=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basededatos_java.sql"));
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea=reader.readLine())!=null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    void insertarEnologo(String nombre, String apellidos, LocalDate fechaNacimiento,String bodega) {
        String sentenciaSql="INSERT INTO enologos (nombre,apellidos, fechanacimiento,idbodega)" +
                "VALUES (?,?,?,?) ";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,nombre);
            sentencia.setString(2,apellidos);
            sentencia.setDate(3, Date.valueOf(fechaNacimiento));
            sentencia.setString(4,bodega.substring(0,1));
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void insertarBodega(String bodega, String email,String telefono,String direccion,String denominacionOrigen) {
        String sentenciaSql="INSERT INTO bodegas (nombre,email, telefono,direccion,denominacion_origen)" +
                "VALUES (?,?,?,?,?) ";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,bodega);
            sentencia.setString(2,email);
            sentencia.setString(3, telefono);
            sentencia.setString(4,direccion);
            sentencia.setString(5,denominacionOrigen);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void insertarVino(String nombre,String enologo,String bodega,String tipoVino,String año) {
        String sentenciaSql="INSERT INTO vinos (nombre,idenologo,idbodega,tipo_vino,anio)" +
                "VALUES (?,?,?,?,?)";
        PreparedStatement sentencia=null;

        int idbodega = Integer.parseInt(bodega.split(" ")[0]);
        int idenologo =  Integer.parseInt(enologo.split(" ")[0]);

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,nombre);
            sentencia.setInt(2,idenologo);
            sentencia.setInt(3, idbodega);
            sentencia.setString(4,tipoVino);
            sentencia.setString(5,año);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void modificarEnologo(String nombre, String apellidos, LocalDate fechaNacimiento, String bodega, Integer idenologo) {
        String sentenciaSql="UPDATE enologos SET nombre=?,apellidos=?,fechanacimiento=?,idbodega=?" +
                "WHERE idenologo=?";
        PreparedStatement sentencia=null;
        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,nombre);
            sentencia.setString(2,apellidos);
            sentencia.setDate(3, Date.valueOf(fechaNacimiento));
            sentencia.setString(4,bodega.substring(0,1));
            sentencia.setInt(5, idenologo);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarBodega(String bodega, String email, String telefono, String direccion, String denominacionOrigen, int idbodega) {
        String sentenciaSql="UPDATE bodegas SET nombre=?,email=?,telefono=?,direccion=?,denominacion_origen=?" +
                "WHERE idbodega=? ";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,bodega);
            sentencia.setString(2,email);
            sentencia.setString(3, telefono);
            sentencia.setString(4,direccion);
            sentencia.setString(5,denominacionOrigen);
            sentencia.setInt(6,idbodega);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarVino(String nombre,String enologo,String bodega,String tipoVino,String año,int idvino) {
        String sentenciaSql="UPDATE vinos SET nombre=?,idenologo=?,idbodega=?,tipo_vino=?,anio=?" +
                "WHERE idvino=?";
        PreparedStatement sentencia=null;

        int idbodega = Integer.parseInt(bodega.split(" ")[0]);
        int idenologo =  Integer.parseInt(enologo.split(" ")[0]);

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,nombre);
            sentencia.setInt(2,idenologo);
            sentencia.setInt(3, idbodega);
            sentencia.setString(4,tipoVino);
            sentencia.setString(5,año);
            sentencia.setInt(6,idvino);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void eliminarEnologo(int idenologo) {
        String sentenciaSql = "DELETE FROM enologos WHERE idenologo=?";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idenologo);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarBodega(int idbodega) {
        String sentenciaSql="DELETE FROM bodegas WHERE idbodega=? ";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idbodega);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarVino(int idvino) {
        String sentenciaSql="DELETE FROM vinos WHERE idvino=? ";
        PreparedStatement sentencia=null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idvino);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia!=null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ResultSet consultarEnologo() throws SQLException {
        String sentenciaSql="SELECT idenologo AS 'ID'," +
                "nombre AS 'Nombre'," +
                "apellidos AS 'Apellidos'," +
                "fechanacimiento AS 'Fecha de nacimiento'," +
                "idbodega AS 'Bodega de trabajo'" +
                "FROM enologos";
        PreparedStatement sentencia=null;
        ResultSet resultado=null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado=sentencia.executeQuery();
        return resultado;
    }

    ResultSet consultarBodega() throws SQLException {
        String sentenciaSql = "SELECT idbodega as 'ID', " +
                "nombre as 'Nombre bodega', " +
                "email as 'Email', " +
                "telefono as 'Teléfono', " +
                "direccion as 'Dirección', " +
                "denominacion_origen as 'D.O.' " +
                "FROM bodegas";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    ResultSet consultarVinos() throws SQLException {
        String sentenciaSql = "SELECT b.idvino AS 'ID', " +
                "b.nombre AS 'Nombre', " +
                "concat(a.idenologo, ' - ', a.apellidos, ',', a.nombre) AS 'Enólogo', " +
                "concat(e.idbodega, ' - ', e.nombre) AS 'Bodega', " + // Cambié 'e.bodega' por 'e.nombre' basado en tu tabla
                "b.tipo_vino AS 'Tipo de Vino', " +
                "b.anio AS 'Año' " + // Eliminada la coma extra aquí
                "FROM vinos AS b " +
                "INNER JOIN bodegas AS e " +
                "ON e.idbodega = b.idbodega " +
                "INNER JOIN enologos AS a " +
                "ON a.idenologo = b.idenologo";
        PreparedStatement sentencia=null;
        ResultSet resultado=null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado=sentencia.executeQuery();
        return resultado;
    }

    boolean vinoYaExiste(String nombre) {
        String consulta="SELECT existeNombreVino(?)";
        PreparedStatement function;
        boolean vinoExists=false;
        try {
            function=conexion.prepareStatement(consulta);
            function.setString(1,nombre);
            ResultSet rs =function.executeQuery();
            rs.next();
            vinoExists=rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vinoExists;
    }

    boolean bodegaNombreYaExiste(String nombre) {
        String consulta="SELECT existeNombreBodega(?)";
        PreparedStatement function;
        boolean nombreBodegaExists=false;
        try {
            function=conexion.prepareStatement(consulta);
            function.setString(1,nombre);
            ResultSet rs =function.executeQuery();
            rs.next();
            nombreBodegaExists=rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreBodegaExists;
    }

    boolean enologoNombreYaExiste(String nombre, String apellidos) {
        String nombreCompleto=apellidos+", "+nombre;
        String consulta="SELECT existeNombreEnologo(?)";
        PreparedStatement function;
        boolean enologoNombreExists=false;
        try {
            function=conexion.prepareStatement(consulta);
            function.setString(1,nombreCompleto);
            ResultSet rs =function.executeQuery();
            rs.next();
            enologoNombreExists=rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enologoNombreExists;
    }

    private void getPropValues() {
        //cargar de fichero los datos de ip,user,passs,admin
        //controlo excepcion y cierro recursos en el finally
        InputStream inputStream=null;
        Properties prop = new Properties();
        try {
            //nombre fichero
            String propFileName="config.properties";
            inputStream = new FileInputStream(propFileName);
            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password=prop.getProperty("pass");
            adminPassword=prop.getProperty("admin");
        } catch (IOException e) {
            System.out.println("Excepcion "+e);
        } finally {
            //los recursos se cierran en el finally porque se ejecuta siempre
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void setPropValues(String ip,String user,String pass,String adminPass) {
        //controlo excepcion
        try {
            Properties prop = new Properties();
            prop.setProperty("ip",ip);
            prop.setProperty("user",user);
            prop.setProperty("pass",pass);
            prop.setProperty("admin",adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ip=ip;
        this.user=user;
        this.password=pass;
        this.adminPassword=adminPass;
    }
}
