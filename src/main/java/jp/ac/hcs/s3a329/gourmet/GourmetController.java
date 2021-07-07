package jp.ac.hcs.s3a329.gourmet;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GourmetController {
	@Autowired
	private GourmetService gourmetService;
	/**
	 * 郵便番号から住所を検索し、結果画面を表示する
	 * @param zipcode 検索する郵便番号(ハイフン無し)
	 * @param prinicipal ログイン情報
	 * @param model
	 * @return 結果画面 - 郵便番号
	 */
	@PostMapping("/gourmet")
	public String getZipCode(@RequestParam("gourmetName")String gourmetName,
			Principal principal, Model model) {
		String large_service_area="SS40";
		
			ShopEntity shopEntity = gourmetService.getGourmet(gourmetName,large_service_area);
	
		model.addAttribute("shopEntity",shopEntity);
		return "gourmet/gourmet";
		
	}
}
