package com.example.novelapplication.sqlite;

import android.content.Context;
import android.os.AsyncTask;

public class OpDatabase {
    private static RoomDao roomDao;
    public static void init(Context context){
        roomDao = RoomDatabase.get(context).getRoomDao();
    }
    private OpDatabase(){}
    private  static OpDatabase opDatabase;
    public static OpDatabase get(){
        if (opDatabase == null){
            synchronized (OpDatabase.class){
                if (opDatabase == null){
                    opDatabase = new OpDatabase();
                }
            }
        }
        return opDatabase;
    }

    //注册一条用户信息
    public void addUser(User user){
        new AddUserTask(roomDao).execute(user);
    }
    static class AddUserTask extends AsyncTask<User, Void, Void> {
        private RoomDao roomDao;
        public AddUserTask(RoomDao roomDao){
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            roomDao.addUser(users);
            return null;
        }
    }

    //根据用户名查询一条信息
    public void findUserByUsername(String username, FindUserTask.OnLoginListener onLoginListener){
        new FindUserTask(roomDao,onLoginListener).execute(username);
    }
    public static class FindUserTask extends AsyncTask<String, Void,User> {
        private RoomDao roomDao;
        private OnLoginListener onLoginListener;
        public FindUserTask(RoomDao roomDao,OnLoginListener onLoginListener){
            this.roomDao = roomDao;
            this.onLoginListener =onLoginListener;
        }

        @Override
        protected User doInBackground(String... username) {
            return roomDao.findUser(username);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            onLoginListener.done(user);
        }

        public interface OnLoginListener{
            void done(User user);
        }
    }

    //注册一条用户信息
    public void updateUser(User user){
        new UpdateUserTask(roomDao).execute(user);
    }
    static class UpdateUserTask extends AsyncTask<User, Void, Void> {
        private RoomDao roomDao;
        public UpdateUserTask(RoomDao roomDao){
            this.roomDao = roomDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            roomDao.updateUser(users);
            return null;
        }
    }
}
