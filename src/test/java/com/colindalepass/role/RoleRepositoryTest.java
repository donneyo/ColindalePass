package com.colindalepass.role;

import static org.assertj.core.api.Assertions.assertThat;

import com.colindalepass.entity.Role;
import com.colindalepass.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback (false)
public class RoleRepositoryTest {


	@Autowired
	private RoleRepository repo;

		@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Manage everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);

	}

//	@Test
//	public void testCreateFirstRole(){
//			Role roleOccupant = new Role("Occupant", "Generate codes");
//			Role savedRole = repo.save(roleOccupant);
//
//			assertThat(savedRole.getId()).isGreaterThan(0);
//		}
	


	@Test
	public void testCreateRestRoles() {
		Role roleOccupant = new Role("Occupant",
				"Generate Codes");
		Role roleSecurity = new Role("Security",
				"Validate Codes");

		repo.saveAll(List.of(roleOccupant,roleSecurity));
	}

}
