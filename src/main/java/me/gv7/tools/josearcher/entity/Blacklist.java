package me.gv7.tools.josearcher.entity;

public class Blacklist {
    private String field_name;
    private String field_value;
    private String field_type;

    public Blacklist() {
    }

    public Blacklist(Builder builder) {
        this.field_name = builder.field_name;
        this.field_value = builder.field_value;
        this.field_type = builder.field_type;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "field_name='" + field_name + '\'' +
                ", field_value='" + field_value + '\'' +
                ", field_type='" + field_type + '\'' +
                '}';
    }

    public static class Builder {
        private String field_name;
        private String field_value;
        private String field_type;

        public Builder setField_name(String field_name) {
            this.field_name = field_name;
            return this;
        }

        public Builder setField_value(String field_value) {
            this.field_value = field_value;
            return this;
        }

        public Builder setField_type(String field_type) {
            this.field_type = field_type;
            return this;
        }

        public Blacklist build() {
            return new Blacklist(this);
        }
    }
}
