package me.gv7.tools.josearcher.entity;


class BlacklistTest {
    public static void main(String[] args) {
        Blacklist blacklist = new Blacklist.Builder()
                .setField_name("map")
                .setField_type("HashMap")
                .setField_value("test")
                .build();
        System.out.println(blacklist);
    }
}