package jp.ac.hcs.s3a329.user;

import java.security.Principal;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 登録されているユーザの一覧を表示する
	 * @param model
	 * @return ユーザ一覧画面
	 * 
	 */
	@GetMapping("/user/list")
	public String getUserList(Principal principal, Model model) {
//		log.info(user_id);
		UserEntity userEntity = userService.selectAll();
		model.addAttribute("userEntity",userEntity);
		
		return "user/userList";
		
	}
	/**
	 * ユーザ登録画面（管理者用）を表示する
	 * @param form 登録時の入力チェック用UserForm
	 * @param model
	 * @return ユーザ登録画面（管理者用）
	 * 
	 */
	@GetMapping("/user/insert")
	public String getUserInsert(UserForm form, Model model) {
		return "user/insert";
	}
	
	/**
	 * 1件分のユーザ情報をデータベースに登録する
	 * @param form 登録するユーザ情報（パスワードは平文）
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ一覧画面
	 */
	
	@PostMapping("/user/insert")
	public String getUserInsert(@ModelAttribute @Validated UserForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {
		//form.setDarkmode(false);
		//入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserInsert(form , model);
		}
		UserData data = userService.refillToData(form);
		//data.setEnabled(true);
		boolean isSuccess = userService.insertOne(data);
		if(isSuccess) {
			log.info("成功");
		}else {
			log.info("失敗");
		}
		return getUserList(principal, model);
	}
	
	/**
	 * ユーザの詳細を表示する
	 * @param user_id ユーザID(メールアドレス)
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ詳細画面
	 */
	@GetMapping("/user/detail/{id}")
	public String getUserDetail(@PathVariable("id")String user_id,
			Principal principal,
			Model model) {
		String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9.-])*@([a-zA-Z0-9-])+([a-zA-Z0-9._-]+)+$";
		Pattern p = Pattern.compile(pattern);
		//ユーザIDがnullかどうかとメールアドレスの形式になっているか確かめる
		
		if((!(p.matcher(user_id).find())) || user_id =="") {
			model.addAttribute("error","不正な操作がありました。");
			log.info("不正な操作を確認");
			//不正な操作があるためユーザ一覧に戻る
			return getUserList(principal, model);
		}
		
		UserData userData = new UserData();
		userData = userService.selectOne(user_id);
		
		if(userData.equals(null)) {
			model.addAttribute("error","不正な操作がありました。");
			log.info("不正な操作を確認");
			//不正な操作があるためユーザ一覧に戻る
			return getUserList(principal, model);
		}
		
		model.addAttribute("error","");
		model.addAttribute("userData", userData);
		return "user/detail";
	}
	/**
	 * ユーザ情報を削除する
	 * @param user_id ユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザリスト画面
	 */
	@PostMapping("/user/delete")
	public String getUserDelete(@RequestParam("user_id")String user_id,
								Principal principal,
								Model model) {
		boolean isSuccess = userService.deleteOne(user_id);
		if(isSuccess) {
			log.info("削除に成功しました");
		}else {
			log.info("削除に失敗しました");
		}
		log.info(user_id);
		return getUserList(principal, model);
		
	}
}
