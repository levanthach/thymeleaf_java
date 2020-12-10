package vn.ikornsolution.thymeleaf.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import vn.ikornsolution.thymeleaf.demo.User;
import vn.ikornsolution.thymeleaf.demo.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUser(){
		return (List<User>) userRepository.findAll();
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public void updateUser(User user) {
		System.out.println(user.getEmail());
//		userRepository.updateUser(nameModify, emailModify, phoneModify, id);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public Page<User> findPaginated(int pageNo, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.userRepository.findAll(pageable);
	}
	
	public List<User> searchUsers(String keyword) {
		return userRepository.search(keyword);
	}

}
