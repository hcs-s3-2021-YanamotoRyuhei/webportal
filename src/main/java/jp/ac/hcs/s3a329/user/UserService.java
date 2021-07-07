package jp.ac.hcs.s3a329.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	public UserEntity selectAll() {
		/**
		 * ユーザ取得業務ロジック
		 * 
		 * @param userId ユーザID
		 * @return UserEntity
		 */
		UserEntity userEntity = new UserEntity();
		try {
			userEntity = userRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return userEntity;
	}

}
