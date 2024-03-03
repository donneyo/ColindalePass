package com.colindalepass.user.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.colindalepass.entity.User;
import com.colindalepass.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


//import com.eventApp.repository.UserRepository;
//import com.eventApp.entity.User;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@DataJpaTest(showSql = false)
public class userEntityRepositoryTest {


	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TestEntityManager entityManager;

		@Test
		public void testCreateUser(){
			User user = new User( "donneyo63@gmail.com","adeniyi","deji","password");

			User savedUser = userRepo.save(user);

			assertThat(savedUser).isNotNull();
			assertThat(savedUser.getId()).isGreaterThan(0);
		}

		@Test
		public void testGetById() {
			User user = userRepo.findById(2).get();

			assertThat(user.getFirstName()).isEqualTo("Adeniyi");
		}

		@Test
		public void testUpdateName() {
		String newName = "Donneyo";

		User oldName = userRepo.findById(2).get();

		oldName.setFirstName(newName);

		User saveDNewName = userRepo.save(oldName);

		assertThat(saveDNewName.getFirstName()).isEqualTo(newName);


		}

		@Test
		public void testCreateUser2(){
            User user = new User("adedeji", "donneyo","donneyo@gmail.com","word");

            User savedUser = userRepo.save(user);

			assertThat(savedUser).isNotNull();
			assertThat(savedUser.getId()).isGreaterThan(0);
		}

		@Test
		public void testdeleteUser() {
            User UserId = userRepo.findById(2).get();

			userRepo.delete(UserId);
		}

	@Test
	public void testEnableUser() {
		Integer id = 17;
		userRepo.updateEnabledStatus(id, true);

	}



}