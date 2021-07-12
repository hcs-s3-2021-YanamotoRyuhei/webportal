package jp.ac.hcs.s3a329.task;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
	 * @param userId ユーザID
	 * @return TaskEntity
	 */
	public TaskEntity selectAll(String user_id) {
		TaskEntity taskEntity = new TaskEntity();
		try {
			taskEntity = taskRepository.selectAll(user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			taskEntity = null;
		}
		return taskEntity;
	}
	/**
	 * タスク追加業務ロジック
	 * @param user_id ユーザID
	 * @param commentコメント
	 * @param date 日付
	 * @return true|false
	 */
	public boolean insertOne(String user_id, String comment, Date date) {
		TaskData taskData = new TaskData();
		try {
			taskData.setUser_id(user_id);
			taskData.setComment(comment);
			taskData.setLimitday(date);

			taskRepository.insertOne(taskData);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * タスク情報をCSVファイルとしてサーバに保存する.
	 * 
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void taskListCsvOut(String user_id) throws DataAccessException {
		taskRepository.tasklistCsvOut(user_id);
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * 
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}
	/**
	 * タスク削除業務ロジック
	 * @param id タスクID
	 * @return true|false
	 */
	public boolean deleteOne(int id) {
		try {
			taskRepository.deleteOne(id);
		} catch (DataAccessException e) {
			return false;
		}
		return true;
	}
}
