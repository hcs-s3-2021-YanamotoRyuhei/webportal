package jp.ac.hcs.s3a329.user;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * フロント-バックでユーザ情報をやり取りする
 * 各項目のデータ使用はUserEntityを参照とする
 *
 */
@Data
public class UserForm {
	
	/**ユーザID(メールアドレス)*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;
	
	/**パスワード*/
	private String password;
	
	/**ユーザ名*/
	@NotBlank(message = "{require_check}")
	private String user_name;
	
	/**ダークモードフラグ*/
	private boolean darkmode;
	
	/**権限*/
	@NotBlank(message = "{require_check}")
	private String role;
}
