package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User findUser(String car_model, int car_series) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("FROM User user LEFT OUTER JOIN FETCH user.car WHERE user.car.model=:model AND user.car.series=:series")
                .setParameter("model", car_model).setParameter("series", car_series);
        return (User) query.getSingleResult();
    }

}
