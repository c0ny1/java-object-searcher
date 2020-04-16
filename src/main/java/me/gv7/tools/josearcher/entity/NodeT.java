package me.gv7.tools.josearcher.entity;


public class NodeT {
    private String chain;
    private String field_name;
    private Object field_object;
    private int current_depth;

    public NodeT(Builder builder){
        this.chain = builder.chain;
        this.field_name = builder.field_name;
        this.field_object = builder.field_object;
        this.current_depth = builder.current_depth;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public Object getField_object() {
        return field_object;
    }

    public void setField_object(Object field_object) {
        this.field_object = field_object;
    }

    public int getCurrent_depth() {
        return current_depth;
    }

    public void setCurrent_depth(int current_depth) {
        this.current_depth = current_depth;
    }

    public static class Builder{
        private String chain;
        private String field_name;
        private Object field_object;
        private int current_depth;

        public Builder setChain(String chain) {
            this.chain = chain;
            return this;
        }

        public Builder setField_name(String field_name) {
            this.field_name = field_name;
            return this;
        }

        public Builder setField_object(Object field_object) {
            this.field_object = field_object;
            return this;
        }

        public Builder setCurrent_depth(int current_depth) {
            this.current_depth = current_depth;
            return this;
        }

        public NodeT build(){
            return new NodeT(this);
        }
    }
}
