/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ph.sp.dao.exceptions.NonexistentEntityException;
import com.ph.sp.dao.inf.ITenantBillDurationDao;
import com.ph.sp.domain.TenantBillDuration;

/**
 * 
 * @author hackmel
 */
@Repository
public class TenantBillDurationDao implements ITenantBillDurationDao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public TenantBillDuration create(TenantBillDuration tenantBillDuration) {
		if (entityManager != null) {
			entityManager.persist(tenantBillDuration);

		}
		return tenantBillDuration;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public TenantBillDuration edit(TenantBillDuration tenantBillDuration)
			throws NonexistentEntityException, Exception {
		if (entityManager != null) {
			tenantBillDuration = entityManager.merge(tenantBillDuration);
		}

		Long id = tenantBillDuration.getId();
		if (findTenantBillDuration(tenantBillDuration.getTenant().getId(),
				tenantBillDuration.getBill().getId()) == null) {
			throw new NonexistentEntityException("The bill Charges with id "
					+ id + " no longer exists.");
		}
		return tenantBillDuration;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) throws NonexistentEntityException {
		TenantBillDuration tenantBillDuration = null;

		if (entityManager != null) {
			try {
				tenantBillDuration = entityManager.getReference(
						TenantBillDuration.class, id);
				tenantBillDuration.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException(
						"The bill Charges with id " + id + " no longer exists.",
						enfe);
			}
			entityManager.remove(tenantBillDuration);
		}
	}

	public List<TenantBillDuration> findTenantBillDuration(Long tenantId,
			Long billId) {
		Query q = entityManager
				.createQuery(
						"SELECT a FROM TenantBillDuration a where a.tenant.id=:tenantId and a.bill.id=:billId")
				.setParameter("tenantId", tenantId)
				.setParameter("billId", billId);
		List<TenantBillDuration> tenantBillDurationList = q.getResultList();

		return tenantBillDurationList;
	}

	public List<TenantBillDuration> findTenantBillDuration(Long billId) {
		Query q = entityManager
				.createQuery(
						"SELECT a FROM TenantBillDuration a where a.bill.id=:billId")
				.setParameter("billId", billId);
		List<TenantBillDuration> tenantBillDurationList = q.getResultList();

		return tenantBillDurationList;
	}

}
