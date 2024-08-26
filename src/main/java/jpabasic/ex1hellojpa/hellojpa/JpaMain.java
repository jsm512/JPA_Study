package jpabasic.ex1hellojpa.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*
        단위 작업에서 항상 EntityManager를 만들어 줘야됨
        EntityManager는 Thread간 절대로 공유 하면 안됨 -> 사용하고 버려
        */
        EntityManager em = emf.createEntityManager();

        /*
        단위 작업 전 항상 Transaction안에서 작업
        데이터 변경은 무조건 트랜잭션 안에서 실행
         */
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {
            //이대로 코드를 작성하면 실행이 된 거 같아도 db에 저장이 안되는 걸 확인함 -> 모든 작업에 있어서 transaction안에서 작업해야 됨
//            //객체 저장
//            Member member = new Member();
//            member.setId(4L);
//            member.setName("HelloD");
//            em.persist(member);

            //수정
            Member findMember = em.find(Member.class, 1L); //find 메소드로 id가 1인 Member을 가져옴 -> 이후 수정 또는 삭제
            findMember.setName("HelloJPA");
//            em.persist(); // 수정할 때 persist() 메소드는 필요 없다.. jpa를 통해 객체를 가져오면 jpa가 관리를 알아서 해줌 자동으로 update query를 날려 수정


//            em.remove(findMember); //찾은 member 삭제

            /*
            쿼리문으로 모든 Member를 불러올 수도 있음
            Member 객체를 대상으로 함 -> 대상이 테이블이 아니라 객체가 됨
             */
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
////                    .setFirstResult(5) //페이징 관련
////                    .setMaxResults(8)
//                    .getResultList();

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
