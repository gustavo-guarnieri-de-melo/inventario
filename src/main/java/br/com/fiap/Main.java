package br.com.fiap;

import br.com.fiap.domain.entity.Bem;
import br.com.fiap.domain.entity.Departamento;
import br.com.fiap.domain.entity.Inventario;
import br.com.fiap.domain.entity.TipoDeBem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory( "maria-db" );
        EntityManager manager = factory.createEntityManager();


//        persistir( manager );

        Inventario inventario = findInventarioById(manager);

        List <Bem> bens= findAllBens(manager);

        for (Bem b: bens){
            inventario.addBem(b);
        }


//        manager.createQuery( "From Bem" ).getResultList().forEach( System.out::println );

        manager.close();
        factory.close();

    }
    private List<Bem> findAllBens(EntityManager manager){
        String jpql = "FROM Bem";
        return manager.createQuery(jpql).getResultList();
    }

    private static Inventario findInventarioById(EntityManager manager) {
        Long idInventario = Long.valueOf( JOptionPane.showInputDialog( "ID do Inventário" ) );
        Inventario inventario = manager.find( Inventario.class, idInventario );
        System.out.println( inventario );
        return inventario;
    }

    private static void persistir(EntityManager manager) {
        TipoDeBem tipo = new TipoDeBem();
        tipo.setNome( "AUTOMOVEIS" );

        Departamento departamento = new Departamento();
        departamento.setNome( "SEGURANCA" );

        Inventario inventario = new Inventario();
        inventario.setInicio( LocalDate.now() )
                .setDepartamento( departamento );

        Bem veiculo1 = new Bem();
        veiculo1.setNome( "Cadilac Lyric" )
                .setTipo( tipo )
                .setLocalizacao( departamento )
                .setEtiqueta( "2132132" )
                .setAquisicao( LocalDate.now().minusYears( 1 ) );


        manager.getTransaction().begin();

        manager.persist( veiculo1 );
        manager.persist( inventario );

        manager.getTransaction().commit();


        System.out.println( veiculo1 );
        System.out.println( inventario );
    }
}