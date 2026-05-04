package org.example.domain.models;

public abstract class AggregateRoot<T> {
    protected  T id;

    public void setId(T id) {
        this.id = id;
    }
}
