package vn.ikornsolution.thymeleaf.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.ikornsolution.thymeleaf.demo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value = "SELECT id, name, email, phone FROM user u WHERE CONCAT(u.id, ' ', u.name, ' ', u.email, ' ', u.phone) LIKE %?1%", nativeQuery = true)
	public List<User> search(String pageable);
	
	@Query(value = "UPDATE user u set u.name = :nameModify, u.email = :emailModify, u.phone = :phoneModify where u.id = :id" , nativeQuery = true)
	public void updateUser(@Param("nameModify") String nameModify, @Param("emailModify") String mailModify,  @Param("phoneModify") String phoneModify, @Param("id") String id);
}
