package jp.ac.hcs.s3a329.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
	public TaskEntity selectAll(String user_id) {
		TaskEntity taskEntity = new TaskEntity();
		try {
			taskEntity = taskRepository.selectAll(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			taskEntity = null;
		}
		return taskEntity;
	}
	
	public boolean insertOne(String user_id,String comment, Date date) {
		TaskData taskData = new TaskData();
		try {
			taskData.setUser_id(user_id);
			taskData.setComment(comment);
			taskData.setLimitday(date);

			taskRepository.insertOne(taskData);
			return true;
		}catch(DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
