package com.example.novelapplication.dao.db;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.novelapplication.model.Book;
import com.example.novelapplication.model.BookShelfItem;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class BookShelfItemDb {



//    public void createBookShelfItemTable(){
//        Connector.getDatabase();
//    }
//    public void addBookShelfItem(int userId, Book book){
//
//        BookShelfItem bookShelfItem=new BookShelfItem(1,userId,book.get_id(),book.getTitle(),
//                "2020-7-6 8:10",book.getCover(),book.getLastChapter(),
//        book.getUpdated());
//        bookShelfItem.save();
//    }
//    public void removeBookShelfItem(int bookShelfItemId){
//        DataSupport.delete(BookShelfItem.class,bookShelfItemId);
//
//    }
//    @SuppressLint("LongLogTag")
//    public BookShelfItem selectBookShelfItemByBookIdAndUserId(String  bookId,int userId){
//        List<BookShelfItem> bookShelfItemList= DataSupport.where("bookId=? and userId=?",bookId,userId+"").limit(1).find(BookShelfItem.class);
//       // Log.d("bookShelfItemList.size()",bookShelfItemList.size()+"");
//        if (bookShelfItemList.size()==1){
//            return bookShelfItemList.get(0);
//        } else{
//            return null;
//        }
//    }
//    public List<BookShelfItem> selectBookShelfItemByUserId(int userId){
//        List<BookShelfItem> bookShelfItemList= DataSupport.where("userId=?",userId+"").limit(1).find(BookShelfItem.class);
//       return  bookShelfItemList;
//    }

}
