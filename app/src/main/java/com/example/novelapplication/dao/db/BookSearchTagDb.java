package com.example.novelapplication.dao.db;

import android.util.Log;

import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookSearchTag;
import com.example.novelapplication.model.BookShelfItem;

import org.litepal.crud.DataSupport;

import java.util.List;


public class BookSearchTagDb {
    public void addBookSearchTag(String tag){

        BookSearchTag bookSearchTag=new BookSearchTag();
        bookSearchTag.setName(tag);
        bookSearchTag.save();
    }
    public void deleteBookSearchTag(int id){
        DataSupport.delete(BookSearchTag.class,id);
    }
    public void deleteAllBookSearchTag(){
        DataSupport.deleteAll(BookSearchTag.class);
    }
    public List<BookSearchTag> queryAllBookSearchTag(){
        return  DataSupport.findAll(BookSearchTag.class);
    }


    public BookSearchTag queryBookSearchTagByName(String name){
        BookSearchTag bookSearchTag=DataSupport.where("name=?",name).findFirst(BookSearchTag.class);
        //Log.d("bookSearchTag",bookSearchTag.toString());
        return  bookSearchTag;
    }
}
