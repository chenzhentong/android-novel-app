package com.example.novelapplication.dao.db;
import android.util.Log;
import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookViewRecord;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import java.util.List;

public class BookViewRecordDb {

    //创建书籍浏览表
    public void createBookViewTable(){
        Connector.getDatabase();
    }
    //添加
    public void addBookViewRecord(BookViewRecord bookViewRecord){


        bookViewRecord.save();

    }
    //删除书籍浏览信息
    public void deleteAllBookViewRecords(){
        DataSupport.deleteAll(BookViewRecord.class);
    }
    //更新书籍浏览信息
    public void updateBookViewRecord(){
        BookViewRecord bookViewRecord=new BookViewRecord();
        bookViewRecord.setViewDate("2020-7-3 11:33");
        bookViewRecord.updateAll("id=?","1");
    }
    //查询书籍浏览信息
    public  BookViewRecord queryBookViewRecordByBookId(String bookId){
        List<BookViewRecord> bookViewRecordList=DataSupport.where("bookId=?",bookId).find(BookViewRecord.class);
        if (bookViewRecordList.size()==1){
            return bookViewRecordList.get(0);
        } else {
            return null;
        }
    }
    public List<BookViewRecord> queryAllBookViewRecord(){
        return DataSupport.findAll(BookViewRecord.class);

    }

}
