package org.mdev.revolution.utilities;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mdev.revolution.Revolution;

public class HibernateUtil {
    private static final ThreadLocal session = new ThreadLocal();

    @SuppressWarnings("unchecked")
    public static synchronized Session currentSession() throws HibernateException {
        Session s = (Session) session.get();

        if (s == null) {
            s = Revolution.getInstance().getDatabaseManager().openSession();
            session.set(s);
        }

        return s;
    }

    @SuppressWarnings("unchecked")
    public static synchronized void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);

        if (s != null)
            s.close();
    }
}