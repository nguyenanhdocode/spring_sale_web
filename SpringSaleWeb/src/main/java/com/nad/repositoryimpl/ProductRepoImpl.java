/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.repositoryimpl;

import com.nad.pojo.Product;
import com.nad.springsaleweb.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class ProductRepoImpl {
    public List<Product> getAll(Map<String, String> params) {
        try(Session session = HibernateUtils.getFactory().openSession()) {
            
            List<Product> result;
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root root = query.from(Product.class);
            query.select(root);
         
            List<Predicate> predicates = new ArrayList<>();
            
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty())
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", kw)));
            
            String fromPrice = params.get("fromPrice");
            String toPrice = params.get("toPrice");
            
            if (fromPrice != null && toPrice == null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice)));
            else if (fromPrice == null && toPrice != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice)));
            else
                predicates.add(builder.between(root.get("price"), Double.parseDouble(fromPrice)
                        , Double.parseDouble(toPrice)));
            
            
            query.where(predicates.toArray(Predicate[]::new));
            
            Query q = session.createQuery(query);
            
            result = q.getResultList();
            
            return result;
        }
    }
}
