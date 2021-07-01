package jp.ac.hcs.s3a329.task;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Slf4j
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
		TaskEntity taskEntity = taskService.getTask(user_id);
		model.addAttribute("taskEntity",taskEntity);
		return "task/task";
		
	}
}
