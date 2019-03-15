package com.xelba.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.xelba.entity.Employee;

public class EmployeeDao {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeePU");
    private static EntityManager em = emf.createEntityManager();

    public Employee getEmp(int id) {
        return em.find(Employee.class, id);
    }

    public void createEmp(Employee emp) {
        em.getTransaction().begin();
        em.persist(emp);
        em.getTransaction().commit();
    }

	public void updateEmp(Employee transientEmp) {
        em.getTransaction().begin();
        em.merge(transientEmp);
        em.flush();
        em.getTransaction().commit();
	}

	public void deleteEmp(Integer id) {
        em.getTransaction().begin();
        Employee emp = em.find(Employee.class, id);
        em.remove(emp);
        em.getTransaction().commit();
	}
}