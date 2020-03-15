package meru.application.designer.builder;

public interface EntityBuilder<XMLTYPE, O> {
    public O build(XMLTYPE xmlTYPE);
}