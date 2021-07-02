package jp.ac.hcs.s3a329.task;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TaskController {
	@Autowired
	private TaskService taskService;
	/**
	 * 郵便番号から住所を検索し、結果画面を表示する
	 * @param weather 検索する郵便番号(ハイフン無し)
	 * @param prinicipal ログイン情報
	 * @param model
	 * @return 結果画面 - 郵便番号
	 */
	@GetMapping("/task")
	public String getTask(Principal principal, Model model) {
		String user_id = principal.getName();
//		log.info(user_id);
		TaskEntity taskEntity = taskService.selectAll(user_id);
		model.addAttribute("taskEntity",taskEntity);
		return "task/task";
		
	}
	
	@PostMapping("/task/insert")
	public String insertTask(@RequestParam("comment") String comment,
							 @RequestParam("limitday") String limitday,
							 Principal principal,Model model) throws ParseException {
		String user_id = principal.getName();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(limitday);
		boolean isSuccess = taskService.insertOne(user_id,comment,date);
		if(isSuccess) {
			log.info("成功");
		}else {
			log.info("失敗");
		}
		return getTask(principal, model);
		
	}
}
