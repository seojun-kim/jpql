package hellojpa.jpql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class JpqlApplication {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			Team team = new Team();
			team.setName("team");
			em.persist(team);

			Member member = new Member();
			member.setUsername("member");
			member.setAge(20);
			member.setType(MemberType.ADMIN);
			member.changeTeam(team);
			em.persist(member);

			String query = "select m from Member m";
			List<Member> result = em.createQuery(query, Member.class)
					.getResultList();

			for (Member member1 : result) {
				System.out.println("member1 = " + member1);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
