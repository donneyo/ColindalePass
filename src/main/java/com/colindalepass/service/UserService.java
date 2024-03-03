package com.colindalepass.service;


import com.colindalepass.entity.AuthenticationType;
import com.colindalepass.entity.Role;
import com.colindalepass.entity.User;
import com.colindalepass.exception.UserNotFoundException;
import com.colindalepass.repository.RoleRepository;
import com.colindalepass.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {


    public static final int USERS_PER_PAGE = 4;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;


    public List<User> listAll() {
        return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        if (keyword != null) {
            return userRepo.findAll(keyword, pageable);
        }

        return userRepo.findAll(pageable);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);

        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getId()).get();

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }

        } else {
            encodePassword(user);
        }

       return userRepo.save(user);
    }

    public void registerUser(User user) {
        encodePassword(user);
//        user.setAuthenticationType(AuthenticationType.DATABASE);
        user.setEnabled(true);
        user.setCreatedTime(new Date());

        String randomCode = RandomString.make();
        user.setVerificationCode(randomCode);

        userRepo.save(user);

    }


//    public void registerUserwithoutEmial(User user) {
//        encodePassword(user);
//        user.setEnabled(true);
//        user.setCreatedTime(new Date());
//
//        String randomCode = RandomString.make();
//        user.setVerificationCode(randomCode);
//
//        userRepo.save(user);
//
//    }
    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            return false;
        } else {
            return userByEmail.getId() == id;
        }
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        userRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }

    public Page<User> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
        return (Page<User>) userRepo.findAll(pageable);
    }

    public User getByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User updateAccount(User userInForm) {
        User userInDB = userRepo.findById(userInForm.getId()).get();

        if (!userInForm.getPassword().isEmpty()) {
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }

        if (userInForm.getPhotos() != null) {
            userInDB.setPhotos(userInForm.getPhotos());
        }

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepo.save(userInDB);
    }

    public String updateResetPasswordToken(String email) throws UserNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            String token = RandomString.make(30);
            user.setResetPasswordToken(token);
            userRepo.save(user);

            return token;
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(String token, String newPassword) throws UserNotFoundException {
        User customer = userRepo.findByResetPasswordToken(token);
        if (customer == null) {
            throw new UserNotFoundException("No customer found: invalid token");
        }

        customer.setPassword(newPassword);
        customer.setResetPasswordToken(null);
        encodePassword(customer);

        userRepo.save(customer);
    }

//    public void updateAuthenticationType(User user, AuthenticationType type) {
//        if (!user.getAuthenticationType().equals(type)) {
//            userRepo.updateAuthenticationType(user.getId(), type);
//        }
//    }

//    public void addNewCustomerUponOAuthLogin(String name, String email,
//                                             AuthenticationType authenticationType)  {
//        User user = new User();
//        user.setEmail(email);
//        setName(name, user);
//
//        user.setEnabled(true);
//        user.setCreatedTime(new Date());
//        user.setAuthenticationType(authenticationType);
//        user.setPassword("");
////        user.setAddressLine1("");
////        customer.setCity("");
////        customer.setState("");
////        customer.setPhoneNumber("");
////        customer.setPostalCode("");
////        customer.setCountry(countryRepo.findByCode(countryCode));
//
//        userRepo.save(user);
//    }

//    private void setName(String name, User user) {
//        String[] nameArray = name.split(" ");
//        if (nameArray.length < 2) {
//            user.setFirstName(name);
//            user.setLastName("");
//        } else {
//            String firstName = nameArray[0];
//            user.setFirstName(firstName);
//
//            String lastName = name.replaceFirst(firstName + " ", "");
//            user.setLastName(lastName);
//        }
//
//    }
    public void update(User customerInForm) {
        User customerInDB = userRepo.findById(customerInForm.getId()).get();

        if (customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
            if (!customerInForm.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
                customerInForm.setPassword(encodedPassword);
            } else {
                customerInForm.setPassword(customerInDB.getPassword());
            }
        } else {
            customerInForm.setPassword(customerInDB.getPassword());
        }

        customerInForm.setEnabled(customerInDB.isEnabled());
        customerInForm.setCreatedTime(customerInDB.getCreatedTime());
        customerInForm.setVerificationCode(customerInDB.getVerificationCode());
        customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());

        userRepo.save(customerInForm);



    }


}
