package br.com.biblioteca.dao;

import br.com.biblioteca.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class UsuarioDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = -282780611124798898L;

	/**
	 * Localiza um Usuario pelo nome
	 * @param nome Nome do Usuario
	 * @return Objeto da classe Usuario
	 */
	public Usuario localizar(String nome) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Usuario usuario = null;
		
		try {

			t.begin();
			Query q = em.createQuery("from Usuario where nome like :nome");
			q.setParameter("nome", nome);
			usuario = (Usuario) q.getSingleResult();
			t.commit();

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();
		}

		return usuario;
	}

	/**
	 * Insere (persiste) um Usuario
	 * @param usuario Usuario a ser persistido
	 * @return True se bem sucedido, false se houve erro.
	 */
	public boolean inserir(Usuario usuario) {

		EntityManager em = factory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		boolean result = false;
		Usuario existentUsuario = null;
		
		try {

			t.begin();

			/* verifica se ja' existe um usuario com o mesmo nome */
			Query q = em.createQuery("from Usuario where nome like :nome");
			q.setParameter("nome", usuario.getNome());

			try {
				existentUsuario = (Usuario) q.getSingleResult();
			} catch(Exception e) { }
			
			/* se nao existe o usuario, persiste */
			if (existentUsuario == null) {
				em.persist(usuario);
			} else { /* ja' existe o usuario, somente retorna seu id */
				usuario.setIdUsuario(existentUsuario.getIdUsuario());
			}
			
			t.commit();
			result = true;

		} catch (Exception e) {

			if (debugInfo) {
				e.printStackTrace();
			}
			if (t.isActive()) t.rollback();

		} finally {

			em.close();

		}

		return result;
	}
}
