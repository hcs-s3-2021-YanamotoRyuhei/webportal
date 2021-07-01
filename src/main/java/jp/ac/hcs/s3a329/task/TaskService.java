package jp.ac.hcs.s3a329.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	/**
	 * タスク取得業務ロジック
	 * 
	 * @param userId ユーザID
	 * @return TaskEntity
	 */
	public TaskEntity getTask(String user_id) {
		TaskEntity taskEntity = new TaskEntity();
		try {
		taskEntity = taskRepository.selectAll(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			taskEntity = null;
		}
		return taskEntity;
	}
}
