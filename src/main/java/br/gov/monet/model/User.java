package br.gov.monet.model;

import br.gov.monet.core.annotation.MonetTable;

@MonetTable("TBL_USER")
public class User {
	
    private Long  id;
    private String name;
    private String email;

    public Long  getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

