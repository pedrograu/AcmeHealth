package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//Devuelve un customer dado el id del userAccount
    @Query("select c from Customer c where c.userAccount.id=?1")
    Customer findByUserAccountId(int id);

    //Devuelve una collection de customer que no tenga direccion, es decir, que sean especialistas.
    @Query("select c from Customer c where c.address = null")
    Collection<Customer> findOnlySpecialist();

}
