package io.fripointer.integrations.keycloak.apis;

import javax.ws.rs.QueryParam;

public class Query extends Pagination {
    
    @QueryParam("search")
    private String query;
    
    @QueryParam("briefRepresentation")
    private boolean simple;
    
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public boolean isSimple() {
        return simple;
    }
    
    public void setSimple(boolean simple) {
        this.simple = simple;
    }
}
