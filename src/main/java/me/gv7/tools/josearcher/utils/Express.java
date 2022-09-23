package me.gv7.tools.josearcher.utils;

class Express {
    private String field;

    public String getField() {
        return field;
    }

    public String getClassName() {
        return className;
    }

    private String className;

    public Express(String data) {
        String[] datas = data.split(" = ");
        this.field = datas[0].trim();
        this.className = datas[1].trim();
    }
}
