package com.enterprisex.wallsofwonder.oms.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderTrackingIdGenerator {

    private static final String NEXT_VAL_QUERY = "SELECT nextval('oms.orders_meta_row_number_seq')";
    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yy");

    @PersistenceContext
    private EntityManager entityManager;

    public String generate(EntityManager em, Object owner) {
        EntityManager targetEm = (em != null) ? em : entityManager;
        Number nextVal = (Number) targetEm.createNativeQuery("SELECT nextval(:sequenceName)")
                .setParameter("sequenceName", "oms.orders_meta_row_number_seq")
                .getSingleResult();
        if (nextVal == null) {
            throw new RuntimeException("Failed to generate sequence value for " + NEXT_VAL_QUERY);
        }

        // Format sequence
        String lineSerialNo = String.format("%015d", nextVal);
        String strYear = simpleFormat.format(new Date());

        String prefix = "D2O";
        return prefix + strYear + lineSerialNo;
    }

}

