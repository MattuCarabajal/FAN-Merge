package Pages;

import java.sql.*;
<<<<<<< Updated upstream
import java.util.*;
=======
import java.util.ResourceBundle;
>>>>>>> Stashed changes


public class remoteDB {
	
	/*static {
		try {
<<<<<<< Updated upstream
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Correctly loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
=======
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
>>>>>>> Stashed changes
			System.err.println("Unable to load MySQL Driver");
		}
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Connection cSession;
		
<<<<<<< Updated upstream
		System.out.println("1");
		
		try {
			System.out.println("2");
			String sHost = "jdbc:mysql://10.78.100.41:1740/FAB_CAC_FACT_DIGITAL_DOCUMENT";
			String sUser = "A000132";
			String sPass = "dskore154_";
			//String sSID = "sas";
			
			cSession = DriverManager.getConnection(sHost, sUser, sPass);
			
			System.out.println("Connected");
			
			Statement stmt=cSession.createStatement();
			ResultSet rs = stmt.executeQuery("");
			
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
			
			cSession.close();
=======
		try {
			String sHost = "jdbc:mysql://10.78.100.41:1740/FAB_CAC_FACT_DIGITAL_DOCUMENT";
			String sUser = "A000132";
			String sPass = "dskore154_";
			
			
			cSession = DriverManager.getConnection(sHost, sUser, sPass);
			
			Statement stmt=cSession.createStatement();
>>>>>>> Stashed changes
		}
		catch (SQLException sqleError) {
			System.out.println("SQL Connection error with the following message: " + sqleError.getMessage());
		}
		
		
	}*/
	
<<<<<<< Updated upstream
	/*private static Connection con=null;
	private static Connection cnx;
	private static String bd = "FAB_CAC_FACT_DIGITAL_DOCUMENT";
	private static String user = "A000132";
	//private static String password = "admin";
	private static String password = "dskore154_";
	//private static String host = "localhost:3306";
	private static String host = "10.78.100.41:1740";
=======
	private static Connection con=null;
	private static Connection cnx;
	private static String bd = "login";
	private static String user = "root";
	//private static String password = "admin";
	private static String password = "adrian";
	//private static String host = "localhost:3306";
	private static String host = "127.0.0.1:3306";
>>>>>>> Stashed changes
	private static String server = "jdbc:mysql://"+host+"/"+bd;
	
	public static Connection getConnection() {
		try	{
			if( con == null ) {
				// con esto determinamos cuando finalize el programa
				Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
				ResourceBundle rb  =ResourceBundle.getBundle("conexion.bruno");
				String driver = rb.getString("driver");
				String url = rb.getString("url");
				String user = rb.getString("user");
				String password = rb.getString("password");
				String bd = rb.getString("bd");
				Class.forName(driver);
				con = DriverManager.getConnection(url, user, password);
			}
			//System.out.println("conectado");
			return con;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException("Error al crear la conexion",ex);
		}
	}

	static class MiShDwnHook extends Thread {
		// justo antes de finalizar el programa la JVM invocara a este metodo donde podemos cerrar la conexion
		public void run() {
			try	{
				Connection con = remoteDB.getConnection();
				con.close();
			}
			catch( Exception ex ) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
<<<<<<< Updated upstream
	}*/
	
	private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL = "jdbc:mysql://10.78.100.41:1740/FAB_CAC_FACT_DIGITAL_DOCUMENT";
    private static final String DEFAULT_USERNAME = "A000132";
    private static final String DEFAULT_PASSWORD = "dskore154_";

    public static void main(String[] args) {
        long begTime = System.currentTimeMillis();

        String driver = ((args.length > 0) ? args[0] : DEFAULT_DRIVER);
        String url = ((args.length > 1) ? args[1] : DEFAULT_URL);
        String username = ((args.length > 2) ? args[2] : DEFAULT_USERNAME);
        String password = ((args.length > 3) ? args[3] : DEFAULT_PASSWORD);

        Connection connection = null;

        try {
            connection = createConnection(driver, url, username, password);
            System.out.println("Connected");
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println(meta.getDatabaseProductName());
            System.out.println(meta.getDatabaseProductVersion());

            String sqlQuery = "SELECT PERSON_ID, FIRST_NAME, LAST_NAME FROM PERSON ORDER BY LAST_NAME";
            System.out.println("before insert: " + query(connection, sqlQuery, Collections.EMPTY_LIST));

            connection.setAutoCommit(false);
            String sqlUpdate = "INSERT INTO PERSON(FIRST_NAME, LAST_NAME) VALUES(?,?)";
            List parameters = Arrays.asList("Foo", "Bar");
            int numRowsUpdated = update(connection, sqlUpdate, parameters);
            connection.commit();

            System.out.println("# rows inserted: " + numRowsUpdated);
            System.out.println("after insert: " + query(connection, sqlQuery, Collections.EMPTY_LIST));
        } catch (Exception e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            close(connection);
            long endTime = System.currentTimeMillis();
            System.out.println("wall time: " + (endTime - begTime) + " ms");
        }
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
    	Class.forName(driver);
        if ((username == null) || (password == null) || (username.trim().length() == 0) || (password.trim().length() == 0)) {
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, username, password);
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> map(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
        } finally {
            close(rs);
        }
        return results;
    }

    public static List<Map<String, Object>> query(Connection connection, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> results = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            rs = ps.executeQuery();
            results = map(rs);
        } finally {
            close(rs);
            close(ps);
        }
        return results;
    }

    public static int update(Connection connection, String sql, List<Object> parameters) throws SQLException {
        int numRowsUpdated = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            numRowsUpdated = ps.executeUpdate();
        } finally {
            close(ps);
        }
        return numRowsUpdated;
    }
=======
	}
>>>>>>> Stashed changes

}