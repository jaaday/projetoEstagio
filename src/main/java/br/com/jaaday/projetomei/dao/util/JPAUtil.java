/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jaaday.projetomei.dao.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sephi_000
 */
public class JPAUtil {
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("projetoMEIPU");
}
