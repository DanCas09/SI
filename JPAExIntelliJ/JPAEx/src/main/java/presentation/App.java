/*
 Walter Vieira (2022-04-22)
 Sistemas de Informação - projeto JPAAulas_ex3
 Código desenvolvido para iulustração dos conceitos sobre acesso a dados, concretizados com base na especificação JPA.
 Todos os exemplos foram desenvolvidos com EclipseLinlk (3.1.0-M1), usando o ambientre Eclipse IDE versão 2022-03 (4.23.0).
 
Não existe a pretensão de que o código estaja completo.

Embora tenha sido colocado um esforço significativo na correção do código, não há garantias de que ele não contenha erros que possam 
acarretar problemas vários, em particular, no que respeita à consistência dos dados.  
 
*/

package presentation;

import java.util.Scanner;


import businessLogic.*;


/**
 * Hello world!
 *
 */

public class App 
{
	protected interface ITest {
		void test();
	}
	
   @SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception
   {   BLService srv = new BLService();
   	ITest tests[] = new ITest[] {
         () -> {try { srv.test1(); } catch(Exception e) {}} 
//         , () -> {try { srv.teste2(); } catch(Exception e) {}} 
//         , () -> {try { srv.teste3(); } catch(Exception e) {}} 
//         , () -> {try { srv.teste4(); } catch(Exception e) {}} 
//         , () -> {try { srv.teste5(); } catch(Exception e) {}} 
      };
   	
   	Scanner imp = new Scanner(System.in );
   	System.out.printf("Escolha um teste (1-%d)? ",tests.length);
   	int option = imp.nextInt();
   	if (option >= 1 && option <= tests.length)
   		tests[--option].test();


       	 
   }
}
