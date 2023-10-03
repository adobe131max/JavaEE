package edu.whu.service.impl;

import edu.whu.dao.BookDao;
import edu.whu.service.BookService;

public class BookServiceImpl implements BookService {

    public BookDao bookDao;

    @Override
    public void save() {
        System.out.println("BookService save ...");
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
