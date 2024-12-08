package br.gov.monet.core.configuration;

import br.gov.monet.core.connection.DatabaseConnection;
import br.gov.monet.query.MonetPersistenceQueryImpl;

public class RepositoryFactory {

	private final DatabaseConnection connection;

    public RepositoryFactory(DatabaseConnection connection) {
        this.connection = connection;
    }

    public <T> T getRepository(Class<T> repositoryClass) {
        try {
            // Supondo que todos os repositórios tenham um construtor que aceita DatabaseConnection
            return repositoryClass.getConstructor(DatabaseConnection.class).newInstance(connection);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar repositório: " + repositoryClass.getSimpleName(), e);
        }
    }
	
}
