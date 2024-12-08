package br.gov.monet.query;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import br.gov.monet.core.annotation.MonetTable;
import br.gov.monet.core.connection.DatabaseConnection;
import br.gov.monet.core.utils.MonetPage;
import br.gov.monet.core.utils.MonetPageImpl;
import br.gov.monet.core.utils.MonetPageable;

/**
 * Class responsible for handling generic database queries using JDBC.
 * This class provides methods such as `findById` and `findAll`, which can be used with any type of entity.
 * It dynamically maps entities to the corresponding database tables and executes queries generically.
 * 
 * Author: Gabriel Filipy
 * All rights reserved.
 */

public class MonetPersistenceQueryImpl<T> implements MonetPersistenceQuery<T> {

	private final Class<T> entityClass;
    private final DatabaseConnection connection;

    /**
     * Constructor to initialize entity type and database connection.
     *
     * @param entityClass The class type of the entity.
     * @param connection  The database connection instance.
     */
    public MonetPersistenceQueryImpl(Class<T> entityClass, DatabaseConnection connection) {
        this.entityClass = entityClass;
        this.connection = connection;
    }

    /**
     * Finds an entity by its ID.
     * @param id The ID of the entity.
     * @return The entity object, or null if not found.
     * @throws SQLException If an SQL error occurs.
     */
    public T findById2(Object id) {
    	String tableName = getTableName();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds all entities in the table.
     * @return A list of all entities.
     * @throws SQLException If an SQL error occurs.
     */
    public MonetPage<T> collectionPage(MonetPageable pageable) {
        String tableName = getTableName();
        List<T> resultList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName.toUpperCase() + 
                     " LIMIT ? OFFSET ?";
        System.out.println(sql);

        long total = 0;
        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pageable.getPageSize()); 
            stmt.setInt(2, pageable.getPageNumber() * pageable.getPageSize()); 

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance();
                    for (Field field : entityClass.getDeclaredFields()) {
                        String columnName = field.getName().toLowerCase();
                        Object value;

                        try {
                            value = rs.getObject(columnName);
                        } catch (SQLException e) {
                            System.err.println("Column not found: " + columnName);
                            continue;
                        }

                        field.setAccessible(true);
                        field.set(entity, value);
                    }
                    resultList.add(entity); 
                }
            }

            String countSql = "SELECT COUNT(*) FROM " + tableName.toUpperCase();
            try (PreparedStatement countStmt = conn.prepareStatement(countSql);
                 ResultSet countRs = countStmt.executeQuery()) {
                if (countRs.next()) {
                    total = countRs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new MonetPageImpl<T>(resultList, pageable.getPageNumber(), pageable.getPageSize(), total);

    }

    /**
     * Maps the result set to an entity object.
     * @param rs The result set.
     * @return The mapped entity.
     * @throws SQLException If an SQL error occurs.
     * @throws IllegalAccessException If reflection access fails.
     * @throws InstantiationException If entity instantiation fails.
     */
    private T mapResultSetToEntity(ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);  // Allow access to private fields.
            try {
                field.set(entity, rs.getObject(field.getName()));  // Set the value from the result set.
            } catch (SQLException e) {
                // Handle cases where a field is not present in the result set.
                // Log it or ignore it depending on your needs.
            }
        }

        return entity;
    }
    
    /**
     * Retorna o nome da tabela da anotação @TableName da classe genérica.
     * 
     * @return Nome da tabela
     */
    private String getTableName() {
        // Verifica se a classe genérica tem a anotação @TableName
        if (entityClass.isAnnotationPresent(MonetTable.class)) {
        	MonetTable tableNameAnnotation = entityClass.getAnnotation(MonetTable.class);
            return tableNameAnnotation.value();  // Retorna o valor da anotação
        } else {
            // Caso não tenha a anotação, pode lançar uma exceção ou retornar um nome padrão
            throw new RuntimeException("Table name annotation not found for class: " + entityClass.getName());
        }
    }
	
}
