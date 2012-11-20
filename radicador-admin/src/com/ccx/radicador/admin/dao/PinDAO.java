package com.ccx.radicador.admin.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ccx.radicador.admin.vo.PinVO;

@Singleton
@Startup
@Named("dao")
@ApplicationScoped
public class PinDAO {

	@PersistenceContext(unitName = "ccxPU")
	EntityManager em;

	public List<PinVO> getPines() {
		TypedQuery<PinVO> query = em.createQuery(
				"select p from PinVO p order by p.updatedTime", PinVO.class);
		List<PinVO> pines = query.getResultList();
		return pines;
	}

	public String getPin(String key, boolean isCCX) {
		String result = "-1";
		TypedQuery<PinVO> query = null;
		query = em
				.createQuery(
						"select p from PinVO p where p.usedBy is NULL and p.updatedTime is NULL and p.isCCX = ?1",
						PinVO.class);
		query.setParameter(1, isCCX);
		try {
			List<PinVO> pines = query.getResultList();
			if (pines.size() > 0) {
				PinVO pin = pines.get(0);
				result = pin.getPIN();
				pin.setUsedBy(key);
				pin.setUpdatedTime(new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void savePines(List<PinVO> pines) throws Exception {
		Query query=em.createQuery("delete from PinVO p");
		query.executeUpdate();
		for (PinVO p : pines) {
			em.persist(p);
		}
	}
}
