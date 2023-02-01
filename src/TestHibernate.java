import hibernate.UserHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
        UserHib userHib = new UserHib(entityManagerFactory);
//    User user = new User("admin","admin","admin","admin", LocalDate.parse("2001-12-22"),"admin","admin", UserRole.ADMIN,true);
//    userHib.createUser(user);
    }
}
