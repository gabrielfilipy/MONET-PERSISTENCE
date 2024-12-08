package br.gov.monet.core.bean;

import br.gov.monet.core.configuration.RepositoryFactory;
import br.gov.monet.core.connection.DatabaseConnection;

//@Component
public class MonetConfigResolver {

	DatabaseConnection connection;
    public RepositoryFactory repositoryFactory;

    // Construtor que será chamado automaticamente ao instanciar a classe
    public MonetConfigResolver() {
        // Inicializar a conexão com o banco de dados
        connection = new DatabaseConnection(
            "jdbc:postgresql://localhost:5432/Monet2", 
            "postgres",                          
            "admin"                          
        );

        repositoryFactory = new RepositoryFactory(connection);

        System.out.println("MonetConfigResolver foi instanciado e configurado!");
    }
	
}
