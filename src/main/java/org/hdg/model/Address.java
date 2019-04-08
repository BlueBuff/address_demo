package org.hdg.model;

import java.util.List;
import java.util.Objects;

/**
 * @auther: huangxiaojun
 * @date: 2019/04/08 14:10
 */
public class Address implements Cloneable{
    private int id;
    private String name;
    private int parent;
    private List<Address> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<Address> getChild() {
        return child;
    }

    public void setChild(List<Address> child) {
        this.child = child;
    }

    public Address(){

    }

    public Address(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.parent = builder.parent;
        this.child = builder.child;
    }


    public static class Builder{
        private int id;
        private String name;
        private int parent;
        private List<Address> child;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setParent(int parent) {
            this.parent = parent;
            return this;
        }

        public Builder setChild(List<Address> child) {
            this.child = child;
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getId() == address.getId() &&
                getParent() == address.getParent() &&
                Objects.equals(getName(), address.getName()) &&
                Objects.equals(getChild(), address.getChild());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getParent(), getChild());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", child=" + child +
                '}';
    }
}
