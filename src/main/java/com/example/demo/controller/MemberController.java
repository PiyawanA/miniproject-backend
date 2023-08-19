package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;

@RestController
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@GetMapping("/member")
	public ResponseEntity<Object> getMember() {
		try {

			List<Member> members = memberRepository.findAll();
			return new ResponseEntity<>(members, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/member")
	public ResponseEntity<Object> addMember(@RequestBody Member body) {
		try {
			Member newMember = memberRepository.save(body);
			return new ResponseEntity<>(newMember, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/member/{memId}")
	public ResponseEntity<Object> getMemDetail(@PathVariable Long memId, @RequestBody Member body) {
		try {

			Optional<Member> member = memberRepository.findById(memId);
			if (member.isPresent()) {
				return new ResponseEntity<>(member, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Member Not Found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/member/{memId}")
	public ResponseEntity<Object> updateMember(@PathVariable("memId") Long memId, @RequestBody Member body) {

		try {

			Optional<Member> memberFind = memberRepository.findById(memId);

			if (memberFind.isPresent()) {

				Member memberEdit = memberFind.get();

				memberEdit.setFirstname(body.getFirstname());
				memberEdit.setLastname(body.getLastname());
				memberEdit.setEmail(body.getEmail());
				memberEdit.setUsername(body.getUsername());
				memberEdit.setPassword(body.getPassword());

				memberRepository.save(memberEdit);

				return new ResponseEntity<>(memberEdit, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Member Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/memDelete/{memId}")
	public ResponseEntity<Object> deletEmployee(@PathVariable Long memId) {

		try {
			Optional<Member> employee = memberRepository.findById(memId);

			if (employee.isPresent()) {

				memberRepository.delete(employee.get());

				return new ResponseEntity<>("Delete Sucess", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/checkUsername")
	public ResponseEntity<Object> checkUsername(@RequestParam("username") String username) {

		try {
			Optional<Member> userFound = memberRepository.findByUsername(username);

			if (userFound.isPresent()) {
				return new ResponseEntity<>(true, HttpStatus.OK);

			} else {
				return new ResponseEntity<>(false, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody Member body) {
		try {

			if (memberRepository.findByUsername(body.getUsername()).isPresent()) {
				return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
			}

			// Create a new user
			Member newMember = new Member();
			newMember.setFirstname(body.getFirstname());
			newMember.setLastname(body.getLastname());
			newMember.setEmail(body.getEmail());
			newMember.setUsername(body.getUsername());
			newMember.setPassword(body.getPassword());

			Member savedMember = memberRepository.save(newMember);
			return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody Member body) {
		try {

			Optional<Member> memberFound = memberRepository.findByUsername(body.getUsername());

			if (memberFound.isPresent() && memberFound.get().getPassword().equals(body.getPassword())) {

				memberFound.get().setPassword(null);
				return new ResponseEntity<>(memberFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
