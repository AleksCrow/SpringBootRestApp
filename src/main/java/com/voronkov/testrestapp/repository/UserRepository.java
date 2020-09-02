package com.voronkov.testrestapp.repository;

import com.voronkov.testrestapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**Repository for work with users
 * @author A.Voronkov
 * @since 29.08.2020
 * @version 1.0
 */
@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**Use spring data evans - find last by id
	 * @return user
	 */
	User findTopByOrderByIdDesc();

	/**find by email
	 * @param email
	 * @return user
	 */
	User findByEmailIgnoreCase(String email);
}
