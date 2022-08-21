package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.Arrays;

public class MainDao {

    public static void main(String[] args) {
//       ---------CREATE USERS---------------
        UserDao userDao = new UserDao();
//        User user1 = new User();
//        user1.setUserName("Jan Kowalski");
//        user1.setEmail("jan@wp.pl");
//        user1.setPassword("trudneHaslo");
//        userDao.create(user1);
//
//        User user2 = new User();
//        user2.setUserName("Jan nowak");
//        user2.setEmail("nowak@wp.pl");
//        user2.setPassword("bardzotrudneHaslo");
//        userDao.create(user2);

//-----------FUNKCJA READ--------------------
//        System.out.println(userDao.read(1));
//        System.out.println(userDao.read(2));
//        System.out.println(userDao.read(3));


//-----------FUNKCJA UPDATE---------------------
        User updateUser = userDao.read(1);
        updateUser.setUserName("Janusz");
        updateUser.setEmail("janusz@wp.pl");
        updateUser.setPassword("nowehaslo");
        userDao.update(updateUser);

//----------FUNKCJA DELETE---------------------
//        userDao.delete(1);

        System.out.println(Arrays.toString(userDao.findAllUsers()));




    }

}
