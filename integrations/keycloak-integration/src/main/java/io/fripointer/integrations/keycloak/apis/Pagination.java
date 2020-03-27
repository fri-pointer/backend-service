package io.fripointer.integrations.keycloak.apis;

import javax.ws.rs.QueryParam;

public class Pagination {
    
    @QueryParam("first")
    protected Integer offset;
    
    @QueryParam("max")
    protected Integer limit;
    
    public Integer getOffset() {
        return offset;
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
