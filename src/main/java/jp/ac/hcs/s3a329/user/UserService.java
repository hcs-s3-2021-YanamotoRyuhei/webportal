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
	
	/**
	 * 入力項目をUserDataへ変換する
	 * （このメソッドは入力チェックを実施したうえで呼び出すこと)
	 * @param form 入力データ
	 * @return UserData
	 */
	UserData refillToData(UserForm form) {
		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setPassword(form.getPassword());
		data.setUser_name(form.getUser_name());
		//data.setDarkmode(form.isDarkmode());
		data.setRole(form.getRole());
		//初期値は有効とする
		data.setEnabled(true);
		data.setDarkmode(false);
		return data;
	}
	/**
	 * ユーザ情報を新規登録する
	 * @param data ユーザ情報
	 * @return true|false
	 */
	public boolean insertOne(UserData data) {
		int count=0;
		try {
			count = userRepository.insertOne(data);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return count > 0;
	}
	/**
	 * ユーザ情報を検索する
	 * @param user_id ユーザID
	 * @return ユーザデータ
	 */
	public UserData selectOne(String user_id) {
		UserData userData = new UserData();
		try {
			userData = userRepository.selectOne(user_id);
		}catch(DataAccessException e) {
			e.printStackTrace();
		}
		return userData;
	}
	/**
	 * ユーザ情報を削除する
	 * @param user_id ユーザIÐ
	 * @return true|false
	 */
	public boolean deleteOne(String user_id) {
		int count = 0;
		try {
			count = userRepository.deleteOne(user_id);
		}catch(DataAccessException e) {
			e.printStackTrace();
		}
		return count > 0;
	}
}
