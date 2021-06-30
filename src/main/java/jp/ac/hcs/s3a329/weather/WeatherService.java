package jp.ac.hcs.s3a329.weather;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 天気情報を操作する
 * お天気APIを活用する
 *-http://weather.tsukumijima.net/
 */
@Transactional
@Service
public class WeatherService {
	@Autowired
	RestTemplate restTemplate;
	
	/**郵便番号検索APIリクエストURL */
	private static final String URL = "https://weather.tsukumijima.net/api/forecast?city={cityCode}";
	
	/**
	 * 気象情報取得業務ロジック
	 * @param cityCode 都市コード
	 * @return WeatherEntity
	 */
	
	public WeatherEntity getWeather(String cityCode) {
		
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, cityCode);
		
		//エンティティクラスを生成
		WeatherEntity weatherEntity = new WeatherEntity();
		
		//jsonクラスへの変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する(例外の可能性あり)
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			
			//weatherEntity.getDescription().add(node.get("description"));
			//JsonNode description = node.get("description");
			//String text = description.get("bodyText").asText();
			//weatherEntity.setDescription(text);
			
			//resultsパラメータの抽出(配列分取得する)
			for (JsonNode forecast : node.get("forecasts")) {
				//データクラスの生成(result1件分)
				WeatherData data = new WeatherData();
				//dataLabelをDataクラスへ設定
				data.setDateLabel(forecast.get("dateLabel").asText());
				//telopをDataクラスへ設定
				data.setTelop(forecast.get("telop").asText());
				//DataクラスをEneityの配列に追加
				weatherEntity.getForecasts().add(data);

			}
		}catch (IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();

		}
		return weatherEntity;
	}
}
