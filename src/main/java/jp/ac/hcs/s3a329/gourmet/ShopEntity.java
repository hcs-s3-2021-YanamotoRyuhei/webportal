package jp.ac.hcs.s3a329.gourmet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * キーワード検索結果の店舗情報
 * 各項目のデータ使用については、APIの使用を参照とする
 * 1つのキーワードで複数店舗が返ってくるため、リスト構造となっている
 * -http://webservice.recruit.co.jp
 *
 */
@Data
public class ShopEntity {
	/**検索ワード*/
	private String search;
	
	/**メッセージ*/
	private String message;
	
	
	/**郵便番号情報のリスト*/
	private List<ShopData> shop = new ArrayList<ShopData>();
}
