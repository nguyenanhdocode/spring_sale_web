/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.nad.springsaleweb;

import com.nad.pojo.Category;
import com.nad.pojo.Product;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class SpringSaleWeb {

    public static void main(String[] args) {
        try(Session session = HibernateUtils.getFactory().openSession()) {
            Category c = session.get(Category.class, 2);
            Set<Product> products = c.getProducts();
            
            products.forEach(p -> {
                System.out.printf("%d - %s - %s\n",
                        p.getId(), p.getName(), p.getPrice());
            });
        }
    }
}
