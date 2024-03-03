package com.colindalepass.user;


import com.colindalepass.entity.Role;
import com.colindalepass.entity.User;
import com.colindalepass.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@DataJpaTest(showSql = false)
public class UserRepositoryTest {

    @Autowired
	private UserRepository userRepo;

    @Autowired
    private  TestEntityManager entityManager;





    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User userNeyo = new User("neyo", "don" , "neyo@gmail.com" , "neyo2023");
        userNeyo.addRole(roleAdmin);

        User savedUser = userRepo.save(userNeyo);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles(){
        User userNeyo = new User("ade@gmail.com", "ade2023" , "ade" , "niyi");
        Role roleSecurity = new Role(3);
        Role roleAdmin = new Role(1);
        Role roleOccupant = new Role(2);

        userNeyo.addRole(roleSecurity);
        userNeyo.addRole(roleAdmin);
        userNeyo.addRole(roleOccupant);

        User savedUser = userRepo.save(userNeyo);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers(){
       Iterable<User> listUsers = userRepo.findAll();
       listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userNiyi = userRepo.findById(1).get();
        System.out.println(userNiyi);
        assertThat(userNiyi).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userNiyi = userRepo.findById(1).get();
        Role roleOccupant = new Role(2);

        userNiyi.setEnabled(true);
        userNiyi.setEmail("neyo@gmail.com");

        userNiyi.addRole(roleOccupant);

        userRepo.save(userNiyi);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "neyo@gmail.com";
        User user = userRepo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testDisableUser() {
        Integer id = 1;
        userRepo.updateEnabledStatus(id, false);

    }

    @Test
    public void testEnableUser() {
        Integer id = 2;
        userRepo.updateEnabledStatus(id, true);

    }


}
