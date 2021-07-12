package jp.ac.hcs.s3a329.gourmet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * グルメ検索機能を操作する
 * ホットペッパーの店検索APIを活用する
 *-http://webservice.recruit.co.jp
 */
@Transactional
@Service
public class GourmetService {
	@Autowired
	RestTemplate restTemplate;
	
	/**郵便番号検索APIリクエストURL */
	private static final String URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key={apikey}&name={name}&large_service_area={large_service_area}&format=json";
	/**APIキー*/
	private static final String API_KEY ="d75af4121e42c5ec";
	/**
	 * 指定したキーワードに紐づく店舗情報を取得する
	 * @param name キーワード
	 * @param large_service_area エリア指定
	 * @return shopEntity
	 */
	
	public ShopEntity getGourmet(String name,String large_service_area) {
		
		
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, API_KEY, name,large_service_area);
		
		//エンティティクラスを生成
		ShopEntity shopEntity = new ShopEntity();
		shopEntity.setSearch(name);
		//jsonクラスへの変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する(例外の可能性あり)
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);
			
			//statusパラメータの抽出
			//String logo_image = node.get("status").asText();
			//shopEntity.setStatus(logo_image);

			//messageパラメータの抽出
			//String message = node.get("message").asText();
			//shopEntity.setMessage(message);
			
			//resultsパラメータの抽出(配列分取得する)
			for (JsonNode shop : node.get("results").get("shop")) {
				//データクラスの生成(result1件分)
				ShopData shopData = new ShopData();
				
				shopData.setId(shop.get("id").asText());
				shopData.setName(shop.get("name").asText());
				shopData.setLogo_image(shop.get("logo_image").asText());
				shopData.setName_kana(shop.get("name_kana").asText());
				shopData.setAddress(shop.get("address").asText());
				shopData.setAddress_url("https://www.google.com/maps/search/?api=1&query="+shop.get("address").asText());
				shopData.setAccess(shop.get("access").asText());
				shopData.setUrl(shop.get("urls").get("pc").asText());
				shopData.setImage(shop.get("photo").get("mobile").get("l").asText());
				
				//可変長配列の末尾に追加
				shopEntity.getShop().add(shopData);
			}
		}catch (IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();

		}
		return shopEntity;
	}
}
