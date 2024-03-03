//package com.eventApp.user.test2;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.Rollback;
//
//import com.eventApp.UserEntity;
//import com.eventApp.UserRepository;
//
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
//@DataJpaTest(showSql = false)
//public class userRepoTest {
//	
//	
//	private UserRepository userRepo;
//
//	@Autowired
//	private TestEntityManager entityManager;
//
//	
//	@Test
//	public void testGetById() {
//		UserEntity user = userRepo.findById(2).get();
//
//		assertThat(user.getFirstName()).isEqualTo("Adeniyi");
//	}
//
//	
//	
//	
//}
