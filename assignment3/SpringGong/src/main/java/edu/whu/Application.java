package edu.whu;

import edu.whu.dao.BookDao;
import edu.whu.service.BookService;

public class Application {

    // 没有去解决配置依赖的问题，只能将被依赖的bean放在前面

    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext("applicationContext.xml");
        BookService bookService = (BookService) context.getBean("bookService");
        BookDao bookDao = (BookDao) context.getBean("bookDao");
        bookService.save();
        bookDao.save();
    }
}
