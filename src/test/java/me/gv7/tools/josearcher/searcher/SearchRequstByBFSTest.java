package me.gv7.tools.josearcher.searcher;

import java.util.List;
import java.util.ArrayList;

import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.test.TestClass;


class SearchRequstByBFSTest {
    public static void main(String[] args) {
        //Thread thread = Thread.currentThread();
        TestClass target =  new TestClass();
        List<Keyword> keys = new ArrayList<>();
        keys.add(new Keyword.Builder().setField_type("entity").build());
        List<Blacklist> blacklists = new ArrayList<>();
        blacklists.add(new Blacklist.Builder().setField_name("parallelLockMap").build());
        SearchRequstByBFS searcher = new SearchRequstByBFS(target,keys);
        //searcher.setMax_search_depth(1000);
        searcher.setIs_debug(true);
        //searcher.setBlacklists(blacklists);
        searcher.searchObject();
    }
}