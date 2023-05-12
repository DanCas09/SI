/*
 Walter Vieira (2022-04-22)
 Sistemas de Informação - projeto JPAAulas_ex1
 Código desenvolvido para iulustração dos conceitos sobre acesso a dados, concretizados com base na especificação JPA.
 Todos os exemplos foram desenvolvidos com EclipseLinlk (3.1.0-M1), usando o ambientre Eclipse IDE versão 2022-03 (4.23.0).
 
Não existe a pretensão de que o código estaja completo.

Embora tenha sido colocado um esforço significativo na correção do código, não há garantias de que ele não contenha erros que possam 
acarretar problemas vários, em particular, no que respeita à consistência dos dados.  
 
*/

package businessLogic;

import java.util.List;
import jakarta.persistence.*;

import model.*;

/**
 * Hello world!
 *
 */
public class BLService 
{
    //@SuppressWarnings("unchecked")
	public void test1() throws Exception
    { //
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAEx");
        EntityManager em = emf.createEntityManager();
        try 
        {
        	//Criar um aluno
            System.out.println("Ler um aluno");
            em.getTransaction().begin();

        	String sql = "SELECT a FROM Aluno a";
        	Query query = em.createQuery(sql);
            List<Aluno> la = query.getResultList();

            for (Aluno ax : la) 
            {
                System.out.printf("%d ", ax.getNumal());
                System.out.printf("%s \n", ax.getNomeal());
            }
            
  
        } 
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        	throw e;
        }
        finally 
        {
        	em.close();
            emf.close();
        }
    }
}
