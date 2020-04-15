package me.gv7.tools.josearcher.entity;


class KeywordTest {
    public static void main(String[] args) {
        Keyword key = new Keyword.Builder().setField_name("entity").setField_value("111").setField_type("wewe").build();
        System.out.println(key);
    }
}