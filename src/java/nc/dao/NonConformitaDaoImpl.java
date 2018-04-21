package nc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import nc.model.Fornitore;
import nc.model.NonConformita;
import nc.model.Reparto;
import nc.model.Tipo;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("NonConformitaDao")
public class NonConformitaDaoImpl implements NonConformitaDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public NonConformita findByCodice(int codice) {
        return (NonConformita) getSession().get(NonConformita.class, codice);
    }

    @Override
    public void saveNonConformita(NonConformita toSave) {
        getSession().persist(toSave);
    }

    @Override
    public List<NonConformita> findAll() {
        String sql = "SELECT * FROM NonConformita";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(NonConformita.class);
        return new ArrayList<>(query.list());
    }

    @Override
    public double findCostoPerTipo(int anno, Tipo tipo) {
        String sql = "SELECT * FROM NonConformita WHERE Tipo = :nome_tipo AND year(DataChiusura) = :anno";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(NonConformita.class);
        query.setParameter("nome_tipo", tipo.getNome());
        query.setParameter("anno", anno);
        ArrayList<NonConformita> res = new ArrayList<>(query.list());
        double sum = 0;
        if (res != null) {
            for (NonConformita tmp : res) {
                sum += tmp.getCosto();
            }
        }
        return sum;

        /**
         * Criteria criteria = getSession().createCriteria(NonConformita.class);
         * //raggruppa per tipo, e anno criteria.add(Restrictions.eq("tipo",
         * tipo)); criteria.add(Restrictions.eq("dataChiusura", anno));
         * ArrayList<NonConformita> res = new ArrayList<>(criteria.list()); int
         * sum = 0; for (NonConformita tmp : res) { sum += tmp.getCosto(); }
         * return sum;
         */
    }

    @Override
    public double findCostoPerReparto(int anno, Reparto rep) {
        String sql = "SELECT * FROM NonConformita WHERE IDReparto = :nome_reparto AND year(DataChiusura) = :anno";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(NonConformita.class);
        query.setParameter("nome_reparto", rep.getNome());
        query.setParameter("anno", anno);
        ArrayList<NonConformita> res = new ArrayList<>(query.list());
        double sum = 0;
        if (res != null) {
            for (NonConformita tmp : res) {
                sum += tmp.getCosto();
            }
        }
        return sum;
        /*
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        //raggruppa per tipo, e anno
        criteria.add(Restrictions.eq("reparto", rep));
        criteria.add(Restrictions.eq("dataChiusura", anno));
        ArrayList<NonConformita> res = new ArrayList<>(criteria.list());
        int sum = 0;
        for (NonConformita tmp : res) {
            sum += tmp.getCosto();
        }
        return sum;
         */
    }

    @Override
    public double findCostoPerFornitore(int anno, Fornitore forn) {
        String sql = "SELECT * FROM NonConformita WHERE PivaFornitore = :nome_forn AND year(DataChiusura) = :anno";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(NonConformita.class);
        query.setParameter("nome_forn", forn.getPiva());
        query.setParameter("anno", anno);
        ArrayList<NonConformita> res = new ArrayList<>(query.list());
        double sum = 0;
        if (res != null) {
            for (NonConformita tmp : res) {
                sum += tmp.getCosto();
            }
        }
        return sum;
        /*
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        //raggruppa per tipo, e anno
        criteria.add(Restrictions.eq("fornitore", forn));
        criteria.add(Restrictions.eq("dataChiusura", anno));
        ArrayList<NonConformita> res = new ArrayList<>(criteria.list());
        int sum = 0;
        for (NonConformita tmp : res) {
            sum += tmp.getCosto();
        }
        return sum;
         */
    }

    @Override
    public double findAllCostoPerNonConformita(int anno) {
        int a= Calendar.YEAR;
        String sql = "SELECT * FROM NonConformita WHERE year(DataApertura) = :annoI AND year(DataChiusura) = :annoF";
        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(NonConformita.class);
        query.setParameter("annoI", a);
        query.setParameter("annoF", anno);
        ArrayList<NonConformita> res = new ArrayList<>(query.list());
        double sum = 0;
        if (res != null) {
            for (NonConformita tmp : res) {
                sum += tmp.getCosto();
            }
        }
        return sum;
        /*
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        Date d = new Date (Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR);
        //raggruppa per anno
        criteria.add(Restrictions.eq("annoI", d));
        criteria.add(Restrictions.eq("annoF", anno));
        ArrayList<NonConformita> res = new ArrayList<>(criteria.list());
        int sum = 0;
        for (NonConformita tmp : res) {
            sum += tmp.getCosto();
        }
        return sum;
         */
    }

    @Override
    public List<NonConformita> findAllAperte() {
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        criteria.add(Restrictions.isNull("dataChiusura"));
        criteria.add(Restrictions.isNull("azioniCorrettive"));
        return (List<NonConformita>) criteria.list();
    }

    @Override
    public List<NonConformita> findAllInElaborazione() {
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        criteria.add(Restrictions.isNull("dataChiusura"));
        criteria.add(Restrictions.isNotNull("azioniCorrettive"));
        return (List<NonConformita>) criteria.list();
    }

    @Override
    public List<NonConformita> findAllChiuse() {
        Criteria criteria = getSession().createCriteria(NonConformita.class);
        criteria.add(Restrictions.isNotNull("dataChiusura"));
        return (List<NonConformita>) criteria.list();
    }

}
