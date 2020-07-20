package app.erp;

import app.domain.AppEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bus_app_error")
public class AppError extends AppEntity {


    @Column(name="source", nullable=false)
    private java.lang.String source;

    @Column(name="context")
    private java.lang.String context;

    @Column(name="error", nullable=false)
    private java.lang.String error;

    public java.lang.String getSource() {
        
        return source;
    }

    public void setSource(java.lang.String source) {

        this.source = source;
    }

    public java.lang.String getContext() {
        
        return context;
    }

    public void setContext(java.lang.String context) {

        this.context = context;
    }

    public java.lang.String getError() {
        
        return error;
    }

    public void setError(java.lang.String error) {

        this.error = error;
    }
}
