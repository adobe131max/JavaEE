package edu.whu.dao.impl;

import edu.whu.dao.BookDao;

public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
        System.out.println("BookDao save ...");
    }
}
