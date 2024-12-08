package br.gov.monet.core.connection;

import java.sql.*;

/**
 * Class responsible for database connection using JDBC.
 * This class manages the connection parameters and provides a method to establish a connection to the database.
 * 
 * Author: Gabriel Filipy
 * All rights reserved.
 */

public class DatabaseConnection {

	private String url;
    private String username;
    private String password;

    /**
     * Constructor to initialize the connection parameters.
     *
     * @param url      Database URL.
     * @param username Database username.
     * @param password Database password.
     */
    public DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Creates and returns a connection to the database.
     *
     * @return Connection connected to the database.
     * @throws SQLException If an error occurs during the connection.
     */
    public Connection getConnection() throws SQLException {
        // Ensures the driver is loaded automatically in Java 11.
        return DriverManager.getConnection(url, username, password);
    }
	
}
